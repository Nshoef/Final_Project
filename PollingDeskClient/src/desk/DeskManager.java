package desk;

import java.rmi.RemoteException;

import service.AreaInfo;

public interface DeskManager {
	
	/**
	 * this method is use to get the information about the election
	 * @return an object with the relevant information about the election
	 */
	public AreaInfo getInfo();
	
	/**
	 * This method send a single vote to the polling station database. 
	 * @param cans the elected candidate/s. should be in ascending order if they are ranked (first is the most proffered choice)
	 * @return true if the vote was written successfully on the database.
	 * @throws RemoteException
	 */
	public boolean vote(String[] cans); 

}
