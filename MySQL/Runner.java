package Projekt_mit_foreign_key_mySQL;

import java.sql.*;

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
		String url = "jdbc:mysql://localhost:3306/kunde_artikel";
		String user = "root";
		String password = "";
		
		Connection c = getConnection(url, user, password);
		
		dropTable(c, "Bestellung");
		dropTable(c, "Kunde");
		dropTable(c, "Artikel");
		
		Kunde.createTable(c, "Kunde");
		Artikel.createTable(c, "Artikel");
		Bestellung.createTable(c, "Bestellung");
		
		Kunde.insertInto(c, "Kunde", "Tobias", "tobiahauser@tsn.at");
		Kunde.insertInto(c, "Kunde", "Julian", "jujungmann@tsn.at");
		
		Artikel.insertInto(c, "Artikel", "Kappe", 20);
		Artikel.insertInto(c, "Artikel", "Schuhe", 30);
		
		Artikel.UpdateTable(c, "Artikel");
		
		Artikel.insertIntoMitLagerbestand(c, "Artikel", "Kappe", 20, 50);
		Artikel.insertIntoMitLagerbestand(c, "Artikel", "Schuhe", 30, 100);
		
		Bestellung.insertInto(c, "Bestellung", "2022-04-12", 1, 1, 3);
		Bestellung.insertInto(c, "Bestellung", "2022-06-17", 2, 2, 5);
		Bestellung.insertInto(c, "Bestellung", "2022-05-21", 1, 1, 51);
		
		Bestellung.select(c, "Bestellung", 1, 1, 3, "Kappe", 20);
		Bestellung.select(c, "Bestellung", 2, 2, 5, "Schuhe", 30);
		Bestellung.select(c, "Bestellung", 1, 1, 7, "Kappe", 20);
		Bestellung.select(c, "Bestellung", 1, 1, 41, "Kappe", 20);
		
		Bestellung.deleteBestellung(c, "Bestellung", "2022-04-12", 1, 1);
		
		Bestellung.updateBestellung(c, "Bestellung", "2022-06-17", 2, 2, 12);
			
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