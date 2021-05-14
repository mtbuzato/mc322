package com.unicamp.mc322.lab07;

import java.util.Scanner;

import com.unicamp.mc322.lab07.game.Map;
import com.unicamp.mc322.lab07.game.entities.frogs.Frog;
import com.unicamp.mc322.lab07.game.obstacles.Predator;
import com.unicamp.mc322.lab07.game.obstacles.Rock;
import com.unicamp.mc322.lab07.game.obstacles.Trap;
import com.unicamp.mc322.lab07.util.Vector2D;

public class FrogyGame {

  private Map map;
  public static void main(String[] args) {
    Frog f = new Frog("Jogador 1", "J1");
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

    game.start();
  }

  public FrogyGame(int width, int height, String defaultIcon, Frog frog) {
    this.map = new Map(width, height, defaultIcon, frog);
  }

  public Map getMap() {
    return map;
  }

  public void start() {
    Scanner keyboard = new Scanner(System.in);
    boolean running = true;

    while (running) {
      map.render();

      System.out.print("\nEnter the command: ");
      String command = keyboard.nextLine();
      
      switch (command) {
        case "q":
          running = false;
          break;
        case "w":
          break;
        case "a":
          break;
        case "s":
          break;
        case "d":
          break;
      }
    }

    keyboard.close();
  }
}
