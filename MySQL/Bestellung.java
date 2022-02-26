package Projekt_mit_foreign_key_mySQL;

import java.sql.*;
public class Bestellung {
	
	static void createTable(Connection c, String tableName) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists " + tableName +
						" (Datum varchar(10), KundenID integer, " +
						"ArtikelID integer, anzahl integer, foreign key (KundenID) references Kunde(id), " +
						"foreign key (ArtikelID) references Artikel(ID), primary key(KundenID,ArtikelID,Datum));";
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
		if(rs.next())
		{
			lagerbestand = rs.getInt("Lagerbestand");
		}
		rs.close();
		stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lagerbestand;
	}
	
	static void insertInto(Connection c, String tableName, String Datum, int KundenID, int ArtikelID, int anzahl) {
		int strKid = 0;
		int strAid = 0;
		String strDid = "";
		try {
			Statement stmt = c.createStatement();
			String sql = "insert into " + tableName + " (Datum,KundenID, ArtikelID, anzahl) values" +
						" (\"" + Datum + "\", " + KundenID + ", " + ArtikelID + ", " + anzahl + ");";
			stmt.executeUpdate(sql);
			ResultSet rs = stmt.executeQuery("select KundenID,ArtikelID,Datum from Bestellung where KundenID = " + KundenID + " and ArtikelID = " + ArtikelID + " and Datum = \"" + Datum + "\";");
			while(rs.next())
			{
				strKid = rs.getInt("KundenID");
				strAid = rs.getInt("ArtikelID");
				strDid = rs.getString("Datum");
			}
			rs.close();
			stmt.close();
			System.out.println();
			System.out.println("Bestellung:    BestellungID: " + strKid +","+ strAid +","+ strDid + "    KundenID: " + KundenID + "    ArtikelID: " +
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
			String strBezeichnung ="";
			if(rs.next())
			{
				strBezeichnung = rs.getString("bezeichnung");
			}
			rs.close();
			ResultSet rs2 = stmt.executeQuery("select name from Kunde where id = " + KundenID + ";");
			String strName ="";
			if(rs2.next())
			{
				strName = rs2.getString("name");
			}
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
	
	static void deleteBestellung(Connection c, String tableName, String Datum, int KundenID, int ArtikelID) {
		try {
			Statement stmt = c.createStatement();
			String sql = "delete from " + tableName + " where KundenID = " + KundenID + " and ArtikelID = " + ArtikelID + " and Datum = \"" + Datum + "\";";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void updateBestellung(Connection c, String tableName, String Datum, int KundenID, int ArtikelID, int anzahl) {
		try {
			Statement stmt = c.createStatement();
			String sql = "update " + tableName + " set anzahl = " +anzahl+ " where KundenID = " + KundenID + " and ArtikelID = " + ArtikelID + " and Datum = \"" + Datum + "\";";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}