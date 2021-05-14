package com.unicamp.mc322.lab07.game.entities.frogs;

import com.unicamp.mc322.lab07.game.entities.Entity;
import com.unicamp.mc322.lab07.util.Direction;
import com.unicamp.mc322.lab07.util.Vector2D;

public abstract class Frog extends Entity {
  private Vector2D lastPos;

  public Frog(String name, String icon) {
    super(name, icon);

    this.lastPos = this.getPosition();
  }

  protected void setLastPosition(Vector2D pos) {
    this.lastPos = pos;
  }

  public Vector2D getLastPosition() {
    return lastPos;
  }

  public abstract void move(Direction dir);

  public void eat(Entity e) {
    addPoints(e.getPoints());
    e.kill();
  };
}
