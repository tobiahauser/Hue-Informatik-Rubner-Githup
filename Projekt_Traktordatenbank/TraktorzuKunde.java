package Projekt_Traktordatenbank;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class TraktorzuKunde {

	static void createTable(Connection c, String tableName) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists " + tableName + "(Anfangsdatum Date, Enddatum Date, TraktorID integer, KundenID integer, foreign key(KundenID) references Kunde(id), foreign key(TraktorID) references Traktor(id), primary key(TraktorID,KundenID,Enddatum));";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static void insertInto(Connection c, String tableName, LocalDate enddatum,String modell, String marke, int traktorid,String vorname, String nachname, int kundenid) {
		int KundenID = 0;
		int TraktorID = 0;
		int primary_KundenID = 0;
		int primary_TraktorID = 0;
		Date primary_Anfangsdatum = null;
		Date primary_Enddatum = null;
		LocalDate Datum = LocalDate.now();
		java.sql.Date Zeit = java.sql.Date.valueOf(Datum);
		java.sql.Date Zeit2 = java.sql.Date.valueOf(enddatum);
		try {
			String sql = "insert into " + tableName + " values (?, ?, ?, ?);";
			PreparedStatement preparedStmt = c.prepareStatement(sql);

			ResultSet rs = preparedStmt.executeQuery("select id from Kunde where vorname = \"" + vorname + "\" and nachname = \"" + nachname + "\" and id = " + kundenid + ";");
			while(rs.next())
			{
				KundenID = rs.getInt("id");
			}
			rs.close();

			ResultSet rs2 = preparedStmt.executeQuery("select id from Traktor where modell = \"" + modell + "\" and marke = \"" + marke + "\" and id = " + traktorid + ";");
			while(rs2.next())
			{
				TraktorID = rs2.getInt("id");
			}
			rs2.close();

			preparedStmt.setDate(1,Zeit);
			preparedStmt.setDate(2,Zeit2);
			preparedStmt.setInt(3,TraktorID);
			preparedStmt.setInt(4,KundenID);
			preparedStmt.executeUpdate();

			System.out.println();
			System.out.printf("TraktorZuKunde:    Anfangsdatum: " + Zeit + "    Enddatum: " + Zeit2 + "    KundenID: " + KundenID + "    TraktorID: " + TraktorID + "\n");
			System.out.println("Die Datensätze wurden der Klasse " + tableName + " hinzugefügt!");
			preparedStmt.close();
		} catch (SQLException e) {
			try
			{
				Statement stmt = c.createStatement();	
				ResultSet rs3 = stmt.executeQuery("select Anfangsdatum,Enddatum,TraktorID,KundenID from " + tableName + " where KundenID = " + KundenID + " and TraktorID = " + TraktorID + ";");
				while(rs3.next())
				{
					primary_Anfangsdatum = rs3.getDate("Anfangsdatum");
					primary_Enddatum = rs3.getDate("Enddatum");
					primary_TraktorID = rs3.getInt("TraktorID");
					primary_KundenID = rs3.getInt("KundenID");
				}
				rs3.close();
				stmt.close();
				if (Zeit.equals(primary_Anfangsdatum) && Zeit2.equals(primary_Enddatum) && Integer.valueOf(primary_TraktorID).equals(TraktorID) && Integer.valueOf(primary_KundenID).equals(KundenID))
				{
					System.out.println("Diese Datensätze wurden schon verknüpft!");
					System.out.println();
				}
				else
				{
					String sql = "insert into " + tableName + " values (?, ?, ?, ?);";
					PreparedStatement preparedStmt = c.prepareStatement(sql);

					preparedStmt.setDate(1,primary_Anfangsdatum);
					preparedStmt.setDate(2,primary_Enddatum);
					preparedStmt.setInt(3,primary_TraktorID);
					preparedStmt.setInt(4,primary_KundenID);
					preparedStmt.executeUpdate();
					preparedStmt.close();
					System.out.printf("TraktorZuKunde:    Anfangsdatum: " + primary_Anfangsdatum + "    Enddatum: " + primary_Enddatum + "    KundenID: " + KundenID + "    TraktorID: " + TraktorID + "\n");
					System.out.println("Der Datensatz wurden der Klasse " + tableName + " hinzugefügt!");
					System.out.println();
				}
			}
			catch(SQLException d)
			{
				d.printStackTrace();
			}
		}
	}
}
