package controller;

import java.time.LocalDateTime;
import javax.xml.bind.annotation.adapters.XmlAdapter;

// Diese Klasse hilft dabei, eine Zeit-Datum-Variable richtig in einer XML-Datei zu speichern
// und diese auch wieder auszulesen


public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
	public LocalDateTime unmarshal(String v) throws Exception {
		return LocalDateTime.parse(v);
	}

	public String marshal(LocalDateTime v) throws Exception {
		return v.toString();
	}
}