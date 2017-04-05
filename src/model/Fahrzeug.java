package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Fahrzeug implements Comparable<Fahrzeug> {

	private final StringProperty marke;
	private final StringProperty model;
	private final StringProperty kennzeichen;
	private final StringProperty klasse;

	public Fahrzeug(String _marke, String _model, String _kennzeichen, String _klasse) {
		this.marke = new SimpleStringProperty(_marke);
		this.model = new SimpleStringProperty(_model);
		this.kennzeichen = new SimpleStringProperty(_kennzeichen);
		this.klasse = new SimpleStringProperty(_klasse);
	}

	public Fahrzeug() {
		this(null, null, null, null);
	}

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

	// Gibt den Marke und Modell im formatierten String aus
	public String toString() {
		return getMarke() + " " + getModel();
	}

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
