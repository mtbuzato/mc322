package com.unicamp.mc322.lab07.game.entities;

import com.unicamp.mc322.lab07.util.Vector2D;

public class Entity {
  private Vector2D pos;
  private String icon;
  private String name;
  private boolean alive;
  private double points;

  public Entity(String name, String icon) {
    this.name = name;
    this.icon = icon;
    this.pos = new Vector2D(0, 0);
    this.alive = true;
    this.points = 0;
  }

  public void moveTo(Vector2D pos) {
    this.pos = pos;
  }

  public Vector2D getPosition() {
    return pos;
  }

  public String getName() {
    return name;
  }

  public String getIcon() {
    return icon;
  }

  public void kill() {
    this.alive = false;
  }

  public boolean isAlive() {
    return alive;
  }

  public double getPoints() {
    return points;
  }

  public void addPoints(double points) {
    this.points += points;
  }

  public void removePoints(double points) {
    this.points -= points;
  }
}
