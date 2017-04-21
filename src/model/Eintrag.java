package model;

public class Eintrag {
	
	private String X;
	private int Y;
	
	public Eintrag(String X, int Y){
		this.X = X;
		this.Y = Y;
	}
	
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
