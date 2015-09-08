package integrationTest;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import desk.DeskManager;
import desk.DeskManagerImpl;
import manager.Manager;
import manager.ManagerImpl;
import manager.StationManager;
import manager.StationManagerImpl;
import service.DeskServiceProxy;
import services.ManagerServiceProxy;

/**
 * 
 * @author Noam
 * This is an integration test class testing the respond of the system to an incorrect use.
 */
public class WrongPathTest {
	

	@Before
	public void clearDB() {
		String url1 = "jdbc:mysql://localhost:3306/main_server_db";
		String url2 = "jdbc:mysql://localhost:3306/polling_server_db";
		String user = "root";
		String pass = "noam83";
		try {
			Connection con = DriverManager.getConnection(url1, user, pass);
			con.createStatement().execute("delete from area");
			con.createStatement().execute("delete from candidates");
			con.createStatement().execute("delete from electing_system");
			con.createStatement().execute("delete from election");
			con.createStatement().execute("delete from running_election");
			con.createStatement().execute("delete from votes");
			con = DriverManager.getConnection(url2, user, pass);
			con.createStatement().execute("delete from info");
			con.createStatement().execute("delete from candidates");
			con.createStatement().execute("delete from votes");
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test() throws RemoteException {
		try {
			DeskManager desk1 = new DeskManagerImpl(1, new DeskServiceProxy()); // try to open desk without station
			assertTrue(false);
		}  catch (RemoteException e) {
			assertTrue(true);
		}
		StationManager station1 = new StationManagerImpl(new service.ManagerServiceProxy()); //try to use station without open it first
		assertNull(station1.getInfo());
		station1 = new StationManagerImpl("station1", "south", new service.ManagerServiceProxy()); //try to open station without election
		assertFalse(station1.updateInfo());
		Manager mainManager = new ManagerImpl(new ManagerServiceProxy());
		assertTrue(mainManager.getResult("election", "area", "can", 1) == 0); //try to get result of non existing parameters
		assertFalse(mainManager.setCurrentElection("name")); // try to start election of non existing elections
		String[] cans = {"can1", "can2"};
		assertFalse(mainManager.createNewElection("name", "system", new String[]{"area"}, new String[][]{cans})); // try to create election with non existing system
		assertNull(mainManager.getLastElectionCans("area", "system")); // try to get candidates on a non existing area/system
		assertNull(mainManager.getCans("election", "area")); // try to get candidate of a non existing area/election
		assertNull(mainManager.getSavedElectionsNames());
		assertNull(mainManager.getSavedSystems());
		
		assertTrue(mainManager.createNewSystem("system", new String[]{"a1", "a2"}, new int[]{1,2}, new boolean[]{ false, false})); // creating a system
		assertFalse(mainManager.createNewSystem("system", new String[]{"a1", "a2"}, new int[]{1,2}, new boolean[]{ false, false})); // try to create a system with the same name
		assertFalse(mainManager.createNewSystem("system2", new String[]{"a3", "a3"}, new int[]{1,2}, new boolean[]{ false, false})); // try to create system with 2 areas with the same name
		assertTrue(mainManager.createNewSystem("system2", new String[]{"a3", "a4"}, new int[]{1,2}, new boolean[]{ false, false}));
		assertTrue(mainManager.getSavedSystems().length == 2);
		assertTrue(mainManager.getSystemAreas("system").length == 2);
		assertNull(mainManager.getLastElectionCans("a1", "system"));
		station1 = new StationManagerImpl("station1", "south", new service.ManagerServiceProxy()); //try to open station without election
		assertFalse(station1.updateInfo());
		
		String[] a1Can = { "1a", "1b", "1c"};
		String[] a2Can = {"2a", "2b", "2c"};
		assertFalse(mainManager.createNewElection("election", "system", new String[] { "a11", "a22"}, new String[][]{a1Can, a2Can})); // try to create an election with illegal areas
		assertTrue(mainManager.createNewElection("election", "system", new String[] { "a1", "a2"}, new String[][]{a1Can, a2Can})); // create an election
		assertFalse(mainManager.createNewElection("election", "system", new String[] { "a1", "a2"}, new String[][]{a1Can, a2Can})); // try to create election with same name
		assertTrue(mainManager.createNewElection("election2", "system", new String[] { "a1", "a2"}, new String[][]{a1Can, a2Can})); // creation of another election
		assertTrue(mainManager.createNewElection("election3", "system2", new String[] { "a3", "a4"}, new String[][]{a1Can, a2Can})); // creation of another election on a different system
		assertTrue(mainManager.getResult("election", "a1", "a1Can", 1) == 0);
		
		assertTrue(mainManager.setCurrentElection("election2")); // set election
		assertFalse(mainManager.setCurrentElection("election")); // try to set another election
		
		station1 = new StationManagerImpl("station1", "south", new service.ManagerServiceProxy()); //try to open station on a non existing area
		assertFalse(station1.updateInfo());
		station1 = new StationManagerImpl("station1", "a1", new service.ManagerServiceProxy()); //try to open station on a non existing area
		assertTrue(station1.updateInfo());
		assertTrue(station1.getInfo().getElectionName().equals("election2"));
		station1 = new StationManagerImpl(new service.ManagerServiceProxy());
		assertTrue(station1.getInfo().getElectionName().equals("election2"));
		
		DeskManager desk1 = new DeskManagerImpl(1, new DeskServiceProxy());
		assertFalse(desk1.vote(new String[]{"2a", "1a"}));
		
		assertTrue(station1.closeStation());
	
		assertTrue(station1.getResutls().get("1a").length == 0);
		assertTrue(station1.getResutls().get("1b").length == 0);
		
		assertTrue(mainManager.endRunningElection());
		assertTrue(mainManager.getResult("election2", "a1", "1a", 1) == 0);
	}
}
