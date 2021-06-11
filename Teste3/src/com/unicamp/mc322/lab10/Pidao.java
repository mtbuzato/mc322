package com.unicamp.mc322.lab10;

import java.util.ArrayList;
import java.util.HashMap;

import com.unicamp.mc322.lab10.person.Customer;
import com.unicamp.mc322.lab10.person.Deliveryman;
import com.unicamp.mc322.lab10.restaurant.Restaurant;
import com.unicamp.mc322.lab10.util.Position;

public class Pidao {
  private ArrayList<Restaurant> restaurants;
  private HashMap<String, Customer> customers;
  private HashMap<String, Deliveryman> deliverymen;

  public Pidao() {
    this.restaurants = new ArrayList<Restaurant>();
    this.customers = new HashMap<String, Customer>();
    this.deliverymen = new HashMap<String, Deliveryman>();
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

  public Customer createCustomer(String name, String cpf, Position address) {
    if (this.customers.containsKey(cpf)) {
      throw new IllegalArgumentException("Já existe um(a) cliente com esse CPF.");
    }

    Customer customer = new Customer(name, cpf, address);

    this.customers.put(cpf, customer);

    return customer;
  }

  public Deliveryman createDeliveryman(String name, String cpf) {
    if (this.deliverymen.containsKey(cpf)) {
      throw new IllegalArgumentException("Já existe um(a) entregador(a) com esse CPF.");
    }

    Deliveryman deliveryman = new Deliveryman(name, cpf);

    this.deliverymen.put(cpf, deliveryman);

    return deliveryman;
  }

  public void printOrderSummary() {
    for (Restaurant restaurant : restaurants) {
      restaurant.printOrderSummary();
    }
  }

  public void printRestaurantReviewSummary() {
    System.out.println("Avaliações dos restaurantes:\n");

    for (Restaurant restaurant : restaurants) {
      System.out.printf(" - %s: %.2f estrelas\n", restaurant.getName(), restaurant.getStars());
    }

    System.out.println();
  }

  public void printDeliverymenReviewSummary() {
    System.out.println("Avaliações dos entregadores dos restaurantes:\n");

    for (Restaurant restaurant : restaurants) {
      System.out.printf(" - %s:\n", restaurant.getName());
      restaurant.printDeliverymenReviewSummary();
    }

    System.out.println();
  }

  public void printItemReviewSummary() {
    System.out.println("Avaliações dos itens dos restaurantes:\n");

    for (Restaurant restaurant : restaurants) {
      System.out.printf(" - %s:\n", restaurant.getName());
      restaurant.printItemReviewSummary();
    }

    System.out.println();
  }
}
