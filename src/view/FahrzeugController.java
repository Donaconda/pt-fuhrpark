package view;


import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import controller.MainApp;
import model.Fahrzeug;
import model.Mitarbeiter;

public class FahrzeugController {
	
	// Attribute/IDs, die zur Verknüpfung der fxml-Dateimit dem Code benötigt werden
	@FXML
	private TableView<Fahrzeug> fahrzeugTabelle;
	@FXML
	private TableColumn<Fahrzeug, String> kennzeichenSpalte;
	@FXML
	private TableColumn<Fahrzeug, String> modellSpalte;
	@FXML
	private Label markeLabel;
	@FXML
	private Label modellLabel;
	@FXML
	private Label kennzeichenLabel;
	@FXML
	private Label klasseLabel;

	private MainApp mainApp;
	
	public FahrzeugController(){
	}
	
	@FXML
	private void initialize() {
		// Initialisiere die Tabelle mit zwei Spalten
		kennzeichenSpalte.setCellValueFactory(cellData -> cellData.getValue().KennzeichenProperty());
		modellSpalte.setCellValueFactory(cellData -> cellData.getValue().ModelProperty());

		// Initialisiere die Mitarbeiterinfo
		zeigeFahrzeuginfo(null);

		// Zeige die passenden Mitarbeiterdetails an, wenn sich die Auswahl ändert
		fahrzeugTabelle.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> zeigeFahrzeuginfo(newValue));
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		// Fülle die Tabelle mit Daten aus der .xml-Datei
		fahrzeugTabelle.setItems(mainApp.getFahrzeugData());
	}
	
	private void zeigeFahrzeuginfo(Fahrzeug fz) {
		if (fz != null) {
			// Fülle die Labels mit den Daten aus dem Mitarbeiter Objekt
			markeLabel.setText(fz.getMarke());
			modellLabel.setText(fz.getModel());
			kennzeichenLabel.setText(fz.getKennzeichen());
			klasseLabel.setText(fz.getKlasse());
		} else {
			// Mitarbeiter ist null, zeige nichts an
			markeLabel.setText("");
			modellLabel.setText("");
			kennzeichenLabel.setText("");
			klasseLabel.setText("");
		}
	}
}
