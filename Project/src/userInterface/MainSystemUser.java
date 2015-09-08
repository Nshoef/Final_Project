package userInterface;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import manager.Manager;
import manager.ManagerImpl;
import services.Area;
import services.ManagerServiceProxy;
/**
 * This class is a simple user interface to the main manager part of the system.
 * @author Noam
 *
 */
public class MainSystemUser implements Runnable{
	private static Scanner in;
	private static Manager manager;
	
	public MainSystemUser(InputStream input) {
		manager = new ManagerImpl(new ManagerServiceProxy());
		in = new Scanner(input);
	}
	public static void main(String[] args) {
		new MainSystemUser(System.in).run();
	}

	
	/**
	 * This method open a main menu and get 
	 */
	public void run() {
		System.out.println("Welcome to your generic voting system");
		boolean work = true;
		while(work) {
			try{
				System.out.println("What would you like to do now: ");
				System.out.println("1. Create new electing System \n2. Create new election \n3. Start a voting process");
				System.out.println("4. End voting process \n5. View results \n0. exit");
				int choice = Integer.parseInt(in.nextLine());
				if(choice==0) {
					System.out.println("Byebye"); 
					work = false;
				} else if(choice==1) {
					createNewSystem();
				} else if (choice==2) {
					createNewElection();
				} else if (choice==3) {
					seCurrentElection();
				} else if (choice==4) {
					if(manager.endRunningElection()) {
						System.out.println("Election Stoped!");
					}
				} else if (choice==5) {
					viewResult();
				} else {
					System.out.println("Illegal input");
				}
			} catch (NumberFormatException e) {
				System.out.println("Illegal input");
			}
			
		}
	}
	
	/**
	 * This method create a new system according to the user input.
	 */
	private void createNewSystem() {
		boolean check = false;
		String name = "";
		while(!check) {	
			System.out.println("Enter a name for the system: ");
			name = in.nextLine();
			String[] existSystems = manager.getSavedSystems();
			if(existSystems != null) {
				for(String s : existSystems) {
					if(name.equals(s)) {
						System.out.println("This name is already exist, choose another name");
						return;
					}
				}
			}
			check = true;
		}
		int numOfArea = 0;
		while(check) {
			try {
				System.out.println("Enter the number of areas for this system: ");
				numOfArea = Integer.parseInt(in.nextLine());
				if(numOfArea<0) {
					System.out.println("You must have at least 1 area");
				} else {
					check = false;
				}
			} catch (NumberFormatException e) {
				System.out.println("Illegal input");
			}	
		}
		String[] areas = new String[numOfArea];
		int[] novpvs = new int[numOfArea];
		boolean[] areRanked = new boolean[numOfArea];
		for(int i=0; i<numOfArea; i++) {
			int num = i+1;
			System.out.println("Area no "+num+": enter a name");
			areas[i] = in.nextLine();
			int novpv=0;
			check = false;
			while(!check) {
				try {
					System.out.println("Enter the number of votes which each voter has to choose:");
					novpv = Integer.parseInt(in.nextLine());
					if(novpv<1) {
						System.out.println("Number of votes shoud be at least 1");
					} else {
						novpvs[i] = novpv;
						check = true;
					}	
				} catch (NumberFormatException e) {
					System.out.println("Illegal input");	
				}
			}
			if(novpv>1) {
				check = false;
				while(!check) {
					System.out.println("Enter y/n if the votes shoud be ranked:");
					String ans = in.nextLine();
					if(ans.equals("y")) {
						areRanked[i] = true;
						check = true;
					} else if (ans.equals("n")) {
						areRanked[i] = false;
						check = true;
					} else {
						System.out.println("Illegal Input");
					}	
				}
			} else {
				areRanked[i] = false;
			}
		}
		if(manager.createNewSystem(name, areas, novpvs, areRanked)) {
			System.out.println("System updated successfully!");
		} else {
			System.out.println("Something went wrong!, try again");
		}
	}				
	
	/**
	 * This method create a new election according to the user input.
	 */
	private void createNewElection() {
		try {
			boolean check = false;
			String name = "";
			while(!check) {
				System.out.println("Enter a name for the election: ");
				name = in.nextLine();
				String[] elections = manager.getSavedElectionsNames();
				if(elections != null) {
					for(String s : elections) {
						if(name.equals(s)) {
							System.out.println("Name already exsist, choose another name");
							return;	
						}
					}
				}
				check = true;
			}	
			String system = "";
			while(check) {
				System.out.println("Choose the system you would like to use in these elections: ");
				for(String s : manager.getSavedSystems()) {
					System.out.println(s);
				}
				system = in.nextLine();
				for(String s : manager.getSavedSystems()) {
					if(system.equals(s)) {
						check = false;		
					}
				}
				if (check) {
					System.out.println("You have enter a system which does not exist, try again");
				}		
			}
			Area[] areas = manager.getSystemAreas(system);
			String[] area = new String[areas.length];
			for(int i=0; i<area.length; i++) {
				area[i] = areas[i].getName();
			}
			String[][] cans = new String[area.length][];
			List<String> temp = new ArrayList<String>();
			for(int i=0; i<area.length; i++) {
				int num = i+1;
				System.out.println("Area no "+num+": "+ area[i]);
				String[] last = manager.getLastElectionCans(area[i], system);
				if (last != null) {
					for(String s : last) {
						check = false;
						while(!check) {
							System.out.println("Would you like to leave "+ s +"as a candidate in this area? y/n");
							String ans = in.nextLine();
							if(ans.equals("y")) {
								temp.add(s);
								check = true;
							} else if(ans.equals("n")) {
								check = true;
							} else {
								System.out.println("Illegal input");
							}	
						}		
					}
				}
				check = false;
				while(!check) {
					System.out.println("Would you like to add more candidates? y/n");
					String ans = in.nextLine();
					if(ans.equals("y")) {
						System.out.println("Enter his name: ");
						String newCan = in.nextLine();
						if(temp.contains(newCan)) {
							System.out.println("This candidate aready exist");
						} else {
							temp.add(newCan);
						}
					} else if(ans.equals("n")) {
						check = true;
					} else {
						System.out.println("Illegal input");
					}
				}
				cans[i] = new String[temp.size()];
				for(int j=0; j<temp.size(); j++) {
					cans[i][j] = temp.get(j);
				}
				temp.clear();
			}
			if(manager.createNewElection(name, system, area, cans)) {
				System.out.println("The election has been added to the database!");
			} else {
				System.out.println("Something went wrong, try again");
			}
			
		} catch (NullPointerException e) {
			System.out.println("No electing system found");
			return;
		}	
	}
	
	/**
	 * This method start a new voting process on an election according to the user choice.
	 */
	private void seCurrentElection() {
		if(manager.getRunningElection() != null) {
			System.out.println("Election is already running");
			return;
			}
		try {
			boolean check = false;
			while(!check) {
				System.out.println("These are the saved elections: ");
				for(String s : manager.getSavedElectionsNames()) {
					System.out.println(s);
				}
				System.out.println("Enter the name of the election you would like to start the voting process or 0 to cancel");
				String name = in.nextLine();
				if(name.equals("0")) {
					return;
				}
				if(manager.setCurrentElection(name)) {
					System.out.println(name+" election start now!");
					check = true;
				} else {
					System.out.println("Connection problem or illegal input, make sure you enter the name correctly");
				}
			}
		} catch (NullPointerException e) {
			System.out.println("No election found");
		}	
	}
	
	/**
	 * This method view the result of an election according to the user choice.
	 */
	private void viewResult() { 
		if(manager.getSavedElectionsNames() == null) { // if there are no election in the system
			System.out.println("No elections found");
		} else {
			String[] elections =  manager.getSavedElectionsNames();
			if(elections.length == 0) { //this case not spouse to  as if there are no system it should be null
				System.out.println("No elections found");	
			} else {
				boolean check = false;
				while(!check) {
					System.out.println("Please type the name of the election to be viewed or 0 to exit: ");
					for(String s : manager.getSavedElectionsNames()) {
						System.out.println(s);
					}
					String election  = in.nextLine();
					if(election.equals(0)) {
						check = true;
					} else {
						for(String s : manager.getSavedElectionsNames()) {
							if(election.equals(s)) {
								String system = manager.getSystemElection(election);
								Area[] areas = manager.getSystemAreas(system);
								for(int i=0; i<areas.length; i++) {
									System.out.println("Result for area "+areas[i].getName()+":");
									String[] cans = manager.getCans(election, areas[i].getName());
									for(String c : cans) {
										System.out.print("Candidate "+ c+": ");
										for(int j=1; j<=areas[i].getNumOfVotesPerVoter();j++) {
											System.out.print("Vote "+j+"["+manager.getResult(election, areas[i].getName(), c , j)+"] ");
										}
										System.out.println();
									}
									System.out.println();
								}
								check = true;
								return;
							}
						}
						System.out.println("No such election, type again");
					}
				}
			}
		}
	}
}
