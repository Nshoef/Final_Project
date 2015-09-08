package userTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import userInterface.DeskUser;
import userInterface.MainSystemUser;
import userInterface.StationManagerUser;

public class Test {
	private static String url1 = "jdbc:mysql://localhost:3306/main_server_db";
	private static String url2 = "jdbc:mysql://localhost:3306/polling_server_db";
	private static String user = "root";
	private static String pass = "noam83";

	public static void main(String[] args) throws SQLException, FileNotFoundException {
		Connection con = DriverManager.getConnection(url1, user, pass);
		con.createStatement().execute("delete from electing_system");
		con.createStatement().execute("delete from election");
		con.createStatement().execute("delete from area");
		con.createStatement().execute("delete from candidates");
		con.createStatement().execute("delete from running_election");
		con.createStatement().execute("delete from votes");
		con = DriverManager.getConnection(url2, user, pass);
		con.createStatement().execute("delete from info");
		con.createStatement().execute("delete from candidates");
		con.createStatement().execute("delete from votes");
		
		
		MainSystemUser main = new MainSystemUser(new FileInputStream("./src/userTest/mainInput.txt"));
		StationManagerUser station = new StationManagerUser(new FileInputStream("./src/userTest/stationInput.txt"));
		DeskUser desk1 = new DeskUser(new FileInputStream("./src/userTest/desk1Input.txt"));
		DeskUser desk2 = new DeskUser(new FileInputStream("./src/userTest/desk2Input.txt"));
		
		main.run();
		station.run();
		//desk1.run();
		
		//desk2.run();
	

	}

}
