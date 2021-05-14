package com.unicamp.mc322.lab07.game.obstacles;

import java.util.ArrayList;

import com.unicamp.mc322.lab07.util.Vector2D;

public abstract class Obstacle {
  private ArrayList<Vector2D> positions;
  private String icon;

  public Obstacle(String icon) {
    this.icon = icon;
    this.positions = new ArrayList<Vector2D>();
  }

  public String getIcon() {
    return icon;
  }

  public void addPosition(Vector2D pos) {
    this.positions.add(pos);
  }

  public ArrayList<Vector2D> getPositions() {
    return this.positions;
  }
}
