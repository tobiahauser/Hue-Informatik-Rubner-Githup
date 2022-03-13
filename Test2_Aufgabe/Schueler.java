package Test_2_Bsp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Schueler {
	
	static void createTable(Connection c, String tableName) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists " + tableName +
						" (id integer primary key auto_increment, name varchar(30), geburtsdatum varchar(30), jahre integer, beschreibung varchar(40));";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void insertInto(Connection c, String tableName, String name, String geburtsdatum, int jahre, String beschreibung) {
		int SchuelerID = 0;
		try {
			Statement stmt = c.createStatement();
			
			String sql = "insert into " + tableName + " (name,geburtsdatum,jahre,beschreibung) values" +
						"(\"" + name + "\", \"" + geburtsdatum + "\", " + jahre + ", \"" + beschreibung + "\");";
			stmt.executeUpdate(sql);
			
			ResultSet rs = stmt.executeQuery("select id from Schueler where Name = \"" + name + "\";");
			while(rs.next())
			{
				SchuelerID = rs.getInt("id");
			}
			rs.close();
			System.out.println();
			System.out.printf("Schueler:    SchulerID: " + SchuelerID + "    Name: " + name + "    Geburtsdatum: " + geburtsdatum + "    Alter: " + jahre + "    Beschreibung: " + beschreibung + "\n");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}