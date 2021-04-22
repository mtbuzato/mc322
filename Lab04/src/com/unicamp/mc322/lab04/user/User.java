package com.unicamp.mc322.lab04.user;

import com.unicamp.mc322.lab04.util.Position;

public class User {
  private String name;
  private String cpf;
  private Position deliveryAddress;
  
  public User(String name, String cpf, Position deliveryAddress) {
    this.name = name;
    this.cpf = cpf;
    this.deliveryAddress = deliveryAddress;
  }

  public User(String name, String cpf, int x, int y) {
    this.name = name;
    this.cpf = cpf;
    this.deliveryAddress = new Position(x, y);
  }

  public String getCPF() {
    return cpf;
  }

  public String getName() {
    return name;
  }
}
