package Autoverleih_CSV_Einlesen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

class Kunde {
	
	static void createTable(Connection c, String tableName) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists " + tableName +
						" (id integer primary key, name varchar(30), " +
						"email varchar(40));";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	static void insertInto(Connection c, String[] einfuegen) {
		try {
			String sql = "insert into Kunde values (?, ?, ?);";
			PreparedStatement preparedStmt = c.prepareStatement(sql);
			preparedStmt.setInt(1,Integer.parseInt(einfuegen[0]));
			preparedStmt.setString(2,einfuegen[1]);
			preparedStmt.setString(3,einfuegen[2]);
			preparedStmt.executeUpdate();
			
			System.out.println();
			System.out.printf("Kunde:    KundeID: " + einfuegen[0] + "    Name: " + einfuegen[1] + "    Email: " + einfuegen[2] + "\n");
			preparedStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
