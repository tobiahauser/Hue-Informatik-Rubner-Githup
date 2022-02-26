package Projekt_mit_foreign_key_mySQL;

import java.sql.*;

class Kunde {
	
	static void createTable(Connection c, String tableName) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists " + tableName +
						" (id integer primary key auto_increment, name varchar(30), " +
						"email varchar(40));";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	static void insertInto(Connection c, String tableName, String name, String email) {
		try {
			Statement stmt = c.createStatement();
			String sql = "insert into " + tableName + " (name, email) values " +
						"(\"" + name + "\", \"" + email + "\");";
			System.out.println();
			System.out.printf("Kunde:    Name: " + name + "    Email: " + email + "\n");
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}