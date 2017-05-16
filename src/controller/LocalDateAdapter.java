package controller;

import java.time.LocalDate;
import javax.xml.bind.annotation.adapters.XmlAdapter;

// Diese Klasse hilft dabei, eine Datum-Variable richtig in einer XML-Datei zu speichern
// und diese auch wieder auszulesen

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
	public LocalDate unmarshal(String v) throws Exception {
		return LocalDate.parse(v);
	}

	public String marshal(LocalDate v) throws Exception {
		return v.toString();
	}
}