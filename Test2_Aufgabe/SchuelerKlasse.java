package Test_2_Bsp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void insertInto(Connection c, String Schuelername, String Klassenname) {
			int SchuelerID = 0;
			int KlassenID = 0;
			LocalDate Datum = LocalDate.now();
			java.sql.Date Zeit = java.sql.Date.valueOf(Datum);
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
						" (\"" + Zeit + "\","  + SchuelerID + ","  + KlassenID + ");";
			stmt.executeUpdate(sql);
			stmt.close();
			System.out.println();
			System.out.printf("SchuelerKlasse:    Datum: " + Zeit + "    SchuelerID: " + SchuelerID + "    KlassenID: " + KlassenID + "\n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}