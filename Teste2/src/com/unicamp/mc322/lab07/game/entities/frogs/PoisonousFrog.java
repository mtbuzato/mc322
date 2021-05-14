package com.unicamp.mc322.lab07.game.entities.frogs;

import java.util.Random;

import com.unicamp.mc322.lab07.game.entities.Entity;
import com.unicamp.mc322.lab07.util.Direction;
import com.unicamp.mc322.lab07.util.Vector2D;

public class PoisonousFrog extends Frog {

  private Random random;
  private final Direction[] directions = {Direction.FORWARD, Direction.BACKWARD, Direction.LEFT, Direction.RIGHT};
  
  public PoisonousFrog(String name, String icon) {
    super(name, icon);

    this.random = new Random();
  }

  @Override
  public void move(Direction dir) {
    this.setLastPosition(new Vector2D(this.getPosition()));

    Direction moveDir = directions[random.nextInt(4)];
    int multiplier = random.nextInt(4) + 1;

    switch (moveDir) {
      case FORWARD:
        this.getPosition().addY(-multiplier);
        break;
      case BACKWARD:
        this.getPosition().addY(multiplier);
        break;
      case RIGHT:
        this.getPosition().addX(multiplier);
        break;
      case LEFT:
        this.getPosition().addX(-multiplier);
        break;
    }
  }

  @Override
  public void eat(Entity e) {
    super.eat(e);
    addPoints(e.getPoints() * 0.2);
  }

}
