package testings;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import services.DBConnection;
import services.PollingStationService;


/**
 * This class tests the PollingStationService class. It use a mock implementation of the DBConnetion class
 * in order to make sure it always return legal values. 
 * @author Noam
 *
 */
public class PollingStationServiceTest {
	private static PollingStationService service;
	private static DBConnection mock;

	@Before
	public void setUpBeforeClass() {
		service = new PollingStationService(new DBMock());
		mock = new DBMock();
	}

	@Test
	public void testGetAreaInfo() {
		assertTrue(service.getAreaInfo("area").getElectionName().equals(mock.getAreaInfo("area").getElectionName()));
		
	}

	@Test
	public void testUpdateResults() {
		assertEquals(service.updateResults("id", "election", "area", new String[0]), mock.addVote("id", "election", "area", new String[0]));
	}

}
