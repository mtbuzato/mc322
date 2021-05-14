package com.unicamp.mc322.lab07.game.obstacles;

import com.unicamp.mc322.lab07.util.Vector2D;

public class Rock extends Obstacle {
  public Rock() {
    super("<>");
  }

  @Override
  public void addPosition(Vector2D pos) {
    if (this.getPositions().size() != 0) {
      throw new IllegalStateException("Pedras só podem ter uma posição.");
    }

    super.addPosition(pos);
  }
}
