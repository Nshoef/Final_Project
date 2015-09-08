package services;
/**
 * This interface represent the connection with the database. It has methods that serve both the main manager and the polling station web services.
 * @author Noam
 *
 */
public interface DBConnection {
	
	/**
	 * This method return the relevant information to the given area as an AreaInfo object
	 * @param area is the name of the area to be given information about.
	 * @return the an AreaInfo object containing the relevant information or null if there is no running elections.
	 */
	public AreaInfo getAreaInfo(String area);
	
	/**
	 * This method add a vote to the database.
	 * @param id is the id of the vote (must be unique).
	 * @param election is the election of the vote.
	 * @param area is the area of the vote.
	 * @param votes is the vote/s which where chosen.
	 * @return true if the vote successfully entered into the database.
	 */
	public boolean addVote(String id, String election, String area, String[] votes);
	
	/**
	 * This method set a new electing system in the database.
	 * @param name is the name of the electing system.
	 * @param areas is a list with the names of the areas of the system
	 * @param novpvs is an array of in which the number of candidates each voter has to choose (where novpvs[i] match areas[i]).
	 * @param areRanked is an array of boolean which state if the novpvs has to be in order (novpvs[i] match areRanked[i]).
	 * @return true if the system was successfully updated in the database.
	 */
	public boolean setNewElectingSystem(String name, String[] areas, int[] novpvs, boolean[] areRanked);
	
	/**
	 * This method return the names of the saved elections.
	 * @return an array of string with the names of the saved elections or null if there are no.
	 */
	public String[] getSavedElectionsNames();
	
	
	/**
	 * This method return the names of the systems which are saved on the database.
	 * @return a array of string with the name of the saved systems or null if there are non.
	 */
	public String[] getSavedSystems();
	
	/**
	 * This method return a list of the candidates who run in the given area and system on the last election.
	 * @param area is the name of the area.
	 * @param system is the system.
	 * @return an array of string with the name of the candidates or null if there wasn't any previous election.
	 */
	public String[] getLastElectionCans(String area, String system);
	
	/**
	 * This method set a new election on a given electing system.
	 * @param name is the name of the new election.
	 * @param electingSystem is the system which is used in this election.
	 * @return true if the new election was successfully updated on the database.
	 */
	public boolean setNewElection(String name, String electingSystem);
	
	/**
	 * This method set candidates who run on a given election and area.
	 * @param cans is the names of the candidates.
	 * @param electionName is the election these candidates run for.
	 * @param area is the area they run on.
	 * @return true if the database was successfully updated.
	 */
	public boolean setCandidates(String[] cans, String electionName, String area);
	/**
	 * This method returns all the information about a given voting system inside an area objects.
	 * @param name is the name of the voting system which the areas belong to.
	 * @return an array of area object where each object contain the information of one area in this system of null if
	 * the system does not exist.
	 */
	public Area[] getSystemAreas(String name);
	
	/**
	 * This method begin a voting process on a given electing system.
	 * @param electionName is the name of the election.
	 * @return true if the database was updated successfully, false if not or if there is already an election in process.
	 */
	public boolean setCurrentElection(String electionName);
	
	/**
	 * This method return the current running election or null if there is no running election.
	 * @return the current running election or null if there is no running election.
	 */
	public String getRunningElection();
	
	/**
	 * This method stop a voting process. polling station won't be able to get any updates.
	 * @return true if the database was updated successfully.
	 */
	public boolean endRunningElection();
	
	/**
	 * This method return the number of vote for a given candidate on a given, voting number, area, and election.
	 * @param election the number of vote for a given candidate on a given, voting number, area, and election.
	 * @return the number of vote for a given candidate on a given, voting number, area, and election.
	 */
	public Integer getResult(String election, String area, String can, int voteNum);
	
	/**
	 * This method return the name of the electing system that is used in the given election.
	 * @param election is the name of the election to be checked.
	 * @return a string with the name of the electing system that is used in the given election.
	 */
	public String getSystem(String election);
	
	/**
	 * This method return the names of the candidates who run in the given election and area.
	 * @param election is the name of the election.
	 * @param area is the name of the area.
	 * @return an array of string with the names of the candidates who run in the given election and area.
	 */
	public String[] getCans(String election, String area);
	
	/**
	 * This method remove the given election only if there is no votes associate with it.
	 * @param name is the name of the election to be removed.
	 * @return true if the election removed from the database.
	 */
	public boolean removeElection(String name);
	
	/**
	 * This method remove the given electing system from the system only if there are no election associate with it.
	 * @param name is the name of the system to be removed.
	 * @return true if the electing system removed from the database.
	 */
	public boolean removeSystem(String name);
}
