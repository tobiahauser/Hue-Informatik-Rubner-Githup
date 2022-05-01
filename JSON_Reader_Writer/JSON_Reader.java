package JSON_Reader_Writer;


import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class JSON_Reader {
	
	static void createTable(Connection c, String tableName) {
		try {
			Statement stmt = c.createStatement();
			String sql = "create table if not exists " + tableName +
						" (vorname varchar(30), nachname varchar(30), age integer, klasse varchar(30), beschreibung varchar(30), primary key(vorname,nachname));";
			stmt.executeUpdate(sql);
			System.out.println("Table " + tableName + " wurde erstellt!");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static void read(Connection c, String file, String tableName) {
		try {
			File f = new File(file);
			Scanner s = new Scanner(f);
			String d = "";
			while (s.hasNextLine()) {
				d = s.nextLine();
				Object ob = new JSONParser().parse(d);
				JSONObject js = (JSONObject) ob;
				
				String vorname = (String)js.get("vorname");
				String nachname = (String)js.get("nachname");
				long age = (long)js.get("age");
				String klasse = (String)js.get("klasse");
				String beschreibung = (String)js.get("beschreibung");
				
				String sql = "insert into " + tableName + " (vorname, nachname, age, klasse, beschreibung) values (?, ?, ?, ?, ?);";
				
				PreparedStatement stmt = c.prepareStatement(sql);
				stmt.setString(1, vorname);
				stmt.setString(2, nachname);
				stmt.setLong(3, age);
				stmt.setString(4, klasse);
				stmt.setString(5, beschreibung);
				stmt.executeUpdate();
				stmt.close();
				System.out.printf("JSON_Reader:    Vorname: " + vorname + "    Nachname: " + nachname + "    Alter: " + age + "    Klasse: " + klasse + "    Beschreibung: " + beschreibung + "\n");
				System.out.println();
			}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File wurde nicht gefunden!");
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}