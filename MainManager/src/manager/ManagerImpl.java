package manager;


import java.rmi.RemoteException;
import services.Area;
import services.ManagerService;

/**
 * 
 * @author Noam
 * This class implements the main manager of the system, it has a managerServiceproxy which is a web service connection to interact with the database.
 */
public class ManagerImpl implements Manager {
	private ManagerService proxy;
	
	
	/**
	 * The constructor initialised the manager service
	 * @param proxy
	 */
	public ManagerImpl(ManagerService proxy) {
		this.proxy = proxy;	
	}
	
	@Override
	public boolean createNewSystem(String name, String[] areas, int[] novpvs, boolean[] areRanked) {
		try {
			return proxy.setNewElectingSystem(name, areas, novpvs, areRanked);
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public String[] getSavedSystems() {
		try {
			return proxy.getSavedSystems();
		} catch (RemoteException e) {
			return null;
		}
	}
	
	@Override
	public String[] getLastElectionCans(String area, String system) {
		try {
			return proxy.getLastElectionCans(area, system);
		} catch (RemoteException e) {
			return null;
		}
	}
	
	@Override
	public Area[] getSystemAreas(String name) {
		try {
			return proxy.getSystem(name);
		} catch (RemoteException e) {
			return null;
		}	
	}
	
	@Override
	public boolean createNewElection(String name, String system, String[] area, String[][] cans) {
		try {
			if(area.length != cans.length) { //make sure there are candidates for each area.
				return false;
			}
			if(confirmAreas(area, system)) { //check that the areas are same as in the system.
				if(proxy.createNewElection(name, system)) {
					for(int i=0; i<area.length; i++) {
						if(!proxy.setCandidates(cans[i], name, area[i])) {
							proxy.removeElection(name); //remove everything if there was a failure.
							return false;
						}
					}
					return true;
				}
			}	
		} catch (RemoteException | NullPointerException e) {
			return false;
		}
		return false;	
	}
	
	/**
	 * This is a helper method to confirm that the list of areas are the same as in the system.
	 * @param areas is the list of area to be checked.
	 * @param system is the system which the list is checked against.
	 * @return true if the list match the system's list.
	 * @throws RemoteException if there is a connection problem.
	 * @throws NullPointerException if there is no such a system.
	 */
	private boolean confirmAreas(String[] areas, String system) throws RemoteException, NullPointerException {
		for(Area a : getSystemAreas(system)) {
			boolean check = false;
			for(String s : areas) {
				if(a.getName().equals(s)) {
					check = true;
				}
			}
			if(!check) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String[] getSavedElectionsNames() {
		try {
			return proxy.getSavedElectionsNames();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public boolean setCurrentElection(String name) {
		try {
			return proxy.setCurrentElection(name);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean endRunningElection() {
		try {
			return proxy.endRunningElection();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public String getSystemElection(String election) {
		try {
			return proxy.getSystemElection(election);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String[] getCans(String election, String area) {
		try {
			return proxy.getCans(election, area);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Integer getResult(String election, String area, String can, int voteNum) {
		try {
			return proxy.getResult(election, area, can, voteNum);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String getRunningElection() {
		try {
			return proxy.getRunningElection();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
}
	