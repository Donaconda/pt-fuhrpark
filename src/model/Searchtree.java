package model;

public class Searchtree<T extends Comparable<T>> {

	private class Knoten {
		T		wert;	// Wert des Knoten
		Knoten	links;	// linker Unterbaum
		Knoten	rechts;	// rechter Unterbaum
	}

	private Knoten wurzel;

	void insert(T elem) {
		Knoten knoten = wurzel; // Hilfsknoten
		Knoten vater = null;	// Vater von knoten
		int diff = 0;

		while (knoten != null) {
			vater = knoten;
			diff = knoten.wert.compareTo(elem);
			if (diff == 0) {
				return;
			} else if (diff > 0) {
				knoten = knoten.links;
			} else {
				knoten = knoten.rechts;
			}
		}

		// neuen Knoten anlegen und initialisieren
		knoten = new Knoten();
		knoten.links = knoten.rechts = null;
		knoten.wert = elem;

		if (vater == null) {	// Baum war bisher leer
			wurzel = knoten;	// dann ist jetzt knoten die Wurzel
		} else {				// ansonsten wird knoten Unterbaum von vater
			if (diff > 0) {		// vater.wert > elem => neuer linker Unterbaum
				vater.links = knoten;
			} else {			// vater.wert < elem => neuer rechter Unterbaum
				vater.rechts = knoten;
			}
		}
	}

	void delete(T elem) {
		Knoten knoten = wurzel;
		Knoten vater = null;
		int linksrechts = 0;
		// finde elem(zu löschendes Element) im Suchbaum
		while (elem.compareTo(knoten.wert) != 0) {
			if (elem.compareTo(knoten.wert) < 0) {
				vater = knoten;
				knoten = knoten.links;
				linksrechts = -1; // links von vater
			} else {
				vater = knoten;
				knoten = knoten.rechts;
				linksrechts = 1; // rechts von vater
			}
		}
		// elem(zu löschendes Element) ist Wurzel
		if (knoten == wurzel) {
			Knoten temp;
			temp = wurzel.rechts;
			Knoten temp2;
			temp2 = temp;
			while (temp.links != null) {
				temp2 = temp;
				temp = temp.links;
			}
			temp2.links = null;
			temp.links = wurzel.links;
			temp.rechts = wurzel.rechts;
			wurzel = temp;
		// elem(zu löschendes Element) hat keine Folge-Knoten
		} else if (knoten.rechts == null & knoten.links == null) {
			if (linksrechts == -1) {	// elem links von seinem vater
				vater.links = null;
			} else {
				vater.rechts = null;
			}
		// elem(zu löschendes Element) hat einen Folge-Knoten links
		} else if (knoten.rechts == null & knoten.links != null) {
			if (linksrechts == -1) {	// elem links von seinem vater
				vater.links = knoten.links;
			} else {
				vater.rechts = knoten.links;
			}
		// elem(zu löschendes Element) hat einen Folge-Knoten rechts
		} else if (knoten.rechts != null & knoten.links == null) {
			if (linksrechts == -1) {	// elem links von seinem vater
				vater.links = knoten.rechts;
			} else {
				vater.rechts = knoten.rechts;
			}
		// elem(zu löschendes Element) hat zwei Folge-Knoten
		} else if (knoten.rechts != null & knoten.links != null) {
			Knoten knoten2;
			Knoten vater2 = null;
			if (linksrechts == -1) {	// elem links von seinem vater
				knoten2 = vater.links.rechts;
				while (knoten2.links != null) { // finde den Knoten mit dem nächs höheren Wert im Baum
					vater2 = knoten2;
					knoten2 = knoten2.links;
				}
				vater.links = knoten2;
				vater.links.links = knoten.links;
				vater.links.rechts = knoten.rechts;
				if (vater2 != null) // der alte vater des "neuen" elements zeigt nicht mehr auf das "neue" element
					vater2.links = null;
			} else {
				knoten2 = vater.rechts.rechts;
				while (knoten2.links != null) { // finde den Knoten mit dem nächs höheren Wert im Baum
					vater2 = knoten2;
					knoten2 = knoten2.links;
				}
				vater.rechts = knoten2;
				vater.rechts.links = knoten.links;
				vater.rechts.rechts = knoten.rechts;
				if (vater2 != null) // der alte vater des "neuen" elements zeigt nicht mehr auf das "neue" element
					vater2.links = null;
			}
			knoten.rechts = null;
			knoten.links = null;
		}
	}
}
