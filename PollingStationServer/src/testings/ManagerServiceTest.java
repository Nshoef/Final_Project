package testings;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.Test;

import service.DBConnection;
import service.ManagerService;

/**
 * 
 * @author Noam
 * This class test the ManagerService web service. It use a mock implementation of the DBConnection and compare its result
 * with another mock.
 */
public class ManagerServiceTest {
	private DBConnection db;
	private ManagerService service;
	
	@Before
	public void createService() {
		db = new MockDBImpl();
		service = new ManagerService(db);	
	}

	@Test
	public void testUpdateInfo() throws RemoteException {
		assertEquals(service.updateInfo("area", "name"), db.updateInfo("area", "name"));
	}

	@Test
	public void testGetResults() {
		for(int i=0; i<service.getResults("can").length; i++) {
			assertEquals(service.getResults("can")[i], db.getResult("can")[i]);
		}
	}

	@Test
	public void testSentResult() throws RemoteException {
		assertEquals(service.sentResult(), db.sendResults());
	}

	@Test
	public void testGetAreaInfo() {
		assertEquals(service.getAreaInfo(), db.getInfo());
	}

	@Test
	public void testCloseStation() {
		assertEquals(service.closeStation(), db.closeStation());
	}
}
