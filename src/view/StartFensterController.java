package view;

import controller.MainApp;
import javafx.fxml.FXML;

public class StartFensterController {
	
	private MainApp mainApp;
	
	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}
	
	@FXML
	public void handleTabMitarbeiter(){
		mainApp.zeigeMitarbeiterFenster();
	}
	
	@FXML
	public void handleTabFahrzeug(){
		mainApp.zeigeFahrzeugFenster();
	}
	
	@FXML
	public void handleTabBuchung(){
		mainApp.zeigeBuchungFenster();
	}
	
	@FXML
	private void handleFahrzeugStatistics() {
	  mainApp.showBirthdayStatistics();
	}
	
	@FXML
	private void handleAusleihzeitStatistics() {
	  mainApp.showAusleihdauerStatistics();
	}
}
