package com.unicamp.mc322.lab07.game.entities.frogs;

import com.unicamp.mc322.lab07.util.Direction;
import com.unicamp.mc322.lab07.util.Vector2D;

public class GreenFrog extends Frog {
  
  public GreenFrog(String name, String icon) {
    super(name, icon);
  }

  @Override
  public void move(Direction dir) {
    this.setLastPosition(new Vector2D(this.getPosition()));

    switch (dir) {
      case FORWARD:
        this.getPosition().addY(-1);
        break;
      case BACKWARD:
        this.getPosition().addY(1);
        break;
      case RIGHT:
        this.getPosition().addX(1);
        break;
      case LEFT:
        this.getPosition().addX(-1);
        break;
    }
  }

}
