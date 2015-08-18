package manager;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Scanner;
import services.Area;
import services.AreaResult;
import services.ManagerServiceProxy;


public class Manager {
	private Scanner in;
	private ManagerServiceProxy proxy;
	
	
	
	public Manager() {
		proxy = new ManagerServiceProxy();
		
	}

	public boolean createNewSystem(InputStream input) {
		try {
			in = new Scanner(input);
			String name = in.nextLine();
			int numOfArea = in.nextInt();
			proxy.setNewElectingSystem(name, numOfArea);
			for(int i = 0; i<numOfArea; i++) {
				String areaName = in.nextLine();
				int novpv = in.nextInt();
				boolean isRanked = in.hasNextBoolean();
				proxy.setNewArea(areaName, name, novpv, isRanked);
			}	
			return true;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;	
	}
	
	
	public Area[] getSystem(String name) {
		try {
			return proxy.getSystem(name);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean createNewElection(String name, String electingSystemName, InputStream input) {
		try {
			in = new Scanner(input);
			proxy.setNewElection(name, electingSystemName);
			Area[] system = getSystem(electingSystemName);
			for(Area area : system) {
				int numOfCans = in.nextInt();
				String[] cans = new String[numOfCans];
				for(String s : cans) {
					s = in.nextLine();
				}
				proxy.setCandidates(cans, electingSystemName, area.getName());	
			}
			return true;	
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;	
	}
	
	public boolean setCurrentElection(String name) {
		try {
			return proxy.setCurrentElection(name);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public AreaResult[] getResult(String election) {
		try {
			return proxy.getResult(election);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static void main(String[] args) {
		Manager m = new Manager();
	}
}
	

