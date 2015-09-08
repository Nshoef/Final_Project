package desk;

import java.rmi.RemoteException;

import service.AreaInfo;
import service.DeskService;

/**
 * 
 * @author Noam
 * This class represent a single polling desk in a polling station
 * Desks should be numbered by the station manager so that each desk has it's own number
 * id is a unique identifier for the vote 
 */
public class DeskManagerImpl implements DeskManager {
	private DeskService ws;
	private AreaInfo info;

	/**
	 * This constructor initialised the desk with it's connection to the polling station and it's information.
	 * @param num a unique number of the desk
	 * @param ds the web service which connect with the station database.
	 * @throws RemoteException if the connection with the database failed.
	 */
	public DeskManagerImpl(int num, DeskService ds) throws RemoteException {
		ws = ds;
		if(ws.getInfo() != null) {
		    info = ws.getInfo();
		} else {
			throw new RemoteException();
		}
	}
	
	@Override
	public AreaInfo getInfo() {
		return info;
	}
	
	@Override
	public boolean vote(String[] cans) {
		try {
			if(info.getNumOfVotePerVoter() == cans.length) {
				if(confirmCans(cans)) {
					return ws.addVote(cans);
				}		
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * This is a helper method which check if the candidates in the list exist in the current election and area.
	 * @param cans is the candidates to be checked.
	 * @return true if they are all exist.
	 */
	private boolean confirmCans(String[] cans) {
		for(String s : cans) {
			boolean check = false;
			for(String j : info.getCanNames()) {
				if(s.equals(j)) {
					check = true;
				}
			}
			if(!check) {
				return false;
			}
		}
		return true;
	}
}
