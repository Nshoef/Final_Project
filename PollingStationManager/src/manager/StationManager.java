package manager;

import java.rmi.RemoteException;
import java.util.Map;

import service.AreaInfo;

/**
 * 
 * @author Noam
 * This interface represent the polling station manager.
 */
public interface StationManager {
	
	/**
	 * This method is use to retrieve the relevant information for this polling station from the main 
	 * database and to store it in the local database.
	 * @return true if the process completed successfully
	 * @throws RemoteException
	 */
	public boolean updateInfo();
	
	/**
	 * This method is use to send the result of this polling station to the main database.
	 * @return true of the result were sent successfully to the main database.
	 * @throws RemoteException
	 */
	public boolean sendResults();
	
	/**
	 * This method return the result of this station as a map of candidates and array of votes
	 * (according to the number of vote per voter).
	 * 
	 * @return a map representing the result of the election in this station.
	 * @throws RemoteException
	 */
	public Map<String, Integer[]> getResutls();
	
	/**
	 * This method return the relevant information of the election in this area.
	 * @return an AreaInfo object containing the relevant information of the election to this area.
	 */
	public AreaInfo getInfo();
	
	/**
	 * This method remove all the information from the database.
	 * @return
	 */
	public boolean closeStation();

}
