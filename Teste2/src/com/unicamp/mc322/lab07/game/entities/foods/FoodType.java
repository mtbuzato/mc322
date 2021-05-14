package com.unicamp.mc322.lab07.game.entities.foods;

public enum FoodType {
  FIREFLY("Vagalume", "va", 15),
  CRICKET("Grilo", "gr", 20);

  private String name;
  private String icon;
  private double points;

  private FoodType(String name, String icon, double points) {
    this.name = name;
    this.icon = icon;
    this.points = points;
  }

  public String getName() {
    return name;
  }

  public String getIcon() {
    return icon;
  }

  public double getPoints() {
    return points;
  }
}
