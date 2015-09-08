package testings;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import service.AreaInfo;

/**
 * 
 * @author Noam
 * This class tests the AreaInfo class
 */
public class AreaInfoTest {
	private static AreaInfo info;
	String station;
	String election;
	String area;
	String[] cans;
	int novpv;
	boolean isRanked;
	
	@Before
	public void createInfo() {
		station = "stationName";
		election = "electionName";
		area = "area";
		cans = new String[] { "a", "b"};
		novpv = 2;
		isRanked = true;
		info = new AreaInfo(station, election, area, cans, novpv, isRanked);
	}

	@Test
	public void testGetStationName() {
		assertTrue(info.getStationName().equals(station));
	}

	@Test
	public void testGetElectionName() {
		assertTrue(info.getElectionName().equals(election));
	}

	@Test
	public void testGetArea() {
		assertTrue(info.getArea().equals(area));
	}

	@Test
	public void testGetCanNames() {
		for(int i=0; i<info.getCanNames().length; i++) {
			assertTrue(info.getCanNames()[i].equals(cans[i]));
		}
	}

	@Test
	public void testGetNumOfVotePerVoter() {
		assertEquals(info.getNumOfVotePerVoter(), novpv);
	}

	@Test
	public void testIsRanked() {
		assertEquals(info.isRanked(), isRanked);
	}

}
