package com.unicamp.mc322.lab07.game.entities.frogs;

import java.util.Random;

import com.unicamp.mc322.lab07.game.entities.Entity;
import com.unicamp.mc322.lab07.util.Direction;
import com.unicamp.mc322.lab07.util.Vector2D;

public class TomatoFrog extends Frog {

  private Random random;
  
  public TomatoFrog(String name, String icon) {
    super(name, icon);

    this.random = new Random();
  }

  @Override
  public void move(Direction dir) {
    this.setLastPosition(new Vector2D(this.getPosition()));

    switch (dir) {
      case FORWARD:
        this.getPosition().addY(-(random.nextInt(2) + 2));
        break;
      case BACKWARD:
        int multiplier = random.nextInt(3) + 1;

        if (multiplier == 3) {
          multiplier = 4;
        }

        this.getPosition().addY(multiplier);
        break;
      case RIGHT:
        this.getPosition().addX(random.nextInt(3) + 1);
        break;
      case LEFT:
        this.getPosition().addX(-(random.nextInt(3) + 1));
        break;
    }
  }

  @Override
  public void eat(Entity e) {
    super.eat(e);
    addPoints(e.getPoints() * 0.1);
  }

}
