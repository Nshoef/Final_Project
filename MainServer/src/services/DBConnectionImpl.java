package services;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * This class implements the database interaction interface. It has a connection which do the connection itself.
 * @author Noam
 *
 */
public class DBConnectionImpl implements DBConnection{
	private static Connection con;
	String url = "jdbc:mysql://localhost:3306/main_server_db";
	String user = "root";
	String pass = "noam83";
	
	
	/**
	 * The constructor create the connection with the database
	 */
	public DBConnectionImpl() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, pass);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}	
	}
	
	@Override
	public AreaInfo getAreaInfo(String area) {
		try{
			ResultSet result = con.createStatement().executeQuery(
					"SELECT * FROM RUNNING_ELECTION");		
			if(!result.next()) { // this is in case there is no running elections
				return null;
			}
			String electionName = result.getString(1);
			result = con.createStatement().executeQuery("SELECT SYSTEM_NAME FROM ELECTION WHERE NAME = '"+electionName+"'");
			result.first();
			if(checkArea(area, result.getString(1))) { //make sure the area exist.
				result = con.createStatement().executeQuery(
						"SELECT NUM_OF_VOTES_PER_VOTER, IS_RANKED "
						+ "FROM AREA, ELECTION "
						+ "WHERE AREA.NAME= '"+area+"' AND ELECTION.NAME= '"+electionName+"' and AREA.SYSTEM_NAME= ELECTION.SYSTEM_NAME");
				result.next();
				int novpv = result.getInt(1);
				boolean isRanked = result.getBoolean(2);
				ResultSet can = con.createStatement().executeQuery(
						"SELECT CANDIDATE_NAME FROM CANDIDATES WHERE AREA_NAME='"+area+"' AND ELECTION_NAME='"+electionName+"'");
				can.last();
				String[] cans = new String[can.getRow()];
				can.first();
				for(int i=0; i<cans.length; i++) {
					cans[i] = can.getString(1);
					can.next();	
				}
				return new AreaInfo(electionName, area, cans, novpv, isRanked);	
			}	
		}  catch (SQLException e) {
			return null;
		}
		return null;
	}
	
	@Override
	public boolean addVote(String id, String election, String area, String[] votes) {
		try{
			ResultSet check = con.createStatement().executeQuery(
					"SELECT * FROM VOTES WHERE STATION_ID = '"+id+"' AND ELECTION_NAME = '"+election+"'");
			if(check.next()) { //check if a vote with this id is already in the database.
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
	
	@Override
	public boolean setNewElectingSystem(String name, String[] areas, int[] novpvs, boolean[] areRanked) {
		for(String s1 : areas) {  //make sure there are no duplications in areas
			int match = 0;
			for(String s2 : areas) {
				if(s1.equals(s2)) {
					match++;
				}
			}
			if(match>1) {
				return false;
			}
		}
		if(areas.length != novpvs.length) { //make sure there is information for all areas
			return false;
		}
		if(areas.length != areRanked.length) { //make sure there is information for all areas
			return false;
		}
		int numOfArea = areas.length;
		if (setNewElectingSystem(name, numOfArea)) { //update the system's table, continue only if updated
			for(int i = 0; i<numOfArea; i++) { 
				String areaName = areas[i];
				int novpv = novpvs[i];
				boolean isRanked = areRanked[i];
				if (!setNewArea(areaName, name, novpv, isRanked)) { //update each area, continue only if updated
					removeSystem(name); //remove everything if there was a failure.
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * This method is a helper method which sets a new election system in the database
	 * @param name is the name of the electing system.
	 * @param numOfAreas is the number of areas in this system.
	 * @return true if the system successfully updated in the database.
	 */
	private boolean setNewElectingSystem(String name, int numOfAreas) {
		try {
			if(!checkSystem(name)) { // make sure a system with the same name does not exist.
				con.createStatement().execute("INSERT INTO ELECTING_SYSTEM VALUES ('"+name+"', '"+numOfAreas+"')");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * This is a helper method which sets a new area on a given system
	 * @param name is the name of the area.
	 * @param system is the voting system which this area is a part of.
	 * @param novpv is the number of votes which each voter has to choose.
	 * @param isRanked is a boolean which state if the the votes of each voter should be in order or not.
	 * @return true if the new area was successfully updated in the database.
	 */
	private boolean setNewArea(String name, String system, int novpv, boolean isRanked) {
		try {
			if( checkSystem(system) && !checkArea(name, system)) { //make sure the system and the area exist in the system
				con.createStatement().execute("INSERT INTO AREA VALUES ('"+name+"', '"+system+"', '"+novpv+"', '"+(isRanked? 1 :0)+"')");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();	
		}
		return false;
	}
	
	@Override
	public String[] getSavedElectionsNames() {
		try {
			ResultSet result = con.createStatement().executeQuery("SELECT NAME FROM ELECTION");
			if(result.last()) {
				String[] elections = new String[result.getRow()];
				result.first();
				for(int i=0; i<elections.length; i++) {
					elections[i] = result.getString(1);
					result.next();
				}
				return elections;
			}
			return null;
		} catch (SQLException e) {
			return null;
		}
	}
	
	@Override
	public String[] getSavedSystems() {
		try {
			ResultSet result = con.createStatement().executeQuery("SELECT NAME FROM ELECTING_SYSTEM");
			if(result.last()) {
				String[] sys = new String[result.getRow()];
				result.first();
				for(int i=0; i<sys.length; i++) {
					sys[i] = result.getString(1);
					result.next();
				}
				return sys;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String[] getLastElectionCans(String area, String system) {	
		try {
			if(checkSystem(system) && checkArea(area, system)) { //check if the system and area exist in the system
				ResultSet result = con.createStatement().executeQuery("SELECT NAME FROM ELECTION WHERE SYSTEM_NAME = '"+system+"'");
				if(result.last()) {
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
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean setCandidates(String[] cans, String electionName, String area) {
		try {
			if (checkElection(electionName) && checkArea(area, getSystem(electionName))) { //check if the election and area exist
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
	
	@Override
	public boolean setNewElection(String name, String electingSystem) {
		try {
			if (checkSystem(electingSystem)) { //check that system exist
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
	
	@Override
	public Area[] getSystemAreas(String name) {
		try {
			if(checkSystem(name)) { //check if the system exist
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
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
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
	
	@Override
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
	
	@Override
	public boolean endRunningElection() {
		try {
			con.createStatement().execute("DELETE FROM RUNNING_ELECTION");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
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
	
	@Override
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
	
	@Override
	public String[] getCans(String election, String area) {
		try {
			if(checkArea(area, getSystem(election)) && checkElection(election)) { //check if the election and area exist
				ResultSet result = con.createStatement().executeQuery(
						"SELECT CANDIDATE_NAME FROM CANDIDATES WHERE AREA_NAME = '"+area+"' AND ELECTION_NAME = '"+election+"'");
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
	
	@Override
	public boolean removeElection(String name) {
		try {
			ResultSet result = con.createStatement().executeQuery("SELECT * FROM VOTE WHERE ELECTION_NAME = '"+name+"'");
			if(!result.next()) {
				con.createStatement().executeQuery("DELETE FROM ELECTION WHERE NAME = '"+name+"'");
				con.createStatement().executeQuery("DELETE FROM CANDIDATES WHERE SYSTEM_NAME = '"+name+"'");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean removeSystem(String name) {
		try {
			ResultSet result = con.createStatement().executeQuery("SELECT * FROM ELECTION WHERE SYSTEM_NAME = '"+name+"'");
			if(!result.next()) {
				con.createStatement().executeQuery("DELETE FROM ELECTING_SYSTEM WHERE NAME = '"+name+"'");
				con.createStatement().executeQuery("DELETE FROM AREA WHERE SYSTEM_NAME = '"+name+"'");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;	
	}
	
	/**
	 * This is a helper method that check if the given area exist on the given system
	 * @param area is the name of the area.
	 * @param system is the name of the system.
	 * @return true if this area exist in this system.
	 */
	private boolean checkArea(String area, String system) {
		if(getSystemAreas(system) != null) {
			for(Area a : getSystemAreas(system)) {
				if(a.getName().equals(area)){
					return true;
				}
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
		if(getSavedSystems() != null) {
			for(String s : getSavedSystems()) {
				if(s.equals(system)) {
					return true;
				}
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
		if(getSavedElectionsNames() != null) {
			for(String s : getSavedElectionsNames()) {
				if(s.equals(election)) {
					return true;
				}
			}
		}
		return false;	
	}
}
