package com.unicamp.mc322.lab10.restaurant.order;

public enum OrderState {
  NEW(0),
  IN_PREPARATION(1),
  READY(2),
  DELIVERING(3),
  FINISHED(4);

  private int order;

  private OrderState(int order) {
    this.order = order;
  }

  public int getOrder() {
    return order;
  }

  public boolean canMoveTo(OrderState state) {
    return state.getOrder() - 1 == this.order;
  }
}
