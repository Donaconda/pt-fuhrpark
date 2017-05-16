package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Mitarbeiter;

//Controller für MitarbeiterDialog.fxml

public class MitarbeiterDialogController {

	// Attribute/IDs, die zur Verknüpfung der fxml-Datei mit dem Code benötigt werden
	@FXML
	private TextField vornameFeld;
	@FXML
	private TextField nachnameFeld;
	@FXML
	private TextField strasseFeld;
	@FXML
	private TextField plzFeld;
	@FXML
	private TextField wohnortFeld;
	@FXML
	private TextField geburtstagFeld;

	private Stage dialogStage;
	private Mitarbeiter ma;
	private boolean okClicked = false;

	@FXML
	private void initialize() {
	}

	// Setze die Stage dieses Dialogs, damit sich ein neues Fenster öffnet
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	// Setze die Details in die zugehörigen Textfelder aus dem ausgewählten Mitarbeiter-Objekt
	public void setMitarbeiter(Mitarbeiter ma) {
		this.ma = ma;

		vornameFeld.setText(ma.getVorname());
		nachnameFeld.setText(ma.getNachname());
		strasseFeld.setText(ma.getStrasse());
		plzFeld.setText(ma.getPlz());
		wohnortFeld.setText(ma.getWohnort());
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			geburtstagFeld.setText(ma.getGeburtstag().format(formatter));
			geburtstagFeld.setPromptText("dd.mm.yyyy");
		} catch (Exception e) {

		}

	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {
		if (isInputValid()) {
			ma.setVorname(vornameFeld.getText());
			ma.setNachname(nachnameFeld.getText());
			ma.setStrasse(strasseFeld.getText());
			ma.setPlz(plzFeld.getText());
			ma.setWohnort(wohnortFeld.getText());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			ma.setGeburtstag(LocalDate.parse(geburtstagFeld.getText(), formatter));

			okClicked = true;
			dialogStage.close();
		}
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	// Überprüfe, ob die Eingabe gültig ist
	private boolean isInputValid() {
		String errorMessage = "";
		if (vornameFeld.getText() == null || vornameFeld.getText().length() == 0) {
			errorMessage += "Vorname ungültig!\n";
		}
		if (nachnameFeld.getText() == null || nachnameFeld.getText().length() == 0) {
			errorMessage += "Nachname ungültig!\n";
		}
		if (strasseFeld.getText() == null || strasseFeld.getText().length() == 0) {
			errorMessage += "Straße ungültig!\n";
		}
		if (plzFeld.getText() == null || plzFeld.getText().length() != 5) {
			errorMessage += "PLZ ungültig!\n";
		}
		if (wohnortFeld.getText() == null || wohnortFeld.getText().length() == 0) {
			errorMessage += "Wohnort ungültig!\n";
		}
		if (geburtstagFeld.getText() == null || geburtstagFeld.getText().length() == 0) {
			errorMessage += "Geburtstag ungültig! (tt.mm.jjjj)\n";
		}
		if (errorMessage.length() == 0) {
			return true;
		} else {
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