package controller;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Sortierer {		// Klasse für Selection Sort
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ObservableList<? extends Comparable> sort(List<? extends Comparable> list) {
		ObservableList<Object> sortiert = FXCollections.observableArrayList();
		int listursprungsgroeße = list.size();
		for (int y = 0; y < listursprungsgroeße; y++) { // So viele Durchläufe wie die Ursprungsliste Einträge hat
			int merker = 0;
			Object o = list.get(0);
			for (int i = 0; i < list.size(); i++) {		// For-Schleife sucht das kleinste verbleibende Element der Liste
				if (list.get(i).compareTo(o) < 0) {
					o = list.get(i);
					merker = i;
				}
			}
			sortiert.add(o);							// Kleinstes Element zu sortierter Liste hinzufügen &
			list.remove(merker);						// aus Ursprungsliste entfernen
		}
		return (ObservableList<? extends Comparable>) sortiert;
	}
}
