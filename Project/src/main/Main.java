package main;

import userInterface.DeskUser;
import userInterface.MainSystemUser;
import userInterface.StationManagerUser;

public class Main {
	public static void main(String[] args) {

		switch(args[0]) {
		case "main" : new MainSystemUser(System.in).run();
		return;
		case "station" : new StationManagerUser(System.in).run();
		return;
		case "desk" : new DeskUser(System.in).run();
		return;
		default : System.out.println("invalid input");
		}
		
	}

}
