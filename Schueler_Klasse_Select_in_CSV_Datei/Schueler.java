package Schueler_Klasse_Select_in_CSV_Datei;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Schueler {
	
	static void createTable(Connection c, String tableName) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists " + tableName +
						" (id integer auto_increment, name varchar(30), geburtsdatum Date, jahre integer, beschreibung varchar(40), datierung Date, primary key(id,name,geburtsdatum));";
			stmt.executeUpdate(sql);
			System.out.println("Table " + tableName + " wurde erstellt.");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void insertInto(Connection c, String name, LocalDate geburtsdatum, int jahre, String beschreibung) {
		int SchuelerID = 0;
		String Schuelername = "";
		java.sql.Date datum = java.sql.Date.valueOf(geburtsdatum);
		java.sql.Date datierung = java.sql.Date.valueOf(LocalDate.now());
		String Schueler_geburtsdatum = "";
		int Schueler_jahre = 0;
		String Schueler_beschreibung = "";
		String Schueler_datierung = "";
		try {
			String sql = "insert into Schueler (name,geburtsdatum,jahre,beschreibung,datierung) values (?, ?, ?, ?, ?);";
			PreparedStatement preparedStmt = c.prepareStatement(sql);
			
			preparedStmt.setString(1,name);
			preparedStmt.setDate(2,datum);
			preparedStmt.setInt(3,jahre);
			preparedStmt.setString(4,beschreibung);
			preparedStmt.setDate(5,datierung);
			preparedStmt.executeUpdate();
			System.out.println();
			ResultSet rs = preparedStmt.executeQuery("select id,name,geburtsdatum,jahre,beschreibung,datierung from Schueler;");
			while(rs.next())
			{
				SchuelerID = rs.getInt("id");
				Schuelername = rs.getString("name");
				Schueler_geburtsdatum = rs.getString("geburtsdatum");
				Schueler_jahre = rs.getInt("jahre");
				Schueler_beschreibung = rs.getString("beschreibung");
				Schueler_datierung = rs.getString("datierung");
			}
			rs.close();
			System.out.printf("Schueler:    ID: " + SchuelerID + "    Name: " + Schuelername + "    Geburtsdatum: " + Schueler_geburtsdatum + "    Alter: " + Schueler_jahre + "    Beschreibung: " + Schueler_beschreibung + "    Zuordnungsdatum: " + Schueler_datierung + "\n");
			preparedStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void Schueler_Writer(Connection c, String schueler) {
		try {
			File f = new File(schueler);
			FileWriter fw = new FileWriter(f);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Statement stmt = c.createStatement();
			String sql = "select id,name,geburtsdatum,jahre,beschreibung,datierung from schueler;";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				Date geburtsdatum = rs.getDate("geburtsdatum");
				int jahre = rs.getInt("jahre");
				String beschreibung = rs.getString("beschreibung");
				Date datierung = rs.getDate("datierung");
				
				
				String datum = df.format(geburtsdatum);
				String datum2 = df.format(datierung);
				String filewriter = id + ", " + name + ", " + datum + ", " + jahre + ", " + beschreibung + ", " + datum2 + "\n";
				fw.write(filewriter);
				System.out.println("CSV-Datei wurde befüllt!");
			}
			
			rs.close();
			stmt.close();
			fw.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
