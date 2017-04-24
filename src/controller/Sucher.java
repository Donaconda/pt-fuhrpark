package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Buchung;

public class Sucher {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean suche(ObservableList<? extends Comparable> liste, Comparable gesuchtesObjekt) {		// Prüft ob ein Objekt in einer Liste enthalten ist
		for (Comparable c : liste) {																			// For Each
			if (c.compareTo(gesuchtesObjekt) == 0) {															// Vergleich (ist o das gesuchte Objekt?)
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	public static Object suche(ObservableList<? extends Comparable> liste, String gesuchtesObjekt) { // Sucht ein Objekt aus einer Liste und gibt es zurück
		for (Comparable c : liste) {															  	 // For Each
			if (c.toString().compareTo(gesuchtesObjekt) == 0) {										 // Vergleich
				return c;
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
