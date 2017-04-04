package view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import controller.Statistik;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Buchung;
import model.Eintrag;
import model.Mitarbeiter;
import model.MitarbeiterListWrapper;

public class MitarbeiterAusleihzeitController {

	public static ObservableList<Mitarbeiter> mitarbeiterData = FXCollections.observableArrayList();

	@FXML
	private BarChart<String, Integer> barChart;

	@FXML
	private CategoryAxis xAxis;

	private ObservableList<String> mitarbeiterNamen = FXCollections.observableArrayList();

	@FXML
	private void initialize() {
		ladeMitarbeiter();
	}
	
	public void setData(List<Buchung> eintraege) {
		int schleifenzaehler = 0;
		ArrayList<Eintrag> statistik = Statistik.mitarbeiterLeihtage(eintraege);
        int[] ausleihzeit = new int[statistik.size()];
        for (Eintrag e : statistik) {
        	if(!mitarbeiterNamen.contains(e.getMitarbeiter().toString()))
        		mitarbeiterNamen.add(e.getMitarbeiter().toString());
            ausleihzeit[schleifenzaehler]=e.getAusleihzeit();
            schleifenzaehler++;
        }
        xAxis.setCategories(mitarbeiterNamen);

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        // Create a XYChart.Data and add it to the series.
        for (int i = 0; i < mitarbeiterNamen.size(); i++) {
            series.getData().add(new XYChart.Data<>(mitarbeiterNamen.get(i), ausleihzeit[i]));
        }

        barChart.getData().add(series);
    }
	
	public static void ladeMitarbeiter() {
	    try {
	        JAXBContext context = JAXBContext
	                .newInstance(MitarbeiterListWrapper.class);
	        Unmarshaller um = context.createUnmarshaller();

	        // Reading XML from the file and unmarshalling.
	        File f = new File("C:/Users/tgma07/git/pt-fuhrpark/src/resources/mitarbeiterData.xml");
	        MitarbeiterListWrapper wrapper = (MitarbeiterListWrapper) um.unmarshal(f);

	        mitarbeiterData.clear();
	        mitarbeiterData.addAll(wrapper.getMitarbeiter());

	    } catch (Exception e) { // catches ANY exception
	    	e.printStackTrace();
	    	Alert alert = new Alert(AlertType.ERROR);
	    	alert.setTitle("Error");
	    	alert.setHeaderText("Could not load data");
	    	alert.setContentText("Could not load data from file");

	    	alert.showAndWait();
	    }
	}
}
