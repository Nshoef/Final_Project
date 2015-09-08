package testings;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import services.Area;

/**
 * This class test the Area class
 * @author Noam
 *
 */
public class AreaTest {
	private Area area;
	String areaName;
	String sysName;
	int novpv;
	boolean isRanked;
	
	@Before
	public void createArea() {
		areaName = "South";
		sysName = "Someland";
		novpv = 4;
		isRanked = true;
		area = new Area(areaName, sysName ,novpv , isRanked);
	}

	@Test
	public void testGetName() {
		assertEquals(area.getName(),areaName);
	}

	@Test
	public void testGetSystemName() {
		assertEquals(area.getSystemName(),sysName);
	}

	@Test
	public void testGetNumOfVotesPerVoter() {
		assertEquals(area.getNumOfVotesPerVoter(),novpv);
	}

	@Test
	public void testIsRanked() {
		assertEquals(area.isRanked(),isRanked);
	}

}
