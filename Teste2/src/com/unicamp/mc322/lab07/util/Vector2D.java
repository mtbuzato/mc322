package com.unicamp.mc322.lab07.util;

public class Vector2D {
  private int x;
  private int y;

  public Vector2D(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int distanceTo(Vector2D b) {
    return Math.abs(this.getX() - b.getX()) + Math.abs(this.getY() - b.getY());
  }

  public boolean adjacentTo(Vector2D b) {
    if (this.getX() == b.getX() && this.getY() == b.getY()) {
      return false;
    }

    if (Math.abs(this.getX() - b.getX()) > 1) {
      return false;
    }

    if (Math.abs(this.getY() - b.getY()) > 1) {
      return false;
    }

    return true;
  }

  @Override
  public String toString() {
    return this.getX() + ":" + this.getY();
  }
}
