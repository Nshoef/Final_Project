package desk;

import java.rmi.RemoteException;

import service.AreaInfo;
import service.DeskService;
import service.DeskServiceProxy;

/**
 * 
 * @author Noam
 * This class represent a single polling desk in a polling station
 * Desks should be numbered by the station manager so that each desk has it's own number
 * id is a unique identifier for the vote 
 */
public class DeskManager {
	private DeskService ws;
	private AreaInfo info;

	/**
	 * in initialisation of the desk it's automatically asks for the relevant information form the station database
	 * @param num is the number of desk
	 * @throws RemoteException
	 */
	public DeskManager(int num) throws RemoteException {
		ws = new DeskServiceProxy();
		if(ws.getInfo() != null) {
		    info = ws.getInfo();
		} else {
			throw new RemoteException();
		}
	}
	
	/**
	 * this method is use to get the information about the election
	 * @return an object with the relevant information about the election
	 */
	public AreaInfo getInfo() {
		return info;
	}
	
	/**
	 * This method send a single vote to the polling station database. 
	 * @param cans the elected candidate/s. should be in ascending order if they are ranked (first is the most proffered choice)
	 * @return true if the vote was written successfully on the database.
	 * @throws RemoteException
	 */
	public boolean vote(String[] cans) throws RemoteException {
        String query = "INSERT INTO VOTES (ELECTION_NAME, AREA_NAME";
		for(int i=1; i<=info.getNumOfVotePerVoter(); i++) {
			query = query+", VOTE"+i;
		}
		query = query+") VALUES ('"+info.getElectionName()+"', '"+info.getArea();
		for(String s : cans) {
			query = query+"', '"+s;
		}
		query = query+"')";
		ws.addVote(query);
		return true;
	}
}
