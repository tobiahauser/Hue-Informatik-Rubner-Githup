package Projekt_mit_foreign_key_mySQL;

import java.sql.*;

public class Artikel {
	
	static void createTable(Connection c, String tableName) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists " + tableName +
						" (id integer primary key auto_increment, bezeichnung varchar(40)," +
						" preis integer);";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void insertInto(Connection c, String tableName, String bezeichnung, int preis) {
		try {
			Statement stmt = c.createStatement();
			String sql = "insert into " + tableName + " (bezeichnung, preis) values" +
						" (\"" + bezeichnung + "\", " + preis + ");";
			System.out.println();
			System.out.printf("Artikel:    Bezeichnung: " + bezeichnung + "    Preis: " + preis + "\n");
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void UpdateTable(Connection c, String tableName) {
		try {
			Statement stmt = c.createStatement();
			String sql = "Alter table " + tableName + " add Lagerbestand Integer;";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void insertIntoMitLagerbestand(Connection c, String tableName, String bezeichnung, int preis, int Lagerbestand) {
		try {
			Statement stmt = c.createStatement();
			String sql = "Update " + tableName + " set Lagerbestand = " + Lagerbestand + " where bezeichnung = \"" + bezeichnung + "\";";
			System.out.println();
			System.out.printf("Artikel:    Bezeichnung: " + bezeichnung + "    Preis: " + preis + "    Lagerbestand: " + Lagerbestand + "\n");
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}