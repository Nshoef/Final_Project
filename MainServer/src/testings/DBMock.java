package testings;

import services.Area;
import services.AreaInfo;
import services.DBConnection;
/** 
 * This class is a mocking implementation of DBConnection, the returns values are always a legal return
 * values of this methods. This implementation is used to test the services without being concern about
 * the dependency on the DBConnection class.
 * @author Noam
 *
 */
public class DBMock implements DBConnection {
	private static final AreaInfo info;
	private static final Area area;
	
	static {
		info = new AreaInfo("name", "area",new String[]{ "cans"}, 3, true);
		area = new Area("area", "system", 1, false);
	}

	@Override
	public AreaInfo getAreaInfo(String area) {
		return info;
	}

	@Override
	public boolean addVote(String id, String election, String area, String[] votes) {
		return true;
	}

	@Override
	public String[] getSavedElectionsNames() {
		return new String[]{"a", "b"};
	}


	@Override
	public String[] getSavedSystems() {
		return new String[]{"a","b", "c"};
	}

	@Override
	public String[] getLastElectionCans(String area, String system) {
		return new String[]{"1","2", "3", "4"};
	}

	@Override
	public Area[] getSystemAreas(String name) {
		return new Area[]{area};
	}

	@Override
	public boolean setCurrentElection(String electionName) {
		return true;
	}

	@Override
	public String getRunningElection() {
		return "election";
	}

	@Override
	public boolean endRunningElection() {
		return true;
	}

	@Override
	public Integer getResult(String election, String area, String can, int voteNum) {
		return 1;
	}

	@Override
	public String getSystem(String election) {
		return "System";
	}

	@Override
	public String[] getCans(String election, String area) {
		return new String[]{"a", "b", "c"};
	}

	@Override
	public boolean removeElection(String name) {
		return true;
	}


	@Override
	public boolean removeSystem(String name) {
		return true;
	}

	@Override
	public boolean setNewElectingSystem(String name, String[] areas, int[] novpvs, boolean[] areRanked) {
		return true;
	}

	@Override
	public boolean setNewElection(String name, String electingSystem) {
		return true;
	}

	@Override
	public boolean setCandidates(String[] cans, String electionName, String area) {
		return true;
	}
	
}
