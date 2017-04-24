package model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import controller.LocalDateTimeAdapter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Buchung implements Comparable<Buchung> { // Modell einer Buchung:

	// Attribute:
	private final StringProperty id; 					// eindeutige Identifizierungsnummer für eine Buchunga
	private final StringProperty mitarbeiter; 			// Mitarbeiter gespeichert mit Vor- und Nachname
	private final StringProperty fahrzeug; 				// Fahrzeug gespeichert mit Marke, Modell und Kennzeichen
	private final StringProperty zweck; 				// Verwendungszweck des Fahrzeugs
	private final ObjectProperty<LocalDateTime> beginn; // Beginn der Buchung
	private final ObjectProperty<LocalDateTime> ende; 	// Ende der Buchung

	// Konstruktor:
	public Buchung(String _id, String _mitarbeiter, String _fahrzeug, String _zweck, LocalDateTime _beginn,
			LocalDateTime _ende) {
		this.id = new SimpleStringProperty(_id);
		this.mitarbeiter = new SimpleStringProperty(_mitarbeiter);
		this.fahrzeug = new SimpleStringProperty(_fahrzeug);
		this.zweck = new SimpleStringProperty(_zweck);
		this.beginn = new SimpleObjectProperty<LocalDateTime>(_beginn);
		this.ende = new SimpleObjectProperty<LocalDateTime>(_ende);
	}

	// leerer Konstruktor:
	public Buchung() {
		this(null, null, null, null, null, null);
	}

	// Vergleichsmethode für zwei Buchungen (Implementierung des Comparable-Interfaces):
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

	public long dauer() { // Berechnung des Buchungszeitraums
		return this.getBeginn().until(this.getEnde(), ChronoUnit.HOURS); // Dauer wird in Stunden returned
	}

	// Getter- und Setter-Methoden (insgesamt drei pro Attribut, da die Property auch returned werden kann):
	public String getId() {
		return id.get();
	}

	public void setId(String _id) {
		this.id.set(_id);
	}

	public StringProperty idProperty() {
		return id;
	}

	public String getMitarbeiter() {
		return mitarbeiter.get();
	}

	public void setMitarbeiter(String _mitarbeiter) {
		this.mitarbeiter.set(_mitarbeiter);
	}

	public StringProperty mitarbeiterProperty() {
		return mitarbeiter;
	}

	public String getFahrzeug() {
		return fahrzeug.get();
	}

	public void setFahrzeug(String _fahrzeug) {
		this.fahrzeug.set(_fahrzeug);
	}

	public StringProperty fahrzeugProperty() {
		return fahrzeug;
	}

	public String getKennzeichen() {
		return fahrzeug.get().split("\\(")[1].split("\\)")[0];
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