package view;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import controller.MainApp;
import model.Fahrzeug;

//Controller für Fahrzeug.fxml

public class FahrzeugController {

	// Attribute/IDs, die zur Verknüpfung der fxml-Datei mit dem Code benötigt werden
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
	@FXML
	private TextField sucheFeld;

	private MainApp mainApp;

	public FahrzeugController() {
	}

	// Initialisiert diese Controller-Klasse automatisch, nachdem die zugehörige fxml-Datei fertig geladen ist
	@FXML
	private void initialize() {
		// Initialisiere die Tabelle mit zwei Spalten
		kennzeichenSpalte.setCellValueFactory(cellData -> cellData.getValue().KennzeichenProperty());
		modellSpalte.setCellValueFactory(cellData -> cellData.getValue().MarkeProperty().concat(' ').concat(cellData.getValue().ModelProperty()));
		// Initialisiere die Fahrzeuginfo
		zeigeFahrzeuginfo(null);
		// Zeige die passenden Fahrzeugdetails an, wenn sich die Auswahl in der Tabelle ändert
		fahrzeugTabelle.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> zeigeFahrzeuginfo(newValue)); 
		// Aktualisiere die Tabelle jedes Mal, wenn sich das Suchfeld ändert
        sucheFeld.textProperty().addListener((observable, oldValue, newValue) -> {
        	filterTabelle(newValue);
        });
	}

	// Wird in MainApp.java aufgerufen, um dieser Klasse eine Referenz zu sich selbst zu geben
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		// Fülle die Tabelle mit Daten aus der .xml-Datei
		fahrzeugTabelle.setItems(mainApp.getFahrzeugData());
	}

	private void zeigeFahrzeuginfo(Fahrzeug fz) {
		if (fz != null) {
			// Fülle die Labels mit den Daten aus dem Fahrzeug-Objekt
			markeLabel.setText(fz.getMarke());
			modellLabel.setText(fz.getModel());
			kennzeichenLabel.setText(fz.getKennzeichen());
			klasseLabel.setText(fz.getKlasse());
		} else {
			// Fahrzeug ist null, zeige nichts an
			markeLabel.setText("");
			modellLabel.setText("");
			kennzeichenLabel.setText("");
			klasseLabel.setText("");
		}
	}

	@FXML
	private void handleFahrzeugLoeschen() {
		int selectedIndex = fahrzeugTabelle.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			fahrzeugTabelle.getItems().remove(selectedIndex);
			mainApp.saveFahrzeugDataToFile(mainApp.getFahrzeugFilePath());
		} else {
			// Wenn nichts ausgewählt ist
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Keine Auswahl");
			alert.setHeaderText("Kein Fahrzeug ausgewählt");
			alert.setContentText("Bitte wählen Sie zuerst einen Fahrzeug aus der Tabelle aus.");
			alert.showAndWait();
		}
	}

	@FXML
	private void handleFahrzeugNeu() {
		Fahrzeug tempItem = new Fahrzeug();
		boolean okClicked = mainApp.zeigeFahrzeugDialog(tempItem);
		if (okClicked) {
			mainApp.getFahrzeugData().add(tempItem);
			mainApp.saveFahrzeugDataToFile(mainApp.getFahrzeugFilePath());
		}
	}

	@FXML
	private void handleFahrzeugBearbeiten() {
		Fahrzeug selectedItem = fahrzeugTabelle.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			boolean okClicked = mainApp.zeigeFahrzeugDialog(selectedItem);
			if (okClicked) {
				mainApp.saveFahrzeugDataToFile(mainApp.getFahrzeugFilePath());
				zeigeFahrzeuginfo(selectedItem);
			}
		} else {
			// Wenn nichts ausgewählt ist
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Keine Auswahl");
			alert.setHeaderText("Kein Fahrzeug ausgewählt");
			alert.setContentText("Bitte wählen Sie zuerst ein Fahrzeug aus der Tabelle aus.");
			alert.showAndWait();
		}
	}
	
	// Filtert die Tabelle nach dem gegebenen String
	private void filterTabelle(String newValue){
		// Packe alle Fahrzeugdaten in ein FilteredList-Objekt
		FilteredList<Fahrzeug> filteredData = new FilteredList<>(mainApp.getFahrzeugData(), p -> true);
		// Setze den Filtertext (Prädikat) auf die Liste
        filteredData.setPredicate(fz -> {
            // Wenn das Suchfeld leer ist, zeige alle Daten an
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            // Vergleiche Kennzeichen, Marke und Modell jedes Fahrzeugs mit dem Prädikat
            String lowerCaseFilter = newValue.toLowerCase();
            if (fz.getKennzeichen().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (fz.getMarke().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if(fz.getModel().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }
            // Gebe false zurück, wenn das Prädikat nicht passt
            return false;
        });
        // Aktualisiere die Tabelle
        fahrzeugTabelle.setItems(filteredData);
	}
}
