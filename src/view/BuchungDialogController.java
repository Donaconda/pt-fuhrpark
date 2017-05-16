package view;

import java.time.LocalDateTime;
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

//Controller f�r BuchungDialog.fxml

public class BuchungDialogController {

	// Attribute/IDs, die zur Verkn�pfung der fxml-Datei mit dem Code ben�tigt werden
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

	@FXML
	private void initialize() {
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	// F�llt die Auswahlfelder "Mitarbeiter" und "Fahrzeug" mit den Daten aus den XML-Dateien
	// und "Zweck" mit den hier definierten Strings
	public void fillCombobox() {
		try {
			// Mitarbeiterliste f�r die ComboBox "mitarbeiterFeld"
			ObservableList<String> maListe = FXCollections.observableArrayList();
			maListe.clear();
			for (Mitarbeiter m : mainApp.getMitarbeiterData()) {
				maListe.add(m.toString());
			}
			mitarbeiterFeld.getItems().addAll(maListe);

			// Fahrzeugliste f�r die ComboBox "fahrzeugFeld"
			ObservableList<String> fzListe = FXCollections.observableArrayList();
			fzListe.clear();
			for (Fahrzeug f : mainApp.getFahrzeugData()) {
				fzListe.add(f.toString());
			}
			fahrzeugFeld.getItems().addAll(fzListe);

			// Strings f�r die ComboBox "zweckFeld"
			zweckFeld.getItems().addAll("Stadtfahrt", "Langstreckenfahrt", "Transport von Arbeitsmaterialien",
					"Sonstiges");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Setze die Stage dieses Dialogs, damit sich ein neues Fenster �ffnet
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	// Setze die Details in die zugeh�rigen Textfelder aus dem ausgew�hlten Buchung-Objekt
	public void setBuchung(Buchung bu) {
		this.bu = bu;
		startdatumFeld.setPromptText("tt.mm.jjjj hh:mm");
		enddatumFeld.setPromptText("tt.mm.jjjj hh:mm");
		try {
			idFeld.setText(bu.getId().toString());
			mitarbeiterFeld.getSelectionModel().select(bu.getMitarbeiter());
			fahrzeugFeld.getSelectionModel().select(bu.getFahrzeug());
			zweckFeld.getSelectionModel().select(bu.getZweck());
			startdatumFeld.setText(bu.getBeginn().format(mainApp.getDTFormatter()));
			enddatumFeld.setText(bu.getEnde().format(mainApp.getDTFormatter()));
		} catch (Exception e) {
			
		}

	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {
		if (isInputValid()) {
			bu.setMitarbeiter(mitarbeiterFeld.getSelectionModel().getSelectedItem());
			bu.setFahrzeug(fahrzeugFeld.getSelectionModel().getSelectedItem());
			bu.setZweck(zweckFeld.getSelectionModel().getSelectedItem());
			bu.setBeginn(LocalDateTime.parse(startdatumFeld.getText(), mainApp.getDTFormatter()));
			bu.setEnde(LocalDateTime.parse(enddatumFeld.getText(), mainApp.getDTFormatter()));

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
		
		if (startdatumFeld.getText() == null || startdatumFeld.getText().length() == 0) {
			errorMessage += "Startdatum ung�ltig! (tt.mm.jjjj hh:mm)\n";
		}
		if (enddatumFeld.getText() == null || enddatumFeld.getText().length() == 0) {
			errorMessage += "Enddatum ung�ltig! (tt.mm.jjjj hh:mm)\n";
		}
		// �berpr�fe, ob Fahrzeug zum gegebenen Zeitraum schon vergeben ist
		String fzkz = fahrzeugFeld.getSelectionModel().getSelectedItem().split("\\(")[1].split("\\)")[0];
		ObservableList<Buchung> tempBuData = Sucher.sucheBuchungenNachKennzeichen(mainApp.getBuchungData(), fzkz);
		LocalDateTime tempStartLDT = LocalDateTime.parse(startdatumFeld.getText(), mainApp.getDTFormatter());
		LocalDateTime tempEndLDT = LocalDateTime.parse(enddatumFeld.getText(), mainApp.getDTFormatter());
		for(Buchung bu : tempBuData){
			// Wenn es sich nicht um die gleiche Buchung handelt...
			if(bu.getId().compareTo(idFeld.getText()) != 0){
				// ... Und sich die Zeitr�ume �berschneiden...
				if(tempStartLDT.compareTo(bu.getEnde()) <= 0 && tempEndLDT.compareTo(bu.getBeginn()) >= 0){
					// ... Werfe eine Fehlermeldung
					errorMessage += "Das ausgew�hlte Fahrzeug ist bereits von " + bu.getBeginn().format(mainApp.getDTFormatter()) + " bis " + bu.getEnde().format(mainApp.getDTFormatter()) + " an " + bu.getMitarbeiter() + " verbucht!\n";
				}
			}
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