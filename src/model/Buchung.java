package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Buchung {

	public final IntegerProperty id;
	private final ObjectProperty<Mitarbeiter> mitarbeiter;
	private final ObjectProperty<Fahrzeug> fahrzeug;

	public Buchung(int _id, Mitarbeiter _mitarbeiter, Fahrzeug _fahrzeug) {
		this.id = new SimpleIntegerProperty(_id);
		this.mitarbeiter = new SimpleObjectProperty<Mitarbeiter>(_mitarbeiter);
		this.fahrzeug = new SimpleObjectProperty<Fahrzeug>(_fahrzeug);
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

	public Fahrzeug getFahrzeug() {
		return fahrzeug.get();
	}

}
