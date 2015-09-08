package services;


import javax.jws.WebService;

/**
 * 
 * @author Noam
 * This class is a web service serving the main manager to interact with the database.
 */
@WebService
public class ManagerService {
	private static DBConnection db;
	
	static {
		db = new DBConnectionImpl();
	}
	
	/**
	 * This is a default constructor
	 */
	public ManagerService() {}

	/**
	 * This constructor enable the class to use a non default database connection (used by the testers).
	 * @param db the DBConnection to be used
	 */
	public ManagerService(DBConnection db) {
		ManagerService.db = db;
	}
	
	/**
	 * This method return the relevant information to the given area as an AreaInfo object
	 * @param area is the name of the area to be given information about.
	 * @return the an AreaInfo object containing the relevant information.
	 */
	public AreaInfo getAreaInfo(String area) {
		return db.getAreaInfo(area);
	}
	
	/**
	 * This method set a new electing system in the database.
	 * @param name is the name of the electing system.
	 * @param areas is a list with the names of the areas of the system
	 * @param novpvs is an array of in which the number of candidates each voter has to choose (where novpvs[i] match areas[i]).
	 * @param areRanked is an array of boolean which state if the novpvs has to be in order (novpvs[i] match areRanked[i]).
	 * @return true if the system was successfully updated in the database.
	 */
	public boolean setNewElectingSystem(String name, String[] areas, int[] novpvs, boolean[] areRanked) {
		return db.setNewElectingSystem(name, areas, novpvs, areRanked);
	}
	
	/**
	 * This method create a new election in the database according to the given parameters.
	 * @param name is the name of the election.
	 * @param system is the name of the electing system to be use in this elections.
	 * @param area is the areas of the system (must match the system's areas).
	 * @param cans is a list of a list of candidates for each area (String[i][] is the candidates for area area[i])
	 * @return
	 */
	public boolean createNewElection(String name, String system) {
		return db.setNewElection(name, system);
	}
	public boolean setCandidates(String[] cans, String electionName, String area) {
		return db.setCandidates(cans, electionName, area);
	}

	/**
	 * This method return the names of the saved electing systems.
	 * @return an array of string with the names of the saved systems.
	 */
	public String[] getSavedSystems() {
		return db.getSavedSystems();
	}
	
	
	/**
	 * This method return a list of the candidates who run in this area on the last election.
	 * @param area is the name of the area.
	 * @return an array of string with the name of the candidates or null if there is no previous election.
	 */
	public String[] getLastElectionCans(String area, String system) {
		return db.getLastElectionCans(area, system);
	}
	
	/**
	 * This method return the manes of the saved elections.
	 * @return an array of string with the names of the saved systems.
	 */
	public String[] getSavedElectionsNames() {
		return db.getSavedElectionsNames();
	}
	
	/**
	 * This method returns all the information about a given voting system inside an area objects.
	 * @param name is the name of the voting system which the areas belong to.
	 * @return an array of area object where each object contain the information of one area in this system.
	 */
	public Area[] getSystem(String name) {
		return db.getSystemAreas(name);
	}
	
	/**
	 * This method begin a voting process on a given electing system.
	 * @param electionName is the name of the election.
	 * @return true if the database was updated successfully, false if not or if there is already an election in process.
	 */
	public boolean setCurrentElection(String electionName) {
		return db.setCurrentElection(electionName);
	}
	
	/**
	 * This method stop a voting process. polling station won't be able to get any updates.
	 * @return
	 */
	public boolean endRunningElection() {
		return db.endRunningElection();
	}
	
	/**
	 * This method return the name of the electing system that is used in the given election.
	 * @param election is the name of the election to be checked.
	 * @return a string with the name of the electing system that is used in the given election.
	 */
	public String getSystemElection(String election) {
		return db.getSystem(election);
	}
	
	/**
	 * This method return the names of the candidates who run for the given election in the given area.
	 * @param election is the name of the election on which the candidate are running for.
	 * @param area is the name of the area on which the candidates are running on.
	 * @return
	 */
	public String[] getCans(String election, String area) {
		return db.getCans(election, area);
	}
	
	/**
	 * This method return the result (number of votes) for the given candidate in a given area, election, and vote number.
	 * @param election is the name of the election.
	 * @param area is the area which this can is running on.
	 * @param can is the name of the candidate.
	 * @param voteNum is the number of vote from which the results are (according to the number of vote per voter).
	 * @return an integer with the number of votes which the given candidate got according to the parameters.
	 */
	public Integer getResult(String election, String area, String can, int voteNum) {
		return db.getResult(election, area, can, voteNum);
	}
	
	/**
	 * This method remove the given election only if there is no votes associate with it.
	 * @param name is the name of the election to be removed.
	 * @return true if the election removed from the database.
	 */
	public boolean removeElection(String name) {
		return db.removeElection(name);
	}
	
	/**
	 * This method remove the given electing system from the system only if there are no election associate with it.
	 * @param name is the name of the system to be removed.
	 * @return true if the electing system removed from the database.
	 */
	public boolean removeSystem(String name) {
		return db.removeSystem(name);
	}
	
	/**
	 * This method return the name of the running election.
	 * @return the name of the running election or null if there is no running election.
	 */
	public String getRunningElection(){
		return db.getRunningElection();
	}
}
