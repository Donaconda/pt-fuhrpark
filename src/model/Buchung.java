package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import controller.LocalDateAdapter;
import controller.LocalDateTimeAdapter;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Buchung implements Comparable<Buchung>{

	public final IntegerProperty id;
	private final ObjectProperty<Mitarbeiter> mitarbeiter;
	private final ObjectProperty<Fahrzeug> fahrzeug;
	private final StringProperty zweck;
	private final ObjectProperty<LocalDateTime> beginn;
	private final ObjectProperty<LocalDateTime> ende;

	public Buchung(int _id, Mitarbeiter _mitarbeiter, Fahrzeug _fahrzeug, String _zweck, LocalDateTime _beginn, LocalDateTime _ende) {
		this.id = new SimpleIntegerProperty(_id);
		this.mitarbeiter = new SimpleObjectProperty<Mitarbeiter>(_mitarbeiter);
		this.fahrzeug = new SimpleObjectProperty<Fahrzeug>(_fahrzeug);
		this.zweck = new SimpleStringProperty(_zweck);
		this.beginn = new SimpleObjectProperty<LocalDateTime>(_beginn);
		this.ende = new SimpleObjectProperty<LocalDateTime>(_ende);
	}

	public Buchung() {
		this(0, null, null, null, null, null);
	}

	@Override
	public int compareTo(Buchung bu) {
		if (this.getId().compareTo(bu.getId()) == 0)
			if (this.getMitarbeiter().compareTo(bu.getMitarbeiter()) == 0)
				if (this.getFahrzeug().compareTo(bu.getFahrzeug()) == 0)
					return 0;
				else if (this.getFahrzeug().compareTo(bu.getFahrzeug()) < 0)
					return -1;
				else
					return 1;
			else if (this.getMitarbeiter().compareTo(bu.getMitarbeiter()) < 0)
				return -1;
			else
				return 1;
		else if (this.getId().compareTo(bu.getId()) < 0)
			return -1;
		else
			return 1;
	}

	public long dauer() { // Dauer in Stunden returned
		return this.getBeginn().until(this.getEnde(), ChronoUnit.HOURS);
	}

	public Integer getId() {
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

	public String getZweck() {
		return zweck.get();
	}

	public void setZweck(String _zweck) {
		this.zweck.set(_zweck);
	}

	public StringProperty zweckProperty() {
		return zweck;
	}
	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class) 
	public LocalDateTime getBeginn() {
		return beginn.get();
	}

	public void setBeginn(LocalDateTime _beginn) {
		this.beginn.set(_beginn);
	}

	public ObjectProperty<LocalDateTime> beginnProperty() {
		return beginn;
	}

	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class) 
	public LocalDateTime getEnde() {
		return ende.get();
	}

	public void setEnde(LocalDateTime _ende) {
		this.ende.set(_ende);
	}

	public ObjectProperty<LocalDateTime> endeProperty() {
		return ende;
	}

}
