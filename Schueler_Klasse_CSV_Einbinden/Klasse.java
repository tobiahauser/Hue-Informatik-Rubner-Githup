package Schueler_Klasse_CSV_Einlesen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

class Klasse {
	
	static void createTable(Connection c, String tableName, String[] einfuegen) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists " + tableName +
						" (" + einfuegen[0] + " primary key," + einfuegen[1] + "," + einfuegen[2] + ");";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	static void insertInto(Connection c, String[] einfuegen) {
		try {
			String sql = "insert into Klasse values (?, ?, ?);";
			PreparedStatement preparedStmt = c.prepareStatement(sql);
			preparedStmt.setInt(1,Integer.parseInt(einfuegen[0]));
			preparedStmt.setString(2,einfuegen[1]);
			preparedStmt.setInt(3,Integer.parseInt(einfuegen[2]));
			preparedStmt.executeUpdate();
			
			System.out.println();
			System.out.printf("Klasse:    ID: " + einfuegen[0] + "    Name: " + einfuegen[1] + "    anzSchueler: " + einfuegen[2] + "\n");
			preparedStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
