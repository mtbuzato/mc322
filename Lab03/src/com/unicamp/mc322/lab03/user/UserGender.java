package com.unicamp.mc322.lab03.user;

public enum UserGender {
  MASCULINE("Masculino"),
  FEMININE("Feminino"),
  OTHER("Outro");

  private String output;
  private UserGender(String output) {
    this.output = output;
  }

  public String getOutput() {
    return output;
  }
}
