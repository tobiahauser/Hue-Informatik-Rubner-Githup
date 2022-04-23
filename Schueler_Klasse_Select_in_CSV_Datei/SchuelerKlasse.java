package Schueler_Klasse_Select_in_CSV_Datei;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class SchuelerKlasse {
	
	static void createTable(Connection c, String tableName) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists " + tableName +
						" (Datum Date not null, SchuelerID integer, " +
						"KlassenID integer, foreign key (SchuelerID) references Schueler(id), " +
						"foreign key (KlassenID) references Klasse(id), primary key(SchuelerID,KlassenID,Datum));";
			stmt.executeUpdate(sql);
			System.out.println("Table " + tableName + " wurde erstellt.");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void insertInto(Connection c, String Schuelername, String Klassenname) {
			int SchuelerID = 0;
			int KlassenID = 0;
			java.sql.Date Datum = java.sql.Date.valueOf(LocalDate.now());
			try {
				Statement stmt = c.createStatement();
				
				ResultSet rs = stmt.executeQuery("select id from Schueler where Name = \"" + Schuelername + "\";");
				while(rs.next())
				{
					SchuelerID = rs.getInt("id");
				}
				rs.close();
				
				ResultSet rs2 = stmt.executeQuery("select id from Klasse where Name = \"" + Klassenname + "\";");
				while(rs2.next())
				{
					KlassenID = rs2.getInt("id");
				}
				rs2.close();
			
			String sql = "insert into SchuelerKlasse (Datum,SchuelerID,KlassenID) values" +
						" (\"" + Datum + "\","  + SchuelerID + ","  + KlassenID + ");";
			stmt.executeUpdate(sql);
			stmt.close();
			System.out.println();
			System.out.printf("SchuelerKlasse:    Zuordnungsdatum: " + Datum + "    SchuelerID: " + SchuelerID + "    KlassenID: " + KlassenID + "\n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void Schueler_Klasse_Writer(Connection c, String schueler_klasse) {
		try {
			File f = new File(schueler_klasse);
			FileWriter fw = new FileWriter(f);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Statement stmt = c.createStatement();
			String sql = "select Datum,SchuelerID,KlassenID from SchuelerKlasse;";
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				Date Datum = rs.getDate("Datum");
				int SchuelerID = rs.getInt("SchuelerID");
				int KlassenID = rs.getInt("KlassenID");
				String datum = df.format(Datum);
				String filewriter = datum + ", " + SchuelerID + ", " + KlassenID +  "\n";
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