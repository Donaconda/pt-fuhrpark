package view;

import java.util.ArrayList;
import java.util.List;
import controller.Statistik;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import model.Buchung;
import model.Eintrag;
import model.Mitarbeiter;

public class MitarbeiterAusleihzeitController {

	@FXML
	private BarChart<String, Integer> barChart;

	public void setData(List<Buchung> buchungData, ObservableList<Mitarbeiter> mitarbeiterData) {
		ArrayList<Eintrag> statistik = Statistik.calcMitarbeiterLeihzeit(buchungData, mitarbeiterData);
		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		
		for(Eintrag e : statistik){
			series.getData().add(new XYChart.Data<>(e.getX(), e.getY()));
		}
		
		barChart.getData().add(series);
	}

}
