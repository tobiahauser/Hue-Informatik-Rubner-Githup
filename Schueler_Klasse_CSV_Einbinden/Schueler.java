package Schueler_Klasse_CSV_Einlesen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Schueler {
	
	static void createTable(Connection c, String tableName, String[] einfuegen) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists " + tableName +
						" ("+ einfuegen[0] + " primary key," + einfuegen[1] + "," + einfuegen[2] + "," + einfuegen[3] + "," + einfuegen[4] + ");";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void insertInto(Connection c, String[] einfuegen) {
		try {
			String sql = "insert into Schueler values (?, ?, ?, ?, ?);";
			PreparedStatement preparedStmt = c.prepareStatement(sql);
			
			preparedStmt.setInt(1,Integer.parseInt(einfuegen[0]));
			preparedStmt.setString(2,einfuegen[1]);
			preparedStmt.setString(3,einfuegen[2]);
			preparedStmt.setInt(4,Integer.parseInt(einfuegen[3]));
			preparedStmt.setString(5,einfuegen[4]);
			preparedStmt.executeUpdate();
			System.out.println();
			System.out.printf("Schueler:    ID: " + einfuegen[0] + "    Name: " + einfuegen[1] + "    Geburtsdatum: " + einfuegen[2] + "    Alter: " + einfuegen[3] + "    Beschreibung: " + einfuegen[4] + "\n");
			preparedStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
