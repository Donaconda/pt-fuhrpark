package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Fahrzeug;

//Controller f�r FahrzeugDialog.fxml

public class FahrzeugDialogController {

	// Attribute/IDs, die zur Verkn�pfung der fxml-Datei mit dem Code ben�tigt werden
	@FXML
	private TextField markeFeld;
	@FXML
	private TextField modellFeld;
	@FXML
	private TextField kennzeichenFeld;
	@FXML
	private ComboBox<String> klasseFeld;

	private Stage dialogStage;
	private Fahrzeug fz;
	private boolean okClicked = false;

	// Initialisiert diese Controller-Klasse automatisch, nachdem die zugeh�rige fxml-Datei fertig geladen ist
	@FXML
	private void initialize() {
		ObservableList<String> options = FXCollections.observableArrayList("PKW", "LKW", "Motorrad");
		klasseFeld.getItems().addAll(options);
	}

	// Setze die Stage dieses Dialogs, damit sich ein neues Fenster �ffnet
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	// Setze die Details in die zugeh�rigen Textfelder aus dem ausgew�hlten Fahrzeug-Objekt
	public void setFahrzeug(Fahrzeug fz) {
		this.fz = fz;
		try {
			markeFeld.setText(fz.getMarke());
			modellFeld.setText(fz.getModel());
			kennzeichenFeld.setText(fz.getKennzeichen());
			klasseFeld.getSelectionModel().select(fz.getKlasse());
		} catch(Exception e) {
			
		}
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {
		if (isInputValid()) {
			fz.setMarke(markeFeld.getText());
			fz.setModel(modellFeld.getText());
			fz.setKennzeichen(kennzeichenFeld.getText());
			fz.setKlasse(klasseFeld.getSelectionModel().getSelectedItem().toString());

			okClicked = true;
			dialogStage.close();
		}
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	// �berpr�fe, ob die Eingabe g�ltig ist
	private boolean isInputValid() {
		String errorMessage = "";
		if (markeFeld.getText() == null || markeFeld.getText().length() == 0) {
			errorMessage += "Marke ung�ltig!\n";
		}
		if (modellFeld.getText() == null || modellFeld.getText().length() == 0) {
			errorMessage += "Modell ung�ltig!\n";
		}
		if (kennzeichenFeld.getText() == null || kennzeichenFeld.getText().length() == 0) {
			errorMessage += "Kennzeichen ung�ltig!\n";
		}
		if (errorMessage.length() == 0) {
			return true;
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Ung�ltige Felder");
			alert.setHeaderText("Bitte korrigieren Sie die ung�ltigen Felder");
			alert.setContentText(errorMessage);
			alert.showAndWait();
			return false;
		}
	}
}