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
public class Manager {
	private String stationName;
	private String area;
	private AreaInfo info;
	private ManagerServiceProxy service;
	
	/**
	 * 
	 * @param name is the name of the polling station
	 * @param area is the area of this polling station
	 * @throws RemoteException 
	 */
	Manager(String name, String area) throws RemoteException {
		this.stationName = name;
		this.area = area;
		service = new ManagerServiceProxy();
		info = service.getAreaInfo();
	}
	
	/**
	 * This method is use to retrieve the relevant information for this polling station from the main 
	 * database and to store it in the local database.
	 * @return true if the process completed successfully
	 * @throws RemoteException
	 */
	public boolean updateInfo() throws RemoteException {
		return service.updateInfo(area, stationName);
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
			for(int a : votes) {
				iVotes[a] = a;
			}
			result.put(s, iVotes);
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		
	}
	
	

}
