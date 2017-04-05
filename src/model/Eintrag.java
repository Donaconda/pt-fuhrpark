package model;

public class Eintrag {

	private Fahrzeug fahrzeug;
	private Mitarbeiter mitarbeiter;
	private int ausleihzeit;
	private int haeufigkeit;

	public Fahrzeug getFahrzeug() {
		return fahrzeug;
	}

	public void setFahrzeug(Fahrzeug fahrzeug) {
		this.fahrzeug = fahrzeug;
	}

	public Mitarbeiter getMitarbeiter() {
		return mitarbeiter;
	}

	public void setMitarbeiter(Mitarbeiter mitarbeiter) {
		this.mitarbeiter = mitarbeiter;
	}

	public int getAusleihzeit() {
		return ausleihzeit;
	}

	public void setAusleihzeit(int ausleihzeit) {
		this.ausleihzeit = ausleihzeit;
	}

	public int getHaeufigkeit() {
		return haeufigkeit;
	}

	public void setHaeufigkeit(int haeufigkeit) {
		this.haeufigkeit = haeufigkeit;
	}

}
