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
}
