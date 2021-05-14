package com.unicamp.mc322.lab07.game.obstacles;

import com.unicamp.mc322.lab07.util.Vector2D;

public class Predator extends Obstacle {
  public Predator() {
    super("$$");
  }

  @Override
  public void addPosition(Vector2D pos) {
    int positionsSize = this.getPositions().size();

    if (positionsSize > 1) {
      throw new IllegalStateException("Predadores só podem ocupar duas posições.");
    }

    if (positionsSize != 0) {
      Vector2D firstPos = this.getPositions().get(0);

      if (!firstPos.adjacentTo(pos)) {
        throw new IllegalArgumentException("Predadores precisam estar adjacentes.");
      }
    }

    super.addPosition(pos);
  }
}
