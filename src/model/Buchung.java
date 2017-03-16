package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Buchung implements Comparable<Buchung>{

	public final IntegerProperty id;
	private final ObjectProperty<Mitarbeiter> mitarbeiter;
	private final ObjectProperty<Fahrzeug> fahrzeug;

	public Buchung(int _id, Mitarbeiter _mitarbeiter, Fahrzeug _fahrzeug) {
		this.id = new SimpleIntegerProperty(_id);
		this.mitarbeiter = new SimpleObjectProperty<Mitarbeiter>(_mitarbeiter);
		this.fahrzeug = new SimpleObjectProperty<Fahrzeug>(_fahrzeug);
	}

	@Override
	public int compareTo(Buchung arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getId() {
		return id.get();
	}

	public void setId(int _id) {
		this.id.set(_id);
	}

	public IntegerProperty idProperty() {
		return id;
	}

	public Mitarbeiter getMitarbeiter() {
		return mitarbeiter.get();
	}

	public void setMitarbeiter(Mitarbeiter _mitarbeiter) {
		this.mitarbeiter.set(_mitarbeiter);
	}

	public ObjectProperty<Mitarbeiter> mitarbeiterProperty() {
		return mitarbeiter;
	}

	public Fahrzeug getFahrzeug() {
		return fahrzeug.get();
	}

	public void setFahrzeug(Fahrzeug _fahrzeug) {
		this.fahrzeug.set(_fahrzeug);
	}

	public ObjectProperty<Fahrzeug> fahrzeugProperty() {
		return fahrzeug;
	}

}
