package controller;

import java.util.Observable;

import javafx.collections.ObservableList;
import model.Buchung;
import model.Fahrzeug;
import model.Mitarbeiter;

public class Sucher {
	
	public static boolean suche(ObservableList<Fahrzeug> liste, Fahrzeug fahrzeug){
		for(Fahrzeug f : liste){
			if(f.compareTo(fahrzeug)==0){
				return true;
			}
		}
		return false;
	}
	
	public static boolean suche(ObservableList<Mitarbeiter> liste, Mitarbeiter mitarbeiter){
		for(Mitarbeiter m : liste){
			if(m.compareTo(mitarbeiter)==0){
				return true;
			}
		}
		return false;
	}
	
	public static boolean suche(ObservableList<Buchung> liste, Buchung buchung){
		for(Buchung b : liste){
			if(b.compareTo(buchung)==0){
				return true;
			}
		}
		return false;
	}

}
