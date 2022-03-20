package Autoverleih;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class Kunde {
	
	static void createTable(Connection c, String tableName) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists " + tableName +
						" (id integer primary key auto_increment, name varchar(30), " +
						"email varchar(40));";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	static void insertInto(Connection c, String name, String email) {
		int KundenID = 0;
		try {
			String sql = "insert into Kunde (name, email) values (?, ?);";
			PreparedStatement preparedStmt = c.prepareStatement(sql);
			preparedStmt.setString(1,name);
			preparedStmt.setString(2,email);
			preparedStmt.executeUpdate();
			
			ResultSet rs = preparedStmt.executeQuery("select id from Kunde where name = \"" + name + "\";");
			while(rs.next())
			{
				KundenID = rs.getInt("id");
			}
			rs.close();
			System.out.println();
			System.out.printf("Kunde:    KundeID: " + KundenID + "    Name: " + name + "    Email: " + email + "\n");
			preparedStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
