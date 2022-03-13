package Test_2_Bsp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Klasse {
	
	static void createTable(Connection c, String tableName) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists " + tableName +
						" (id integer primary key auto_increment, name varchar(40), anzSchueler integer);";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void insertInto(Connection c, String tableName, String name, int anzSchueler) {
		int KlassenID = 0;
		try {
			Statement stmt = c.createStatement();
			String sql = "insert into " + tableName + " (name,anzSchueler) values (\"" + name + "\", " + anzSchueler + ");";
			stmt.executeUpdate(sql);
			
			ResultSet rs = stmt.executeQuery("select id from Klasse where Name = \"" + name + "\";");
			while(rs.next())
			{
				KlassenID = rs.getInt("id");
			}
			rs.close();
			System.out.println();
			System.out.printf("Klasse:    KlassenID: " + KlassenID + "    Name: " + name + "    AnzSchueler: " + anzSchueler + "\n");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}