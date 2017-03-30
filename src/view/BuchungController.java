package view;


import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import controller.MainApp;
import model.Buchung;

public class BuchungController {

	// Attribute/IDs, die zur Verknüpfung der fxml-Datei mit dem Code benötigt werden
	@FXML
	private TableView<Buchung> buchungTabelle;
	@FXML
	private TableColumn<Buchung, String> idSpalte;
	@FXML
	private TableColumn<Buchung, String> mitarbeiterSpalte;
	@FXML
	private Label idLabel;
	@FXML
	private Label mitarbeiterLabel;
	@FXML
	private Label fahrzeugLabel;
	@FXML
	private Label zweckLabel;
	@FXML
	private Label startdatumLabel;
	@FXML
	private Label enddatumLabel;

	private MainApp mainApp;

	public BuchungController(){
	}

	@FXML
	private void initialize() {
		// Initialisiere die Tabelle mit zwei Spalten
		idSpalte.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		mitarbeiterSpalte.setCellValueFactory(cellData -> cellData.getValue().mitarbeiterProperty());

		// Initialisiere die Mitarbeiterinfo
		zeigeBuchunginfo(null);

		// Zeige die passenden Mitarbeiterdetails an, wenn sich die Auswahl ändert
		buchungTabelle.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> zeigeBuchunginfo(newValue));
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		// Fülle die Tabelle mit Daten aus der .xml-Datei
		buchungTabelle.setItems(mainApp.getBuchungData());
	}

	private void zeigeBuchunginfo(Buchung bu) {
		if (bu != null) {
			// Fülle die Labels mit den Daten aus dem Mitarbeiter Objekt
			idLabel.setText(bu.getId());
			mitarbeiterLabel.setText(bu.getMitarbeiter());
			fahrzeugLabel.setText(bu.getFahrzeug());
			zweckLabel.setText(bu.getZweck());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
			startdatumLabel.setText(bu.getBeginn().format(formatter));
			enddatumLabel.setText(bu.getEnde().format(formatter));
		} else {
			// Mitarbeiter ist null, zeige nichts an
			idLabel.setText("");
			mitarbeiterLabel.setText("");
			fahrzeugLabel.setText("");
			zweckLabel.setText("");
			startdatumLabel.setText("");
			enddatumLabel.setText("");
		}
	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new person.
	 */
	@FXML
	private void handleBuchungNeu() {
		Buchung tempItem = new Buchung();
		boolean okClicked = mainApp.zeigeBuchungDialog(tempItem);
		if (okClicked) {
			mainApp.getBuchungData().add(tempItem);
			mainApp.saveBuchungDataToFile(mainApp.getBuchungFilePath());
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected person.
	 */
	@FXML
	private void handleBuchungBearbeiten() {
		Buchung selectedItem = buchungTabelle.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			boolean okClicked = mainApp.zeigeBuchungDialog(selectedItem);
			if (okClicked) {
				mainApp.saveBuchungDataToFile(mainApp.getBuchungFilePath());
				zeigeBuchunginfo(selectedItem);
			}
		} else {
			// Wenn nichts ausgewählt ist
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Keine Auswahl");
			alert.setHeaderText("Keine Buchung ausgewählt");
			alert.setContentText("Bitte wählen Sie zuerst eine Buchung aus der Tabelle aus.");
			alert.showAndWait();
		}
	}
}
