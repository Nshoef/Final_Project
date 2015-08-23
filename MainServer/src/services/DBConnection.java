package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is use to interact with the database, it has methods that serve both the main manager and the polling station web services.
 * @author Noam
 *
 */
public class DBConnection {
	private static Connection con;
	private static final String url = "jdbc:mysql://localhost:3306/main_server_db";
	private static final String user = "root";
	private static final String pass = "noam83";
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, pass);
		} catch ( SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method return the relevant information to the given area as an AreaInfo object
	 * @param area is the name of the area to be given information about.
	 * @return the an AreaInfo object containing the relevant information.
	 */
	public AreaInfo getAreaInfo(String area) {
		String electionName;
		String[] cans;
		int novpv;
		boolean isRanked;
		try {
			ResultSet result = con.createStatement().executeQuery(
					"SELECT * FROM AREA, ELECTION, RUNNING_ELECTION "
					+ "WHERE AREA.SYSTEM_NAME = ELECTION.SYSTEM_NAME AND ELECTION.NAME = RUNNING_ELECTION.NAME");
			if(!result.next()) {
				return null;
			}
			result = con.createStatement().executeQuery("SELECT NAME FROM RUNNING_ELECTION");
			if(!result.next()) {
				return null;
			}
			electionName = result.getString(1);
			result = con.createStatement().executeQuery("SELECT SYSTEM_NAME FROM ELECTION WHERE NAME = '"+electionName+"'");
			result.first();
			System.out.println(area);
			if(checkArea(area, result.getString(1))) { //make sure the area exist.
				result = con.createStatement().executeQuery(
						"SELECT NUM_OF_VOTES_PER_VOTER, IS_RANKED "
						+ "FROM AREA, ELECTION "
						+ "WHERE AREA.NAME= '"+area+"' AND ELECTION.NAME= '"+electionName+"' and AREA.SYSTEM_NAME= ELECTION.SYSTEM_NAME");
				result.next();
				novpv = result.getInt(1);
				isRanked = result.getBoolean(2);
				ResultSet can = con.createStatement().executeQuery(
						"SELECT CANDIDATE_NAME FROM CANDIDATES WHERE AREA_NAME='"+area+"' AND ELECTION_NAME='"+electionName+"'");
				can.last();
				cans = new String[can.getRow()];
				can.first();
				for(int i=0; i<cans.length; i++) {
					cans[i] = can.getString(1);
					can.next();	
				}
				return new AreaInfo(electionName, area, cans, novpv, isRanked);	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * This method add a vote to the database by executing a statement with the information of the vote
	 * @param statment is an sql statement containing the relevant information to be added to the database.
	 * @return true is the vote was successfully entered to the database.
	 */
	public boolean addVote(String id, String election, String area, String[] votes) {
		try{
			ResultSet check = con.createStatement().executeQuery("SELECT * FROM VOTES WHERE STATION_ID = '"+id+"'");
			if(check.next()) {
				return true;
			}
			String query = "INSERT INTO VOTES (STATION_ID, ELECTION_NAME, AREA_NAME";
			for(int i=1; i<=votes.length; i++) {
				query = query+", VOTE"+i;
			}
			query = query+") VALUES"
					+ "('"+id+"', '"+election+"', '"+area;
			for(int i=0; i<votes.length; i++) {
				query = query+"', '"+ votes[i];
			}
			query = query+"')";
			con.createStatement().execute(query);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * This method set a new electing system on the database(not include the setting of the particular area).
	 * @param name is the name of the new system.
	 * @param numOfAreas is the number of areas in the new system.
	 * @return true if the new system was successfully updated on the database.
	 */
	public boolean setNewElectingSystm(String name, int numOfAreas) {
		try {
			con.createStatement().execute("INSERT INTO ELECTING_SYSTEM VALUES ('"+name+"', '"+numOfAreas+"')");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * This method return the names of the saved elections.
	 * @return an array of string with the names of the saved elections.
	 */
	public String[] getSavedElectionsNames() {
		try {
			ResultSet result = con.createStatement().executeQuery("SELECT NAME FROM ELECTION");
			result.last();
			String[] elections = new String[result.getRow()];
			result.first();
			for(int i=0; i<elections.length; i++) {
				elections[i] = result.getString(1);
				result.next();
			}
			return elections;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This method set a new area on a given system
	 * @param name is the name of the area.
	 * @param system is the voting system which this area is a part of.
	 * @param novpv is the number of votes which each voter has to choose.
	 * @param isRanked is a boolean which state if the the votes of each voter should be in order or not.
	 * @return true if the new area was successfully updated in the database.
	 */
	public boolean setNewArea(String name, String system, int novpv, boolean isRanked) {
		try {
			if( checkSystem(system) && !checkArea(name, system)) {
				con.createStatement().execute("INSERT INTO AREA VALUES ('"+name+"', '"+system+"', '"+novpv+"', '"+(isRanked? 1 :0)+"')");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();	
		}
		return false;
	}
	
	/**
	 * This method return the names of the systems which are saved on the database.
	 * @return a array of string with the name of the saved systems or empty array if there are non.
	 */
	public String[] getSavedSystems() {
		try {
			ResultSet result = con.createStatement().executeQuery("SELECT NAME FROM ELECTING_SYSTEM");
			result.last();
			String[] sys = new String[result.getRow()];
			result.first();
			for(int i=0; i<sys.length; i++) {
				sys[i] = result.getString(1);
				result.next();
			}
			return sys;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This method return a list of the candidates who run in this area on the last election.
	 * @param area is the name of the area.
	 * @return an array of string with the name of the candidates or an empty array if there wasn't any previous election.
	 */
	public String[] getLastElectionCans(String area, String system) {	
		try {
			if(checkSystem(system) && checkArea(area, system)) {
				ResultSet result = con.createStatement().executeQuery("SELECT NAME FROM ELECTION WHERE SYSTEM_NAME = '"+system+"'");
				if(!result.last()) {
					return new String[0]; // return empty array if there is no previous election
				} 
				String lastElectionName = result.getString(1);
				result = con.createStatement().executeQuery(
						"SELECT CANDIDATE_NAME FROM CANDIDATES WHERE AREA_NAME = '"+area+"' AND ELECTION_NAME = '"+lastElectionName+"'");
				result.last();
				String[] cans = new String[result.getRow()];
				result.first();
				for(int i=0; i<cans.length; i++) {
					cans[i] = result.getString(1);
					result.next();	
				}
				return cans;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * This method set candidates who run on a given election and area.
	 * @param cans is the names of the candidates.
	 * @param electionName is the election these candidates run for.
	 * @param area is the area they run on.
	 * @return true if the database was successfully updated.
	 */
	public boolean setCandidates(String[] cans, String electionName, String area) {
		try {
			if (checkElection(electionName)) {
				for(String s : cans) {
					con.createStatement().execute("INSERT INTO CANDIDATES VALUES ('"+s+"', '"+electionName+"', '"+area+"')");
				}
				return true;	
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}	
		return false;
	}
	
	/**
	 * This method set a new election on a given electing system.
	 * @param name is the name of the new election.
	 * @param electingSystem is the system which is used in this election.
	 * @return true if the new election was successfully updated on the database.
	 */
	public boolean setNewElection(String name, String electingSystem) {
		try {
			if (checkSystem(electingSystem)) { //check that system exsist
				if(!checkElection(name)) { // check for no duplicate names
					con.createStatement().execute("INSERT INTO ELECTION VALUES ('"+name+"', '"+electingSystem+"')");
					return true;
				}	
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * This method returns all the information about a given voting system inside an area objects.
	 * @param name is the name of the voting system which the areas belong to.
	 * @return an array of area object where each object contain the information of one area in this system.
	 */
	public Area[] getSystemAreas(String name) {
		try {
			ResultSet result = con.createStatement().executeQuery(
					"SELECT NAME, NUM_OF_VOTES_PER_VOTER, IS_RANKED FROM AREA WHERE SYSTEM_NAME = '"+name+"'");
			result.last();
			Area[] areas = new Area[result.getRow()];
			result.first();
			for(int i=0; i<areas.length; i++) {
				areas[i] = new Area(result.getString(1), name, result.getInt(2), result.getBoolean(3));
				result.next();
			}
			return areas;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * This method begin a voting process on a given electing system.
	 * @param electionName is the name of the election.
	 * @return true if the database was updated successfully, false if not or if there is already an election in process.
	 */
	public boolean setCurrentElection(String electionName) {
		try {
			if(getRunningElection() == null && checkElection(electionName)) {
				con.createStatement().execute("INSERT INTO RUNNING_ELECTION VALUES ('"+electionName+"')");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;	
	}
	
	/**
	 * This method return the current running election or null if there is no running election.
	 * @return the current running election or null if there is no running election.
	 */
	public String getRunningElection() {
		try {
			ResultSet result = con.createStatement().executeQuery("SELECT NAME FROM RUNNING_ELECTION");
			if(result.next()) {
				return result.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * This method stop a voting process. polling station won't be able to get any updates.
	 * @return
	 */
	public boolean endRunningElection() {
		try {
			con.createStatement().execute("DELETE FROM RUNNING_ELECTION");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * This method return the number of vote for a given candidate on a given, voting number, area, and election.
	 * @param election the number of vote for a given candidate on a given, voting number, area, and election.
	 * @return
	 */
	public Integer getResult(String election, String area, String can, int voteNum) {
		try {
			ResultSet result = con.createStatement().executeQuery(
							"SELECT COUNT(VOTE"+voteNum+") FROM VOTES "
							+ "WHERE ELECTION_NAME= '"+election+"' AND AREA_NAME= '"+area+"' AND VOTE"+voteNum+"= '"+can+"'");
			result.next();
			return result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	/**
	 * This method return the name of the electing system that is used in the given election.
	 * @param election is the name of the election to be checked.
	 * @return a string with the name of the electing system that is used in the given election.
	 */
	public String getSystem(String election) {
		try {
			ResultSet result= con.createStatement().executeQuery("SELECT SYSTEM_NAME FROM ELECTION WHERE NAME = '"+election+"'");
			if(result.next()) {
				return result.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * This method return the names of the candidates who run in the given election and area.
	 * @param election is the name of the election.
	 * @param area is the name of the area.
	 * @return an array of string with the names of the candidates who run in the given election and area.
	 */
	public String[] getCans(String election, String area) {
		try {
			ResultSet result= con.createStatement().executeQuery(
					"SELECT CANDIDATE_NAME FROM CANDIDATES WHERE AREA_NAME = '"+area+"' AND ELECTION_NAME = '"+election+"'");
			result.last();
			String[] cans = new String[result.getRow()];
			result.first();
			for(int i=0; i<cans.length; i++) {
				cans[i] = result.getString(1);
				result.next();
			}
			return cans;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * This is a helper method that check if the given area exist on the given system
	 * @param area is the name of the area.
	 * @param system is the name of the system.
	 * @return true if this area exist in this system.
	 */
	private boolean checkArea(String area, String system) {
		for(Area a : getSystemAreas(system)) {
			if(a.getName().equals(area)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This is a helper method that check if the given system exist in the database.
	 * @param system the name of the system.
	 * @return true if the system exist.
	 */
	private boolean checkSystem(String system) {
		for(String s : getSavedSystems()) {
			if(s.equals(system)) {
				return true;
			}
		}
		return false;	
	}
	
	/**
	 * This is a helper method that check if the given election exist in the database.
	 * @param election is the name of the election to be checked.
	 * @return true if this election exist in the database.
	 */
	private boolean checkElection(String election) {
		for(String s : getSavedElectionsNames()) {
			if(s.equals(election)) {
				return true;
			}
		}
		return false;	
	}
}
