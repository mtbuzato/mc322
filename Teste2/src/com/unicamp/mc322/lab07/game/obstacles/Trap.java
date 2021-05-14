package com.unicamp.mc322.lab07.game.obstacles;

import com.unicamp.mc322.lab07.util.Vector2D;

public class Trap extends Obstacle {
  public Trap() {
    super("{}");
  }

  @Override
  public void addPosition(Vector2D pos) {
    int positionsSize = this.getPositions().size();

    if (positionsSize > 2) {
      throw new IllegalStateException("Armadilhas só podem ocupar três posições.");
    }

    if (positionsSize >= 1) {
      Vector2D a = this.getPositions().get(0);

      if (a.distanceTo(pos) > 2) {
        throw new IllegalArgumentException("Armadilhas precisam estar a duas posições de distância.");
      }
    }

    if (positionsSize == 2) {
      Vector2D b = this.getPositions().get(1);

      if (b.distanceTo(pos) > 2) {
        throw new IllegalArgumentException("Armadilhas precisam estar a duas posições de distância.");
      }
    }


    super.addPosition(pos);
  }
}
