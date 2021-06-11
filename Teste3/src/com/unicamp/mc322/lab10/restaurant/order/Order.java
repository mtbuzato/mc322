package com.unicamp.mc322.lab10.restaurant.order;

import java.util.HashMap;
import java.util.Map.Entry;

import com.unicamp.mc322.lab10.person.Customer;
import com.unicamp.mc322.lab10.person.Deliveryman;
import com.unicamp.mc322.lab10.restaurant.Restaurant;
import com.unicamp.mc322.lab10.restaurant.discount.Discount;
import com.unicamp.mc322.lab10.restaurant.order.item.Item;
import com.unicamp.mc322.lab10.review.Review;

public class Order {
  private OrderType type;
  private OrderState state;
  private HashMap<Item, Integer> items;
  private Customer customer;
  private Restaurant restaurant;
  private double total;
  private Deliveryman deliveryman;

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
        if (items.size() == 0) {
          throw new IllegalStateException("Não é possível preparar um pedido sem itens.");
        }

        this.state = newState;

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
        this.state = newState;

        if (type.equals(OrderType.DELIVERY)) {
          deliveryman = restaurant.requestDelivery(this);
        }

        break;

      default:
        this.state = newState;

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

  private Review createReview(int stars, String comment) {
    if (!state.equals(OrderState.FINISHED)) {
      throw new IllegalStateException("Não é possível avaliar esse pedido ainda.");
    }

    return new Review(stars, comment, customer);
  }

  private Review createReview(int stars) {
    if (!state.equals(OrderState.FINISHED)) {
      throw new IllegalStateException("Não é possível avaliar esse pedido ainda.");
    }

    return new Review(stars, customer);
  }

  public void reviewRestaurant(int stars, String comment) {
    restaurant.receiveReview(createReview(stars, comment));
  }

  public void reviewRestaurant(int stars) {
    restaurant.receiveReview(createReview(stars));
  }

  public void reviewItem(int stars, String comment, Item item) {
    if (!items.containsKey(item)) {
      throw new IllegalArgumentException("Esse item não faz parte desse pedido.");
    }

    item.receiveReview(createReview(stars, comment));
  }

  public void reviewItem(int stars, Item item) {
    if (!items.containsKey(item)) {
      throw new IllegalArgumentException("Esse item não faz parte desse pedido.");
    }

    item.receiveReview(createReview(stars));
  }

  public void reviewDeliveryman(int stars, String comment) {
    if (!type.equals(OrderType.DELIVERY)) {
      throw new IllegalStateException("Esse pedido não é para entrega.");
    }

    Review review = createReview(stars, comment);
    
    // Criando a review antes, nessa chamada especificamente, garante
    // que o entregador já tenha sido atribuído

    deliveryman.receiveReview(review);
  }

  public void reviewDeliveryman(int stars) {
    if (!type.equals(OrderType.DELIVERY)) {
      throw new IllegalStateException("Esse pedido não é para entrega.");
    }

    Review review = createReview(stars);

    deliveryman.receiveReview(review);
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
