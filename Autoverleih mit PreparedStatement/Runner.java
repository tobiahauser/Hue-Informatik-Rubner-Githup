package Autoverleih;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

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
		String url = "jdbc:mysql://localhost:3306/autoverleih";
		String user = "tobias";
		String password = "t250904";
		
		try {	
		Connection c = getConnection(url, user, password);
		
		dropTable(c, "AutoKunde");
		dropTable(c, "Kunde");
		dropTable(c, "Auto");
		
		Auto.createTable(c,"Auto");
		Kunde.createTable(c,"Kunde");
		AutoKunde.createTable(c,"AutoKunde");
		
		Auto.insertInto(c,"IL 345 AZ","Audi","A6",250,35000);
		Auto.insertInto(c,"IL 786 ZU","BMW","X3",200,30000);
		Auto.insertInto(c,"IL 344 PR","Mercedes","AMG GT",300,45000);
		
		Kunde.insertInto(c,"Livio Novak","linovak@tsn.at");
		Kunde.insertInto(c,"Julian Jungmann","jujungmann@tsn.at");
		Kunde.insertInto(c,"Valentin Hoertnagl","vahoertnagl@tsn.at");
		
		AutoKunde.insertInto(c,"IL 345 AZ","Livio Novak",LocalDate.of(2022,12,20));
		AutoKunde.insertInto(c,"IL 786 ZU","Julian Jungmann",LocalDate.of(2023,01,20));
		AutoKunde.insertInto(c,"IL 344 PR","Valentin Hoertnagl",LocalDate.of(2023,02,20));
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void dropTable(Connection c, String tableName) {
		try {
			Statement stmt = c.createStatement();
			String sql = "drop table if exists " + tableName + ";";
			System.out.println("Table " + tableName + " wurde gelöscht.");
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
