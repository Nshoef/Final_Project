package testings;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import services.AreaInfo;

/**
 * This test class test the class AreaInfo.
 * @author Noam
 *
 */
public class AreaInfoTest {
	AreaInfo info;
	String name;
	String area;
	String[] cans;
	int novpv;
	boolean isRanked;
	

	@Before
	public void setUpBeforeClass() {
		name = "electionName";
		area = "area";
		cans = new String[]{"can1", "can2", "can3"};
		novpv = 4;
		isRanked = false;
		info = new AreaInfo(name, area, cans, novpv, isRanked);
	}

	@Test
	public void testGetElectionName() {
		assertTrue(info.getElectionName().equals(name));
	}

	@Test
	public void testGetArea() {
		assertTrue(info.getArea().equals(area));
	}

	@Test
	public void testGetCanNames() {
		for(int i=0; i<cans.length; i++) {
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
