package com.unicamp.mc322.lab03.hotel;

public enum Service {
  BREAKFAST("Café da Manhã"),
  LUNCH("Almoço"),
  DINNER("Jantar"),
  GYM("Academia"),
  SPA("Spa"),
  ROOM_SERVICE("Serviço de Quarto");

  private String output;

  private Service(String output) {
    this.output = output;
  }

  public String getOutput() {
    return output;
  }
}
