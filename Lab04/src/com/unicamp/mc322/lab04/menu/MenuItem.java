package com.unicamp.mc322.lab04.menu;

public class MenuItem {
  private String name;
  private double price;
  private String id;

  public MenuItem(String id, String name, double price) {
    this.name = name;
    this.price = price;
    this.setID(id);
  }

  public void setID(String id) {
    if (id.length() != 5) {
      throw new IllegalArgumentException("O identificadaor de um item do cardápio deve ter 5 caracteres.");
    }

    this.id = id.toUpperCase();
  }

  public String getID() {
    return id;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public double getDiscountedPrice(double value, DiscountType type) {
    if (type == DiscountType.FIXED_VALUE) {
      if (value <= 0) {
        throw new IllegalArgumentException("O desconto de valor fixo deve ser maior que zero.");
      }

      if (price >= value) {
        throw new IllegalArgumentException("O desconto de valor fixo deve ser menor que o preço padrão do item.");
      }

      return price - value;
    }

    // If not FIXED_VALUE then PERCENTAGE
    if (value <= 0 || value >= 100) {
      throw new IllegalArgumentException("O desconto em porcentagem deve ser um valor maior que zero e menor que cem.");
    }

    return price - (price * (value / 100));
  }
}
