package com.unicamp.mc322.lab10.restaurant.order;

import java.util.HashMap;
import java.util.Map.Entry;

import com.unicamp.mc322.lab10.person.Customer;
import com.unicamp.mc322.lab10.restaurant.Restaurant;
import com.unicamp.mc322.lab10.restaurant.discount.Discount;
import com.unicamp.mc322.lab10.restaurant.order.item.Item;

public class Order {
  private OrderType type;
  private OrderState state;
  private HashMap<Item, Integer> items;
  private Customer customer;
  private Restaurant restaurant;
  private double total;

  public Order(OrderType type, Customer customer, Restaurant restaurant) {
    this.type = type;
    this.state = OrderState.NEW;
    this.items = new HashMap<Item, Integer>();
    this.customer = customer;
    this.restaurant = restaurant;
    this.total = -1;
  }

  public OrderType getType() {
    return type;
  }

  public double getTotal() {
    if (total < 0) {
      throw new IllegalStateException("O preço do pedido ainda não foi calculado");
    }

    return total;
  }

  public boolean belongsTo(Customer customer) {
    return this.customer.equals(customer);
  }

  public void addItem(String itemId, int quantity) {
    Item item = restaurant.getItem(itemId);

    if (item == null) {
      throw new IllegalArgumentException("O restaurante não tem esse produto.");
    }

    this.items.put(item, quantity);
  }

  public void addItem(String itemId) {
    addItem(itemId, 1);
  }

  public OrderState getState() {
    return state;
  }

  public void setState(OrderState newState) {
    if (!this.state.canMoveTo(newState)) {
      throw new IllegalArgumentException("Não é possível ir para essa etapa do processo.");
    }

    switch (newState) {
      case IN_PREPARATION:
        double total = 0;

        for (Entry<Item, Integer> entry : items.entrySet()) {
          Item item = entry.getKey();

          double itemTotal = item.getPrice();

          if (restaurant.hasItemDiscount(item.getID())) {
            itemTotal -= restaurant.getItemDiscount(item.getID()).calculateDiscount(itemTotal);
          }

          total += itemTotal * entry.getValue();
        }

        total += this.getDeliveryPrice();

        Discount customerDiscount = restaurant.getCustomerDiscount(customer);

        if (customerDiscount != null) {
          total -= customerDiscount.calculateDiscount(total);
        }

        this.total = total;
        break;
      
      case READY:
        if (type.equals(OrderType.DELIVERY)) {
          restaurant.requestDelivery(this);
        }

      default:
        break;
    }
  }

  public boolean canCancel() {
    return state.equals(OrderState.NEW) || state.equals(OrderState.IN_PREPARATION);
  }

  public boolean canPickup() {
    return state.equals(OrderState.READY) && type.equals(OrderType.PICKUP);
  }

  private double getDeliveryPrice() {
    if (!type.equals(OrderType.DELIVERY)) {
      return 0;
    }

    return 0.5 * restaurant.getAddress().distanceTo(customer.getAddress());
  }

  public void printSummary() {
    System.out.printf("   CLIENTE: %s\n", customer.getName());
    System.out.printf("   TIPO: %s\n", type.toString());
    System.out.println("   ITENS:");

    for (Entry<Item, Integer> entry : items.entrySet()) {
      System.out.printf("     - (%dx) %s\n", entry.getValue(), entry.getKey().getName());
    }

    System.out.println("      -----------");

    if (!state.equals(OrderState.NEW)) {
      if (type.equals(OrderType.DELIVERY)) {
        System.out.printf("      TAXA DE ENTREGA: R$%.2f\n", getDeliveryPrice());
      }

      System.out.printf("      TOTAL: R$%.2f\n", getTotal());
    } else {
      System.out.println("      TOTAL NÃO CALCULADO");
    }
  }
}
