package view;

import java.util.ArrayList;
import java.util.List;
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
		fahrzeugNamen.add("Gesamt");
		xAxis.setCategories(fahrzeugNamen);
	}

	public void setData(List<Buchung> buchungData, ObservableList<Fahrzeug> fahrzeugData) {
		ArrayList<Eintrag> statistik = Statistik.calcFzTypHaeufigkeit(buchungData, fahrzeugData);
		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		
		for(Eintrag e : statistik){
			series.getData().add(new XYChart.Data<>(e.getX(), e.getY()));
		}
		
		barChart.getData().add(series);
	}

}
