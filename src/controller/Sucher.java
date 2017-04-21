package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Buchung;
import model.Fahrzeug;
import model.Mitarbeiter;

public class Sucher {

	public static boolean suche(ObservableList<Fahrzeug> liste, Fahrzeug fahrzeug) {
		for (Fahrzeug f : liste) {
			if (f.compareTo(fahrzeug) == 0) {
				return true;
			}
		}
		return false;
	}

	public static boolean suche(ObservableList<Mitarbeiter> liste, Mitarbeiter mitarbeiter) {
		for (Mitarbeiter m : liste) {
			if (m.compareTo(mitarbeiter) == 0) {
				return true;
			}
		}
		return false;
	}

	public static boolean suche(ObservableList<Buchung> liste, Buchung buchung) {
		for (Buchung b : liste) {
			if (b.compareTo(buchung) == 0) {
				return true;
			}
		}
		return false;
	}

	public static Fahrzeug sucheFahrzeug(ObservableList<Fahrzeug> liste, String fahrzeug) {
		for (Fahrzeug f : liste) {
			if (f.toString().compareTo(fahrzeug) == 0) {
				return f;
			}
		}
		return null;
	}

	public static Mitarbeiter sucheMitarbeiter(ObservableList<Mitarbeiter> liste, String mitarbeiter) {
		for (Mitarbeiter m : liste) {
			if (m.toString().compareTo(mitarbeiter) == 0) {
				return m;
			}
		}
		return null;
	}

	public static ObservableList<Buchung> sucheBuchungenNachKennzeichen(ObservableList<Buchung> liste, String kennzeichen){
		ObservableList<Buchung> result = FXCollections.observableArrayList();
		// Für jedes Element/jede Buchung in der Buchungsliste
		for(Buchung bu : liste){
			// Wenn die aktuelle Buchung das Fahrzeug enthält...
			if(bu.getKennzeichen().compareTo(kennzeichen) == 0){
				// ... Füge es der Ergebnisliste hinzu
				result.add(bu);
			}
		}
		return result;
	}
}
