package model;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import controller.LocalDateAdapter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Mitarbeiter implements Comparable<Mitarbeiter> { // Modell eines Mitarbeiters:

	// Attribute:
	private final StringProperty vorname;	// Vorname des Mitarbeiters
	private final StringProperty nachname;	// Nachname des Mitarbeiters
	private final StringProperty strasse;	// Straße des Mitarbeiters
	private final StringProperty wohnort;	// Wohnort des Mitarbeiters
	private final StringProperty plz;		// Postleitzahl des Wohnorts des Mitarbeiters
	private final ObjectProperty<LocalDate> geburtstag; // Geburtstag des Mitarbeiters

	// Konstruktor:
	public Mitarbeiter(String _vorname, String _nachname, String _strasse, String _wohnort, String _plz,
			LocalDate _geburtstag) {
		this.vorname = new SimpleStringProperty(_vorname);
		this.nachname = new SimpleStringProperty(_nachname);
		this.strasse = new SimpleStringProperty(_strasse);
		this.wohnort = new SimpleStringProperty(_wohnort);
		this.plz = new SimpleStringProperty(_plz);
		this.geburtstag = new SimpleObjectProperty<LocalDate>(_geburtstag);
	}

	// leerer Konstruktor:
	public Mitarbeiter() {
		this("", "", "", "", "", null);
	}

	// Vergleichsmethode für zwei Mitarbeiter (Implementierung des Comparable-Interfaces):
	@Override
	public int compareTo(Mitarbeiter mi) {
		if (this.getNachname().compareTo(mi.getNachname()) == 0) {
			if (this.getVorname().compareTo(mi.getVorname()) == 0) {
				if (this.getStrasse().compareTo(mi.getStrasse()) == 0) {
					if (this.getWohnort().compareTo(mi.getWohnort()) == 0) {
						if (this.getPlz().compareTo(mi.getPlz()) == 0) {
							if (this.getGeburtstag().compareTo(mi.getGeburtstag()) == 0) {
								return 0;
							} else if (this.getGeburtstag().compareTo(mi.getGeburtstag()) < 0) {
								return -1;
							} else {
								return 1;
							}
						} else if (this.getPlz().compareTo(mi.getPlz()) < 0) {
							return -1;
						} else {
							return 1;
						}
					} else if (this.getWohnort().compareTo(mi.getWohnort()) < 0) {
						return -1;
					} else {
						return 1;
					}
				} else if (this.getStrasse().compareTo(mi.getStrasse()) < 0) {
					return -1;
				} else {
					return 1;
				}
			} else if (this.getVorname().compareTo(mi.getVorname()) < 0) {
				return -1;
			} else {
				return 1;
			}
		} else if (this.getNachname().compareTo(mi.getNachname()) < 0) {
			return -1;
		} else {
			return 1;
		}
		// return 1; // this object is greater than the specified object
		// return -1; // this object is less than the specified object
		// return 0;
	}

	// Gibt den Vor- und Nachnamen im formatierten String aus
	public String toString() {
		return getVorname() + " " + getNachname();
	}

	// Getter- und Setter-Methoden (insgesamt drei pro Attribut, da die Property auch returned werden kann):
	public String getVorname() {
		return vorname.get();
	}

	public void setVorname(String _vorname) {
		this.vorname.set(_vorname);
	}

	public StringProperty vornameProperty() {
		return vorname;
	}

	public String getNachname() {
		return nachname.get();
	}

	public void setNachname(String _nachname) {
		this.nachname.set(_nachname);
	}

	public StringProperty nachnameProperty() {
		return nachname;
	}

	public String getStrasse() {
		return strasse.get();
	}

	public void setStrasse(String _strasse) {
		this.strasse.set(_strasse);
	}

	public StringProperty strasseProperty() {
		return strasse;
	}

	public String getWohnort() {
		return wohnort.get();
	}

	public void setWohnort(String _wohnort) {
		this.wohnort.set(_wohnort);
	}

	public StringProperty wohnortProperty() {
		return wohnort;
	}

	public String getPlz() {
		return plz.get();
	}

	public void setPlz(String _plz) {
		this.plz.set(_plz);
	}

	public StringProperty plzProperty() {
		return plz;
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getGeburtstag() {
		return geburtstag.get();
	}

	public void setGeburtstag(LocalDate _geburtstag) {
		this.geburtstag.set(_geburtstag);
	}

	public ObjectProperty<LocalDate> geburtstagProperty() {
		return geburtstag;
	}
}