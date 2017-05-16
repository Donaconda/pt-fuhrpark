package controller;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.prefs.Preferences;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Mitarbeiter;
import model.MitarbeiterListWrapper;
import model.Buchung;
import model.BuchungListWrapper;
import model.Fahrzeug;
import model.FahrzeugListWrapper;
import view.StartFensterController;
import view.StatistikController;
import view.BuchungController;
import view.BuchungDialogController;
import view.FahrzeugController;
import view.FahrzeugDialogController;
import view.MitarbeiterController;
import view.MitarbeiterDialogController;

public class MainApp extends Application {

	// -------------------------------------------------------------------
	// Variablen
	// -------------------------------------------------------------------
	
	private Stage primaryStage;
	private BorderPane startFenster;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
	
	// Daten in Form einer Liste
	private ObservableList<Mitarbeiter> mitarbeiterData = FXCollections.observableArrayList();
	private ObservableList<Fahrzeug> fahrzeugData = FXCollections.observableArrayList();
	private ObservableList<Buchung> buchungData = FXCollections.observableArrayList();
	
	// -------------------------------------------------------------------
	// Methoden
	// -------------------------------------------------------------------
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public ObservableList<Mitarbeiter> getMitarbeiterData() {
		return mitarbeiterData;
	}
	
	public ObservableList<Buchung> getBuchungData() {
		return buchungData;
	}
	
	public ObservableList<Fahrzeug> getFahrzeugData() {
		return fahrzeugData;
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public DateTimeFormatter getDTFormatter(){
		return formatter;
	}

	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		// Fenstertitel des Programms
		this.primaryStage.setTitle("Fuhrparkverwaltung BSK");
		initStartFenster();
	}

	@SuppressWarnings("unchecked")
	public void initStartFenster() {
		try {
			// Lade das Startfenster von der fxml-Datei
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/StartFenster.fxml"));
			startFenster = (BorderPane) loader.load();
			// Zeige das Startfenster
			Scene scene = new Scene(startFenster);
			primaryStage.setScene(scene);
			StartFensterController controller = loader.getController();
			controller.setMainApp(this);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Lade alle Daten
		File maFile = new File("src/resources/mitarbeiterData.xml");
		if (maFile != null) {
			loadMitarbeiterDataFromFile(maFile);
		}
		File fzFile = new File("src/resources/fahrzeugData.xml");
		if (fzFile != null) {
			loadFahrzeugDataFromFile(fzFile);
		}
		File buFile = new File("src/resources/buchungData.xml");
		if (buFile != null) {
			loadBuchungDataFromFile(buFile);
		}
		this.mitarbeiterData = (ObservableList<Mitarbeiter>) Sortierer.sort(mitarbeiterData);
		this.fahrzeugData = (ObservableList<Fahrzeug>) Sortierer.sort(fahrzeugData);
		this.buchungData = (ObservableList<Buchung>) Sortierer.sort(buchungData);
		
		// Zeige nach dem Start das Mitarbeiterfenster an
		zeigeMitarbeiterFenster();
	}

	// -------------------------------------------------------------------
	// Methoden zur Anzeige der Fenster und Dialoge
	// -------------------------------------------------------------------
	
	public void zeigeMitarbeiterFenster() {
		try {
			// Lade das Mitarbeiterfenster aus der fxml-Datei
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/Mitarbeiter.fxml"));
			AnchorPane mitarbeiterFenster = (AnchorPane) loader.load();
			// Setze das Mitarbeiterfenster in das Startfenster
			startFenster.setCenter(mitarbeiterFenster);
			MitarbeiterController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void zeigeFahrzeugFenster() {
		try {
			// Lade das Fahrzeugfenster aus der fxml-Datei
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/Fahrzeug.fxml"));
			AnchorPane fahrzeugFenster = (AnchorPane) loader.load();
			// Setze das Fahrzeugfenster in das Startfenster
			startFenster.setCenter(fahrzeugFenster);
			FahrzeugController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void zeigeBuchungFenster() {
		try {
			// Lade das Buchungsfenster aus der fxml-Datei
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/Buchung.fxml"));
			AnchorPane buchungFenster = (AnchorPane) loader.load();
			// Setze das Buchungsfenster in das Startfenster
			startFenster.setCenter(buchungFenster);
			BuchungController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void zeigeStatistikFenster() {
		try {
			// Lade das Statistikfenster aus der fxml-Datei
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/Statistik.fxml"));
			AnchorPane statistikFenster = (AnchorPane) loader.load();
			// Setze das Buchungsfenster in das Startfenster
			startFenster.setCenter(statistikFenster);
			StatistikController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean zeigeMitarbeiterDialog(Mitarbeiter ma) {
		try {
			// Lade das Mitarbeiterdialogfenster aus der fxml-Datei
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/MitarbeiterDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Erstelle eine neue Stage, damit das Dialogfenster über dem Hauptfenster erscheint
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Mitarbeiter bearbeiten");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			MitarbeiterDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			// Übergebe den ausgewählten Mitarbeiter an den neuen Dialog
			controller.setMitarbeiter(ma);
			// Zeige das Dialogfenster und warte, bis es vom Benutzer geschlossen wird
			dialogStage.showAndWait();
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean zeigeFahrzeugDialog(Fahrzeug fz) {
		try {
			// Lade das Fahrzeugdialogfenster aus der fxml-Datei
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/FahrzeugDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Erstelle eine neue Stage, damit das Dialogfenster über dem Hauptfenster erscheint
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Fahrzeug bearbeiten");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			FahrzeugDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			// Übergebe den ausgewählten Mitarbeiter an den neuen Dialog
			controller.setFahrzeug(fz);
			// Zeige das Dialogfenster und warte, bis es vom Benutzer geschlossen wird
			dialogStage.showAndWait();
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean zeigeBuchungDialog(Buchung bu) {
		try {
			// Lade das Buchungsdialogfenster aus der fxml-Datei
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/BuchungDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			// Erstelle eine neue Stage, damit das Dialogfenster über dem Hauptfenster erscheint
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Buchung bearbeiten");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			BuchungDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMainApp(this);
			controller.fillCombobox();
			// Übergebe den ausgewählten Mitarbeiter an den neuen Dialog
			controller.setBuchung(bu);
			// Zeige das Dialogfenster und warte, bis es vom Benutzer geschlossen wird
			dialogStage.showAndWait();
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// -------------------------------------------------------------------
	// Methoden für die Speicherung und das Laden der Daten in bzw. aus XML-Dateien
	// -------------------------------------------------------------------
	
	// Getter und Setter der Dateipfade
	
	public File getMitarbeiterFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		String filePath = prefs.get("maFilePath", null);
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}
	public void setMitarbeiterFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		if (file != null) {
			prefs.put("maFilePath", file.getPath());
		} else {
			prefs.remove("maFilePath");
		}
	}

	public File getFahrzeugFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		String filePath = prefs.get("fzFilePath", null);
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}
	public void setFahrzeugFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		if (file != null) {
			prefs.put("fzFilePath", file.getPath());
		} else {
			prefs.remove("fzFilePath");
		}
	}

	public File getBuchungFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		String filePath = prefs.get("buFilePath", null);
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}
	public void setBuchungFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		if (file != null) {
			prefs.put("buFilePath", file.getPath());
		} else {
			prefs.remove("buFilePath");
		}
	}

	// Methoden für das Laden der Daten
	
	public void loadMitarbeiterDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(MitarbeiterListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();
			// Reading XML from the file and unmarshalling.
			MitarbeiterListWrapper wrapper = (MitarbeiterListWrapper) um.unmarshal(file);
			mitarbeiterData.clear();
			mitarbeiterData.addAll(wrapper.getMitarbeiter());
			// Save the file path to the registry.
			setMitarbeiterFilePath(file);
		} catch (Exception e) { // catches ANY exception
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler");
			alert.setHeaderText("Laden der Daten gescheitert");
			alert.setContentText("Die Daten konnten nicht geladen werden von:\n" + file.getPath());
			alert.showAndWait();
		}
	}

	public void loadFahrzeugDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(FahrzeugListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();
			// Reading XML from the file and unmarshalling.
			FahrzeugListWrapper wrapper = (FahrzeugListWrapper) um.unmarshal(file);
			fahrzeugData.clear();
			fahrzeugData.addAll(wrapper.getFahrzeug());
			// Save the file path to the registry.
			setFahrzeugFilePath(file);
		} catch (Exception e) { // catches ANY exception
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler");
			alert.setHeaderText("Laden der Daten gescheitert");
			alert.setContentText("Die Daten konnten nicht geladen werden von:\n" + file.getPath());
			alert.showAndWait();
		}
	}

	public void loadBuchungDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(BuchungListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();
			// Reading XML from the file and unmarshalling.
			BuchungListWrapper wrapper = (BuchungListWrapper) um.unmarshal(file);
			buchungData.clear();
			buchungData.addAll(wrapper.getBuchung());
			// Save the file path to the registry.
			setBuchungFilePath(file);
		} catch (Exception e) { // catches ANY exception
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler");
			alert.setHeaderText("Laden der Daten gescheitert");
			alert.setContentText("Die Daten konnten nicht geladen werden von:\n" + file.getPath());
			alert.showAndWait();
		}
	}

	// Methoden für das Speichern der Daten
	
	public void saveMitarbeiterDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(MitarbeiterListWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// Wrapping our data.
			MitarbeiterListWrapper wrapper = new MitarbeiterListWrapper();
			wrapper.setMitarbeiter(mitarbeiterData);
			// Marshalling and saving XML to the file.
			m.marshal(wrapper, file);
			// Save the file path to the registry.
			setMitarbeiterFilePath(file);
		} catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler");
			alert.setHeaderText("Speichern der Daten gescheitert");
			alert.setContentText("Die Daten konnten nicht gespeichert werden in:\n" + file.getPath());
			alert.showAndWait();
		}
	}

	public void saveFahrzeugDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(FahrzeugListWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// Wrapping our data.
			FahrzeugListWrapper wrapper = new FahrzeugListWrapper();
			wrapper.setFahrzeug(fahrzeugData);
			// Marshalling and saving XML to the file.
			m.marshal(wrapper, file);
			// Save the file path to the registry.
			setFahrzeugFilePath(file);
		} catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler");
			alert.setHeaderText("Speichern der Daten gescheitert");
			alert.setContentText("Die Daten konnten nicht gespeichert werden in:\n" + file.getPath());
			alert.showAndWait();
		}
	}

	public void saveBuchungDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(BuchungListWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// Wrapping our data.
			BuchungListWrapper wrapper = new BuchungListWrapper();
			wrapper.setBuchung(buchungData);
			// Marshalling and saving XML to the file.
			m.marshal(wrapper, file);
			// Save the file path to the registry.
			setBuchungFilePath(file);
		} catch (Exception e) { // catches ANY exception
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler");
			alert.setHeaderText("Speichern der Daten gescheitert");
			alert.setContentText("Die Daten konnten nicht gespeichert werden in:\n" + file.getPath());
			alert.showAndWait();
		}
	}
}
