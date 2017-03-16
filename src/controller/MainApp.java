package controle;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import ch.makery.address.MainApp;
import ch.makery.address.view.RootLayoutController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Mitarbeiter;
import model.Buchung;
import model.Fahrzeug;


public class MainApp {
	
	private Stage primaryStage;
    private BorderPane rootLayout;
    
    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<Mitarbeiter> mitarbeiterData = FXCollections.observableArrayList();
    private ObservableList<Buchung> buchungData = FXCollections.observableArrayList();
    private ObservableList<Fahrzeug> fahrzeugData = FXCollections.observableArrayList();
    
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");
        
        initRootLayout();
    }
    
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened person file.
        File file = getMitarbeiterFilePath();
        if (file != null) {
            loadMitarbeiterDataFromFile(file);
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
	    String filePath = prefs.get("filePath", null);
	    if (filePath != null) {
	        return new File(filePath);
	    } else {
	        return null;
	    }
	}
	
	public void setMitarbeiterFilePath(File file) {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	    if (file != null) {
	        prefs.put("filePath", file.getPath());

	        // Update the stage title.
	        primaryStage.setTitle("AddressApp - " + file.getName());
	    } else {
	        prefs.remove("filePath");

	        // Update the stage title.
	        primaryStage.setTitle("AddressApp");
	    }
	}
	
	public File getBuchungFilePath() {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	    String filePath = prefs.get("filePath", null);
	    if (filePath != null) {
	        return new File(filePath);
	    } else {
	        return null;
	    }
	}
	
	public void setBuchungFilePath(File file) {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	    if (file != null) {
	        prefs.put("filePath", file.getPath());

	        // Update the stage title.
	        primaryStage.setTitle("AddressApp - " + file.getName());
	    } else {
	        prefs.remove("filePath");

	        // Update the stage title.
	        primaryStage.setTitle("AddressApp");
	    }
	}
	
	public File getFahrzeugFilePath() {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	    String filePath = prefs.get("filePath", null);
	    if (filePath != null) {
	        return new File(filePath);
	    } else {
	        return null;
	    }
	}
	
	public void setFahrzeugFilePath(File file) {
	    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
	    if (file != null) {
	        prefs.put("filePath", file.getPath());

	        // Update the stage title.
	        primaryStage.setTitle("AddressApp - " + file.getName());
	    } else {
	        prefs.remove("filePath");

	        // Update the stage title.
	        primaryStage.setTitle("AddressApp");
	    }
	}

}
