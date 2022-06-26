package Projekt_Traktordatenbank;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

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
		String url = "jdbc:mysql://localhost:3306/projekt_traktordatenbank";
		String user = "tobias";
		String password = "t250904";

		try {	
			Connection c = getConnection(url, user, password);
			File tfile = null;
			File kfile = null;
			boolean kfilekontrolle = false;
			boolean tfilekontrolle = false;
			while (tfilekontrolle == false || kfilekontrolle == false)
			{
				System.out.println("Bitte geben Sie den Pfad an wo Ihre CSV Datei liegt, die Sie auslesen möchten! (PKunde,PTraktor)");
				Scanner a = new Scanner(System.in);
				String pfad = a.nextLine();
				System.out.println(pfad);
				if (pfad.equals("PKunde"))
				{
					kfile = new File("D:\\Schule HTL 3.Klasse\\Informatik\\Projekt_Kunde.csv");
					System.out.println("Pfad wurde gefunden!");
					kfilekontrolle = true;
				}
				else if (pfad.equals("PTraktor"))
				{
					tfile = new File("D:\\Schule HTL 3.Klasse\\Informatik\\Projekt_Traktor.csv");
					System.out.println("Pfad wurde gefunden!");
					tfilekontrolle = true;
				}
				else
				{
					System.out.println("Falsche Eingabe!!!");
				}
			}
			Scanner t = new Scanner(tfile);
			Scanner k = new Scanner(kfile);
			boolean Kontrolle = true;
			boolean Kontrolle2 = true;
			boolean Kontrolle3 = true;
			boolean Kontrolle4 = true;
			boolean Traktoreinlesen = false;
			boolean Kundeeinlesen = false;
			String[] traktorfile = new String[5];
			String[] kundefile = new String[5];
			String string = ""; 

			Traktor.createTable(c,"Traktor");
			Kunde.createTable(c,"Kunde");

			while(Kontrolle && (Traktoreinlesen == false || Kundeeinlesen == false))
			{
				System.out.println("In welche Klasse wollen Sie die Datensätzehinzufügen? (Traktor,Kunde)");
				Scanner s = new Scanner(System.in);
				String name = s.nextLine();
				System.out.println(name);
				if (name.equals("Traktor"))
				{
					while (t.hasNextLine()) {
						string = t.nextLine();
						traktorfile = string.split(";");

						Traktor.insertInto(c,traktorfile,name);	
						System.out.println();
						Traktoreinlesen = true;
					}
				}
				else if (name.equals("Kunde"))
				{
					while (k.hasNextLine()) {
						string = k.nextLine();
						kundefile = string.split(";");

						Kunde.insertInto(c,kundefile,"Kunde",name);
						System.out.println();
						Kundeeinlesen = true;
					}	
				}
				else
				{
					System.out.println("Falsche Eingabe!!!");
				}
			}
			if (Traktoreinlesen == true && Kundeeinlesen == true)
			{
				Kontrolle = false;
				while (Kontrolle2)
				{
					System.out.println("Sollen die Tabellen jetzt verknüpft werden? (Ja,Nein)");
					Scanner g = new Scanner(System.in);
					String answer = g.nextLine();
					System.out.println(answer);
					if (answer.equals("Ja"))
					{
						TraktorzuKunde.createTable(c,"TraktorzuKunde");
						TraktorzuKunde.insertInto(c,"TraktorzuKunde",LocalDate.of(2022,12,20),"1050 vario","Fendt",1,"Julian","Jungmann",1);
						TraktorzuKunde.insertInto(c,"TraktorzuKunde",LocalDate.of(2023,02,24),"t9","New Holand",2,"Livio","Novak",2);
						TraktorzuKunde.insertInto(c,"TraktorzuKunde",LocalDate.of(2023,05,12),"Puma 240","Case",3,"Felix","Hilber",3);
						TraktorzuKunde.insertInto(c,"TraktorzuKunde",LocalDate.of(2023,07,20),"942 vario","Fendt",4,"Simon","Hauser",4);
						while (Kontrolle3)
						{
							System.out.println();
							System.out.println("Wollen Sie die TraktorzuKunde-Tabelle in eine JSON-Datei überführen? (Ja,Nein)");
							Scanner j = new Scanner(System.in);
							String answer2 = j.nextLine();
							System.out.println(answer2);
							if (answer2.equals("Ja"))
							{
								while (Kontrolle4)
								{
									System.out.println("Bitte geben Sie den PFad an wo Ihre JSON-Datei liegt, damit die Daten überführt werden können! (JSON)");
									Scanner h = new Scanner(System.in);
									String answer3 = h.nextLine();
									System.out.println(answer3);
									if (answer3.equals("JSON"))
									{
										System.out.println("Pfad wurde gefunden!");
										JSON_Writer.write(c,"D:\\Schule HTL 3.Klasse\\Informatik\\Projekt_JSON_TraktorzuKunde.txt");
										Kontrolle3 = false;
										Kontrolle4 = false;
									}
									else
									{
										System.out.println("Falsche Eingabe!");
									}
								}
							}
							else if (answer2.equals("Nein"))
							{
								System.out.println("TraktorzuKunde-Tabelle wurde nicht n eine JSON-Tabelle überführt!");
							}
							else
							{
								System.out.println("Falsche Eingabe!!!");
							}
						}
						c.close();
						t.close();
						k.close();
						Kontrolle2 = false;
					}
					else if (answer.equals("Nein"))
					{
						System.out.println("Tabellen wurden nicht miteinander verknüpft!");
					}
					else
					{
						System.out.println("Falsche Eingabe!!!");
					}
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}