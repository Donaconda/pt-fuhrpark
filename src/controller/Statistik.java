package controller;

import model.Eintrag;
import java.util.*;
import model.Fahrzeug;
import model.Buchung;

public class Statistik {

	public static List<Eintrag> calcAusgeliehen(List<Fahrzeug> list){ // Ausbaustufe VII.1
		ArrayList<Eintrag> eintraege= new ArrayList<Eintrag>();
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
		
		
		for(int i = 0; i<list.size();i++){
			if(list.get(i).getKlasse().toLowerCase().compareTo("lkw")==0){
				eintraege.get(0).setHaeufigkeit(eintraege.get(0).getHaeufigkeit()+1);
			} else if(list.get(i).getKlasse().toLowerCase().compareTo("pkw")==0){
				eintraege.get(0).setHaeufigkeit(eintraege.get(1).getHaeufigkeit()+1);
			} else if(list.get(i).getKlasse().toLowerCase().compareTo("motorrad")==0){
				eintraege.get(0).setHaeufigkeit(eintraege.get(2).getHaeufigkeit()+1);
			} 
		}
		return eintraege;
	}
	
	public static List<Eintrag> avrgTime(List<Buchung> list){ // Ausbaustufe VII.2
		List<Eintrag> eintraege = new ArrayList<Eintrag>();
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
		for(Buchung b : list){ // For-Each Schleife
			durchschnitt.setAusleihzeit(durchschnitt.getAusleihzeit()+b.getDauer());
			buchungszaehler++;
			if(b.getFahrzeug().getKlasse().toLowerCase().compareTo("pkw")==0){
				durchschnittPkw.setAusleihzeit(durchschnittPkw.getAusleihzeit()+b.getDauer());
				pkwZaehler++;
			} else if(b.getFahrzeug().getKlasse().toLowerCase().compareTo("lkw")==0){
				durchschnittLkw.setAusleihzeit(durchschnittLkw.getAusleihzeit()+b.getDauer());
				lkwZaehler++;
			} else { // Fahrzeug ist kein PKW oder LKW? => Fahrzeug ist Motorrad
				durchschnittMoto.setAusleihzeit(durchschnittMoto.getAusleihzeit()+b.getDauer());
				motoZaehler++;
			}
		}
		durchschnitt.setAusleihzeit(durchschnitt.getAusleihzeit()/buchungszaehler);
		durchschnittPkw.setAusleihzeit(durchschnittPkw.getAusleihzeit()/pkwZaehler);
		durchschnittLkw.setAusleihzeit(durchschnittLkw.getAusleihzeit()/lkwZaehler);
		durchschnittMoto.setAusleihzeit(durchschnittMoto.getAusleihzeit()/motoZaehler);
		eintraege.add(durchschnitt);
		eintraege.add(durchschnittMoto);
		eintraege.add(durchschnittLkw);
		eintraege.add(durchschnittPkw);
		return eintraege;
	}
	
	public List<Eintrag> mitarbeiterLeihtage(List<Buchung> list){
		List<Eintrag> eintraege= new ArrayList<Eintrag>();
		for(Buchung b : list){ // Für jede Buchung: 
			for(Eintrag e : eintraege){ // FÜr jeden Eintrag
				if(e.getMitarbeiter() == b.getMitarbeiter()){ // Gibt es schon einen Eintrag für den Mitarbeiter?
					e.setAusleihzeit(e.getAusleihzeit()+b.getDauer()); // Erhöhe seine Ausleihzeit um die Dauer der Buchung
				} else{	// Sonst:
					Eintrag a = new Eintrag();				// Erstelle einen neuen Eintrag 
					a.setMitarbeiter(b.getMitarbeiter());	// für den Mitarbeiter aus der Buchung	
					eintraege.add(a);						// und füge ihn der Liste hinzu
				}
			}
		}
		return eintraege;
	}
	 
	//-------------AUSBAUSTUFE VII 2. & 3. einfügen-------------------//
}
