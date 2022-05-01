package JSON_Reader_Writer;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

import org.json.simple.JSONObject;

public class JSON_Writer {
	
	@SuppressWarnings("unchecked")
	static void write(Connection c, String file) {
		try {
			FileWriter fw = new FileWriter(file);
			JSONObject json = new JSONObject();
			String d = "";

			Statement stmt = c.createStatement();
			String sql = "select vorname, nachname, age, klasse, beschreibung from JSON_Read;";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String vorname = rs.getString("vorname");
				String nachname = rs.getString("nachname");
				int age = rs.getInt("age");
				String klasse = rs.getString("klasse");
				String beschreibung = rs.getString("beschreibung");
				
				json.put("vorname", vorname);
				json.put("nachname", nachname);
				json.put("age", age);
				json.put("klasse", klasse);
				json.put("beschreibung", beschreibung);	
				d = d + json;
			}
			fw.write(d);
			fw.flush();
			fw.close();
			rs.close();
			stmt.close();
			System.out.println("Datei wurde befüllt!");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
