package testings;

import static org.junit.Assert.*;


import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import service.DBConnection;
import service.DBConnectionImpl;
import services.AreaInfo;
import services.PollingStationService;

/**
 * 
 * @author Noam
 * This class test the DBConnectionImpl class. It use a mock implementation of the polling station web service which 
 * used by the class and it compare the result with a direct interaction with the datanase with do the same.
 */
public class DBConnectionImplTest {
	private static DBConnection db;
	private static Connection con;

	@Before
	public void createConnection() {
		db = new DBConnectionImpl( new MockService());
		String url = "jdbc:mysql://localhost:3306/polling_server_db";
		String user = "root";
		String pass = "noam83";
		try {
			con = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddVote() throws SQLException {
		con.createStatement().execute("delete from votes");
		con.createStatement().execute("insert into info values ('station', 'area', 'election', 2, 0)");
		con.createStatement().execute("insert into candidates values ('can1', 'election')");
		con.createStatement().execute("insert into candidates values ('can2', 'election')");
		con.createStatement().execute("insert into candidates values ('can3', 'election')");
		con.createStatement().execute("insert into candidates values ('can4', 'election')");
		
		assertTrue(db.addVote(new String[]{"can1", "can2"}));
		ResultSet result = con.createStatement().executeQuery("select * from votes");
		assertTrue(result.next());
		assertTrue(result.getString(2).equals("election"));
		assertTrue(result.getString(3).equals("area"));
		assertTrue(result.getString(4).equals("can1"));
		assertTrue(result.getString(5).equals("can2"));
		assertNull(result.getNString("vote3"));
		assertFalse(result.next());
		assertTrue(db.addVote(new String[]{"can3", "can2", "can4"}));
		result = con.createStatement().executeQuery("select * from votes");
		assertTrue(result.next());
		assertTrue(result.getString(2).equals("election"));
		assertTrue(result.getString(3).equals("area"));
		assertTrue(result.getString(4).equals("can1"));
		assertTrue(result.getString(5).equals("can2"));
		assertNull(result.getNString("vote3"));
		assertTrue(result.next());
		assertTrue(result.getString(2).equals("election"));
		assertTrue(result.getString(3).equals("area"));
		assertTrue(result.getString(4).equals("can3"));
		assertTrue(result.getString(5).equals("can2"));
		assertTrue(result.getString(6).equals("can4"));
		assertNull(result.getNString("vote4"));
		assertFalse(result.next());
		db.addVote(new String[]{"can1", "can2"});
		result = con.createStatement().executeQuery("select * from votes");
		assertTrue(result.next());
		assertTrue(result.next());
		assertTrue(result.next());
		assertTrue(result.getString(2).equals("election"));
		assertTrue(result.getString(3).equals("area"));
		assertTrue(result.getString(4).equals("can1"));
		assertTrue(result.getString(5).equals("can2"));
		assertNull(result.getNString("vote3"));
	}

	@Test
	public void testGetInfo() throws SQLException {
		con.createStatement().execute("delete from info");
		con.createStatement().execute("delete from candidates");
		con.createStatement().execute("insert into info values ('station', 'area', 'election', 3, 1)");
		con.createStatement().execute("insert into candidates values ('can1', 'election')");
		con.createStatement().execute("insert into candidates values ('can2', 'election')");
		assertTrue(db.getInfo().getArea().equals("area"));
		assertTrue(db.getInfo().getElectionName().equals("election"));
		assertTrue(db.getInfo().getStationName().equals("station"));
		assertEquals(db.getInfo().getCanNames().length,2);
		assertTrue(db.getInfo().getCanNames()[0].equals("can1"));
		assertTrue(db.getInfo().getCanNames()[1].equals("can2"));
	}

	@Test
	public void testUpdateInfo() throws SQLException {
		assertTrue(db.updateInfo("area", "name"));
		ResultSet result = con.createStatement().executeQuery("select * from info");
		assertTrue(result.next());
		assertTrue(result.getString(1).equals("name"));
		assertTrue(result.getString(2).equals("area"));
		assertTrue(result.getString(3).equals("election"));
		assertTrue(result.getInt(4) == 1);
		assertFalse(result.getBoolean(5));
		assertFalse(result.next());
		result = con.createStatement().executeQuery("select * from candidates where election_name = 'election'");
		assertTrue(result.next());
		assertTrue(result.getString(1).equals("can1"));
		assertTrue(result.next());
		assertTrue(result.getString(1).equals("can2"));
		assertFalse(result.next());
	}

	@Test
	public void testGetResult() throws SQLException {
		con.createStatement().execute("delete from votes");
		con.createStatement().execute("delete from info");
		assertNull(db.getResult("can1"));
		con.createStatement().execute("insert into info values ('station', 'arinea', 'election', 2, 0)");
		assertArrayEquals(new Integer[] { 0, 0}, db.getResult("can1"));
		con.createStatement().execute("insert into votes "
				+ "(election_name, area_name, vote1, vote2) values ('election', 'area', 'can1', 'can2')");
		assertArrayEquals(new Integer[] { 1, 0}, db.getResult("can1"));
		assertArrayEquals(new Integer[] { 0, 1}, db.getResult("can2"));
		assertArrayEquals(new Integer[] { 0, 0}, db.getResult("can3"));
		con.createStatement().execute("insert into votes"
				+ " (election_name, area_name, vote1, vote2) values ('election', 'area', 'can1', 'can3')");
		assertArrayEquals(new Integer[] { 2, 0}, db.getResult("can1"));
		assertArrayEquals(new Integer[] { 0, 1}, db.getResult("can2"));
		assertArrayEquals(new Integer[] { 0, 1}, db.getResult("can3"));
	}

	@Test
	public void testSendResults() throws SQLException, RemoteException {
		con.createStatement().execute("delete from votes");
		assertTrue(db.sendResults());
		con.createStatement().execute("insert into votes (election_name, area_name, vote1, vote2) values ('election', 'area', 'can1', 'can2')");
		con.createStatement().execute("insert into votes (election_name, area_name, vote1, vote2) values ('election', 'area', 'can1', 'can3')");
		assertTrue(db.sendResults());
	}

	@Test
	public void testCloseStation() throws SQLException {
		con.createStatement().execute("insert into votes (election_name, area_name, vote1, vote2) values ('election4', 'area4', 'can1', 'can2')");
		con.createStatement().execute("insert into info values ('station4', 'arinea', 'election4', 2, 0)");
		con.createStatement().execute("insert into candidates values ('can1', 'election4')");
		assertTrue(db.closeStation());
		ResultSet result = con.createStatement().executeQuery("select * from votes");
		assertFalse(result.next());
		result = con.createStatement().executeQuery("select * from info");
		assertFalse(result.next());
		result = con.createStatement().executeQuery("select * from candidates");
		assertFalse(result.next());
	}
	
	/**
	 * 
	 * @author Noam
	 *This class is a mock implementation of the PollingStation web service to use by the test. It always return a valid return type.
	 */
	private static class MockService implements PollingStationService {

		@Override
		public AreaInfo getAreaInfo(String area) throws RemoteException {
			return new services.AreaInfo("area", new String[]{"can1", "can2"}, "election", 1, false);
		}

		@Override
		public boolean updateResults(String id, String election, String area, String[] votes) throws RemoteException {
			if(id != null && election != null && area != null && votes != null) {
				return true;
			}
			return false;
		}
		
	}

}
