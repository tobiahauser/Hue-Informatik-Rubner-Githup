package Projekt_Traktordatenbank;

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
			String sql = "select k.vorname, k.nachname, k.jahre, k.geburtsdatum, t.modell, t.marke, t.leistung, t.preis from Kunde k INNER JOIN TraktorzuKunde tk ON k.id = tk.KundenID Inner JOIN Traktor t ON t.id = tk.TraktorID;";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String vorname = rs.getString("k.vorname");
				String nachname = rs.getString("k.nachname");
				int jahre = rs.getInt("k.jahre");
				Date geburtsdatum = rs.getDate("k.geburtsdatum");
				String modell = rs.getString("t.modell");
				String marke = rs.getString("t.marke");
				String leistung = rs.getString("t.leistung");
				String preis = rs.getString("t.preis");

				json.put("vorname", vorname);
				json.put("nachname", nachname);
				json.put("jahre", jahre);
				json.put("geburtsdatum", geburtsdatum);
				json.put("modell", modell);	
				json.put("marke", marke);	
				json.put("leistung", leistung);	
				json.put("preis", preis);	
				d = d + json ;
			}
			fw.write(d);
			fw.flush();
			fw.close();
			rs.close();
			stmt.close();
			System.out.println("Die JSON-Datei wurde mit den Daten der TraktorzuKunde-Tabelle befüllt!");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}

