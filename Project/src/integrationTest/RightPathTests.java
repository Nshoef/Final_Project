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
import service.AreaInfo;
import service.DeskServiceProxy;
import services.Area;
import services.ManagerServiceProxy;

/**
 * 
 * @author Noam
 *This is an integration test class testing a correct use of the system.
 */
public class RightPathTests {
	
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
	public void rightPathtest() throws RemoteException {
		Manager mainManager = new ManagerImpl(new ManagerServiceProxy());
		assertTrue(mainManager.createNewSystem("Test1", new String[]{"south", "north"}, new int[]{2,3}, new boolean[]{false, true}));
		String[] s = {"s1", "s2", "s3"};
		String[] n = {"n1", "n2", "n3", "n4", "n5"};
		String[][] cans = {s,n};
		assertNull(mainManager.getSavedElectionsNames());
		assertTrue(mainManager.createNewElection("Test1elec", "Test1", new String[]{"south", "north"}, cans));
		assertTrue(mainManager.setCurrentElection("Test1elec"));
		assertTrue(mainManager.getSavedElectionsNames().length == 1);
		assertTrue(mainManager.getSavedSystems().length == 1);
		Area[] areas = mainManager.getSystemAreas("Test1");
		assertTrue(areas[0].getName().equals("north"));
		assertTrue(areas[0].getNumOfVotesPerVoter() == 3);
		assertTrue(areas[0].getSystemName().equals("Test1"));
		assertTrue(areas[0].isRanked());
		assertTrue(areas[1].getName().equals("south"));
		
		StationManager station1 = new StationManagerImpl("station1", "south", new service.ManagerServiceProxy());
		assertTrue(station1.updateInfo());
		DeskManager desk1 = null;
		DeskManager desk2 = null;
		DeskManager desk3 = null;
		try{
			desk1 = new DeskManagerImpl(1, new DeskServiceProxy());
			desk2 = new DeskManagerImpl(2, new DeskServiceProxy());
			desk3 = new DeskManagerImpl(3, new DeskServiceProxy());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		AreaInfo info = desk1.getInfo();
		assertTrue(info.getStationName().equals("station1"));
		assertTrue(info.getElectionName().equals("Test1elec"));
		assertTrue(info.getArea().equals("south"));
		assertTrue(info.getNumOfVotePerVoter() == 2);
		assertFalse(info.isRanked());
		for(int i=0; i<info.getCanNames().length; i++) {
			assertTrue(info.getCanNames()[i].equals(s[i]));
		}
		assertTrue(desk1.vote(new String[] { "s1", "s2"}));
		assertTrue(station1.getResutls().get("s1")[0] == 1);
		assertTrue(desk2.vote(new String[] { "s1", "s3"}));
		assertTrue(desk2.vote(new String[] { "s3", "s1"}));
		assertTrue(desk2.vote(new String[] { "s1", "s3"}));
		assertTrue(desk2.vote(new String[] { "s2", "s3"}));
		assertTrue(desk3.vote(new String[]{"s2", "s1"}));
		assertTrue(station1.getResutls().get("s1")[0] == 3);
		assertTrue(station1.getResutls().get("s3")[1] == 3);
		assertTrue(station1.getResutls().get("s2")[0] == 2);
		assertTrue(station1.sendResults());
		assertTrue(station1.closeStation());
		
		StationManager station2 = new StationManagerImpl("station2", "south", new service.ManagerServiceProxy());
		assertTrue(station2.updateInfo());
		try{
			desk1 = new DeskManagerImpl(1, new DeskServiceProxy());
			desk2 = new DeskManagerImpl(2, new DeskServiceProxy());
			desk3 = new DeskManagerImpl(3, new DeskServiceProxy());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		info = desk1.getInfo();
		assertTrue(info.getStationName().equals("station2"));
		assertTrue(info.getElectionName().equals("Test1elec"));
		assertTrue(info.getArea().equals("south"));
		assertTrue(info.getNumOfVotePerVoter() == 2);
		assertFalse(info.isRanked());
		for(int i=0; i<info.getCanNames().length; i++) {
			assertTrue(info.getCanNames()[i].equals(s[i]));
		}
		assertTrue(desk1.vote(new String[] { "s1", "s2"}));
		assertTrue(station2.getResutls().get("s1")[0] == 1);
		assertTrue(desk2.vote(new String[] { "s1", "s3"}));
		assertTrue(desk2.vote(new String[] { "s3", "s1"}));
		assertTrue(desk2.vote(new String[] { "s1", "s3"}));
		assertTrue(desk2.vote(new String[] { "s2", "s3"}));
		assertTrue(desk3.vote(new String[]{"s2", "s1"}));
		assertTrue(station2.getResutls().get("s1")[0] == 3);
		assertTrue(station2.getResutls().get("s3")[1] == 3);
		assertTrue(station2.getResutls().get("s2")[0] == 2);
		assertTrue(station2.sendResults());
		assertTrue(station2.closeStation());
		
		StationManager station3 = new StationManagerImpl("station3", "north", new service.ManagerServiceProxy());
		assertTrue(station3.updateInfo());
		try{
			desk1 = new DeskManagerImpl(1, new DeskServiceProxy());
			desk2 = new DeskManagerImpl(2, new DeskServiceProxy());
			desk3 = new DeskManagerImpl(3, new DeskServiceProxy());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		info = desk1.getInfo();
		assertTrue(info.getStationName().equals("station3"));
		assertTrue(info.getElectionName().equals("Test1elec"));
		assertTrue(info.getArea().equals("north"));
		assertTrue(info.getNumOfVotePerVoter() == 3);
		assertTrue(info.isRanked());
		for(int i=0; i<info.getCanNames().length; i++) {
			assertTrue(info.getCanNames()[i].equals(n[i]));
		}
		assertTrue(desk1.vote(new String[] { "n1", "n2", "n3"}));
		assertTrue(station3.getResutls().get("n1")[0] == 1);
		assertTrue(desk2.vote(new String[] { "n1", "n3","n4"}));
		assertTrue(desk2.vote(new String[] { "n3", "n1", "n2"}));
		assertTrue(desk2.vote(new String[] { "n1", "n3", "n2"}));
		assertTrue(desk2.vote(new String[] { "n2", "n3", "n4"}));
		assertTrue(desk3.vote(new String[]{"n2", "n1", "n4"}));
		assertTrue(station3.getResutls().get("n1")[0] == 3);
		assertTrue(station3.getResutls().get("n3")[1] == 3);
		assertTrue(station3.getResutls().get("n2")[2] == 2);
		assertTrue(station3.sendResults());
		
		assertTrue(station3.closeStation());
		assertTrue(mainManager.endRunningElection());
		assertTrue(mainManager.getResult("Test1elec", "south", "s1", 1) == 6);
		assertTrue(mainManager.getResult("Test1elec", "south", "s3", 2) == 6);
		assertTrue(mainManager.getResult("Test1elec", "north", "n3", 2) == 3);	
	}
}
