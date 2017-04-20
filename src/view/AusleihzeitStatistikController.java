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

public class AusleihzeitStatistikController {

	@FXML
	private BarChart<String, Integer> barChart;

	@FXML
	private CategoryAxis xAxis;

	private ObservableList<String> xEintraege = FXCollections.observableArrayList();

	@FXML
	private void initialize() {
		xEintraege.add("PKW");
		xEintraege.add("LKW");
		xEintraege.add("Motorrad");
		xEintraege.add("Gesamt");
		xAxis.setCategories(xEintraege);
	}

	public void setData(List<Buchung> buchungData, ObservableList<Fahrzeug> fahrzeugData) {
		ArrayList<Eintrag> statistik = Statistik.calcFzTypDurchschnittszeit(buchungData, fahrzeugData);
		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		
		for(Eintrag e : statistik){
			series.getData().add(new XYChart.Data<>(e.getX(), e.getY()));
		}
		
		barChart.getData().add(series);
	}
}
