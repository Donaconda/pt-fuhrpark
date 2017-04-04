package controller;

import java.util.ArrayList;
import java.util.List;

import model.Buchung;
import model.Fahrzeug;
import model.Mitarbeiter;

public class Sortierer {

	public static ArrayList<? extends Comparable> sort(List<? extends Comparable> list) {
		ArrayList<Object> sortiert = new ArrayList<Object>();
		int listursprungsgröße = list.size();
		for (int y = 0; y < listursprungsgröße; y++) {
			Object o = list.get(0);
			int merker = 0;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).compareTo(o) < 0) {
					o = list.get(i);
					merker = i;
				}
			}
			sortiert.add(o);
			list.remove(merker);
		}
		return (ArrayList<? extends Comparable>) sortiert;
	}
}
