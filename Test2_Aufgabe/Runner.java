package Test_2_Bsp;

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
		String url = "jdbc:mysql://localhost:3306/schueler_klasse";
		String user = "tobias";
		String password = "t250904";
		
		Connection c = getConnection(url, user, password);
		
		dropTable(c, "SchuelerKlasse");
		dropTable(c, "Schueler");
		dropTable(c, "Klasse");
		
		Schueler.createTable(c,"Schueler");
		Klasse.createTable(c,"Klasse");
		SchuelerKlasse.createTable(c,"SchuelerKlasse");
		
		Schueler.insertInto(c,"Schueler","Hauser Tobias","2004-09-25",17,"hilfsbereit");
		Schueler.insertInto(c,"Schueler","Jungmann Julian","2005-07-18",16,"freundlich");
		Schueler.insertInto(c,"Schueler","Hoertnagl Valentin","2004-12-10",17,"lernwillig");
		
		Klasse.insertInto(c,"Klasse","3AHWII",25);
		
		SchuelerKlasse.insertInto(c,"Hauser Tobias","3AHWII");
		SchuelerKlasse.insertInto(c,"Jungmann Julian","3AHWII");
		SchuelerKlasse.insertInto(c,"Hoertnagl Valentin","3AHWII");
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
