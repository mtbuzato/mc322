package com.unicamp.mc322.lab10;

import java.util.ArrayList;

import com.unicamp.mc322.lab10.restaurant.Restaurant;
import com.unicamp.mc322.lab10.util.Position;

public class Pidao {
  private ArrayList<Restaurant> restaurants;

  public Pidao() {
    this.restaurants = new ArrayList<Restaurant>();
  }

  public Restaurant createRestaurant(String name, String cnpj, Position address) {
    if (getRestaurant(cnpj) != null) {
      throw new IllegalArgumentException("Não é possível criar dois restaurantes com o mesmo CNPJ.");
    }

    Restaurant rest = new Restaurant(name, cnpj, address);

    this.restaurants.add(rest);

    return rest;
  }

  public Restaurant getRestaurant(String cnpj) {
    for (Restaurant restaurant : restaurants) {
      if (restaurant.getCNPJ().equalsIgnoreCase(cnpj)) {
        return restaurant;
      }
    }

    return null;
  }

  public void printOrderSummary() {
    for (Restaurant restaurant : restaurants) {
      restaurant.printOrderSummary();
    }
  }

  public void printRestaurantReviewSummary() {
    System.out.println("Avaliações dos restaurantes:\n");

    for (Restaurant restaurant : restaurants) {
      System.out.printf(" - %s: %.2f estrelas\n", restaurant.getStars());
    }

    System.out.println();
  }

  public void printDeliverymenReviewSummary() {
    System.out.println("Avaliações dos entregadores dos restaurantes:\n");

    for (Restaurant restaurant : restaurants) {
      System.out.printf(" - %s:", restaurant.getName());
      restaurant.printDeliverymenReviewSummary();
    }

    System.out.println();
  }

  public void printItemReviewSummary() {
    System.out.println("Avaliações dos itens dos restaurantes:\n");

    for (Restaurant restaurant : restaurants) {
      System.out.printf(" - %s:", restaurant.getName());
      restaurant.printItemReviewSummary();
    }

    System.out.println();
  }
}
