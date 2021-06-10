package com.unicamp.mc322.lab10.restaurant;

import java.util.ArrayList;
import java.util.HashMap;

import com.unicamp.mc322.lab10.person.Customer;
import com.unicamp.mc322.lab10.person.Deliveryman;
import com.unicamp.mc322.lab10.restaurant.discount.Discount;
import com.unicamp.mc322.lab10.restaurant.discount.DiscountType;
import com.unicamp.mc322.lab10.restaurant.order.Order;
import com.unicamp.mc322.lab10.restaurant.order.OrderState;
import com.unicamp.mc322.lab10.restaurant.order.OrderType;
import com.unicamp.mc322.lab10.restaurant.order.item.Item;
import com.unicamp.mc322.lab10.review.Review;
import com.unicamp.mc322.lab10.util.Position;

public class Restaurant {
  private String name;
  private String cnpj;
  private Position address;
  private ArrayList<Deliveryman> deliverymen;
  private ArrayList<Order> orders;
  private HashMap<String, Item> menu;
  private HashMap<String, Discount> discounts;
  private HashMap<Customer, Integer> customers;
  private ArrayList<Review> reviews;

  public Restaurant(String name, String cnpj, Position address) {
    this.name = name;
    this.cnpj = cnpj;
    this.address = address;
    this.deliverymen = new ArrayList<Deliveryman>();
    this.orders = new ArrayList<Order>();
    this.menu = new HashMap<String, Item>();
    this.discounts = new HashMap<String, Discount>();
    this.customers = new HashMap<Customer, Integer>();
    this.reviews = new ArrayList<Review>();
  }

  public String getName() {
    return name;
  }

  public String getCNPJ() {
    return cnpj;
  }

  public Position getAddress() {
    return address;
  }

  public void addDeliveryman(Deliveryman deliveryman) {
    if (hasDeliveryman(deliveryman)) {
      throw new IllegalArgumentException("Esse entregador já entrega para esse restaurante.");
    }

    deliverymen.add(deliveryman);
    deliveryman.setRestaurant(this);
  }

  public boolean hasDeliveryman(Deliveryman deliveryman) {
    return deliverymen.contains(deliveryman);
  }

  public void addItem(Item item) {
    if (this.menu.containsKey(item.getID())) {
      throw new IllegalArgumentException("Já existe um item com esse ID no cardápio.");
    }

    this.menu.put(item.getID(), item);
  }

  public Item getItem(String id) {
    return this.menu.get(id);
  }

  public void removeItem(String id) {
    if (!this.menu.containsKey(id)) {
      throw new IllegalArgumentException("Não existe um item com esse ID no cardápio.");
    }

    this.menu.remove(id);
  }

  public boolean hasItemDiscount(String id) {
    if (!this.menu.containsKey(id)) {
      throw new IllegalArgumentException("Não existe um item com esse ID no cardápio.");
    }

    return this.discounts.containsKey(id);
  }

  public void addItemDiscount(String id, Discount discount) {
    if (this.hasItemDiscount(id)) {
      throw new IllegalStateException("O item já está com desconto.");
    }

    this.discounts.put(id, discount);
  }

  public void removeItemDiscount(String id) {
    if (!this.hasItemDiscount(id)) {
      throw new IllegalStateException("O item não está com desconto.");
    }

    this.discounts.remove(id);
  }

  public Discount getItemDiscount(String id) {
    if (!this.hasItemDiscount(id)) {
      throw new IllegalStateException("O item não está com desconto.");
    }

    return this.discounts.get(id);
  }

  public Order createOrder(OrderType type, Customer customer) {
    Order order = new Order(type, customer, this);

    this.orders.add(order);
    
    if (!this.customers.containsKey(customer)) {
      this.customers.put(customer, 1);
    } else {
      this.customers.put(customer, this.customers.get(customer) + 1);
    }

    return order;
  }

  public Discount getCustomerDiscount(Customer customer) {
    int orderCount = 0;

    if (this.customers.containsKey(customer)) {
      orderCount = this.customers.get(customer);
    }

    if (orderCount == 1) {
      return new Discount(20, DiscountType.PERCENTAGE);
    }
    
    return null;
  }

  public void requestDelivery(Order order) {
    if (!this.orders.contains(order)) {
      throw new IllegalArgumentException("Esse pedido não pertence a esse restaurante.");
    }

    if (!order.getType().equals(OrderType.DELIVERY)) {
      throw new IllegalArgumentException("Esse pedido não é para entrega.");
    }

    if (!order.getState().equals(OrderState.READY)) {
      throw new IllegalArgumentException("Esse não está pronto para ser entregue.");
    }

    Deliveryman deliveryman = findDeliveryman();

    if (deliveryman == null) {
      throw new IllegalStateException("Não há entregadores disponíveis.");
    }

    deliveryman.deliver(order);
  }

  private Deliveryman findDeliveryman() {
    for (Deliveryman deliveryman : deliverymen) {
      if (!deliveryman.isDelivering()) {
        return deliveryman;
      }
    }

    return null;
  }

  public void receiveReview(Review review) {
    this.reviews.add(review);
  }

  public double getStars() {
    double stars = 0;
    
    for (Review review : reviews) {
      stars += review.getStars();
    }

    return stars / reviews.size();
  }

  public void printOrderSummary() {
    System.out.printf("----------- %s - PEDIDOS -----------\n", name);
    System.out.printf("%d pedidos\n", orders.size());
    System.out.printf("%.2f estrelas\n\n", getStars());

    int i = 0;

    for (Order order : orders) {
      i++;

      System.out.printf("PEDIDO %d:\n", i);
      order.printSummary();
      System.out.println();
    }

    System.out.println();
  }

  public void printMenu() {
    System.out.printf("----------- %s - CARDÁPIO -----------\n", name);
    System.out.printf("%d pedidos\n", orders.size());
    System.out.printf("%.2f estrelas\n\n", getStars());

    for (Item item : menu.values()) {
      if (hasItemDiscount(item.getID())) {
        System.out.printf("[%s] %s - PROMOÇÃO!!!\n", item.getID(), item.getName());
        System.out.printf("   De: R$.2f\n", item.getPrice());
        System.out.printf("   Por: R$.2f\n", item.getPrice() - getItemDiscount(item.getID()).calculateDiscount(item.getPrice()));
      } else {
        System.out.printf("[%s] %s - R$.2f\n", item.getID(), item.getName(), item.getPrice());
      }
    }

    System.out.println();
  }

  public void printDeliverymenReviewSummary() {
    for (Deliveryman deliveryman : deliverymen) {
      System.out.printf("   - %s: %.2f estrelas", deliveryman.getName(), deliveryman.getStars());
    }
  }

  public void printItemReviewSummary() {
    for (Item item : menu.values()) {
      System.out.printf("   - %s: %.2f estrelas", item.getName(), item.getStars());
    }
  }
}
