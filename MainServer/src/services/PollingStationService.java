package services;


import javax.jws.WebService;

/**
 * 
 * @author Noam
 *This class is a web service serving the polling station in order to interact with the main server.
 *It has a connection to the main database. 
 */
@WebService
public class PollingStationService {
	private static DBConnection db;
	
	static {
		db = new DBConnectionImpl();
	}
	
	/**
	 * This is a default constructor
	 */
	public PollingStationService() {}
	
	/**
	 * This constructor enable the class to use a non default database connection (used by the testers).
	 * @param db the DBConnection to be used
	 */
	public PollingStationService(DBConnection db) {
		PollingStationService.db = db;
	}
	
	/**
	 * This method return the relevant information about the given area.
	 * @param area is the name of the area.
	 * @return an AreaInfo object containing the relevant information.
	 */
	public AreaInfo getAreaInfo(String area) {
		return db.getAreaInfo(area);
	}
	
	/**
	 * This method use to add a single vote to the database.
	 * @param statment is the insertion statement containing the vote information.
	 * @return
	 */
	public boolean updateResults(String id, String election, String area, String[] votes) {
		return db.addVote(id, election, area, votes);	
	}
}
