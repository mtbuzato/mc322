package br.unicamp.ic.mc322.teste1;

public class Position {
	private int x;
	private int y;
	
	public Position(int theX, int theY) {
		x = theX;
		y = theY;
	}

	public double getDistanceFrom(Position b) {
    return Math.sqrt(Math.pow(x + b.x, 2) + Math.pow(y + b.y, 2));
  }
}