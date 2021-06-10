package com.unicamp.mc322.lab10.util;

public class Position {
  private int x;
  private int y;

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public double distanceTo(Position b) {
    return Math.sqrt(Math.pow(x - b.x, 2) + Math.pow(y - b.y, 2));
  }
}
