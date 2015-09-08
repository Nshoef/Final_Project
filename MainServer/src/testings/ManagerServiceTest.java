package testings;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import services.ManagerService;

/**
 * This class is a test class of the ManagerService class. It use a mock implementation of 
 * DBConnection in order to make sure the DBConnection return values are always legal.
 * @author Noam
 *
 */
public class ManagerServiceTest {
	private ManagerService manager;
	private DBMock mock;

	@Before
	public void setUpBeforeClass() {
		manager = new ManagerService(new DBMock());	
		mock = new DBMock();
	}

	@Test
	public void testGetAreaInfo() {
		assertEquals(manager.getAreaInfo("area"), mock.getAreaInfo("area"));
	}

	@Test
	public void testSetNewElectingSystem() {
		String[] areas = {"a1", "a2"};
		int[] novpvs = { 1,3 };
		boolean[] areRanked = { false, true};
		assertTrue(manager.setNewElectingSystem("name", areas, novpvs, areRanked));
		assertTrue(mock.setNewElectingSystem("name", areas, novpvs, areRanked));
	}

	@Test
	public void testGetSavedSystems() {
		for(int i=0; i<manager.getSavedSystems().length; i++) {
			assertTrue(manager.getSavedSystems()[i].equals(mock.getSavedSystems()[i]));	
		}
		
	}

	@Test
	public void testGetLastElectionCans() {
		for(int i=0; i<manager.getLastElectionCans("area", "system").length; i++) {
			assertTrue(manager.getLastElectionCans("area", "system")[i].equals(mock.getLastElectionCans("area", "system")[i]));
		}
	}

	@Test
	public void testGetSavedElectionsNames() {
		for(int i=0; i<manager.getSavedElectionsNames().length; i++) {
			assertTrue(manager.getSavedSystems()[i].equals(mock.getSavedElectionsNames()[i]));
		}
	}

	@Test
	public void testCreateNewElection() {
		;
		assertEquals(manager.createNewElection("name", "system"), mock.setNewElection("name", "system"));
		assertEquals(manager.createNewElection("name", "system"), mock.setNewElection("name", "system"));
	}
	
	@Test
	public void testSetCandidates() {
		String[] cans = {"a", "b"};
		assertEquals(manager.setCandidates(cans, "election", "area"), mock.setCandidates(cans, "election", "area"));
		
	}
	

	@Test
	public void testGetSystem() {
		for(int i=0; i<manager.getSystem("name").length; i++) {
			assertEquals(manager.getSystem("name")[i],mock.getSystemAreas("name")[i]);
		}	
	}

	@Test
	public void testSetCurrentElection() {
		assertEquals(manager.setCurrentElection("electionName"), mock.setCurrentElection("electionName"));
	}

	@Test
	public void testEndRunningElection() {
		assertEquals(manager.endRunningElection(), mock.endRunningElection());
	}

	@Test
	public void testGetSystemElection() {
		assertTrue(manager.getSystemElection("system").equals(mock.getSystem("election")));
	}

	@Test
	public void testGetCans() {
		for(int i=0; i<manager.getCans("election", "area").length; i++) {
			assertTrue(manager.getCans("election", "area")[i].equals(mock.getCans("election", "area")[i]));
		}
	}

	@Test
	public void testGetResult() {
		assertEquals(manager.getResult("election", "area", "can", 1), mock.getResult("election", "area", "can", 1));
	}
	
	@Test
	public void testGetRunningElection() {
		assertEquals(manager.getRunningElection(),mock.getRunningElection());
	}
	
}
