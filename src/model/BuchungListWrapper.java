package model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "buchungen")
public class BuchungListWrapper {

	private List<Buchung> buchung;

	@XmlElement(name = "buchung")
	public List<Buchung> getBuchung() {
		return buchung;
	}

	public void setBuchung(List<Buchung> buchung) {
		this.buchung = buchung;
	}

}
