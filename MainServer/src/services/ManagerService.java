package services;

import javax.jws.WebService;

/**
 * 
 * @author Noam
 * This class is a web service serving the main manager to interact with the database.
 */
@WebService
public class ManagerService {
	private static DBConnection db = new DBConnection();
	
	
	
	/**
	 * This method return the relevant information to the given area as an AreaInfo object
	 * @param area is the name of the area to be given information about.
	 * @return the an AreaInfo object containing the relevant information.
	 */
	public AreaInfo getAreaInfo(String area) {
		return db.getAreaInfo(area);
	}
	
	/**
	 * This method set a new electing system on the database(not include the setting of the particular area).
	 * @param name is the name of the new system.
	 * @param numOfAreas is the number of areas in the new system.
	 * @return true if the new system was successfully updated on the database.
	 */
	public boolean setNewElectingSystem(String name, int numOfAreas) {
		return db.setNewElectingSystm(name, numOfAreas);
	}
	
	/**
	 * This method return the names of the saved electing systems.
	 * @return an array of string with the names of the saved systems.
	 */
	public String[] getSavedSystems() {
		return db.getSavedSystems();
	}
	
	/**
	 * This method set a new area on a given system
	 * @param name is the name of the area.
	 * @param system is the voting system which this area is a part of.
	 * @param novpv is the number of votes which each voter has to choose.
	 * @param isRanked is a boolean which state if the the votes of each voter should be in order or not.
	 * @return true if the new area was successfully updated in the database.
	 */
	public boolean setNewArea(String name, String system, int novpv, boolean isRanked) {
		return db.setNewArea(name, system, novpv, isRanked);
	}
	
	/**
	 * This method return a list of the candidates who run in this area on the last election.
	 * @param area is the name of the area.
	 * @return an array of string with the name of the candidates or an empty array if there wasn't any previous election.
	 */
	public String[] getLastElectionCans(String area, String system) {
		return db.getLastElectionCans(area, system);
	}
	
	/**
	 * This method set candidates who run on a given election and area.
	 * @param cans is the names of the candidates.
	 * @param electionName is the election these candidates run for.
	 * @param area is the area they run on.
	 * @return true if the database was successfully updated.
	 */
	public boolean setCandidates(String[] cans, String electionName, String area) {
		return db.setCandidates(cans, electionName, area);
	}
	
	/**
	 * This method return the manes of the saved elections.
	 * @return an array of string with the names of the saved systems.
	 */
	public String[] getSavedElectionsNames() {
		return db.getSavedElectionsNames();
	}
	
	/**
	 * This method set a new election on a given electing system.
	 * @param name is the name of the new election.
	 * @param electingSystem is the system which is used in this election.
	 * @return true if the new election was successfully updated on the database.
	 */
	public boolean setNewElection(String name, String electingSystem) {
		return db.setNewElection(name, electingSystem);
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
}
