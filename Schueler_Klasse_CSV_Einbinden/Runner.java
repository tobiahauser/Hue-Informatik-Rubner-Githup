package Schueler_Klasse_CSV_Einlesen;

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
		String url = "jdbc:mysql://localhost:3306/schueler_klasse_csv_einlesen";
		String user = "tobias";
		String password = "t250904";
		
		try {	
		Connection c = getConnection(url, user, password);
		
		dropTable(c,"Schueler");
		dropTable(c,"Klasse");
		
		File sfile = new File("D:\\Schule HTL 3.Klasse\\Informatik\\Schueler.csv");
		File kfile = new File("D:\\Schule HTL 3.Klasse\\Informatik\\Klasse.csv");
		Scanner s = new Scanner(sfile);
		Scanner k = new Scanner(kfile);
		String[] Schuelerfile = new String[5];
		String[] Klassefile = new String[3];
		String string = ""; 
		int a = 0;
		int b = 0;

		while (s.hasNextLine()) {
			string = s.nextLine();
			Schuelerfile = string.split(";");
			if(a == 0)
			{
			Schueler.createTable(c,"Schueler",Schuelerfile);
			a++;
			}
			else if (a == 1)
			{
			Schueler.insertInto(c,Schuelerfile);
			}
		}
		s.close();
		
		while (k.hasNextLine()) {
			string = k.nextLine();
			Klassefile = string.split(";");
			if (b == 0)
			{
			Klasse.createTable(c,"Klasse",Klassefile);
			b++;
			}
			else if (b == 1)
			{
			Klasse.insertInto(c,Klassefile);
			}
		}
		k.close();
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
