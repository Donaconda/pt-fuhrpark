package controller;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import model.Fahrzeug;
import model.Mitarbeiter;
import model.Buchung;
import model.Eintrag;

public class Statistik {
	
	// Ausbaustufe VII.1 - Häufigkeit der Buchungen für jeden Fahrzeugtyp
	public static ArrayList<Eintrag> calcFzTypHaeufigkeit(List<Buchung> buchungData, ObservableList<Fahrzeug> fahrzeugData) {
		// Erstelle für jeden Fahrzeugtyp einen neuen Eintrag
		ArrayList<Eintrag> eintraege = new ArrayList<Eintrag>();
		eintraege.add(new Eintrag("PKW", 0));
		eintraege.add(new Eintrag("LKW", 0));
		eintraege.add(new Eintrag("Motorrad", 0));
		// Berechnung 
		for (Buchung b : buchungData) {
			String fzKlasse = Sucher.sucheFahrzeug(fahrzeugData, b.getFahrzeug()).getKlasse();
			if (fzKlasse != null) {
				if (fzKlasse.compareTo(eintraege.get(0).getX()) == 0) {
					eintraege.get(0).setY(eintraege.get(0).getY() + 1);
				} else if (fzKlasse.compareTo(eintraege.get(1).getX()) == 0) {
					eintraege.get(1).setY(eintraege.get(1).getY() + 1);
				} else if (fzKlasse.compareTo(eintraege.get(2).getX()) == 0) {
					eintraege.get(2).setY(eintraege.get(2).getY() + 1);
				}
			}
		}
		// Und noch einen Eintrag für die gesamten Buchungen
		eintraege.add(new Eintrag("Gesamt", buchungData.size()));
		
		return eintraege;
	}
	
	
	// Ausbaustufe VII.2 - Durchschnittszeit der Buchungen für jeden Fahrzeugtyp
	public static ArrayList<Eintrag> calcFzTypDurchschnittszeit(List<Buchung> buchungData, ObservableList<Fahrzeug> fahrzeugData) {
		// Erstelle für jeden Fahrzeugtyp einen neuen Eintrag
		ArrayList<Eintrag> eintraege = new ArrayList<Eintrag>();
		eintraege.add(new Eintrag("PKW", 0));
		eintraege.add(new Eintrag("LKW", 0));
		eintraege.add(new Eintrag("Motorrad", 0));
		eintraege.add(new Eintrag("Gesamt", 0));
		// Berechnung
		if(buchungData.size() > 0) {
			int pkwZaehler = 0;
			int lkwZaehler = 0;
			int motoZaehler = 0;
			for (Buchung b : buchungData) { // For-Each Schleife
				eintraege.get(3).setY((int) (eintraege.get(3).getY() + b.dauer()));
				String fzKlasse = Sucher.sucheFahrzeug(fahrzeugData, b.getFahrzeug()).getKlasse();
				if (fzKlasse.compareTo(eintraege.get(0).getX()) == 0) {
					eintraege.get(0).setY((int) (eintraege.get(0).getY() + b.dauer()));
					pkwZaehler++;
				} else if (fzKlasse.compareTo(eintraege.get(1).getX()) == 0) {
					eintraege.get(1).setY((int) (eintraege.get(1).getY() + b.dauer()));
					lkwZaehler++;
				} else { // Fahrzeug ist kein PKW oder LKW? => Fahrzeug ist Motorrad
					eintraege.get(2).setY((int) (eintraege.get(2).getY() + b.dauer()));
					motoZaehler++;
				}
			}
			if(pkwZaehler != 0){
				eintraege.get(0).setY(eintraege.get(0).getY() / pkwZaehler);
			}
			if(lkwZaehler != 0){
				eintraege.get(1).setY(eintraege.get(1).getY() / lkwZaehler);
			}
			if(motoZaehler != 0){
				eintraege.get(2).setY(eintraege.get(2).getY() / motoZaehler);
			}
			eintraege.get(3).setY(eintraege.get(3).getY() / buchungData.size());
		}

		return eintraege;
	}
	
	
	// Ausbaustufe VII.3 - Gesamte Leihzeit jedes Mitarbeiters
	public static ArrayList<Eintrag> calcMitarbeiterLeihzeit(List<Buchung> buchungData, ObservableList<Mitarbeiter> mitarbeiterData) {
		// Erstelle für jeden Mitarbeiter einen neuen Eintrag und berechne die Leihzeit
		boolean eingetragen = false;
		ArrayList<Eintrag> eintraege = new ArrayList<Eintrag>();
		for (Buchung b : buchungData) { // Für jede Buchung
			String ma = Sucher.sucheMitarbeiter(mitarbeiterData, b.getMitarbeiter()).toString();
			for (Eintrag e : eintraege) {
				eingetragen = false;
				if (e.getX().compareTo(ma) == 0 ) {
					eingetragen = true;
					e.setY((int) (e.getY() + b.dauer()));
	
				}
			}
			if (!eingetragen) {
				eintraege.add(new Eintrag(ma,(int) b.dauer()));
			}
		}
		return eintraege;
	}
}
