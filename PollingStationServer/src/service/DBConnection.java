package service;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import services.PollingStationServiceProxy;

/**
 * 
 * @author Noam
 * This class is use to interact with the database, it has methods that serve both the manager and the desk web services.
 */
public class DBConnection {
	private static Connection con;
	private static final String url = "jdbc:mysql://localhost:3306/polling_server_db";
	private static final String user = "root";
	private static final String pass = "noam83";
	
	private static PollingStationServiceProxy proxy;
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, pass);
			proxy = new PollingStationServiceProxy();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method add vote to the database according to the given query.
	 * @param query is an sql query with the relevant information.
	 * @return true if the execution has done successful.
	 */
	public boolean addVote(String query) {
		try {
			con.createStatement().execute(query);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	/**
	 * This method retrieve the relevant information form the database and return it as an Object
	 * @return an AreaInfo object contains the relevant information about the election.
	 */
	public AreaInfo getInfo() {
		try {
			ResultSet info = con.createStatement().executeQuery(
					"SELECT STATION_NAME, AREA_NAME, ELECTION_NAME, NUM_OF_VOTES_PER_VOTER, IS_RANKED FROM INFO");
			info.next();
			String stationName = info.getString(1);
			String area = info.getString(2);
			String electionName = info.getString(3);
			
			int novpv = info.getInt(4);
			boolean isRanked = info.getBoolean(1);
			ResultSet can = con.createStatement().executeQuery(
					"SELECT CANDIDATE_NAME FROM CANDIDATES WHERE ELECTION_NAME = '"+electionName+"'");
			can.last();
			String[] cans = new String[can.getRow()];
			can.first();
			for(int i=0; i<cans.length; i++) {
				cans[i] = can.getString(1);
				can.next();
			}
			return new AreaInfo(stationName, electionName, area, cans, novpv, isRanked);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	/**
	 * This method use to update the database with the relevant information for this station.
	 * @param area is the name of the area of this polling station.
	 * @param name is the name of the polling station.
	 * @return true if the database was updated successfully.
	 * @throws RemoteException
	 */
	public boolean updateInfo(String area, String name) {
		try {
			services.AreaInfo info = proxy.getAreaInfo(area);
			if(info == null) {
				return false;
			}
			String election = info.getElectionName();
			String[] cans = info.getCanNames();
			int novpv = info.getNumOfVotePerVoter();
			int isRanked = info.isRanked()? 1 : 0;
			String sql = "INSERT INTO INFO VALUES ('"+name+"', '"+area+"','"+election+"', '"+novpv+"', '"+isRanked+"')";
			con.createStatement().execute("DELETE FROM INFO"); //delete any previous info
			con.createStatement().execute("DELETE FROM CANDIDATES"); //delete any previous candidates
			con.createStatement().execute(sql);
			for(String s : cans) {
				con.createStatement().execute("INSERT INTO CANDIDATES VALUES('"+s+"', '"+election+"')"); 
			}
			return true;
		} catch (SQLException | RemoteException e) {
			return false;
		}
	}
	
	/**
	 * This method return the result of the given candidate. 
	 * The length of the array depend on the number of votes per voter so that it possible to deal with ranked system.
	 * @param can the candidate whom the result are refers to.
	 * @return array of number of votes
	 */
	public Integer[] getResult(String can) {
		try {
			Integer[] result = new Integer[getInfo().getNumOfVotePerVoter()];
			
			for(int i=1; i<=result.length; i++) {
				ResultSet r = con.createStatement().executeQuery(
						"SELECT COUNT(VOTE"+i+") FROM VOTES WHERE VOTE"+i+" = '"+can+"'");	
				r.next();
				result[i-1] = r.getInt(1);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	/**
	 * This method send all the votes from the local database to the main database
	 * @return
	 * @throws RemoteException
	 */
	public boolean sendresults() throws RemoteException {
		try {
			ResultSet result = con.createStatement().executeQuery("SELECT * FROM VOTES");
			while(result.next()) {
				String  station_id = result.getInt(1)+getInfo().getStationName();
				String election = result.getString(2);
				String area = result.getString(3);
				String[] votes = new String[getInfo().getNumOfVotePerVoter()];
				for(int i=0; i<getInfo().getNumOfVotePerVoter(); i++) {
					votes[i] = result.getString(i+4);
				}
				proxy.updateResults(station_id, election, area, votes);
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	
	}
		
		/**
		 * This method remove all the information from the database.
		 */
	public boolean closeStation() {
		try {
			con.createStatement().execute("DELETE FROM VOTE, INFO, CANDIDATES");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return  false;
		}
		
	}
	
}