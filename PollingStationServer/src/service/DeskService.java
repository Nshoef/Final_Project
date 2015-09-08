package service;


import javax.jws.WebService;

import services.PollingStationServiceProxy;


/**
 * 
 * @author Noam
 *This class is a web service which serve the desk of a polling station.
 *It has a connection to the database via db object.
 */
@WebService
public class DeskService {
	private static DBConnection db;
	
	static {
		db = new DBConnectionImpl(new PollingStationServiceProxy());
	}

	/**
	 * This is a default construction
	 */
	public DeskService() {}
	
	/**
	 * This construction enable the user to use his own connection to the database (used by the testers).
	 * @param db is the DBConnection to be used.
	 */
	public DeskService(DBConnection db) {
		DeskService.db = db;
	}
	
	/**
	 * This method add a vote to the database.
	 * @param cans is the candidate/s which the voter chooses.
	 * @return true if the vote was written successful on the database.
	 */
	public boolean addVote(String[] cans) {
		return db.addVote(cans);
	}
	
	/**
	 * This method return an object with the relevant information to this polling desk
	 * @return an AreaInfo object containing the information about the election. 
	 */
	public AreaInfo getInfo() {
		return db.getInfo();
	}
}
