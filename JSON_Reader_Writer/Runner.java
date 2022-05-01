package JSON_Reader_Writer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Runner {
	
	static Connection getConnection(String url, String user, String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			String url = "jdbc:mysql://localhost:3306/json_reader";
			String user = "tobias";
			String password = "t250904";

			Connection c = getConnection(url, user, password);
			
			dropTable(c,"JSON_Read");
			JSON_Reader.createTable(c,"JSON_Read");
			JSON_Reader.read(c,"D:\\Schule HTL 3.Klasse\\Informatik\\JSON_Read.txt","JSON_Read");
			JSON_Writer.write(c,"D:\\Schule HTL 3.Klasse\\Informatik\\JSON_Write.txt");
			
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void dropTable(Connection c, String tableName) {
		try {
			Statement stmt = c.createStatement();
			String sql = "drop table if exists " + tableName + ";";
			System.out.println("Table " + tableName + " wurde gelöscht!");
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}


