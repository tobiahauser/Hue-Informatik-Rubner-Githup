package Projekt_mit_foreign_key;

import java.sql.*;
public class Bestellung {
	
	static void createTable(Connection c, String tableName) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists " + tableName +
						" (id integer primary key autoincrement, KundenID integer, " +
						"ArtikelID integer, anzahl integer, foreign key (KundenID) references Kunde(id), " +
						"foreign key (ArtikelID) references Artikel(ID));";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static int lagerbestandvorhanden(Connection c, int ArtikelID) {
		int lagerbestand = 0;
		try
		{
		Statement stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("select Lagerbestand from Artikel where id = " + ArtikelID + ";");
		lagerbestand = rs.getInt("Lagerbestand");
		rs.close();
		stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lagerbestand;
	}
	
	static void insertInto(Connection c, String tableName, int KundenID, int ArtikelID, int anzahl) {
		try {
			Statement stmt = c.createStatement();
			String sql = "insert into " + tableName + " (KundenID, ArtikelID, anzahl) values" +
						" (" + KundenID + ", " + ArtikelID + ", " + anzahl + ");";
			stmt.executeUpdate(sql);
			ResultSet rs = stmt.executeQuery("select id from Bestellung where KundenID = " + KundenID + ";");
			int strid = rs.getInt("id");
			rs.close();
			stmt.close();
			System.out.println();
			System.out.println("Bestellung:    BestellungID: " + strid + "    KundenID: " + KundenID + "    ArtikelID: " +
								ArtikelID + "    Anzahl: " + anzahl);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void select(Connection c, String tableName , int KundenID, int ArtikelID, int anzahl, String bezeichnung, int preis) {
		int lagerbestand = lagerbestandvorhanden(c, ArtikelID);
		if (lagerbestand >= anzahl)
		{
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("select bezeichnung from Artikel where id = " + ArtikelID + ";");
			String strBezeichnung = rs.getString("bezeichnung");
			rs.close();
			ResultSet rs2 = stmt.executeQuery("select name from Kunde where id = " + KundenID + ";");
			String strName = rs2.getString("name");
			rs2.close();
			stmt.close();
			System.out.println();
			System.out.println("Bestellung:    Name: " + strName + "    Bezeichnung: " +
								strBezeichnung + "    Anzahl: " + anzahl);
			Artikel.insertIntoMitLagerbestand(c,"Artikel", bezeichnung, preis, lagerbestand-anzahl);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
		else
		{
			System.out.println();
			System.out.println("Lagerbestand ist zu klein !!!");
		}
	}
	
	static void deleteBestellung(Connection c, String tableName, int Id) {
		try {
			Statement stmt = c.createStatement();
			String sql = "delete from " + tableName + " where id = " + Id + ";";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void updateBestellung(Connection c, String tableName, int Id, int anzahl) {
		try {
			Statement stmt = c.createStatement();
			String sql = "update " + tableName + " set anzahl = " +anzahl+ " where id = " + Id + ";";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}