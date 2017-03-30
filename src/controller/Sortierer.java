package controller;

import java.util.ArrayList;

import model.Buchung;
import model.Fahrzeug;
import model.Mitarbeiter;

public class Sortierer {

	public static ArrayList<? extends Comparable> sort(ArrayList<? extends Comparable> list) {
		int merker = 0;
		if (list.get(0).getClass() == Mitarbeiter.class) {
			ArrayList<Mitarbeiter> sortiert = new ArrayList<Mitarbeiter>();
			Mitarbeiter m = (Mitarbeiter) list.get(0);
			for (int y = 0; y < list.size(); y++) {
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).compareTo(m) < 0) {
						m = (Mitarbeiter) list.get(i);
						merker = i;
					}
				}
				sortiert.add(m);
				list.remove(merker);
			}
			return sortiert;
		} else if (list.get(0).getClass() == Fahrzeug.class) {
			ArrayList<Fahrzeug> sortiert = new ArrayList<Fahrzeug>();
			Fahrzeug f = (Fahrzeug) list.get(0);
			for (int y = 0; y < list.size(); y++) {
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).compareTo(f) < 0) {
						f = (Fahrzeug) list.get(i);
						merker = i;
					}
				}
				sortiert.add(f);
				list.remove(merker);
			}
			return sortiert;
		} else {
			ArrayList<Buchung> sortiert = new ArrayList<Buchung>();
			Buchung b = (Buchung) list.get(0);
			for (int y = 0; y < list.size(); y++) {
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).compareTo(b) < 0) {
						b = (Buchung) list.get(i);
						merker = i;
					}
				}
				sortiert.add(b);
				list.remove(merker);
			}
			return sortiert;
		}

	}

}
