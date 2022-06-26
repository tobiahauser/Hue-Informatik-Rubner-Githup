package Projekt_Traktordatenbank;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Kunde {

	static void createTable(Connection c, String tableName) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists " + tableName +
					" (id integer, vorname varchar(255), nachname varchar(255), jahre integer, geburtsdatum varchar(255), primary key(id, vorname, nachname));";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static void insertInto(Connection c, String[] einfuegen, String tableName, String name) {
		int KundenID = 0;
		String vorname = "";
		String nachname = "";
		int jahre = 0;
		String geburtsdatum = "";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		LocalDate Datum = LocalDate.parse(einfuegen[4], dtf);
		java.sql.Date Zuordnungszeitpunkt = java.sql.Date.valueOf(Datum);
		try {
			String sql = "insert into " + tableName + " values (?, ?, ?, ?, ?);";
			PreparedStatement preparedStmt = c.prepareStatement(sql);

			preparedStmt.setInt(1,Integer.parseInt(einfuegen[0]));
			preparedStmt.setString(2,einfuegen[1]);
			preparedStmt.setString(3,einfuegen[2]);
			preparedStmt.setInt(4,Integer.parseInt(einfuegen[3]));
			preparedStmt.setDate(5,Zuordnungszeitpunkt);
			preparedStmt.executeUpdate();
			System.out.println();
			ResultSet rs = preparedStmt.executeQuery("select id,vorname,nachname,jahre,geburtsdatum from " + tableName + " where id = " + einfuegen[0] + " and vorname = \"" + einfuegen[1] + "\" and nachname = \"" + einfuegen[2] + "\";");
			while(rs.next())
			{
				KundenID = rs.getInt("id");
				vorname = rs.getString("vorname");
				nachname = rs.getString("nachname");
				jahre = rs.getInt("jahre");
				geburtsdatum = rs.getString("geburtsdatum");
			}
			rs.close();
			System.out.printf("Kunde:    ID: " + KundenID + "    Vorname: " + vorname + "    Nachname: " + nachname + "    Alter: " + jahre + "    Geburtsdatum: " + geburtsdatum + "\n");
			System.out.println("Der Datensatz wurden der Klasse " + name + " hinzugefügt!");
			preparedStmt.close();
		} catch (SQLException e) {
			try
			{
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery("select id,vorname,nachname,jahre,geburtsdatum from " + tableName + " where id = " + einfuegen[0] + " and vorname = \"" + einfuegen[1] + "\" and nachname = \"" + einfuegen[2] + "\";");
				while(rs.next())
				{
					KundenID = rs.getInt("id");
					vorname = rs.getString("vorname");
					nachname = rs.getString("nachname");
					jahre = rs.getInt("jahre");
					geburtsdatum = rs.getString("geburtsdatum");
				}
				rs.close();

				if (Integer.valueOf(einfuegen[0]).equals(KundenID) && einfuegen[1].equals(vorname) && einfuegen[2].equals(nachname) && Integer.valueOf(einfuegen[3]).equals(jahre) && Zuordnungszeitpunkt.equals(Date.valueOf(geburtsdatum)))
				{
					System.out.println("Datensatz befindet sich schon in der Tabelle!");
				}
			}
			catch(SQLException d)
			{
				d.printStackTrace();
			}
		}
	}
}
