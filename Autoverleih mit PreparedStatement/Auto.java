package Autoverleih;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Auto {
	
	static void createTable(Connection c, String tableName) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists " + tableName +
						" (kennzeichen varchar(10), Marke varchar(40), Klasse varchar(10), Leistung integer, Preis integer, primary key(kennzeichen));";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void insertInto(Connection c, String kennzeichen, String marke, String klasse, int leistung, int preis ) {
		try {
			String sql = "insert into Auto values (?, ?, ?, ?, ?);";
			PreparedStatement preparedStmt = c.prepareStatement(sql);
			
			preparedStmt.setString(1,kennzeichen);
			preparedStmt.setString(2,marke);
			preparedStmt.setString(3,klasse);
			preparedStmt.setInt(4,leistung);
			preparedStmt.setInt(5,preis);
			preparedStmt.executeUpdate();
			System.out.println();
			System.out.printf("Auto:    Kennzeichen: " + kennzeichen + "    Marke: " + marke + "    Klasse: " + klasse + "    Leistung: " + leistung + "    Preis: " + preis + "\n");
			preparedStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
