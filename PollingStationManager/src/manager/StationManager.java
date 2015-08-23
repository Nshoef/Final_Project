package manager;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import service.AreaInfo;
import service.ManagerServiceProxy;

/**
 * 
 * @author Noam
 * This class represent the manager of the polling station. It has a name, area, and a connection to a web service.
 */
public class StationManager {
	private String stationName;
	private String area;
	private AreaInfo info;
	private ManagerServiceProxy service;
	
	/**
	 * This constructor should be used to initialised a station in the database.
	 * @param name is the name of the polling station
	 * @param area is the area of this polling station
	 * @throws RemoteException 
	 */
	public StationManager(String name, String area) throws RemoteException {
		this.stationName = name;
		this.area = area;
		service = new ManagerServiceProxy();
	}
	
	/**
	 * This constructor should be used when the station already exist in the database.
	 * @throws RemoteException
	 */
	public StationManager() throws RemoteException {
		service = new ManagerServiceProxy();
		info = service.getAreaInfo();
		stationName = info.getStationName();
		area = info.getArea();
	}
	
	/**
	 * This method is use to retrieve the relevant information for this polling station from the main 
	 * database and to store it in the local database.
	 * @return true if the process completed successfully
	 * @throws RemoteException
	 */
	public boolean updateInfo() throws RemoteException {
		if( service.updateInfo(area, stationName)) {
			info =  service.getAreaInfo();
			return true;
		}
		return false;
	}
	
	/**
	 * This method is use to send the result of this polling station to the main database.
	 * @return true of the result were sent successfully to the main database.
	 * @throws RemoteException
	 */
	public boolean sendResults() throws RemoteException {
		return service.sentResult();
	}
	
	/**
	 * This method return the result of this station as a map of candidates and array of votes
	 * (according to the number of vote per voter).
	 * 
	 * @return a map representing the result of the election in this station.
	 * @throws RemoteException
	 */
	public Map<String, Integer[]> getResutls() throws RemoteException {
		String[] cans = info.getCanNames();
		Map<String, Integer[]> result = new HashMap<String, Integer[]>();
		for(String s : cans) {
			int[] votes = service.getResults(s);
			Integer[] iVotes = new Integer[votes.length];
			for(int i=0; i<votes.length; i++) {
				iVotes[i] = votes[i];
			}
			result.put(s, iVotes);
		}
		return result;
	}
	
	/**
	 * This method return the relevant information of the election in this area.
	 * @return an AreaInfo object containing the relevant information of the election to this area.
	 */
	public AreaInfo getInfo() {
		try {
			return service.getAreaInfo();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This method remove all the information from the database.
	 * @return
	 */
	public boolean CloseStation() {
		try {
			return service.closeStation();
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
		
	}
}

