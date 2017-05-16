package view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import controller.MainApp;
import model.Buchung;

//Controller für Buchung.fxml

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

	public BuchungController() {
	}

	public int idCounter = 0;

	// Initialisiert diese Controller-Klasse automatisch, nachdem die zugehörige fxml-Datei fertig geladen ist
	@FXML
	private void initialize() {
		// Initialisiere die Tabelle mit zwei Spalten
		idSpalte.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		mitarbeiterSpalte.setCellValueFactory(cellData -> cellData.getValue().mitarbeiterProperty());
		// Initialisiere die Buchungsinfo
		zeigeBuchunginfo(null);
		// Zeige die passenden Buchungsdetails an, wenn sich die Auswahl ändert
		buchungTabelle.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> zeigeBuchunginfo(newValue));
	}

	// Wird in MainApp.java aufgerufen, um dieser Klasse eine Referenz zu sich selbst zu geben
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		// Fülle die Tabelle mit Daten aus der .xml-Datei
		buchungTabelle.setItems(mainApp.getBuchungData());
	}

	private void zeigeBuchunginfo(Buchung bu) {
		if (bu != null) {
			// Fülle die Labels mit den Daten aus dem Buchung-Objekt
			idLabel.setText(bu.getId());
			mitarbeiterLabel.setText(bu.getMitarbeiter());
			fahrzeugLabel.setText(bu.getFahrzeug());
			zweckLabel.setText(bu.getZweck());
			startdatumLabel.setText(bu.getBeginn().format(mainApp.getDTFormatter()));
			enddatumLabel.setText(bu.getEnde().format(mainApp.getDTFormatter()));
		} else {
			// Buchung ist null, zeige nichts an
			idLabel.setText("");
			mitarbeiterLabel.setText("");
			fahrzeugLabel.setText("");
			zweckLabel.setText("");
			startdatumLabel.setText("");
			enddatumLabel.setText("");
		}
	}

	@FXML
	private void handleBuchungNeu() {
		Buchung tempItem = new Buchung();
		calculateID();
		tempItem.setId(String.valueOf(idCounter + 1));
		boolean okClicked = mainApp.zeigeBuchungDialog(tempItem);
		if (okClicked) {
			mainApp.getBuchungData().add(tempItem);
			mainApp.saveBuchungDataToFile(mainApp.getBuchungFilePath());
		}
	}

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

	// Methode, um jeder Buchung eine einzigartige ID zu verleihen
	public void calculateID() {
		ObservableList<Buchung> buchungen = mainApp.getBuchungData();
		this.idCounter = buchungen.size();
	}
}
