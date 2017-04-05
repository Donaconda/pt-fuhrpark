package model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "fahrzeuge")
public class FahrzeugListWrapper {

	private List<Fahrzeug> fahrzeug;

	@XmlElement(name = "fahrzeug")
	public List<Fahrzeug> getFahrzeug() {
		return fahrzeug;
	}

	public void setFahrzeug(List<Fahrzeug> fahrzeug) {
		this.fahrzeug = fahrzeug;
	}

}
