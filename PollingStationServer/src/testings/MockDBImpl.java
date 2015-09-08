package testings;

import java.rmi.RemoteException;

import service.AreaInfo;
import service.DBConnection;

/**
 * 
 * @author Noam
 * This is a mock implementation of the DBConnection to be used by the web service tests.
 * It always return a valid return type.
 */
public class MockDBImpl implements DBConnection {
	private static AreaInfo info = new AreaInfo("stationName", "electionName", "area", new String[]{ "cans"}, 2, false);

	@Override
	public boolean addVote(String[] cans) {
		return true;
	}

	@Override
	public AreaInfo getInfo() {
		return info;
	}

	@Override
	public boolean updateInfo(String area, String name) {
		return true;
	}

	@Override
	public Integer[] getResult(String can) {
		return new Integer[] { 1,2};
	}

	@Override
	public boolean sendResults() throws RemoteException {
		return true;
	}

	@Override
	public boolean closeStation() {
		return true;
	}
}
