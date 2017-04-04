package controller;

import java.io.File;
import java.io.IOException;
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
import view.AusleihzeitStatistikController;
import view.BuchungController;
import view.BuchungDialogController;
import view.FahrzeugController;
import view.FahrzeugDialogController;
import view.FahrzeugHaeufigkeitController;
import view.MitarbeiterAusleihzeitController;
import view.MitarbeiterController;
import view.MitarbeiterDialogController;


public class MainApp extends Application{

	private Stage primaryStage;
    private BorderPane startFenster;

    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<Mitarbeiter> mitarbeiterData = FXCollections.observableArrayList();
    private ObservableList<Buchung> buchungData = FXCollections.observableArrayList();
    private ObservableList<Fahrzeug> fahrzeugData = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }
    
    public MainApp(){
    	
    }
    
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Fuhrparkverwaltung BSK");

        initStartFenster();
    }

    public void initStartFenster() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("/view/StartFenster.fxml"));
            startFenster = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(startFenster);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            
            StartFensterController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
            zeigeMitarbeiterFenster();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened person file.
        File maFile = new File("C:/Users/tgma07/git/pt-fuhrpark/src/resources/mitarbeiterData.xml");
        if (maFile != null) {
            loadMitarbeiterDataFromFile(maFile);
        }
        File fzFile = new File("C:/Users/tgma07/git/pt-fuhrpark/src/resources/fahrzeugData.xml");
        if (fzFile != null) {
            loadFahrzeugDataFromFile(fzFile);
        }
        File buFile = new File("C:/Users/tgma07/git/pt-fuhrpark/src/resources/buchungData.xml");
        if (buFile != null) {
            loadBuchungDataFromFile(buFile);
        }
    }
    
    public void zeigeMitarbeiterFenster() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/Mitarbeiter.fxml"));
            AnchorPane mitarbeiterFenster = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            startFenster.setCenter(mitarbeiterFenster);

            // Give the controller access to the main app.
            MitarbeiterController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void zeigeFahrzeugFenster() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/Fahrzeug.fxml"));
            AnchorPane fahrzeugFenster = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            startFenster.setCenter(fahrzeugFenster);

            // Give the controller access to the main app.
            FahrzeugController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void zeigeBuchungFenster() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/Buchung.fxml"));
            AnchorPane buchungFenster = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            startFenster.setCenter(buchungFenster);

            // Give the controller access to the main app.
            BuchungController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean zeigeMitarbeiterDialog(Mitarbeiter ma){
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/MitarbeiterDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Mitarbeiter bearbeiten");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            MitarbeiterDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMitarbeiter(ma);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean zeigeFahrzeugDialog(Fahrzeug fz){
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/FahrzeugDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Fahrzeug bearbeiten");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            FahrzeugDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setFahrzeug(fz);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean zeigeBuchungDialog(Buchung bu){
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/BuchungDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Buchung bearbeiten");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            BuchungDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMainApp(this);
            controller.fillCombobox();
            controller.setBuchung(bu);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
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

	        // Update the stage title.
	        primaryStage.setTitle("AddressApp - " + file.getName());
	    } else {
	        prefs.remove("maFilePath");

	        // Update the stage title.
	        primaryStage.setTitle("AddressApp");
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

	        // Update the stage title.
	        primaryStage.setTitle("AddressApp - " + file.getName());
	    } else {
	        prefs.remove("fzFilePath");

	        // Update the stage title.
	        primaryStage.setTitle("AddressApp");
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

	        // Update the stage title.
	        primaryStage.setTitle("AddressApp - " + file.getName());
	    } else {
	        prefs.remove("buFilePath");

	        // Update the stage title.
	        primaryStage.setTitle("AddressApp");
	    }
	}

    public void loadMitarbeiterDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(MitarbeiterListWrapper.class);
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
        	alert.setTitle("Error");
        	alert.setHeaderText("Could not load data");
        	alert.setContentText("Could not load data from file:\n" + file.getPath());

        	alert.showAndWait();
        }
    }

    public void loadFahrzeugDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(FahrzeugListWrapper.class);
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
        	alert.setTitle("Error");
        	alert.setHeaderText("Could not load data");
        	alert.setContentText("Could not load data from file:\n" + file.getPath());

        	alert.showAndWait();
        }
    }

    public void loadBuchungDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(BuchungListWrapper.class);
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
        	alert.setTitle("Error");
        	alert.setHeaderText("Could not load data");
        	alert.setContentText("Could not load data from file:\n" + file.getPath());

        	alert.showAndWait();
        }
    }

    public void saveMitarbeiterDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(MitarbeiterListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            MitarbeiterListWrapper wrapper = new MitarbeiterListWrapper();
            wrapper.setMitarbeiter(mitarbeiterData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setMitarbeiterFilePath(file);
        } catch (Exception e) { // catches ANY exception
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("Could not save data");
        	alert.setContentText("Could not save data to file:\n" + file.getPath());

        	alert.showAndWait();
        }
    }

    public void saveFahrzeugDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(FahrzeugListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            FahrzeugListWrapper wrapper = new FahrzeugListWrapper();
            wrapper.setFahrzeug(fahrzeugData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setFahrzeugFilePath(file);
        } catch (Exception e) { // catches ANY exception
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("Could not save data");
        	alert.setContentText("Could not save data to file:\n" + file.getPath());

        	alert.showAndWait();
        }
    }

    public void saveBuchungDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(BuchungListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            BuchungListWrapper wrapper = new BuchungListWrapper();
            wrapper.setBuchung(buchungData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setBuchungFilePath(file);
        } catch (Exception e) { // catches ANY exception
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("Could not save data");
        	alert.setContentText("Could not save data to file:\n" + file.getPath());

        	alert.showAndWait();
        }
    }
    
    public void showBirthdayStatistics() {
        try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/FahrzeugHaeufigkeitStatistik.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Fahrzeug Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the persons into the controller.
            FahrzeugHaeufigkeitController controller = loader.getController();
            controller.setData(buchungData);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showAusleihdauerStatistics() {
        try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/AusleihzeitStatistik.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Birthday Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the persons into the controller.
            AusleihzeitStatistikController controller = loader.getController();
            controller.setData(buchungData);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showMitarbeiterAusleihzeitStatistics() {
        try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/MitarbeiterAusleihzeitStatistik.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Mitarbeiter Ausleihzeit");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the persons into the controller.
            MitarbeiterAusleihzeitController controller = loader.getController();
            controller.setData(buchungData);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
