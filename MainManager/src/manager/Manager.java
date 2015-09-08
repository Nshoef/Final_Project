package manager;

import services.Area;

/**
 * This interface represent the main manager of the system
 * @author Noam
 *
 */
public interface Manager {
	
	
	/**
	 * This method create new voting system according to .the parameters
	 * @param name is the name of the new system, must be unique
	 * @param areas is a list of names of the voting areas for this system (must have at least one area)
	 * @param novpvs is a list of the number of candidates which each voter has to choose in each area (where novpvs[i] is area areas[i]) 
	 * @param areRanked state if the candidates need to be in order (where areRanked[i] is area areas[i])
	 * @return true if the new system entered successfully to the database.
	 */
	public boolean createNewSystem(String name, String[] areas, int[] novpvs, boolean[] areRanked);
	
	/**
	 * This method return a list of the saved election system. 
	 * @return an array of string with the names of the saved systems or null if there aren't any.
	 */
	public String[] getSavedSystems();
	
	/**
	 * This method return a list of the candidates who run in the last election in the given area in the given system. 
	 * @param area is the area which this candidates are running in.
	 * @param system is the system which this area is belongs to.
	 * @return an array of string with the names of the candidates who run in this area in this system or null if one of the 
	 * parameters do not exists.
	 */
	public String[] getLastElectionCans(String area, String system);
	
	/**
	 * This method return all the areas of a given system with their info.
	 * @param name is the name of the voting system
	 * @return an array of area objects containing the information of all the areas in this voting system.
	 * null if there is no such a system.
	 */
	public Area[] getSystemAreas(String name);
	
	/**
	 * This method create a new election. It does not starting the electing process.
	 * @param name is the name of this election.
	 * @param system is the name of the electing system which is used in this election (must exists in the system).
	 * @param area is the list of the system's areas (must be the same as appears in the system).
	 * @param cans is a list of a list of candidates who runs in the election for each area ( where area[i] has the cans of cnas[i]..)
	 * @return true if all the information was successfully written on the database.
	 */
	public boolean createNewElection(String name, String system, String[] area, String[][] cans);
	
	/**
	 * This method return the names of saved elections. 
	 * @return an array of string with the names of the saved elections or null if there aren't any.
	 */
	public String[] getSavedElectionsNames();
	
	/**
	 * This method start an electing process on the given election. 
	 * @param name is the name of the election.
	 * @return true if the database was successfully updated, false if not or if there is already election in process or no such election.
	 */
	public boolean setCurrentElection(String name);
	
	/**
	 * This method end a running election. polling station won't be able update their info's
	 * @return true if the database was updated successfully.
	 */
	public boolean endRunningElection();
	
	/**
	 * This method return the name of the electing system which is in use in the given election.
	 * @param election is the name of the election to be checked.
	 * @return a string with the name of the election system which used in the given election or null if there is no such election.
	 */
	public String getSystemElection(String election);
	
	/**
	 * This method return the candidates who run in the given election in the given area.
	 * @param election is the name of the election these candidates run to.
	 * @param area is the name of the area these candidates run in.
	 * @return an array of string with the name of the candidates who run in the given area and election or null if can not
	 * find parameters in the system.
	 */
	public String[] getCans(String election, String area);
	
	/**
	 * This method return the result for a candidate in a given election and vote.
	 * @param election is the name of the election.
	 * @param area is the area of this candidate.
	 * @param can is the name of the can.
	 * @param voteNum is the vote number according to the number of candidate each voter choose.
	 * @return an Integer with the number of votes which this candidate got in this election, in this area, in this vote number.
	 */
	public Integer getResult(String election, String area, String can, int voteNum);
	
	/**
	 * This method return the running election.
	 * @return the running election or null if there is no running election.
	 */
	public String getRunningElection();
}
