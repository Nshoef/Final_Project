package service;

import java.rmi.RemoteException;

/**
 * 
 * @author Noam
 * This interface represent the connection of the pooling station with the local database. It also interact with main database as
 * a web service client. it has methods that serve both the manager and the desk web services.
 */
public interface DBConnection {
	
	/**
	 * This method add vote to the database.
	 * @param cans is the candidates which the voter chooses.
	 * @return true if the vote has successfully written in the database.
	 */
	public boolean addVote(String[] cans);
	
	/**
	 * This method retrieve the relevant information form the database and return it as an Object
	 * @return an AreaInfo object contains the relevant information about the election.
	 */
	public AreaInfo getInfo();
	
	/**
	 * This method use to update the database with the relevant information for this station.
	 * @param area is the name of the area of this polling station.
	 * @param name is the name of the polling station.
	 * @return true if the database was updated successfully.
	 * @throws RemoteException
	 */
	public boolean updateInfo(String area, String name);
	
	/**
	 * This method return the result of the given candidate. 
	 * The length of the array depend on the number of votes per voter so that it possible to deal with ranked system.
	 * @param can the candidate whom the result are refers to.
	 * @return array of number of votes
	 */
	public Integer[] getResult(String can);
	
	/**
	 * This method send all the votes from the local database to the main database
	 * @return
	 * @throws RemoteException
	 */
	public boolean sendResults() throws RemoteException;
	
	/**
	 * This method send all the votes in the station to the main database and remove all the information from the database.
	 * @return
	 */
	public boolean closeStation();
}
