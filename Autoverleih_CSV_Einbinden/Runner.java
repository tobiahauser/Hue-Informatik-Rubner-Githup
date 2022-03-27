package Autoverleih_CSV_Einlesen;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Runner {
	
	static Connection getConnection(String url, String user, String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/autoverleih_csv_einlesen";
		String user = "tobias";
		String password = "t250904";
		
		try {	
		Connection c = getConnection(url, user, password);
		
		dropTable(c, "AutoKunde");
		dropTable(c, "Kunde");
		dropTable(c, "Auto");
		
		Auto.createTable(c,"Auto");
		Kunde.createTable(c,"Kunde");
		AutoKunde.createTable(c,"AutoKunde");
		
		File afile = new File("D:\\Schule HTL 3.Klasse\\Informatik\\Auto.csv");
		File kfile = new File("D:\\Schule HTL 3.Klasse\\Informatik\\Kunde.csv");
		File akfile = new File("D:\\Schule HTL 3.Klasse\\Informatik\\AutoKunde.csv");
		Scanner a = new Scanner(afile);
		Scanner k = new Scanner(kfile);
		Scanner ak = new Scanner(akfile);
		String[] Autofile = new String[5];
		String[] Kundefile = new String[3];
		String[] AutoKundefile = new String[4];
		String string = ""; 

		while (a.hasNextLine()) {
			string = a.nextLine();
			Autofile = string.split(";");
			Auto.insertInto(c,Autofile);
		}
		a.close();
		
		while (k.hasNextLine()) {
			string = k.nextLine();
			Kundefile = string.split(";");
			Kunde.insertInto(c,Kundefile);
		}
		k.close();
		
		while (ak.hasNextLine()) {
			string = ak.nextLine();
			AutoKundefile = string.split(";");
			AutoKunde.insertInto(c,AutoKundefile);
		}
		ak.close();
		c.close();
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void dropTable(Connection c, String tableName) {
		try {
			Statement stmt = c.createStatement();
			String sql = "drop table if exists " + tableName + ";";
			System.out.println("Table " + tableName + " wurde gelöscht.");
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
