package Projekt_Traktordatenbank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Traktor {

	static void createTable(Connection c, String tableName) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists " + tableName +
					" (id integer, modell varchar(255), marke varchar(255), leistung varchar(255), preis varchar(255), primary key(id, modell, marke));";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static void insertInto(Connection c, String[] einfuegen, String name) {
		int TraktorID = 0;
		String modell = "";
		String marke = "";
		String leistung = "";
		String preis = "";
		try {
			String sql = "insert into Traktor values (?, ?, ?, ?, ?);";
			PreparedStatement preparedStmt = c.prepareStatement(sql);
			preparedStmt.setInt(1,Integer.parseInt(einfuegen[0]));
			preparedStmt.setString(2,einfuegen[1]);
			preparedStmt.setString(3,einfuegen[2]);
			preparedStmt.setString(4,einfuegen[3]);
			preparedStmt.setString(5,einfuegen[4]);
			preparedStmt.executeUpdate();
			System.out.println();
			ResultSet rs = preparedStmt.executeQuery("select id,modell,marke,leistung,preis from Traktor where id = " + einfuegen[0] + " and modell = \"" + einfuegen[1] + "\" and marke = \"" + einfuegen[2] + "\";");
			while(rs.next())
			{
				TraktorID = rs.getInt("id");
				modell = rs.getString("modell");
				marke = rs.getString("marke");
				leistung = rs.getString("leistung");
				preis = rs.getString("preis");
			}
			rs.close();
			System.out.printf("Traktor:    ID: " + TraktorID + "    Modell: " + modell + "    marke: " + marke + "    leistung: " + leistung + "    preis: " + preis + "\n");
			System.out.println("Der Datensatz wurden der Klasse " + name + " hinzugefügt!");
			preparedStmt.close();
		} catch (SQLException e) {
			try
			{
				Statement stmt = c.createStatement();	
				ResultSet rs = stmt.executeQuery("select id,modell,marke,leistung,preis from Traktor where id = " + einfuegen[0] + " and modell = \"" + einfuegen[1] + "\" and marke = \"" + einfuegen[2] + "\";");
				while(rs.next())
				{
					TraktorID = rs.getInt("id");
					modell = rs.getString("modell");
					marke = rs.getString("marke");
					leistung = rs.getString("leistung");
					preis = rs.getString("preis");
				}
				rs.close();

				if (Integer.valueOf(einfuegen[0]).equals(TraktorID) && einfuegen[1].equals(modell) && einfuegen[2].equals(marke) && einfuegen[3].equals(leistung) && einfuegen[4].equals(preis))
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
