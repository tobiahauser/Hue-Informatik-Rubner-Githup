package Autoverleih_CSV_Einlesen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Auto {
	
	static void createTable(Connection c, String tableName) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists " + tableName +
						" (Kennzeichen varchar(10), Marke varchar(40), Klasse varchar(10), Leistung integer, Preis integer, primary key(kennzeichen));";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void insertInto(Connection c, String[] einfuegen) {
		try {
			String sql = "insert into Auto values (?, ?, ?, ?, ?);";
			PreparedStatement preparedStmt = c.prepareStatement(sql);
			
			preparedStmt.setString(1,einfuegen[0]);
			preparedStmt.setString(2,einfuegen[1]);
			preparedStmt.setString(3,einfuegen[2]);
			preparedStmt.setInt(4,Integer.parseInt(einfuegen[3]));
			preparedStmt.setInt(5,Integer.parseInt(einfuegen[4]));
			preparedStmt.executeUpdate();
			System.out.println();
			System.out.printf("Auto:    Kennzeichen: " + einfuegen[0] + "    Marke: " + einfuegen[1] + "    Klasse: " + einfuegen[2] + "    Leistung: " + einfuegen[3] + "    Preis: " + einfuegen[4] + "\n");
			preparedStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
