package manager;


import java.rmi.RemoteException;
import services.Area;
import services.ManagerServiceProxy;

/**
 * 
 * @author Noam
 * This class is the main manager of the system, it has a scanner for input and a managerServiceproxy to interact with the database.
 */
public class Manager {
	private ManagerServiceProxy proxy;
	
	
	
	public Manager() {
		proxy = new ManagerServiceProxy();
		
	}
	
	/**
	 * This method create new voting system according to the input
	 * @param input is the user input(name of system, number of area, and for each area name, number of votes per voter, and if it's ranked or not).
	 * @return true if the new system entered successfully to the database.
	 */
	public boolean createNewSystem(String name, String[] areas, int[] novpvs, boolean[] areRanked) {
		try {
			int numOfArea = areas.length;
			if (proxy.setNewElectingSystem(name, numOfArea)) { //update the system's table, continue only if updated
				for(int i = 0; i<numOfArea; i++) { 
					String areaName = areas[i];
					int novpv = novpvs[i];
					boolean isRanked = areRanked[i];
					if (!proxy.setNewArea(areaName, name, novpv, isRanked)) { //update each area, continue only if updated
						return false;
					}
				}
				return true;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;	
	}
	
	/**
	 * This method return a list of the saved election system. 
	 * @return an array of string with the names of the saved systems of null if there aren't any.
	 */
	public String[] getSavedSystems() {
		try {
			return proxy.getSavedSystems();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * This method return a list of the candidates who run in the last election in the given area in the given system. 
	 * @param area is the area which this candidates are running in.
	 * @param system is the system which this area is belong to.
	 * @return an array of string with the names of the candidates who run in this area in this system.
	 */
	public String[] getLastElectionCans(String area, String system) {
		try {
			return proxy.getLastElectionCans(area, system);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This method return all the areas of a given system with their info.
	 * @param name is the name of the voting system
	 * @return an array of area objects containing the information of all the areas in this voting system.
	 */
	public Area[] getSystemAreas(String name) {
		try {
			return proxy.getSystem(name);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * This method create a new election. It does not starting the electing process.
	 * @param name is the name of this election.
	 * @param electingSystemName is the name of the electing system which is used in this election.
	 * @param input is the user input stating the candidates of each area in this election.
	 * @return true if all the information was successfully written on the database.
	 */
	public boolean createNewElection(String name, String system, String[] area, String[][] cans) {
		try {
			if(proxy.setNewElection(name, system)) {
				for(int i=0; i<area.length; i++) {
					if(!proxy.setCandidates(cans[i], name, area[i])) {
						return false;
					}
				}
				return true;
			}	
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;	
	}
	
	/**
	 * This method return the names of saved elections. 
	 * @return an array of string with the names of the saved elections.
	 */
	public String[] getSavedElectionsNames() {
		try {
			return proxy.getSavedElectionsNames();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * This method start an electing process on the given election. 
	 * @param name is the name of the election.
	 * @return true if the database was successfully updated, false if not or if there is already election in process.
	 */
	public boolean setCurrentElection(String name) {
		try {
			return proxy.setCurrentElection(name);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * This method end a running election. polling station won't be able update their info's
	 * @return true if the database was updated successfully.
	 */
	public boolean endRunningElection() {
		try {
			return proxy.endRunningElection();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * This method return the name of the election system which is in use in the given election.
	 * @param election is the name of the election to be checked.
	 * @return a string with the name of the election system which used in the given election.
	 */
	public String getSystemElection(String election) {
		try {
			return proxy.getSystemElection(election);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This method return the candidates who run in the given election in the given area.
	 * @param election is the name of the election these candidates run to.
	 * @param area is the name of the area these candidates run in.
	 * @return an array of string with the name of the candidates who run in the given area and election.
	 */
	public String[] getCans(String election, String area) {
		try {
			return proxy.getCans(election, area);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This method return the result of a given election.
	 * @param election is the name of the election.
	 * @return an array with areaResult object for each area containing the result of this area.
	 */
	public Integer getResult(String election, String area, String can, int voteNum) {
		try {
			return proxy.getResult(election, area, can, voteNum);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
	

