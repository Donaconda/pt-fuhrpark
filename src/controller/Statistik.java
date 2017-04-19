package controller;

import model.Eintrag;
import java.io.File;
import java.util.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Fahrzeug;
import model.FahrzeugListWrapper;
import model.Mitarbeiter;
import model.MitarbeiterListWrapper;
import model.Buchung;

public class Statistik {

	private static ObservableList<Fahrzeug> fahrzeugData = FXCollections.observableArrayList();
	public static ObservableList<Mitarbeiter> mitarbeiterData = FXCollections.observableArrayList();

	public static ArrayList<Eintrag> calcAusgeliehen(List<Buchung> list) { // Ausbaustufe VII.1
		ladeFahrzeuge();
		ArrayList<Eintrag> eintraege = new ArrayList<Eintrag>();
		Eintrag lkwEintrag = new Eintrag();
		Fahrzeug lkw = new Fahrzeug();
		lkw.setKlasse("lkw");
		lkwEintrag.setFahrzeug(lkw);
		eintraege.add(lkwEintrag);
		Eintrag pkwEintrag = new Eintrag();
		Fahrzeug pkw = new Fahrzeug();
		pkw.setKlasse("pkw");
		pkwEintrag.setFahrzeug(pkw);
		eintraege.add(pkwEintrag);
		Eintrag motoEintrag = new Eintrag();
		Fahrzeug moto = new Fahrzeug();
		moto.setKlasse("motorrad");
		motoEintrag.setFahrzeug(moto);
		eintraege.add(motoEintrag);

		for (int i = 0; i < list.size(); i++) {
			Fahrzeug f;
			//String merker = list.get(i).getFahrzeug().toLowerCase();
			if (Sucher.sucheFahrzeug(fahrzeugData, list.get(i).getFahrzeug()) != null) {
				f = Sucher.sucheFahrzeug(fahrzeugData, list.get(i).getFahrzeug());

				if (f.getKlasse().toLowerCase().compareTo("lkw") == 0) {
					eintraege.get(0).setHaeufigkeit(eintraege.get(0).getHaeufigkeit() + 1);
				} else if (f.getKlasse().toLowerCase().compareTo("pkw") == 0) {
					eintraege.get(1).setHaeufigkeit(eintraege.get(1).getHaeufigkeit() + 1);
				} else if (f.getKlasse().toLowerCase().compareTo("motorrad") == 0) {
					eintraege.get(2).setHaeufigkeit(eintraege.get(2).getHaeufigkeit() + 1);
				}
			}
		}
		return eintraege;
	}

	public static ArrayList<Eintrag> avrgTime(List<Buchung> list) { // Ausbaustufe VII.2
		ladeFahrzeuge();
		ArrayList<Eintrag> eintraege = new ArrayList<Eintrag>();
		Eintrag durchschnitt = new Eintrag();
		Eintrag durchschnittPkw = new Eintrag();
		Eintrag durchschnittLkw = new Eintrag();
		Eintrag durchschnittMoto = new Eintrag();
		Fahrzeug pkw = new Fahrzeug();
		Fahrzeug lkw = new Fahrzeug();
		Fahrzeug moto = new Fahrzeug();
		pkw.setKlasse("pkw");
		lkw.setKlasse("lkw");
		moto.setKlasse("motorrad");
		durchschnittPkw.setFahrzeug(pkw);
		durchschnittLkw.setFahrzeug(lkw);
		durchschnittMoto.setFahrzeug(moto);
		int pkwZaehler = 0;
		int lkwZaehler = 0;
		int motoZaehler = 0;
		int buchungszaehler = 0;
		for (Buchung b : list) { // For-Each Schleife
			durchschnitt.setAusleihzeit((int) (durchschnitt.getAusleihzeit() + b.dauer()));
			buchungszaehler++;
			String fahrzeug = Sucher.sucheFahrzeug(fahrzeugData, b.getFahrzeug()).getKlasse().toLowerCase();
			if (fahrzeug.toLowerCase().compareTo("pkw") == 0) {
				durchschnittPkw.setAusleihzeit((int) (durchschnittPkw.getAusleihzeit() + b.dauer()));
				pkwZaehler++;
			} else if (fahrzeug.toLowerCase().compareTo("lkw") == 0) {
				durchschnittLkw.setAusleihzeit((int) (durchschnittLkw.getAusleihzeit() + b.dauer()));
				lkwZaehler++;
			} else { // Fahrzeug ist kein PKW oder LKW? => Fahrzeug ist Motorrad
				durchschnittMoto.setAusleihzeit((int) (durchschnittMoto.getAusleihzeit() + b.dauer()));
				motoZaehler++;
			}
		}
		try {
			durchschnitt.setAusleihzeit(durchschnitt.getAusleihzeit() / buchungszaehler);
			durchschnittPkw.setAusleihzeit(durchschnittPkw.getAusleihzeit() / pkwZaehler);
			durchschnittLkw.setAusleihzeit(durchschnittLkw.getAusleihzeit() / lkwZaehler);
			durchschnittMoto.setAusleihzeit(durchschnittMoto.getAusleihzeit() / motoZaehler);
		} catch (Exception e) {

		}
		eintraege.add(durchschnitt);
		eintraege.add(durchschnittMoto);
		eintraege.add(durchschnittLkw);
		eintraege.add(durchschnittPkw);
		return eintraege;
	}

	public static ArrayList<Eintrag> mitarbeiterLeihtage(List<Buchung> list) { // Ausbaustufe VII.3
		ladeMitarbeiter();
		boolean eingetragen = false;
		ArrayList<Eintrag> eintraege = new ArrayList<Eintrag>();
		for (Buchung b : list) { // Für jede Buchung
			for (Eintrag e : eintraege) {
				eingetragen = false;
				if (e.getMitarbeiter() == Sucher.sucheMitarbeiter(mitarbeiterData, b.getMitarbeiter())) {
					e.setAusleihzeit((int) (e.getAusleihzeit() + b.dauer()));
					eingetragen = true;
				}
			}
			if (!eingetragen) {
				Eintrag e = new Eintrag();
				e.setMitarbeiter(Sucher.sucheMitarbeiter(mitarbeiterData, b.getMitarbeiter()));
				e.setAusleihzeit((int) (b.dauer()));
				eintraege.add(e);
			}
		}
		return eintraege;
	}

	public static void ladeFahrzeuge() {
		try {
			JAXBContext context = JAXBContext.newInstance(FahrzeugListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			// Reading XML from the file and unmarshalling.
			File f = new File("src/resources/fahrzeugData.xml");
			FahrzeugListWrapper wrapper = (FahrzeugListWrapper) um.unmarshal(f);

			fahrzeugData.clear();
			fahrzeugData.addAll(wrapper.getFahrzeug());

		} catch (Exception e) { // catches ANY exception
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not load data");
			alert.setContentText("Could not load data from file");

			alert.showAndWait();
		}
	}

	public static void ladeMitarbeiter() {
		try {
			JAXBContext context = JAXBContext.newInstance(MitarbeiterListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			// Reading XML from the file and unmarshalling.
			File f = new File("src/resources/mitarbeiterData.xml");
			MitarbeiterListWrapper wrapper = (MitarbeiterListWrapper) um.unmarshal(f);

			mitarbeiterData.clear();
			mitarbeiterData.addAll(wrapper.getMitarbeiter());

		} catch (Exception e) { // catches ANY exception
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not load data");
			alert.setContentText("Could not load data from file");

			alert.showAndWait();
		}
	}
}
