package service;


import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import services.PollingStationService;

/**
 * 
 * @author Noam
 * This class implements the database interaction interface. It has a connection which make the connection with the database and a
 * pollingStationService which make call for the main database station web service.
 */
public class DBConnectionImpl implements DBConnection {
	private static Connection con;
	private static PollingStationService proxy;
	String url = "jdbc:mysql://localhost:3306/polling_server_db";
	String user = "root";
	String pass = "noam83";
	
	/**
	 * The constructor initialised the database connection and the main database connection.
	 * @param proxy is the main database  web service.
	 */
	public DBConnectionImpl(PollingStationService proxy) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, pass);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}
		DBConnectionImpl.proxy = proxy;
	}
	
	@Override
	public boolean addVote(String[] cans) {
		try {
			AreaInfo info = getInfo();
			String area = info.getArea();
			String election = info.getElectionName();
			String query = "INSERT INTO VOTES (ELECTION_NAME, AREA_NAME";
			for(int i=1; i<=cans.length; i++) {
				query = query+", VOTE"+i;
			}
			query = query+") VALUES ('"+election+"', '"+area;
			for(String s : cans) {
				query = query+"', '"+s;
			}
			query = query+"')";
			con.createStatement().execute(query);
			return true;	
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	
	}
	
	@Override
	public AreaInfo getInfo() {
		try {
			ResultSet info = con.createStatement().executeQuery(
					"SELECT STATION_NAME, AREA_NAME, ELECTION_NAME, NUM_OF_VOTES_PER_VOTER, IS_RANKED FROM INFO");
			info.next();
			String stationName = info.getString(1);
			String area = info.getString(2);
			String electionName = info.getString(3);
			
			int novpv = info.getInt(4);
			boolean isRanked = info.getBoolean(5);
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
	
	@Override
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
	
	@Override
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
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public boolean sendResults() throws RemoteException {
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
				if(!proxy.updateResults(station_id, election, area, votes)) {
					return false;
				}
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	
	}
		
	@Override
	public boolean closeStation() {
		try {
			if (sendResults()) {
				con.createStatement().execute("DELETE FROM VOTES");
				con.createStatement().execute("DELETE FROM INFO");
				con.createStatement().execute("DELETE FROM CANDIDATES");
				return true;
			}
			return false;
		} catch (SQLException | RemoteException e) {
			e.printStackTrace();
			return  false;
		}
	}
}