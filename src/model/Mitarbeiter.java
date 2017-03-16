package model;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter; // aus tutorial

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Mitarbeiter implements Comparable<Mitarbeiter> {

	private final StringProperty vorname;
	private final StringProperty nachname;
	private final StringProperty strasse;
	private final StringProperty wohnort;
	private final IntegerProperty plz;
	private final ObjectProperty<LocalDate> geburtstag;

	public Mitarbeiter (String _vorname, String _nachname, String _strasse, String _wohnort,
			int _plz, LocalDate _geburtstag) {
		this.vorname = new SimpleStringProperty(_vorname);
		this.nachname = new SimpleStringProperty(_nachname);
		this.strasse = new SimpleStringProperty(_strasse);
		this.wohnort = new SimpleStringProperty(_wohnort);
		this.plz = new SimpleIntegerProperty(_plz);
		this.geburtstag = new SimpleObjectProperty<LocalDate>(_geburtstag);
	}

	@Override
	public int compareTo(Mitarbeiter mi) {
		if (this.getNachname().compareTo(mi.getNachname()) == 0) {
			if (this.getVorname().compareTo(mi.getVorname()) == 0) {
				if (this.getStrasse().compareTo(mi.getStrasse()) == 0) {
					if (this.getWohnort().compareTo(mi.getWohnort()) == 0) {
						return 0;
					}
				}
			}
		}
		//return 1; // this object is greater than the specified object
		//return -1; // this object is less than the specified object
		//return 0;
	}

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

	public Integer getPlz() {
		return plz.get();
	}

	public void setPlz(int _plz) {
		this.plz.set(_plz);
	}

	public IntegerProperty plzProperty() {
		return plz;
	}

	// @XmlJavaTypeAdapter(LocalDateAdapter.class) // aus Tutorial?!
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
