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

class Klasse {
	
	static void createTable(Connection c, String tableName) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists " + tableName +
						" (id integer primary key auto_increment, name varchar(40), anzSchueler integer, datierung Date);";
			stmt.executeUpdate(sql);
			System.out.println("Table " + tableName + " wurde erstellt.");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	static void insertInto(Connection c, String name, int anzSchueler) {
		int KlassenID = 0;
		String Klassenname = "";
		int Klasse_anzSchueler = 0;
		String Klasse_datierung = "";
		java.sql.Date datum = java.sql.Date.valueOf(LocalDate.now());
		try {
			String sql = "insert into Klasse (name, anzSchueler,datierung) values (?, ?, ?);";
			PreparedStatement preparedStmt = c.prepareStatement(sql);
			preparedStmt.setString(1,name);
			preparedStmt.setInt(2,anzSchueler);
			preparedStmt.setDate(3,datum);
			preparedStmt.executeUpdate();
			System.out.println();
			ResultSet rs = preparedStmt.executeQuery("select id,name,anzSchueler,datierung from Klasse;");
			while(rs.next())
			{
				KlassenID = rs.getInt("id");
				Klassenname = rs.getString("name");
				Klasse_anzSchueler = rs.getInt("anzSchueler");
				Klasse_datierung = rs.getString("datierung");
			}
			rs.close();
			System.out.printf("Klasse:    ID: " + KlassenID + "    Name: " + Klassenname + "    anzSchueler: " + Klasse_anzSchueler + "    Zuordnungsdatum: " + Klasse_datierung + "\n");
			preparedStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void Klasse_Writer(Connection c, String klasse) {
		try { 
			File f = new File(klasse);
			FileWriter fw = new FileWriter(f);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			Statement stmt = c.createStatement();
			String sql = "select id,name,anzSchueler,datierung from Klasse;";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int anzSchueler = rs.getInt("anzSchueler");
				Date d = rs.getDate("datierung");
				String datierung = df.format(d);

				String filewriter = id + ", " + name + ", " + anzSchueler + ", " + datierung + "\n";
				fw.write(filewriter);
				System.out.println("CSV-Datei wurde befüllt!");
			}

			rs.close();
			stmt.close();
			fw.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}   catch (IOException e) {
			e.printStackTrace();
		}
	}
}
