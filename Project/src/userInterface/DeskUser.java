package userInterface;

import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import desk.DeskManager;
import desk.DeskManagerImpl;
import service.DeskServiceProxy;

/**
 * This class is a simple use interface for a desk in polling station.
 * @author Noam
 *
 */
public class DeskUser implements Runnable{
	public static Scanner in;
	public DeskManager desk;
	
	

	public DeskUser(InputStream input) {
		in = new Scanner(input);
	}
	
	/**
	 * This method initialised the desk and start the main menu.
	 */
	public void run() {
		boolean check = false;
		while(!check) {
			try {
				System.out.println("Enter a number for this desk: ");
				int num = Integer.parseInt(in.nextLine());
				desk = new DeskManagerImpl(num, new DeskServiceProxy());
				check = true;
			} catch (NumberFormatException e) {
				System.out.println("Illegal input, try again");
			} catch (RemoteException e) {
				System.out.println("There was a connection error or polling station is not open");
				e.printStackTrace();
				return;
			}
		}
		while(check) {
			try {
				System.out.println("press 1 to start voting, 0 to close this station");
				int num = Integer.parseInt(in.nextLine());
				if(num == 0) {
					System.out.println("Byebye");
					return;
				} else if (num == 1) {
					vote();
				} else { 
					System.out.println("Illegal input");
				}	
			} catch (NumberFormatException e) {
				System.out.println("Illegal input");
			}
		}
	}
		
	/**
	 * This method run a voting process according to the user input.	
	 */
	public void vote() {
		boolean run = true;
		while(run) {
			System.out.println("Welcome to the "+desk.getInfo().getElectionName()+" elections");
			System.out.println("In this election you are requiered to choose "+ desk.getInfo().getNumOfVotePerVoter()+ " candidate/s");
			if(desk.getInfo().getNumOfVotePerVoter()>1) {
				if(desk.getInfo().isRanked()) {
					System.out.println("You should place them in order where the first candidate "
							+ "is your preffered choice and the last is the least preferred");
				} else {
					System.out.println("There is no meanning to the order you place the candidates");
				}
			}
			List<String> leftCans = new LinkedList<String>();
			System.out.println("The candidates for this election are: ");
			for(String s : desk.getInfo().getCanNames()) {
				System.out.println(s);
				leftCans.add(s);
			}
			String[] cans = new String[desk.getInfo().getNumOfVotePerVoter()];
			for(int i=0; i<cans.length; i++) {
				boolean check = false;
				while(!check) {
					try {
						int num = i+1;
						System.out.println(num+"st vote:");
						for(int j=0; j<leftCans.size(); j++) {
							num = j+1;
							System.out.println(num+"- "+ leftCans.get(j));	
						}
						System.out.println("enter the number of your choice: ");
						int choice = Integer.parseInt(in.nextLine());
						
						cans[i] = leftCans.get(choice-1);
						leftCans.remove(choice-1);
						check = true;
					} catch (NumberFormatException | IndexOutOfBoundsException e) {
						System.out.println("unaccepted input, try again");
					}
				}
			}
			System.out.println("this is your choice: ");
			for(String s : cans) {
				System.out.println(s);
			}
			boolean check = false;
			while(!check) {
				try {
					System.out.println("press 1 to confirm, 0 to start again");
					int num = Integer.parseInt(in.nextLine());
					if(num == 1) {
						boolean result = desk.vote(cans);
						if (result) {
							run = false;
							check = true;
							System.out.println("Your vote has been accepted, thank you for voting!");
							
						} else {
							System.out.println("There was a connection error, please try again");
							check = true;
						}
					} else if (num == 0) {
						check = true;
					} else {
						System.out.println("Illegal input");	
					}
				} catch (NumberFormatException | IndexOutOfBoundsException e ) {
					System.out.println("Illegal input");
				}	
			}
		}	
	}
}
