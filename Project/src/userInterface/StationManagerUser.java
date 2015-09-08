package userInterface;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Scanner;

import manager.StationManager;
import manager.StationManagerImpl;
import service.ManagerServiceProxy;

public class StationManagerUser implements Runnable{
	private static Scanner in;
	private StationManager manager;
	
	
	

	public StationManagerUser(InputStream input) {
		in = new Scanner(input);	
	}
	
	
	/**
	 * This method run the main menu of the pooling station.
	 */
	public void run() {
		Boolean check = true;
		while(check) {
			System.out.println("Welcome to the station manager, please choose: ");
			try {
				System.out.println("1. Run a new polling station \n2. Send result to main server \n3. View result \n4. Close this station \n0. Exit");
				int ans = Integer.parseInt(in.nextLine());
				if(ans==0) {
					check = false;
				} else if (ans==1) {
					runNewStation();
				} else if (ans==2) {
					sendResult();
				} else if (ans==3) {
					viewResult();
				} else if (ans==4) {
					closeStation();
				} else {
					System.out.println("Illegal input");
				}
			} catch (NumberFormatException e) {
				System.out.println("Illegl input");
			}
		}
	}
	
	/**
	 * This method run the initialisations of a new station according to the user input.
	 * @throws RemoteException
	 */
	public void runNewStation() {
		System.out.println("Opening a new station");
		System.out.println("Enter a station name: ");
		String name = in.nextLine();
		System.out.println("Enter the area of this station (make sure you enter the exact name)");
		String area = in.nextLine();
		try {
			manager = new StationManagerImpl(name, area, new ManagerServiceProxy());
			System.out.println("Getting information and updates from main server...");
			if(!manager.updateInfo()) {
				System.out.println("Connection problem or wrong area entry");
				boolean check = false;
				while(!check) {
					try {
						System.out.println("press 1 to try again, 0 to exit");
						int choice = Integer.parseInt(in.nextLine());
						if (choice == 0 ) {
							check = true;
						} else if (choice == 1) {
							runNewStation();
							check = true;
						} else {
							System.out.println("Illegal enty, try again");
						}
					} catch (NumberFormatException e) {
						System.out.println("Illegal enty, try again");
					}
				}		
			} else {
				System.out.println("System updated station "+name+" in area "+area+" is open");
			} 
			
		} catch (RemoteException e) {
			System.out.println("Connection problem, try again");
		}
		
	}
	
	/**
	 * This method send the results from the local station database to the main database.
	 */
	public void sendResult() {
		try {
			manager = new StationManagerImpl(new ManagerServiceProxy());
			if(manager.sendResults()){
				System.out.println("Votes sent successfully to the main database");
			} else {
				System.out.println("Something went wrong, try again");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}  catch (NullPointerException e) {
			System.out.println("No election found");
		}
	}
	
	/**
	 * This method send the result to the main database and remove the information from the local database.
	 */
	public void closeStation() {
		try{
			manager.getInfo();
		} catch (NullPointerException e) {
			System.out.println("System is not open");
			return;
		}
		System.out.println("This option will clear all the information on the database, are you sure? y/n");
		boolean check = false;
		while(!check) {
			String ans = in.nextLine();
			if(ans.equals("y")) {
				if(manager.sendResults()) {
					manager.closeStation();
				}
				check = true;
			} else if (ans.equals("n")) {
				check = true;
			} else {
				System.out.println("Illegal input");
			}	
		}
	}
	
	/**
	 * this method view the results of this station.
	 */
	private void viewResult() {
		try {
			manager = new StationManagerImpl(new ManagerServiceProxy());
			Map<String, Integer[]> result = manager.getResutls();
			for(String s : result.keySet()) {
				System.out.print("Can "+s+": ");
				for(int i=0; i<result.get(s).length; i++) {
					int num = i+1;
					System.out.print(" V"+num+"["+ result.get(s)[i]+"] ");	
				}
				System.out.println();
			}
		} catch (RemoteException e) {
			System.out.println("Connection problems, try again");
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("No election found");
		}
	}
}