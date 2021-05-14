package com.unicamp.mc322.lab07.game;

import java.util.ArrayList;
import java.util.HashMap;

import com.unicamp.mc322.lab07.game.entities.Entity;
import com.unicamp.mc322.lab07.game.entities.frogs.Frog;
import com.unicamp.mc322.lab07.game.obstacles.Obstacle;
import com.unicamp.mc322.lab07.util.Vector2D;

public class Map {
  private int width;
  private int height;
  private String defaultIcon;
  private Frog frog;
  private ArrayList<Entity> entities;
  private ArrayList<Obstacle> obstacles;

  public Map(int width, int height, String defaultIcon, Frog frog) {
    this.width = width;
    this.height = height;
    this.defaultIcon = defaultIcon;
    this.frog = frog;
    this.entities = new ArrayList<Entity>();
    this.obstacles = new ArrayList<Obstacle>();
  }

  public void addEntity(Entity entity) {
    if (isOutOfBounds(entity.getPosition())) {
      throw new IllegalArgumentException("Essa entidade está fora do mapa.");
    }

    if (getFilledPositions().contains(entity.getPosition())) {
      throw new IllegalStateException("Já existe uma entidade nessa posição no mapa.");
    }

    entities.add(entity);
  }

  public void addObstacle(Obstacle obstacle) {
    for (Vector2D pos : obstacle.getPositions()) {
      if (isOutOfBounds(pos)) {
        throw new IllegalArgumentException("Essa entidade está fora do mapa.");
      }
  
      if (getFilledPositions().contains(pos)) {
        throw new IllegalStateException("Já existe uma entidade nessa posição no mapa.");
      }
    }

    obstacles.add(obstacle);
  }

  private ArrayList<Vector2D> getFilledPositions() {
    ArrayList<Vector2D> filled = new ArrayList<Vector2D>();

    for (Entity entity : entities) {
      filled.add(entity.getPosition());
    }

    for (Obstacle obstacle : obstacles) {
      filled.addAll(obstacle.getPositions());
    }

    return filled;
  }

  private boolean isOutOfBounds(Vector2D vec) {
    if (vec.getX() < 0 || vec.getY() < 0) {
      return true;
    }

    if (vec.getX() >= width || vec.getY() >= height) {
      return true;
    }

    return false;
  }

  public void render() {
    System.out.print("\033[H\033[2J");  
    System.out.flush();

    HashMap<String, String> iconsMatrix = new HashMap<String, String>();

    iconsMatrix.put(frog.getPosition().toString(), frog.getIcon());

    for (Entity entity : entities) {
      iconsMatrix.put(entity.getPosition().toString(), entity.getIcon());
    }

    for (Obstacle obstacle : obstacles) {
      for (Vector2D obstaclePos : obstacle.getPositions()) {
        iconsMatrix.put(obstaclePos.toString(), obstacle.getIcon());
      }
    }

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (x != 0) {
          System.out.print(" ");
        }

        String posStr = new Vector2D(x, y).toString();

        if (iconsMatrix.containsKey(posStr)) {
          System.out.print(iconsMatrix.get(posStr));
        } else {
          System.out.print(defaultIcon);
        }
      }

      System.out.println();
    }
  }
}
