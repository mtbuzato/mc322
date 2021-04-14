package com.unicamp.mc322.lab03.user;

public class User {
  
  private String name;
  private String cpf;
  private String birthdate;
  private UserGender gender;
  private double balance;
  private boolean smoker;

  public User(String name, String cpf, String birthdate, UserGender gender, boolean smoker) {
    this.name = name;
    this.cpf = cpf;
    this.birthdate = birthdate;
    this.gender = gender;
    this.balance = 0;
    this.smoker = smoker;
  }

  public String getName() {
    return name;
  }

  public double getBalance() {
    return this.balance;
  }

  public void deposit(double amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Só é possível depositar valores positivos e não-nulos.");
    }

    if (amount > 10000) {
      throw new IllegalArgumentException("Só é possível depositar até R$10.000,00 por operação.");
    }

    balance += amount;
  }

  public boolean withdraw(double amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Só é possível sacar valores positivos e não-nulos.");
    }

    if (amount > 10000) {
      throw new IllegalArgumentException("Só é possível sacar até R$10.000,00 por operação.");
    }

    if (balance < amount) {
      return false;
    }

    balance -= amount;

    return true;
  }

  public boolean isSmoker() {
    return smoker;
  }

}
