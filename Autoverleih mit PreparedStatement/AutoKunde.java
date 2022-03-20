package Autoverleih;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class AutoKunde {
	
	static void createTable(Connection c, String tableName) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists " + tableName +
						" (Anfangsdatum Date, Enddatum Date, " +
						"KundenID integer not null, Kennzeichen varchar(20) not null, foreign key (KundenID) references Kunde(id), " +
						"foreign key (Kennzeichen) references Auto(kennzeichen), primary key(KundenID,Kennzeichen,Anfangsdatum,Enddatum));";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void insertInto(Connection c, String kennzeichen, String kundenname, LocalDate enddatum) {
			int KundenID = 0;
			LocalDate Datum = LocalDate.now();
			java.sql.Date Zeit = java.sql.Date.valueOf(Datum);
			java.sql.Date Zeit2 = java.sql.Date.valueOf(enddatum);
			try {
				String sql = "insert into AutoKunde values (?, ?, ?, ?);";
				PreparedStatement preparedStmt = c.prepareStatement(sql);
				
				ResultSet rs = preparedStmt.executeQuery("select id from Kunde where name = \"" + kundenname + "\";");
				while(rs.next())
				{
					KundenID = rs.getInt("id");
				}
				rs.close();
				
				preparedStmt.setDate(1,Zeit);
				preparedStmt.setDate(2,Zeit2);
				preparedStmt.setInt(3,KundenID);
				preparedStmt.setString(4,kennzeichen);
				preparedStmt.executeUpdate();
				
			System.out.println();
			System.out.printf("SchuelerKlasse:    Anfangsdatum: " + Zeit + "    Enddatum: " + Zeit2 + "    Kennzeichen: " + kennzeichen + "    KundenID: " + KundenID + "\n");
			preparedStmt.close();
			} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
