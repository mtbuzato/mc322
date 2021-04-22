package com.unicamp.mc322.lab04.order;

import java.util.ArrayList;

import com.unicamp.mc322.lab04.menu.MenuItem;
import com.unicamp.mc322.lab04.user.User;

public class Order {
  private User user;
  private ArrayList<MenuItem> items;
  private OrderState state;
  private double value;

  public Order(User user) {
    this.user = user;
    this.items = new ArrayList<MenuItem>();
    this.state = OrderState.NEW;
    this.value = -1;
  }

  public int getSize() {
    return items.size();
  }

  public void addItem(MenuItem item) {
    items.add(item);
  }

  public boolean hasItem(String id) {
    for (MenuItem item : items) {
      if (item.getID().equals(id)) {
        return true;
      }
    }

    return false;
  }

  public ArrayList<MenuItem> getItems() {
    return items;
  }

  public ArrayList<String> getItemTypes() {
    ArrayList<String> types = new ArrayList<String>();

    for (MenuItem item: items) {
      if (!types.contains(item.getID())) {
        types.add(item.getID());
      }
    }

    return types;
  }

  public void setState(OrderState state) {
    if (this.state == OrderState.DELIVERED) {
      throw new IllegalStateException("Esse pedido já foi finalizado e não pode ter seu estado alterado.");
    }

    if (this.state.getID() + 1 != state.getID()) {
      throw new IllegalStateException("Não é possível ir para esse estado.");
    }

    this.state = state;
  }

  public OrderState getState() {
    return state;
  }

  public void setValue(double value) {
    this.value = value;
  }

  public double getValue() {
    return value;
  }

  public User getUser() {
    return user;
  }
}
