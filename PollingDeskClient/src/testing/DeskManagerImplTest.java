package testing;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.Test;

import desk.DeskManager;
import desk.DeskManagerImpl;
import service.AreaInfo;
import service.DeskService;

/**
 * 
 * @author Noam
 * This class test the deskManagerImpl class. It use a mock implementation of the desk web service which interact with the database.
 */
public class DeskManagerImplTest {
	private static DeskManager manager1;
	private static DeskManager manager2;
	private static DeskService ds;
	
	@Before
	public void createManager() throws RemoteException {
		ds = new MockService();
		manager1 = new DeskManagerImpl(3, ds);
		manager2 = new DeskManagerImpl(0, ds);
	}

	@Test
	public void testGetInfo() throws RemoteException {
		assertEquals(manager1.getInfo(), ds.getInfo());
		assertEquals(manager2.getInfo(), ds.getInfo());
	}

	@Test
	public void testVote() throws RemoteException {
		String[] cans = manager1.getInfo().getCanNames();
		assertEquals(manager1.vote(cans), ds.addVote(cans));
		assertFalse(manager1.vote(new String[] {"a", "b"}));
			
	}
	
	/**
	 * 
	 * @author Noam
	 * This is a mock implementation of the desk web service to be used by the test.
	 */
	public static class MockService implements DeskService {
		private static AreaInfo info = new AreaInfo(
				"area", new String[] {"can1", "can2"},"electionName", 2, true,"stationName");

		@Override
		public AreaInfo getInfo() throws RemoteException {
			return  info;
		}

		@Override
		public boolean addVote(String[] cans) throws RemoteException {
			return true;
		}	
	}
}
