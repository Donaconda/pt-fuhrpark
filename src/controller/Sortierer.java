package controller;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Sortierer {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ObservableList<? extends Comparable> sort(List<? extends Comparable> list) {
		ObservableList<Object> sortiert = FXCollections.observableArrayList();
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
		return (ObservableList<? extends Comparable>) sortiert;
	}
}
