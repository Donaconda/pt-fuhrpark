package view;

import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import controller.MainApp;
import model.Mitarbeiter;

// Controller für Mitarbeiter.fxml

public class MitarbeiterController {

	// Attribute/IDs, die zur Verknüpfung der fxml-Datei mit dem Code benötigt werden
	@FXML
	private TableView<Mitarbeiter> mitarbeiterTabelle;
	@FXML
	private TableColumn<Mitarbeiter, String> vornameSpalte;
	@FXML
	private TableColumn<Mitarbeiter, String> nachnameSpalte;
	@FXML
	private Label vornameLabel;
	@FXML
	private Label nachnameLabel;
	@FXML
	private Label strasseLabel;
	@FXML
	private Label plzLabel;
	@FXML
	private Label wohnortLabel;
	@FXML
	private Label geburtstagLabel;

	private MainApp mainApp;

	// Initialisiert diese Controller-Klasse automatisch, nachdem die zugehörige fxml-Datei fertig geladen ist
	@FXML
	private void initialize() {
		// Initialisiere die Tabelle mit zwei Spalten
		vornameSpalte.setCellValueFactory(cellData -> cellData.getValue().vornameProperty());
		nachnameSpalte.setCellValueFactory(cellData -> cellData.getValue().nachnameProperty());
		// Initialisiere die Mitarbeiterinfo
		zeigeMitarbeiterinfo(null);
		// Zeige die passenden Mitarbeiterdetails an, wenn sich die Auswahl ändert
		mitarbeiterTabelle.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> zeigeMitarbeiterinfo(newValue));
	}

	
	// Wird in MainApp.java aufgerufen, um dieser Klasse eine Referenz zu sich selbst zu geben
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		// Fülle die Tabelle mit Daten aus der .xml-Datei
		mitarbeiterTabelle.setItems(mainApp.getMitarbeiterData());
	}

	private void zeigeMitarbeiterinfo(Mitarbeiter ma) {
		if (ma != null) {
			// Fülle die Labels mit den Daten aus dem Mitarbeiter-Objekt
			vornameLabel.setText(ma.getVorname());
			nachnameLabel.setText(ma.getNachname());
			strasseLabel.setText(ma.getStrasse());
			plzLabel.setText(ma.getPlz());
			wohnortLabel.setText(ma.getWohnort());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			geburtstagLabel.setText(ma.getGeburtstag().format(formatter));
		} else {
			// Mitarbeiter ist null, zeige nichts an
			vornameLabel.setText("");
			nachnameLabel.setText("");
			strasseLabel.setText("");
			plzLabel.setText("");
			wohnortLabel.setText("");
			geburtstagLabel.setText("");
		}
	}

	@FXML
	private void handleMitarbeiterLoeschen() {
		int selectedIndex = mitarbeiterTabelle.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			mitarbeiterTabelle.getItems().remove(selectedIndex);
			mainApp.saveMitarbeiterDataToFile(mainApp.getMitarbeiterFilePath());
		} else {
			// Wenn nichts ausgewählt ist
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Keine Auswahl");
			alert.setHeaderText("Kein Mitarbeiter ausgewählt");
			alert.setContentText("Bitte wählen Sie zuerst einen Mitarbeiter aus der Tabelle aus.");
			alert.showAndWait();
		}
	}

	@FXML
	private void handleMitarbeiterNeu() {
		Mitarbeiter tempItem = new Mitarbeiter();
		boolean okClicked = mainApp.zeigeMitarbeiterDialog(tempItem);
		if (okClicked) {
			mainApp.getMitarbeiterData().add(tempItem);
			mainApp.saveMitarbeiterDataToFile(mainApp.getMitarbeiterFilePath());
		}
	}

	@FXML
	private void handleMitarbeiterBearbeiten() {
		Mitarbeiter selectedItem = mitarbeiterTabelle.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			boolean okClicked = mainApp.zeigeMitarbeiterDialog(selectedItem);
			if (okClicked) {
				mainApp.saveMitarbeiterDataToFile(mainApp.getMitarbeiterFilePath());
				zeigeMitarbeiterinfo(selectedItem);
			}
		} else {
			// Wenn nichts ausgewählt ist
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Keine Auswahl");
			alert.setHeaderText("Kein Mitarbeiter ausgewählt");
			alert.setContentText("Bitte wählen Sie zuerst einen Mitarbeiter aus der Tabelle aus.");
			alert.showAndWait();
		}
	}
}
