package service;

import javax.jws.WebService;

/**
 * 
 * @author Noam
 *This class is a web service which serve the desk of a polling station.
 *It has a connection to the database via db object.
 */
@WebService
public class DeskService {
	private static DBConnection db = new DBConnection();
	
	/**
	 * This method add a vote to the database by executing the given query.
	 * @param query is an insertion sql query with the relevant information.
	 * @return true if the query was written successful on the database
	 */
	public boolean addVote(String query) {
		return db.addVote(query);
	}
	
	/**
	 * This method return an object with the relevant information to this polling desk
	 * @return an AreaInfo object containing the information about the election. 
	 */
	public AreaInfo getInfo() {
		return db.getInfo();
	}
	

}
