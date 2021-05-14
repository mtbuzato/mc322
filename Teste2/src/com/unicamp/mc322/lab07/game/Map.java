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

  public HashMap<Vector2D, Obstacle> getObstacleMatrix() {
    HashMap<Vector2D, Obstacle> matrix = new HashMap<Vector2D, Obstacle>();

    for (Obstacle obstacle : obstacles) {
      for (Vector2D pos : obstacle.getPositions()) {
        matrix.put(pos, obstacle);
      }
    }

    return matrix;
  }

  public HashMap<Vector2D, Entity> getEntityMatrix() {
    HashMap<Vector2D, Entity> matrix = new HashMap<Vector2D, Entity>();

    for (Entity entity : entities) {
      matrix.put(entity.getPosition(), entity);
    }

    return matrix;
  }

  private HashMap<Vector2D, String> getIconsMatrix() {
    HashMap<Vector2D, String> matrix = new HashMap<Vector2D, String>();

    matrix.put(frog.getPosition(), frog.getIcon());

    for (Entity entity : entities) {
      matrix.put(entity.getPosition(), entity.getIcon());
    }

    for (Obstacle obstacle : obstacles) {
      for (Vector2D obstaclePos : obstacle.getPositions()) {
        matrix.put(obstaclePos, obstacle.getIcon());
      }
    }

    return matrix;
  }

  private ArrayList<Vector2D> getFilledPositions() {
    ArrayList<Vector2D> filled = new ArrayList<Vector2D>();

    filled.addAll(getObstacleMatrix().keySet());
    filled.addAll(getEntityMatrix().keySet());

    return filled;
  }

  public boolean isOutOfBounds(Vector2D vec) {
    if (vec.getX() < 0 || vec.getY() < 0) {
      return true;
    }

    if (vec.getX() >= width || vec.getY() >= height) {
      return true;
    }

    return false;
  }
  
  public void processAliveEntities() {
    ArrayList<Entity> toRemove = new ArrayList<Entity>();

    for (Entity entity : entities) {
      if (!entity.isAlive()) {
        toRemove.add(entity);
      }
    }

    entities.removeAll(toRemove);
  }

  public void render() {
    System.out.print("\033[H\033[2J");  
    System.out.flush();

    HashMap<Vector2D, String> iconsMatrix = getIconsMatrix();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (x != 0) {
          System.out.print(" ");
        }

        Vector2D pos = new Vector2D(x, y);

        if (iconsMatrix.containsKey(pos)) {
          System.out.print(iconsMatrix.get(pos));
        } else {
          System.out.print(defaultIcon);
        }
      }

      System.out.println();
    }
  }
}
