package services;

public class ManagerService {
	private static DBConnection db = new DBConnection();
	
	
	public AreaInfo getAreaInfo(String area) {
		return db.getAreaInfo(area);
	}
	
	public boolean setNewElectingSystem(String name, int numOfAreas) {
		return db.setNewElectingSystm(name, numOfAreas);
	}
	
	public boolean setNewArea(String name, String system, int novpv, boolean isRanked) {
		return db.setNewArea(name, system, novpv, isRanked);
	}
	
	public String[] getLastElectionCans(String area) {
		return db.getLastElectionCans(area);
	}
	
	public boolean setCandidates(String[] cans, String electionName, String area) {
		return db.setCandidates(cans, electionName, area);
	}
	
	public boolean setNewElection(String name, String electingSystem) {
		return db.setNewElection(name, electingSystem);
	}
	
	public Area[] getSystem(String name) {
		return db.getSystemAreas(name);
	}
	
	public boolean setCurrentElection(String electionName) {
		return db.setCurrentElection(electionName);
	}
	
	public boolean endRunningElection() {
		return db.endRunningElection();
	}
	
	public AreaResult[] getResult(String election) {
		return db.getResult(election);
	}
	
	
	
	
	/**
	public boolean setNewSystem(ElctingSystem system) {
		return false;
		
	}
	
	public ElectingSystem getSystem(String name) {
		
	}
	
	public boolean setNewSystem() {
		
	}

	public ??? getResults() {
		
	}
	
	*/

}
