package Schueler_Klasse_Select_in_CSV_Datei;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;


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
		String url = "jdbc:mysql://localhost:3306/schueler_klasse_select_in_csv_datei";
		String user = "tobias";
		String password = "t250904";
		
		try {	
		Connection c = getConnection(url, user, password);
		
		dropTable(c,"SchuelerKlasse");
		dropTable(c,"Schueler");
		dropTable(c,"Klasse");
		
		System.out.println();
		Klasse.createTable(c,"Klasse");
		Schueler.createTable(c,"Schueler");
		SchuelerKlasse.createTable(c,"SchuelerKlasse");
		
		Klasse.insertInto(c,"3AHWII",25);
		Klasse.insertInto(c,"2AHWII",27);
		Klasse.insertInto(c,"1AHWII",30);
		Klasse.Klasse_Writer(c,"D:\\Schule HTL 3.Klasse\\Informatik\\Klasse1.csv");
		
		Schueler.insertInto(c,"Hauser Tobias",LocalDate.of(2004,9,25),17,"freundlich");
		Schueler.insertInto(c,"Stern Julian",LocalDate.of(2006,6,12),15,"hilfsbereit");
		Schueler.insertInto(c,"Hoertnagl Jonas",LocalDate.of(2006,5,27),15,"lernwillig");
		Schueler.Schueler_Writer(c,"D:\\Schule HTL 3.Klasse\\Informatik\\Schueler1.csv");
		
		SchuelerKlasse.insertInto(c,"Hauser Tobias","3AHWII");
		SchuelerKlasse.insertInto(c,"Stern Julian","1AHWII");
		SchuelerKlasse.insertInto(c,"Hoertnagl Jonas","1AHWII");
		SchuelerKlasse.Schueler_Klasse_Writer(c,"D:\\Schule HTL 3.Klasse\\Informatik\\SchuelerKlasse1.csv");
		
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
