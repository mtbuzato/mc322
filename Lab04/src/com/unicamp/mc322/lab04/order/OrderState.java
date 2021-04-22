package com.unicamp.mc322.lab04.order;

public enum OrderState {
  NEW(0),
  IN_PREPARATION(1),
  OUT_FOR_DELIVERY(2),
  DELIVERED(3);

  private int id;

  private OrderState(int id) {
    this.id = id;
  }

  public int getID() {
    return id;
  }
}
