package com.unicamp.mc322.lab07.game.entities.frogs;

import com.unicamp.mc322.lab07.game.entities.Entity;
import com.unicamp.mc322.lab07.util.Vector2D;

public class Frog extends Entity {
  private Vector2D lastPos;
  private int points;

  public Frog(String name, String icon) {
    super(name, icon);

    this.lastPos = this.getPosition();
    this.points = 0;
  }

  public Vector2D getLastPosition() {
    return lastPos;
  }

  public int getPoints() {
    return points;
  }

  public void addPoints(int points) {
    this.points += points;
  }

  public void removePoints(int points) {
    this.points -= points;
  }
}
