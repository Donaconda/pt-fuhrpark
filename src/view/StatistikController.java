package view;

import java.util.ArrayList;
import controller.MainApp;
import controller.Statistik;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import model.Eintrag;

public class StatistikController {

	@FXML
	private BarChart<String, Integer> diagramm;
	
	@FXML
	private Label statistikInfo;

	@FXML
	private CategoryAxis xAchse;

	private MainApp mainApp;
	
	@FXML
	private void initialize() {
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	public void zeigeFzTypHauefigkeitDaten() {
		statistikInfo.setText("Häufigkeit der Buchungen pro Fahrzeugtyp");
		diagramm.getData().clear();
		
		ArrayList<Eintrag> statistik = Statistik.calcFzTypHaeufigkeit(mainApp.getBuchungData(), mainApp.getFahrzeugData());
		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		
		for(Eintrag e : statistik){
			series.getData().add(new XYChart.Data<>(e.getX(), e.getY()));
		}
		
		diagramm.getData().add(series);
	}

	public void zeigeFzTypDurchschnittszeitDaten() {
		statistikInfo.setText("Durchschnittliche Leihdauer der Buchungen pro Fahrzeugtyp");
		diagramm.getData().clear();
		
		ArrayList<Eintrag> statistik = Statistik.calcFzTypDurchschnittszeit(mainApp.getBuchungData(), mainApp.getFahrzeugData());
		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		
		for(Eintrag e : statistik){
			series.getData().add(new XYChart.Data<>(e.getX(), e.getY()));
		}
		
		diagramm.getData().add(series);
	}
	
	public void zeigeMitarbeiterLeihzeitDaten() {
		statistikInfo.setText("Summe der Leihzeiten pro Mitarbeiter");
		diagramm.getData().clear();
		
		ArrayList<Eintrag> statistik = Statistik.calcMitarbeiterLeihzeit(mainApp.getBuchungData(), mainApp.getMitarbeiterData());
		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		
		for(Eintrag e : statistik){
			series.getData().add(new XYChart.Data<>(e.getX(), e.getY()));
		}
		
		diagramm.getData().add(series);
	}
}
