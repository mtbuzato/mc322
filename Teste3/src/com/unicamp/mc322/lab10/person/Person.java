package com.unicamp.mc322.lab10.person;

public class Person {
  private String name;
  private String cpf;

  public Person(String name, String cpf) {
    this.name = name;
    this.cpf = cpf;
  }

  public String getName() {
    return name;
  }

  public String getCPF() {
    return cpf;
  }
}
