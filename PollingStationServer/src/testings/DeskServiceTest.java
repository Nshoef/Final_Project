package testings;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import service.DBConnection;
import service.DeskService;

/**
 * 
 * @author Noam
 * This class test the DeskService web service. It use a mock implementation of the DBConnection and compare its result
 * with another mock.
 */
public class DeskServiceTest {
	private DeskService desk;
	private DBConnection mock;
	
	@Before
	public void createDesk() {
		mock = new MockDBImpl();
		desk = new DeskService(mock);	
	}

	@Test
	public void testAddVote() {
		String[] cans = {"can2", "can2"};
		assertEquals(desk.addVote(cans), mock.addVote(cans));	
	}

	@Test
	public void testGetInfo() {
		assertEquals(desk.getInfo(), mock.getInfo());
	}
}
