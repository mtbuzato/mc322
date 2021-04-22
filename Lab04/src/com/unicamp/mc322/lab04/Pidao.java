package com.unicamp.mc322.lab04;

import java.util.ArrayList;
import java.util.HashMap;

import com.unicamp.mc322.lab04.menu.DiscountType;
import com.unicamp.mc322.lab04.menu.MenuItem;
import com.unicamp.mc322.lab04.order.Order;
import com.unicamp.mc322.lab04.order.OrderState;
import com.unicamp.mc322.lab04.user.User;
import com.unicamp.mc322.lab04.util.Position;

public class Pidao {
  private String name;
  private String cnpj;
  private Position pos;
  private ArrayList<User> users;
  private HashMap<String, MenuItem> menu;
  private HashMap<String, Double> discounts;
  private ArrayList<Order> orders;

  public Pidao(String name, String cnpj, Position pos) {
    this.name = name;
    this.cnpj = cnpj;
    this.pos = pos;
    this.users = new ArrayList<User>();
  }

  public Pidao(String name, String cnpj, int x, int y) {
    this.name = name;
    this.cnpj = cnpj;
    this.pos = new Position(x, y);
    this.users = new ArrayList<User>();
    this.menu = new HashMap<String, MenuItem>();
    this.discounts = new HashMap<String, Double>();
    this.orders = new ArrayList<Order>();
  }

  public User addUser(String name, String cpf, int deliveryX, int deliveryY) {
    User user = new User(name, cpf, deliveryX, deliveryY);

    users.add(user);

    return user;
  }

  public void addToMenu(MenuItem item) {
    if (menu.containsKey(item.getID())) {
      throw new IllegalArgumentException("Já existe um item com esse ID no cardápio.");
    }

    menu.put(item.getID(), item);
  }

  public void removeFromMenu(String id) {
    if (!menu.containsKey(id)) {
      throw new IllegalArgumentException("Não existe item com esse ID no cardápio.");
    }

    for (Order o : orders) {
      if (o.hasItem(id)) {
        throw new IllegalStateException("Não é possível remover esse item agora. Existem pedidos ativos que dependem dele.");
      }
    }

    if (discounts.containsKey(id)) {
      discounts.remove(id);
    }

    menu.remove(id);
  }

  public void applyDiscount(String id, double value, DiscountType type) {
    if (!menu.containsKey(id)) {
      throw new IllegalArgumentException("Não existe item com esse ID no cardápio.");
    }

    if (discounts.containsKey(id)) {
      throw new IllegalStateException("Já existe um desconto aplicado a esse item no cardápio.");
    }

    discounts.put(id, menu.get(id).getDiscountedPrice(value, type));
  }

  public void removeDiscount(String id) {
    if (!menu.containsKey(id)) {
      throw new IllegalArgumentException("Não existe item com esse ID no cardápio.");
    }

    if (!discounts.containsKey(id)) {
      throw new IllegalArgumentException("Não existe desconto aplicado a esse item.");
    }

    discounts.remove(id);
  }

  public boolean hasDiscount(String id) {
    if (!menu.containsKey(id)) {
      throw new IllegalArgumentException("Não existe item com esse ID no cardápio.");
    }

    return discounts.containsKey(id);
  }

  public double getItemPrice(String id) {
    if (!menu.containsKey(id)) {
      throw new IllegalArgumentException("Não existe item com esse ID no cardápio.");
    }

    if (discounts.containsKey(id)) {
      return discounts.get(id);
    }

    return menu.get(id).getPrice();
  }

  public void order(Order order) {
    if (order.getSize() == 0) {
      throw new IllegalStateException("O pedido está vazio.");
    }

    for (String id : order.getItemTypes()) {
      if (!menu.containsKey(id)) {
        throw new IllegalArgumentException("O pedido contém itens não encontrados no cardápio.");
      }
    }
    
    order.setState(OrderState.IN_PREPARATION);
    order.setValue(calculateOrderValue(order));

    orders.add(order);
  }

  public void cancelOrder(Order order) {
    if (!orders.contains(order)) {
      throw new IllegalArgumentException("Pedido não encontrado.");
    }

    if (order.getState().getID() > 1) {
      throw new IllegalStateException("O pedido não pode ser cancelado pois já saiu pra entrega ou foi entregue.");
    }

    orders.remove(order);
  }

  public int getUserOrderCount(User user) {
    int count = 0;

    for (Order o : orders) {
      if (o.getUser().getCPF().equals(user.getCPF())) {
        count++;
      }
    }

    return count;
  }

  public double calculateOrderValue(Order order) {
    int orderCount = getUserOrderCount(order.getUser());

    System.out.printf("USUÁRIO %s FEZ %d COMPRAS.\n", order.getUser().getCPF(), orderCount);

    double value = 0;

    for (MenuItem item : order.getItems()) {
      value += getItemPrice(item.getID());
    }

    if (orderCount == 0) {
      value *= 0.8; // Desconto de 20% na primeira compra
    } else if (orderCount % 9 == 0) {
      double discount = Math.min(60, value);  // Desconto de 100% (limite R$60,00) a cada 10 compras
      value -= discount;
    } else if(value > 100) {
      value *= 0.9; // Desconto de 10% para compras acima de R$100,00
    }

    return value;
  }

  public void printMenu() {
    System.out.printf("Restaurante %s\n", name);
    System.out.printf("(CNPJ: %s)\n", cnpj);
    
    if (menu.size() == 0) {
      return;
    }

    System.out.println("\nCardápio de hoje:");

    for (MenuItem item : menu.values()) {
      System.out.printf("[%s] %s R$ %.2f", item.getID(), item.getName(), getItemPrice(item.getID()));

      if (hasDiscount(item.getID())) {
        System.out.printf(" (PROMOÇÃO! Preço normal: R$ %.2f)", item.getPrice());
      }

      System.out.print("\n");
    }
  }

  public void printOrders() {
    if (orders.size() == 0) {
      System.out.println("Não existem pedidos.");
      return;
    }

    System.out.printf("Existem %d pedidos:\n", orders.size());

    for (Order o : orders) {
      User user = o.getUser();

      System.out.println("--------------------------------------------");
      System.out.printf("Usuário: %s (%s)\n", user.getName(), user.getCPF());

      for (MenuItem item : o.getItems()) {
        System.out.printf(" - %s\n", item.getID());
      }

      System.out.printf("Valor Total: R$ %.2f\n", o.getValue());
    }

    System.out.println("--------------------------------------------");
  }
}
