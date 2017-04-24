package model;

public class Eintrag { // Klasse zur Darstellung der Statistik

	private String X; // x-Koordiante
	private int Y; // y-Koordiante

	// Konstruktor:
	public Eintrag(String X, int Y){
		this.X = X;
		this.Y = Y;
	}

	// Getter- und Setter-Methoden
	public String getX(){
		return X;
	}

	public void setX(String X){
		this.X = X;
	}

	public int getY(){
		return Y;
	}

	public void setY(int Y){
		this.Y = Y;
	}

}
