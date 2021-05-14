package com.unicamp.mc322.lab07.game.entities.foods;

import com.unicamp.mc322.lab07.game.entities.Entity;

public class Food extends Entity {
  public Food(FoodType type) {
    super(type.getName(), type.getIcon());
    super.addPoints(type.getPoints());
  }

  @Override
  public void addPoints(double points) {
    throw new IllegalArgumentException("Não é possível adicionar pontos à uma comida.");
  }

  @Override
  public void removePoints(double points) {
    throw new IllegalArgumentException("Não é possível adicionar pontos à uma comida.");
  }
}
