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

	// Attribute/IDs, die zur Verknüpfung der fxml-Dateimit dem Code benötigt werden
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
	 * The constructor.
	 * The constructor is called before the initialize() method.
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

		// Zeige die passenden Mitarbeiterdetails an, wenn sich die Auswahl ändert
		mitarbeiterTabelle.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> zeigeMitarbeiterinfo(newValue));
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		// Fülle die Tabelle mit Daten aus der .xml-Datei
		mitarbeiterTabelle.setItems(mainApp.getMitarbeiterData());
	}

	/**
	 * Fills all text fields to show details about the person.
	 * If the specified person is null, all text fields are cleared.
	 * 
	 * @param person the person or null
	 */
	private void zeigeMitarbeiterinfo(Mitarbeiter ma) {
		if (ma != null) {
			// Fülle die Labels mit den Daten aus dem Mitarbeiter Objekt
			vornameLabel.setText(ma.getVorname());
			nachnameLabel.setText(ma.getNachname());
			strasseLabel.setText(ma.getStrasse());
			plzLabel.setText(Integer.toString(ma.getPlz()));
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

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new person.
	 */
	@FXML
	private void handleMitarbeiterNeu() {
		Mitarbeiter tempPerson = new Mitarbeiter();
		boolean okClicked = mainApp.zeigeMitarbeiterDialog(tempPerson);
		if (okClicked) {
			mainApp.getMitarbeiterData().add(tempPerson);
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected person.
	 */
	@FXML
	private void handleMitarbeiterBearbeiten() {
		Mitarbeiter selectedPerson = mitarbeiterTabelle.getSelectionModel().getSelectedItem();
		if (selectedPerson != null) {
			boolean okClicked = mainApp.zeigeMitarbeiterDialog(selectedPerson);
			if (okClicked) {
				zeigeMitarbeiterinfo(selectedPerson);
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
