package testings;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.Test;

import manager.Manager;
import manager.ManagerImpl;
import services.Area;
import services.AreaInfo;
import services.ManagerService;

/**
 * This class test the Manager class. It use an inner mocking implementation of the web service 
 * which always returns a legal values of the web service.
 * @author Noam
 *
 */
public class ManagerImplTset {
	private static Manager manager;
	private static ManagerService proxy;

	@Before
	public void setUpBeforeClass() throws Exception {
		manager = new ManagerImpl(new Mock());
		proxy = new Mock();
	}

	@Test
	public void testCreateNewSystem() throws RemoteException {
		assertEquals(manager.createNewSystem("name", new String[]{"area1", "area2"}, new int[]{1, 2}, new boolean[]{true, false}),
				proxy.setNewElectingSystem("name", new String[]{"area1", "area2"}, new int[]{1, 2}, new boolean[]{true, false}));
	}

	@Test
	public void testGetSavedSystems() throws RemoteException {
		for(int i=0; i<manager.getSavedSystems().length; i++) {
			assertTrue(manager.getSavedSystems()[i].equals(proxy.getSavedSystems()[i]));
		}
	}

	@Test
	public void testGetLastElectionCans() throws RemoteException {
		for(int i=0; i<manager.getLastElectionCans("area", "system").length; i++) {
			assertTrue(manager.getLastElectionCans("area", "system")[i].equals(proxy.getLastElectionCans("area", "system")[i]));
		}
	}

	@Test
	public void testGetSystemAreas() throws RemoteException {
		for(int i=0; i<manager.getSystemAreas("name").length; i++) {
			assertTrue(manager.getSystemAreas("name")[i].equals(proxy.getSystem("name")[i]));
		}
	}

	@Test
	public void testCreateNewElection() throws RemoteException {
		String[] a = {"a1", "a2"};
		String[] b = {"b1", "b2"};
		assertFalse(manager.createNewElection("name", "system", new String[]{"area"}, new String[][]{a, b}));
		assertTrue(manager.createNewElection("name", "system", new String[]{"area"}, new String[][]{a}));
		assertTrue(proxy.createNewElection("name", "system"));
		assertTrue(proxy.setCandidates(a, "name", "area"));
	}

	@Test
	public void testGetSavedElectionsNames() throws RemoteException {
		for(int i=0; i<manager.getSavedElectionsNames().length; i++) {
			assertTrue(manager.getSavedElectionsNames()[i].equals(proxy.getSavedElectionsNames()[i]));
		}
	}

	@Test
	public void testSetCurrentElection() throws RemoteException {
		assertEquals(manager.setCurrentElection("name"), proxy.setCurrentElection("name"));
	}

	@Test
	public void testEndRunningElection() throws RemoteException {
		assertEquals(manager.endRunningElection(),proxy.endRunningElection());
	}

	@Test
	public void testGetSystemElection() throws RemoteException {
		assertTrue(manager.getSystemElection("election").equals(proxy.getSystemElection("election")));
	}

	@Test
	public void testGetCans() throws RemoteException {
		for(int i=0; i<manager.getCans("election", "area").length; i++) {
			assertTrue(manager.getCans("election", "area")[i].equals(proxy.getCans("election", "area")[i]));
		}
	}

	@Test
	public void testGetResult() throws RemoteException {
		assertEquals(manager.getResult("election", "area", "can", 2).intValue(), proxy.getResult("election", "area", "can", 2));
	}
	
	@Test
	public void testGetRunnigElection() throws RemoteException {
		assertEquals(manager.getRunningElection(), proxy.getRunningElection());
	}
	
	/**
	 * This is a mocking implementation of the web service used by the Manager class.
	 * @author Noam
	 *
	 */
	private static class Mock implements ManagerService {
		private static AreaInfo info;
		private static Area area;
		
		static {
			info = new AreaInfo();
			area = new Area("area", 1, false, "system");
		}

		@Override
		public int getResult(String election, String area, String can, int voteNum) throws RemoteException {
			return 0;
		}

		@Override
		public AreaInfo getAreaInfo(String area) throws RemoteException {
			return info;
		}

		@Override
		public Area[] getSystem(String name) throws RemoteException {
			return new Area[]{area};
		}


		@Override
		public String[] getSavedSystems() throws RemoteException {
			return new String[] {"a","b"};
		}



		@Override
		public String[] getCans(String election, String area) throws RemoteException {
			return new String[]{"1", "2", "3"};
		}

		@Override
		public boolean setNewElectingSystem(String name, String[] areas, int[] novpvs, boolean[] areRanked) throws RemoteException {
			return true;
		}

		@Override
		public String[] getLastElectionCans(String area, String system) throws RemoteException {
			return new String[] {"a", "b", "c"};
		}

		@Override
		public String[] getSavedElectionsNames() throws RemoteException {
			return new String[] {"a", "b"};
		}

		@Override
		public boolean setCurrentElection(String electionName) throws RemoteException {
			return false;
		}

		@Override
		public boolean endRunningElection() throws RemoteException {
			return false;
		}

		@Override
		public String getSystemElection(String election) throws RemoteException {
			return "System";
		}

		@Override
		public boolean removeElection(String name) throws RemoteException {
			return true;
		}


		@Override
		public boolean removeSystem(String name) throws RemoteException {
			return false;
		}

		@Override
		public boolean createNewElection(String name, String system) throws RemoteException {
			return true;
		}

		@Override
		public boolean setCandidates(String[] cans, String electionName, String area) throws RemoteException {
			return true;
		}

		@Override
		public String getRunningElection() throws RemoteException {
			return "election";
		}
		
	}

}
