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
	        // Assign the month names as categories for the horizontal axis.
	        xAxis.setCategories(xEintraege);
	    }
	    
	    public void setData(List<Buchung> eintraege) {
	        // Count the number of people having their birthday in a specific month.
	        int[] counter = new int[4];
	        ArrayList<Eintrag> statistik = Statistik.avrgTime(eintraege);
	        
	        
		for (Eintrag e : statistik) {
			if (e.getFahrzeug() != null) {
				if (e.getFahrzeug().getKlasse().compareTo("pkw") == 0) {
					counter[0] = e.getAusleihzeit();
				} else if (e.getFahrzeug().getKlasse().compareTo("lkw") == 0) {
					counter[1] = e.getAusleihzeit();
				} else if (e.getFahrzeug().getKlasse().compareTo("motorrad") == 0) {
					counter[2] = e.getAusleihzeit();
				}
			} else{
				counter[3] = e.getAusleihzeit();
			}
		}
	        
	        XYChart.Series<String, Integer> series = new XYChart.Series<>();

	        // Create a XYChart.Data object for each month. Add it to the series.
	        for (int i = 0; i < counter.length; i++) {
	            series.getData().add(new XYChart.Data<>(xEintraege.get(i), counter[i]));
	        }

	        barChart.getData().add(series);
	    }
}
