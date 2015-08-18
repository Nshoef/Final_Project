package service;

import java.rmi.RemoteException;
import javax.jws.WebService;

/**
 * 
 * @author Noam
 * This class is a web service serving the polling station manager. It has a database connection to contact with the database.
 */
@WebService
public class ManagerService {
	private static DBConnection db = new DBConnection();
	
	
	/**
	 * This method undate the local databse with the relevant information according to the given area
	 * @param area is the name of the area which this polling station is in.
	 * @param name is the name of the polling station.
	 * @return true if the update process was successful.
	 * @throws RemoteException
	 */
	public boolean updateInfo(String area, String name) throws RemoteException {
		return db.updateInfo(area, name);
	}
	
	/**
	 * This method return the result of the given candidate.
	 * @param can is the name of the candidate.
	 * @return an array of the result, according to the number of votes per voter 
	 */
	public Integer[] getResults(String can) {
		return db.getResult(can);
		
	}
	
	/**
	 * This method send all the result to the main database.
	 * @return true if the results were sent successfully.
	 * @throws RemoteException
	 */
	public boolean sentResult() throws RemoteException {
		return db.sendresults();
	}
	
	/**
	 * This method return the relevant information of this polling station from the database.
	 * @return an AreaInfo object containing the relevant information from the database
	 */
	public AreaInfo getAreaInfo() {
		return db.getInfo();
	}
	


}
