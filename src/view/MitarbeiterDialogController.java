package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Mitarbeiter;

/**
 * Dialog to edit details of a person.
 */
public class MitarbeiterDialogController {

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

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
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