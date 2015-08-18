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
		} catch (ClassNotFoundException | SQLException e) {
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
			ResultSet result = con.createStatement().executeQuery("SELECT RUNNING_ELECTION FROM RUNNING_ELECTION");
			result.next();
			electionName = result.getString(1);
			result = con.createStatement().executeQuery(
					"SELECT NUM_OF_VOTES_PER_VOTER, IS_RANKED "
					+ "FROM AREA, ELECTION "
					+ "WHERE AREA.NAME= '"+area+"' AND ELECTION_NAME= '"+electionName+"' and AREA.SYSTEM_NAME= ELECTION_SYSTEM_NAME");
			result.next();
			novpv = result.getInt(1);
			isRanked = result.getBoolean(2);
			ResultSet can = con.createStatement().executeQuery(
					"SELECT CANDIDATE_NAME FROM CANDIDATES WHERE AREA_NAME='"+area+"' AND ELECTION_NAME='"+electionName+"'");
			can.last();
			cans = new String[can.getRow()];
			can.first();
			for(String s : cans) {
				s = can.getString(1);
				can.next();	
			}
			return new AreaInfo(electionName, area, cans, novpv, isRanked);
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
	public boolean updateResults(String statment) {
		try{
			con.createStatement().execute(statment);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean setNewElectingSystm(String name, int numOfAreas) {
		try {
			con.createStatement().execute("INSERT INTO ELECTING_SYSTEM VALUES ('"+name+"', '"+numOfAreas+"')");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean setNewArea(String name, String system, int novpv, boolean isRanked) {
		try {
			con.createStatement().execute("INSERT INTO AREA VALUES ('"+name+"', '"+system+"', '"+novpv+"', '"+isRanked+"'");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;	
	}
	
	
	public String[] getLastElectionCans(String area) {	
		try {
			ResultSet result = con.createStatement().executeQuery("SELECT ELECTION_NAME FROM CANDIDATES WHERE AREA = '"+area+"'");
			result.last();
			String lastElectionName = result.getString(1);
			result = con.createStatement().executeQuery(
					"SELECT CANDIDATE_NAME FROM CANDIDATES WHERE AREA = '"+area+"' AND ELECTION_NAME = '"+lastElectionName+"'");
			result.last();
			String[] cans = new String[result.getRow()];
			result.first();
			for(String s : cans) {
				s = result.getString(1);
				result.next();	
			}
			return cans;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean setCandidates(String[] cans, String electionName, String area) {
		try {
			for(String s : cans) {
				con.createStatement().execute("INSERT INTO CANDIDATES VALUES ('"+s+"', "+electionName+"', '"+area+"')");
			}
			return true;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}	
		return false;
	}
	public boolean setNewElection(String name, String electingSystem) {
		try {
			con.createStatement().execute("INSERT INTO ELECTION VALUES ('"+name+"', '"+electingSystem+"')");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Area[] getSystemAreas(String name) {
		try {
			ResultSet result = con.createStatement().executeQuery(
					"SELECT NAME, NUM_OF_VOTES_PER_VOTER, IS_RANKED FROM AREAS WHERE SYSTEM_NAME = '"+name+"')");
			result.last();
			Area[] areas = new Area[result.getRow()];
			result.first();
			for(Area a : areas) {
				a = new Area(result.getString(1), name, result.getInt(2), result.getBoolean(3));
			}
			return areas;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public boolean setCurrentElection(String electionName) {
		try {
			ResultSet result = con.createStatement().executeQuery("SELECT NAME FROM RUNNING_ELECTION");
			result.next();
			if(result.getString(1) == null) {
				return false;
			}
			con.createStatement().execute("INSERT INTO RUNNING_ELECTION VALUES ('"+electionName+"'");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	public boolean endRunningElection() {
		try {
			con.createStatement().execute("DELETE FROM RUNNING_ELECTION");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public AreaResult[] getResult(String election) {
		return new AreaResult[0];
	}

}
