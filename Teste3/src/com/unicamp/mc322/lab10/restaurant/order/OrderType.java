package com.unicamp.mc322.lab10.restaurant.order;

public enum OrderType {
  DELIVERY("Entrega"),
  PICKUP("Retirada");

  private String translation;

  private OrderType(String translation) {
    this.translation = translation;
  }

  @Override
  public String toString() {
    return this.translation;
  }
}
