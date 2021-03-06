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

public class MitarbeiterController {

	// Attribute/IDs, die zur Verkn�pfung der fxml-Dateimit dem Code ben�tigt
	// werden
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

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public MitarbeiterController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialisiere die Tabelle mit zwei Spalten
		vornameSpalte.setCellValueFactory(cellData -> cellData.getValue().vornameProperty());
		nachnameSpalte.setCellValueFactory(cellData -> cellData.getValue().nachnameProperty());

		// Initialisiere die Mitarbeiterinfo
		zeigeMitarbeiterinfo(null);

		// Zeige die passenden Mitarbeiterdetails an, wenn sich die Auswahl
		// �ndert
		mitarbeiterTabelle.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> zeigeMitarbeiterinfo(newValue));
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		// F�lle die Tabelle mit Daten aus der .xml-Datei
		mitarbeiterTabelle.setItems(mainApp.getMitarbeiterData());
	}

	/**
	 * Fills all text fields to show details about the person. If the specified
	 * person is null, all text fields are cleared.
	 * 
	 * @param person
	 *            the person or null
	 */
	private void zeigeMitarbeiterinfo(Mitarbeiter ma) {
		if (ma != null) {
			// F�lle die Labels mit den Daten aus dem Mitarbeiter Objekt
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

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleMitarbeiterLoeschen() {
		int selectedIndex = mitarbeiterTabelle.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			mitarbeiterTabelle.getItems().remove(selectedIndex);
			mainApp.saveMitarbeiterDataToFile(mainApp.getMitarbeiterFilePath());
		} else {
			// Wenn nichts ausgew�hlt ist
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Keine Auswahl");
			alert.setHeaderText("Kein Mitarbeiter ausgew�hlt");
			alert.setContentText("Bitte w�hlen Sie zuerst einen Mitarbeiter aus der Tabelle aus.");
			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new person.
	 */
	@FXML
	private void handleMitarbeiterNeu() {
		Mitarbeiter tempItem = new Mitarbeiter();
		boolean okClicked = mainApp.zeigeMitarbeiterDialog(tempItem);
		if (okClicked) {
			mainApp.getMitarbeiterData().add(tempItem);
			mainApp.saveMitarbeiterDataToFile(mainApp.getMitarbeiterFilePath());
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected person.
	 */
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
			// Wenn nichts ausgew�hlt ist
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Keine Auswahl");
			alert.setHeaderText("Kein Mitarbeiter ausgew�hlt");
			alert.setContentText("Bitte w�hlen Sie zuerst einen Mitarbeiter aus der Tabelle aus.");
			alert.showAndWait();
		}
	}
}
