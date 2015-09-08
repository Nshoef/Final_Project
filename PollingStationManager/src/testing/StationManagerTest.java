package testing;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.Test;

import manager.StationManager;
import manager.StationManagerImpl;
import service.AreaInfo;
import service.ManagerService;

public class StationManagerTest {
	private static ManagerService mock;
	private static StationManager manager;
	
	
	@Before
	public void creareManager() throws RemoteException {
		mock = new MockService();
		manager = new StationManagerImpl("name", "area", mock);
	}

	@Test
	public void testUpdateInfo() throws RemoteException {
		assertEquals(mock.updateInfo("area", "name"), manager.updateInfo());
	}

	@Test
	public void testSendResults() throws RemoteException {
		assertEquals(manager.sendResults(), mock.sentResult());
	}

	@Test
	public void testGetResutls() throws RemoteException {
		manager = new StationManagerImpl(mock);
		String[] name = mock.getAreaInfo().getCanNames();
		for(int i=0; i<name.length; i++) {
			for(int j=0; j<mock.getResults(name[i]).length; j++) {
				assertTrue(manager.getResutls().get(name[i])[j] == mock.getResults(name[i])[j]);
			}
		
		}
	}

	@Test
	public void testGetInfo() throws RemoteException {
		assertTrue(manager.getInfo().equals(mock.getAreaInfo()));
	}

	@Test
	public void testCloseStation() throws RemoteException {
		assertEquals(manager.closeStation(), mock.closeStation());
	}
	
	/**
	 * 
	 * @author Noam
	 *
	 */
	private static class MockService implements ManagerService {
		private static AreaInfo info = new AreaInfo(
				"area", new String[] {"canNames"}, "electionName", 2, true, "stationName");

		@Override
		public int[] getResults(String can) throws RemoteException {
			return new int[] {1,2};
		}

		@Override
		public boolean updateInfo(String area, String name) throws RemoteException {
			return true;
		}

		@Override
		public AreaInfo getAreaInfo() throws RemoteException {
			return info;
		}

		@Override
		public boolean sentResult() throws RemoteException {
			return true;
		}

		@Override
		public boolean closeStation() throws RemoteException {
			return true;
		}
		
	}

}
