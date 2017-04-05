package view;

import java.util.ArrayList;
import java.util.List;

import controller.MainApp;
import controller.Statistik;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import model.Buchung;
import model.Eintrag;
import model.Fahrzeug;

public class FahrzeugHaeufigkeitController {

	@FXML
	private BarChart<String, Integer> barChart;

	@FXML
	private CategoryAxis xAxis;

	private ObservableList<String> fahrzeugNamen = FXCollections.observableArrayList();

	@FXML
	private void initialize() {
		fahrzeugNamen.add("PKW");
		fahrzeugNamen.add("LKW");
		fahrzeugNamen.add("Motorrad");
		xAxis.setCategories(fahrzeugNamen);
	}

	public void setData(List<Buchung> eintraege) {
		int[] haeufigkeit = new int[3];
		ArrayList<Eintrag> statistik = Statistik.calcAusgeliehen(eintraege);
		int i = 0;
		for (Eintrag e : statistik) {
			haeufigkeit[i] = e.getHaeufigkeit();
			i++;
		}

		XYChart.Series<String, Integer> series = new XYChart.Series<>();

		ArrayList<String> fahrzeugTyp = new ArrayList<String>();
		fahrzeugTyp.add("PKW");
		fahrzeugTyp.add("LKW");
		fahrzeugTyp.add("Motorrad");
		// Create a XYChart.Data object for each month. Add it to the series.
		for (int j = 0; j < fahrzeugTyp.size(); j++) {
			series.getData().add(new XYChart.Data<>(fahrzeugTyp.get(j), haeufigkeit[j]));
		}

		barChart.getData().add(series);
	}

}
