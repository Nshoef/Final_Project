package manager;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import service.AreaInfo;
import service.ManagerService;

/**
 * 
 * @author Noam
 * This class represent the manager of the polling station. It has a name, area, and a connection to a web service.
 */
public class StationManagerImpl implements StationManager{
	private String stationName;
	private String area;
	private AreaInfo info;
	private ManagerService service;
	
	/**
	 * This constructor should be used to initialised a station in the database.
	 * @param name is the name of the polling station
	 * @param area is the area of this polling station
	 * @throws RemoteException 
	 */
	public StationManagerImpl(String name, String area, ManagerService ms ) throws RemoteException {
		this.stationName = name;
		this.area = area;
		service = ms;
	}
	
	/**
	 * This constructor should be used when the station already exist in the database.
	 * @throws RemoteException
	 */
	public StationManagerImpl(ManagerService ms) throws RemoteException {
		try{
			service = ms;
			info = service.getAreaInfo();
			stationName = info.getStationName();
			area = info.getArea();
		} catch(NullPointerException e) {
			return;
		}	
	}
	
	@Override
	public boolean updateInfo() {
		try {
			if( service.updateInfo(area, stationName)) {
				info =  service.getAreaInfo();
				return true;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean sendResults() {
		try {
			return service.sentResult();
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public Map<String, Integer[]> getResutls() {
		try {
			String[] cans = info.getCanNames();
			Map<String, Integer[]> result = new HashMap<String, Integer[]>();
			for(String s : cans) {
				int[] votes = service.getResults(s);
				Integer[] iVotes;
				if(votes == null) {
					iVotes = new Integer[0];
				} else {
					iVotes = new Integer[votes.length];
				}
				for(int i=0; i<iVotes.length; i++) {
					iVotes[i] = votes[i];
				}
				result.put(s, iVotes);
			}
			return result;	
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	@Override
	public AreaInfo getInfo() {
		try {
			return service.getAreaInfo();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public boolean closeStation() {
		try {
			return service.closeStation();
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
		
	}
}

