package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import controller.MainApp;
import controller.Sucher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Buchung;
import model.Fahrzeug;
import model.Mitarbeiter;

/**
 * Dialog to edit details of a person.
 */
public class BuchungDialogController {

	@FXML
	private Label idFeld;
	@FXML
	private ComboBox<String> mitarbeiterFeld;
	@FXML
	private ComboBox<String> fahrzeugFeld;
	@FXML
	private ComboBox<String> zweckFeld;
	@FXML
	private TextField startdatumFeld;
	@FXML
	private TextField enddatumFeld;

	private Stage dialogStage;
	private MainApp mainApp;
	private Buchung bu;
	private boolean okClicked = false;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void fillCombobox() {
		try {
			// Mitarbeiterliste für die ComboBox "mitarbeiterFeld"
			ObservableList<String> maListe = FXCollections.observableArrayList();
			maListe.clear();
			for (Mitarbeiter m : mainApp.getMitarbeiterData()) {
				maListe.add(m.toString());
			}
			mitarbeiterFeld.getItems().addAll(maListe);

			// Fahrzeugliste für die ComboBox "fahrzeugFeld"
			ObservableList<String> fzListe = FXCollections.observableArrayList();
			fzListe.clear();
			for (Fahrzeug f : mainApp.getFahrzeugData()) {
				fzListe.add(f.toString());
			}
			fahrzeugFeld.getItems().addAll(fzListe);

			zweckFeld.getItems().addAll("Stadtfahrt", "Langstreckenfahrt", "Transport von Arbeitsmaterialien",
					"Sonstiges");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Sets the person to be edited in the dialog.
	 * 
	 * @param person
	 */
	public void setBuchung(Buchung bu) {
		this.bu = bu;
		try {
			idFeld.setText(bu.getId().toString());
			zweckFeld.getSelectionModel().select(0);
			mitarbeiterFeld.getSelectionModel().select(0);
			fahrzeugFeld.getSelectionModel().select(0);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
			startdatumFeld.setText(bu.getBeginn().format(formatter));
			startdatumFeld.setPromptText("dd.mm.yyyy hh:mm");
			enddatumFeld.setText(bu.getEnde().format(formatter));
			enddatumFeld.setPromptText("dd.mm.yyyy hh:mm");
		} catch (Exception e) {

		}

	}

	/**
	 * Returns true if the user clicked OK, false otherwise.
	 * 
	 * @return
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			bu.setId(idFeld.getText());
			bu.setMitarbeiter(mitarbeiterFeld.getSelectionModel().getSelectedItem());
			bu.setFahrzeug(fahrzeugFeld.getSelectionModel().getSelectedItem());
			bu.setZweck(zweckFeld.getSelectionModel().getSelectedItem());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
			bu.setBeginn(LocalDateTime.parse(startdatumFeld.getText(), formatter));
			bu.setEnde(LocalDateTime.parse(enddatumFeld.getText(), formatter));

			okClicked = true;
			dialogStage.close();
		}
	}

	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	/**
	 * Validates the user input in the text fields.
	 * 
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";

		if (idFeld.getText() == null || idFeld.getText().length() == 0) {
			errorMessage += "ID ungültig!\n";
		}
		// if (mitarbeiterFeld.getText() == null ||
		// mitarbeiterFeld.getText().length() == 0) {
		// errorMessage += "Mitarbeiter ungültig!\n";
		// }
		// if (fahrzeugFeld.getText() == null || fahrzeugFeld.getText().length()
		// == 0) {
		// errorMessage += "Fahrzeug ungültig!\n";
		// }
		// if (zweckFeld.getText() == null || zweckFeld.getText().length() == 0)
		// {
		// errorMessage += "Zweck ungültig!\n";
		// }
		if (startdatumFeld.getText() == null || startdatumFeld.getText().length() == 0) {
			errorMessage += "Startdatum ungültig! (tt.mm.jjjj)\n";
		}
		if (enddatumFeld.getText() == null || enddatumFeld.getText().length() == 0) {
			errorMessage += "Enddatum ungültig! (tt.mm.jjjj)\n";
		}
		// Überprüfe, ob Fahrzeug zum gegebenen Zeitraum schon vergeben ist
		ObservableList<Buchung> tempBuData = Sucher.sucheBuchungenNachFahrzeug(mainApp.getBuchungData(), fahrzeugFeld.getSelectionModel().getSelectedItem());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
		LocalDateTime tempStartLDT = LocalDateTime.parse(startdatumFeld.getText(), formatter);
		LocalDateTime tempEndLDT = LocalDateTime.parse(enddatumFeld.getText(), formatter);
		for(Buchung bu : tempBuData){
			// Wenn sich die Zeiträume überschneiden...
			if(tempStartLDT.compareTo(bu.getEnde()) <= 0 && tempEndLDT.compareTo(bu.getBeginn()) >= 0){
				// ... Werfe eine Fehlermeldung
				errorMessage += "Das Fahrzeug ist bereits von " + bu.getBeginn().format(formatter) + " bis " + bu.getEnde().format(formatter) + " verbucht!\n";
			}
		}
		
		
		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Ungültige Felder");
			alert.setHeaderText("Bitte korrigieren Sie die ungültigen Felder");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}
}