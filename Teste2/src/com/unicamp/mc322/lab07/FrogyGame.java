package com.unicamp.mc322.lab07;

import java.util.HashMap;
import java.util.Scanner;

import com.unicamp.mc322.lab07.game.Map;
import com.unicamp.mc322.lab07.game.entities.Entity;
import com.unicamp.mc322.lab07.game.entities.foods.Food;
import com.unicamp.mc322.lab07.game.entities.foods.FoodType;
import com.unicamp.mc322.lab07.game.entities.frogs.Frog;
import com.unicamp.mc322.lab07.game.entities.frogs.GreenFrog;
import com.unicamp.mc322.lab07.game.obstacles.Obstacle;
import com.unicamp.mc322.lab07.game.obstacles.Predator;
import com.unicamp.mc322.lab07.game.obstacles.Rock;
import com.unicamp.mc322.lab07.game.obstacles.Trap;
import com.unicamp.mc322.lab07.util.Direction;
import com.unicamp.mc322.lab07.util.Vector2D;

public class FrogyGame {

  private Map map;
  private Frog frog;
  private boolean running;
  private Scanner keyboard;
  public static void main(String[] args) {
    Frog f = new GreenFrog("Jogador 1", "J1");
    f.moveTo(new Vector2D(8, 7));
    FrogyGame game = new FrogyGame(10, 10, "--", f);

    Vector2D[] rocks = {
      new Vector2D(2, 7),
      new Vector2D(3, 2),
      new Vector2D(7, 1),
      new Vector2D(8, 4),
      new Vector2D(8, 8),
    };

    for (Vector2D rockPos : rocks) {
      Rock rock = new Rock();
      rock.addPosition(rockPos);
      game.getMap().addObstacle(rock);
    }

    Predator pred = new Predator();
    pred.addPosition(new Vector2D(4, 4));
    pred.addPosition(new Vector2D(5, 5));
    game.getMap().addObstacle(pred);
  
    Trap trap = new Trap();
    trap.addPosition(new Vector2D(0, 0));
    trap.addPosition(new Vector2D(0, 2));
    game.getMap().addObstacle(trap);

    Food va = new Food(FoodType.FIREFLY);
    va.moveTo(new Vector2D(1, 3));
    game.getMap().addEntity(va);

    Food gr = new Food(FoodType.CRICKET);
    gr.moveTo(new Vector2D(4, 7));
    game.getMap().addEntity(gr);

    game.start();
  }

  public FrogyGame(int width, int height, String defaultIcon, Frog frog) {
    this.map = new Map(width, height, defaultIcon, frog);
    this.frog = frog;
  }

  public Map getMap() {
    return map;
  }

  public Frog getFrog() {
    return frog;
  }

  public void start() {
    if (running) {
      throw new IllegalStateException("Tentou-se iniciar o jogo já em progresso.");
    }

    running = true;
    keyboard = new Scanner(System.in);

    map.render();

    tick();
  }

  public void end() {
    if (!running) {
      throw new IllegalStateException("Tentou-se acabar o jogo não em progresso.");
    }

    running = false;

    keyboard.close();
    keyboard = null;

    System.out.println("FIM DE JOGO!");
    System.out.printf("A rã %s obteu %.2f pontos!\n", frog.getName(), frog.getPoints());
  }

  private void tick() {
    if (!running) {
      throw new IllegalStateException("Tentou-se rodar o jogo não em progresso.");
    }

    System.out.print("\nEnter the command: ");
    String command = keyboard.nextLine();
    
    processCommand(command);
    processFrogPhysics();
    map.processAliveEntities();

    map.render();

    if (frog.isAlive()) {
      tick();
    } else {
      end();
    }
  }

  private void processCommand(String command) {
    switch (command) {
      case "q":
        running = false;
        break;
      case "w":
        frog.move(Direction.FORWARD);
        break;
      case "a":
        frog.move(Direction.LEFT);
        break;
      case "s":
        frog.move(Direction.BACKWARD);
        break;
      case "d":
        frog.move(Direction.RIGHT);
        break;
    }
  }

  private void processFrogPhysics() {
    Vector2D frogPos = frog.getPosition();

    if (map.isOutOfBounds(frogPos)) {
      frog.kill();
      return;
    }

    HashMap<Vector2D, Obstacle> obstacles = map.getObstacleMatrix();

    if (obstacles.containsKey(frogPos)) {
      frog.kill();
      return;
    }

    HashMap<Vector2D, Entity> entities = map.getEntityMatrix();

    if (entities.containsKey(frogPos)) {
      frog.eat(entities.get(frogPos));
      return;
    }
  }
}
