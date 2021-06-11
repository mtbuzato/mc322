package com.unicamp.mc322.lab10.person;

import java.util.ArrayList;

import com.unicamp.mc322.lab10.restaurant.Restaurant;
import com.unicamp.mc322.lab10.restaurant.order.Order;
import com.unicamp.mc322.lab10.restaurant.order.OrderState;
import com.unicamp.mc322.lab10.review.Review;

public class Deliveryman extends Person {
  private Restaurant restaurant;
  private Order delivery;
  private ArrayList<Review> reviews;

  public Deliveryman(String name, String cpf){
    super(name, cpf);

    this.reviews = new ArrayList<Review>();
  }

  public boolean hasRestaurant() {
    return restaurant != null;
  }

  public void setRestaurant(Restaurant restaurant) {
    if (hasRestaurant()) {
      throw new IllegalStateException("Esse entregador já entrega para um restaurante.");
    }

    if (!restaurant.hasDeliveryman(this)) {
      throw new IllegalStateException("Esse entregador não entrega para esse restaurante.");
    }

    this.restaurant = restaurant;
  }

  public boolean isDelivering() {
    return delivery != null;
  }

  public void deliver(Order order) {
    if (this.isDelivering()) {
      throw new IllegalStateException("Esse entregador está ocupado.");
    }

    this.delivery = order;
    order.setState(OrderState.DELIVERING);
  }

  public void finishDelivery() {
    if (!this.isDelivering()) {
      throw new IllegalStateException("Esse entregador não possui entrega atualmente.");
    }

    this.delivery.setState(OrderState.FINISHED);
    this.delivery = null;
  }

  public void receiveReview(Review review) {
    this.reviews.add(review);
  }

  public double getStars() {
    if (reviews.size() == 0) {
      return 0;
    }

    double stars = 0;
    
    for (Review review : reviews) {
      stars += review.getStars();
    }

    return stars / reviews.size();
  }
}
