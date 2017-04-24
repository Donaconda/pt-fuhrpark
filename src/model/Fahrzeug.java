package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Fahrzeug implements Comparable<Fahrzeug> { // Modell eines Fahrzeugs:

	// Attribute:
	private final StringProperty marke; 		// Marke des Fahrzeugs
	private final StringProperty model; 		// Modell des Fahrzeugs
	private final StringProperty kennzeichen; 	// Kennzeichen des Fahrzeugs
	private final StringProperty klasse; 		// Auto, Motorrad, etc

	// Konstruktor:
	public Fahrzeug(String _marke, String _model, String _kennzeichen, String _klasse) {
		this.marke = new SimpleStringProperty(_marke);
		this.model = new SimpleStringProperty(_model);
		this.kennzeichen = new SimpleStringProperty(_kennzeichen);
		this.klasse = new SimpleStringProperty(_klasse);
	}

	// leerer Konstruktor:
	public Fahrzeug() {
		this(null, null, null, null);
	}

	// Vergleichsmethode für zwei Fahrzeuge (Implementierung des Comparable-Interfaces):
	@Override
	public int compareTo(Fahrzeug fa) {
		if (this.getMarke().compareTo(fa.getMarke()) == 0)
			if (this.getModel().compareTo(fa.getModel()) == 0)
				if (this.getKennzeichen().compareTo(fa.getKennzeichen()) == 0)
					if (this.getKlasse().compareTo(fa.getKlasse()) == 0)
						return 0;
					else if (this.getKlasse().compareTo(fa.getKlasse()) < 0)
						return -1;
					else
						return 1;
				else if (this.getKennzeichen().compareTo(fa.getKennzeichen()) < 0)
					return -1;
				else
					return 1;
			else if (this.getModel().compareTo(fa.getModel()) < 0)
				return -1;
			else
				return 1;
		else if (this.getMarke().compareTo(fa.getMarke()) < 0)
			return -1;
		else
			return 1;
	}

	// Gibt die Marke, das Modell und das Kennzeichen in einem formatierten String aus
	public String toString() {
		return getMarke() + " " + getModel() + " (" + getKennzeichen() + ")";
	}

	// Getter- und Setter-Methoden (insgesamt drei pro Attribut, da die Property auch returned werden kann):
	public String getMarke() {
		return marke.get();
	}

	public void setMarke(String _marke) {
		this.marke.set(_marke);
	}

	public StringProperty MarkeProperty() {
		return marke;
	}

	public String getModel() {
		return model.get();
	}

	public void setModel(String _model) {
		this.model.set(_model);
	}

	public StringProperty ModelProperty() {
		return model;
	}

	public String getKennzeichen() {
		return kennzeichen.get();
	}

	public void setKennzeichen(String _kennzeichen) {
		this.kennzeichen.set(_kennzeichen);
	}

	public StringProperty KennzeichenProperty() {
		return kennzeichen;
	}

	public String getKlasse() {
		return klasse.get();
	}

	public void setKlasse(String _klasse) {
		this.klasse.set(_klasse);
	}

	public StringProperty KlasseProperty() {
		return klasse;
	}
}