package testings;

import static org.junit.Assert.*;


import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;


import services.DBConnection;
import services.DBConnectionImpl;

/**
 * 
 * @author Noam
 * This calss test the DBConnectionImpl class. It compare it's results with a direct connection with the database as there
 * is no way to isolate it.
 */
public class DBConnectionTest {
	private static DBConnection db;
	private static Connection con;

	
	@Before
	public void createConnection() throws FileNotFoundException {
		db = new DBConnectionImpl();
		String url = "jdbc:mysql://localhost:3306/main_server_db";
		String user = "root";
		String pass = "noam83";
		try {
			con = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAreaInfo() throws SQLException {
		con.createStatement().execute("delete from running_election");
		con.createStatement().execute("delete from election");
		con.createStatement().execute("delete from area");
		con.createStatement().execute("delete from candidates");
		assertNull(db.getAreaInfo("area"));
		con.createStatement().execute("insert into election values ('election', 'system')");
		assertNull(db.getAreaInfo("area"));
		con.createStatement().execute("insert into area values ('area', 'system', 1, 1)");
		assertNull(db.getAreaInfo("area"));
		con.createStatement().execute("insert into candidates values ('can', 'election', 'area')");
		assertNull(db.getAreaInfo("area"));
		con.createStatement().execute("insert into running_election values ('election')");
		assertNotNull(db.getAreaInfo("area"));
	}

	@Test
	public void testAddVote() throws SQLException {
		con.createStatement().execute("delete from votes");
		con.createStatement().execute("insert into votes(station_id, election_name, area_name, vote1, vote2) values ('id1', 'election', 'area', 'vote1', 'vote2')");
		assertTrue(db.addVote("id1", "election", "area", new String[] {"vote1", "vote2"}));
		assertTrue(db.addVote("id2", "election", "area", new String[] {"vote1"}));
	}

	@Test
	public void testSetNewElectingSystm() throws SQLException {
		con.createStatement().execute("delete from electing_system");
		con.createStatement().execute("delete from area");
		String[] areas = {"a1", "a2"};
		int[] novpvs = {1,2};
		boolean[] areRanked = {false, true};
		assertTrue(db.setNewElectingSystem("name", areas, novpvs, areRanked));
		assertFalse(db.setNewElectingSystem("name", areas, novpvs, areRanked));
		ResultSet result = con.createStatement().executeQuery("select * from electing_system");
		assertTrue(result.next());
		assertTrue(result.getString(1).equals("name"));
		assertEquals(result.getInt(2), 2);
		assertFalse(result.next());
		result = con.createStatement().executeQuery("select * from area");
		assertTrue(result.next());
		assertTrue(result.getString(1).equals("a1"));
		assertTrue(result.getString(2).equals("name"));
		assertTrue(result.getInt(3) == 1);
		assertFalse(result.getBoolean(4));
		assertTrue(result.next());
		assertTrue(result.getString(1).equals("a2"));
		assertTrue(result.getString(2).equals("name"));
		assertTrue(result.getInt(3) == 2);
		assertTrue(result.getBoolean(4));
		assertFalse(result.next());
		
	}

	@Test
	public void testGetSavedElectionsNames() throws SQLException {
		con.createStatement().execute("delete from election");
		assertNull(db.getSavedElectionsNames());
		String name1 = "election1";
		con.createStatement().execute("insert into election values ('"+name1+"', 'systm')");
		assertEquals(db.getSavedElectionsNames().length, 1);
		assertTrue(db.getSavedElectionsNames()[0].equals(name1));
		String name2 = "election2";
		con.createStatement().execute("insert into election values ('"+name2+"', 'systm')");
		assertEquals(db.getSavedElectionsNames().length,2);
		assertTrue(db.getSavedElectionsNames()[0].equals(name1));
		assertTrue(db.getSavedElectionsNames()[1].equals(name2));
	}

	@Test
	public void testGetSavedSystems() throws SQLException {
		con.createStatement().execute("delete from electing_system");
		assertNull(db.getSavedSystems());
		String name1 = "system1";
		con.createStatement().execute("insert into electing_system values ('"+name1+"', 1)");
		assertEquals(db.getSavedSystems().length, 1);
		assertTrue(db.getSavedSystems()[0].equals(name1));
		String name2 = "system2";
		con.createStatement().execute("insert into electing_system values ('"+name2+"', 1)");
		assertEquals(db.getSavedSystems().length,2);
		assertTrue(db.getSavedSystems()[0].equals(name1));
		assertTrue(db.getSavedSystems()[1].equals(name2));	
	}

	@Test
	public void testGetLastElectionCans() throws SQLException {
		con.createStatement().execute("delete from area");
		con.createStatement().execute("delete from electing_system");
		con.createStatement().execute("delete from election");
		String area = "area";
		String system = "system";
		assertNull(db.getLastElectionCans(area, system));
		con.createStatement().execute("delete from candidates");
		con.createStatement().execute("insert into electing_system values ('"+system+"', 1)");
		assertNull(db.getLastElectionCans(area, system));
		con.createStatement().execute("insert into area values('"+area+"', '"+system+"', 1, 1)");
		assertNull(db.getLastElectionCans(area, system));
		con.createStatement().execute("insert into election values ('election', 'system')");
		con.createStatement().execute("insert into candidates values ('can1', 'election', 'area')");
		assertEquals(db.getLastElectionCans(area, system).length, 1);
		assertTrue(db.getLastElectionCans(area, system)[0].equals("can1"));
	}

	@Test
	public void testSetNewElection() throws SQLException {
		con.createStatement().execute("delete from electing_system");
		con.createStatement().execute("delete from election");
		assertFalse(db.setNewElection("election", "system"));
		con.createStatement().execute("insert into electing_System values ('system', 2)");
		assertFalse(db.setNewElection("election", "electingSystem"));
		assertTrue(db.setNewElection("election", "system"));
		assertFalse(db.setNewElection("election", "system"));
		ResultSet result = con.createStatement().executeQuery("select * from election");
		assertTrue(result.next());
		assertTrue(result.getString(1).equals("election"));
		assertTrue(result.getString(2).equals("system"));
		assertFalse(result.next());
		
	}
	
	@Test
	public void testSetCandidates() throws SQLException {
		con.createStatement().execute("delete from candidates");
		con.createStatement().execute("delete from election");
		con.createStatement().execute("delete from area");
		String[] cans = {"a", "b"};
		assertFalse(db.setCandidates(cans, "election", "area"));
		con.createStatement().execute("insert into area values ('area','system', 1, 0)");
		con.createStatement().execute("insert into election values ('election', 'system')");
		assertFalse(db.setCandidates(cans, "election", "north"));
		assertFalse(db.setCandidates(cans, "somelect", "area"));
		assertTrue(db.setCandidates(cans, "election", "area"));
		ResultSet result = con.createStatement().executeQuery("select * from candidates");
		assertTrue(result.next());
		result.getString(1).equals("a");
		result.getString(2).equals("election");
		result.getString(3).equals("area");
		assertTrue(result.next());
		result.getString(1).equals("b");
		result.getString(2).equals("election");
		result.getString(3).equals("area");
		assertFalse(result.next());	
	}

	@Test
	public void testGetSystemAreas() throws SQLException {
		con.createStatement().execute("delete from area");
		assertNull(db.getSystemAreas("system1"));
		con.createStatement().execute("insert into area values('area1', 'system1', 1, 0)");
		con.createStatement().execute("insert into area values('area1', 'system2', 2, 1)");
		con.createStatement().execute("insert into area values('area2', 'system2', 2, 1)");
		con.createStatement().execute("insert into electing_system values('system1', 1)");
		con.createStatement().execute("insert into electing_system values('system2', 2)");
		assertEquals(db.getSystemAreas("system1").length,1);
		assertEquals(db.getSystemAreas("system2").length,2);
		assertTrue(db.getSystemAreas("system2")[0].getName().equals("area1"));
		assertTrue(db.getSystemAreas("system2")[1].getName().equals("area2"));
	}

	@Test
	public void testSetCurrentElection() throws SQLException {
		con.createStatement().execute("delete from running_election");
		con.createStatement().execute("delete from election");
		assertFalse(db.setCurrentElection("election1"));
		con.createStatement().execute("insert into election values ('election1', 'system')");
		con.createStatement().execute("insert into election values ('election2', 'system')");
		assertTrue(db.setCurrentElection("election1"));
		assertFalse(db.setCurrentElection("election2"));	
	}

	@Test
	public void testGetRunningElection() throws SQLException {
		con.createStatement().execute("delete from running_election");
		assertNull(db.getRunningElection());
		con.createStatement().execute("insert into running_election values ('election')");
		assertTrue(db.getRunningElection().equals("election"));
	}

	@Test
	public void testEndRunningElection() throws SQLException {
		con.createStatement().execute("delete from running_election");
		assertTrue(db.endRunningElection());
		con.createStatement().execute("insert into running_election values ('election')");
		assertTrue(db.endRunningElection());
		ResultSet result = con.createStatement().executeQuery("select * from running_election");
		assertFalse(result.next());
	}

	@Test
	public void testGetResult() throws SQLException {
		con.createStatement().execute("delete from votes");
		assertTrue(db.getResult("election", "area", "can1", 1) == 0);
		con.createStatement().execute(""
				+ "insert into votes (Station_id, election_name, area_name, vote1)"
				+ " values ('id1', 'election', 'area', 'can1')");
		assertTrue(db.getResult("election", "area", "can1", 1) == 1);
		con.createStatement().execute(""
				+ "insert into votes (Station_id, election_name, area_name, vote1)"
				+ " values ('id2', 'election', 'area', 'can1')");
		assertTrue(db.getResult("election", "area", "can1", 1) == 2);
		assertTrue(db.getResult("election", "area", "can2", 1) == 0);
	}

	@Test
	public void testGetSystem() throws SQLException {
		con.createStatement().execute("delete from election");
		assertNull(db.getSystem("election"));
		con.createStatement().execute("insert into election values ('election', 'system')");
		assertTrue(db.getSystem("election").equals("system"));
		assertNull(db.getSystem("anotherElection"));
		
	}

	@Test
	public void testGetCans() throws SQLException {
		con.createStatement().execute("delete from candidates");
		con.createStatement().execute("delete from area");
		con.createStatement().execute("delete from election");
		con.createStatement().execute("delete from electing_system");
		assertNull(db.getCans("election", "area"));
		con.createStatement().execute("insert into candidates values ('can1', 'election', 'area')");
		con.createStatement().execute("insert into election values ('election', 'system')");
		con.createStatement().execute("insert into electing_system values ('system', 1)");
		con.createStatement().execute("insert into area values ('area', 'system', 1, 0)");
		System.out.println(db.getCans("election", "area").length);
		assertTrue(db.getCans("election", "area").length == 1);
		assertTrue(db.getCans("election", "area")[0].equals("can1"));
		con.createStatement().execute("insert into candidates values ('can2', 'election', 'area')");
		con.createStatement().execute("insert into candidates values ('can1', 'election', 'area2')");
		con.createStatement().execute("insert into candidates values ('can1', 'election2', 'area')");
		assertTrue(db.getCans("election", "area").length == 2);
		assertTrue(db.getCans("election", "area")[0].equals("can1"));
		assertTrue(db.getCans("election", "area")[1].equals("can2"));
	}

}
