package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Fahrzeug;

/**
 * Dialog to edit details of a person.
 */
public class FahrzeugDialogController {

    @FXML
    private TextField markeFeld;
    @FXML
    private TextField modellFeld;
    @FXML
    private TextField kennzeichenFeld;
    @FXML
    private TextField klasseFeld;

    private Stage dialogStage;
    private Fahrzeug fz;
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
    public void setFahrzeug(Fahrzeug fz) {
        this.fz = fz;
        
        markeFeld.setText(fz.getMarke());
        modellFeld.setText(fz.getModel());
        kennzeichenFeld.setText(fz.getKennzeichen());
        klasseFeld.setText(fz.getKlasse());        
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
            fz.setMarke(markeFeld.getText());
            fz.setModel(modellFeld.getText());
            fz.setKennzeichen(kennzeichenFeld.getText());
            fz.setKlasse(klasseFeld.getText());

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

        if (markeFeld.getText() == null || markeFeld.getText().length() == 0) {
            errorMessage += "Marke ungültig!\n"; 
        }
        if (modellFeld.getText() == null || modellFeld.getText().length() == 0) {
            errorMessage += "Modell ungültig!\n"; 
        }
        if (kennzeichenFeld.getText() == null || kennzeichenFeld.getText().length() == 0) {
            errorMessage += "Kennzeichen ungültig!\n"; 
        }
        if (klasseFeld.getText() == null || klasseFeld.getText().length() == 0) {
            errorMessage += "Klasse ungültig!\n"; 
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