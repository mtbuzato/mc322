package com.unicamp.mc322.lab10.restaurant.discount;

public class Discount {
  private double amount;
  private DiscountType type;

  public Discount(double amount, DiscountType type) {
    if (type.equals(DiscountType.PERCENTAGE) && (amount <= 0 || amount > 100)) {
      throw new IllegalArgumentException("O desconto por porcentagem só pode variar de 0 à 100%.");
    } else if (type.equals(DiscountType.FIXED) && amount <= 0) {
      throw new IllegalArgumentException("O desconto fixo deve ter um valor maior que 0.");
    }

    this.type = type;
    this.amount = amount;
  }

  public double calculateDiscount(double value) {
    if (type.equals(DiscountType.FIXED)) {
      return Math.min(amount, value);
    }

    return value * (amount / 100);
  }
}
