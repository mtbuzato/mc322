package com.unicamp.mc322.lab10.person;

import com.unicamp.mc322.lab10.restaurant.Restaurant;
import com.unicamp.mc322.lab10.restaurant.order.Order;
import com.unicamp.mc322.lab10.restaurant.order.OrderState;
import com.unicamp.mc322.lab10.restaurant.order.OrderType;
import com.unicamp.mc322.lab10.restaurant.order.item.Item;
import com.unicamp.mc322.lab10.review.Review;
import com.unicamp.mc322.lab10.util.Position;

public class Customer extends Person {
  
  private Position address;

  public Customer(String name, String cpf, Position address) {
    super(name, cpf);
    this.address = address;
  }

  public Position getAddress() {
    return address;
  }

  public void pickup(Order order) {
    if (!order.belongsTo(this)) {
      throw new IllegalArgumentException("Esse pedido não é desse cliente.");
    }

    if (!order.getState().equals(OrderState.READY) || !order.getType().equals(OrderType.PICKUP)) {
      throw new IllegalStateException("Esse pedido não está pronto para ser retirado.");
    }

    order.setState(OrderState.FINISHED);
  }

  private Review createReview(int stars, String comment) {
    return new Review(stars, comment, this);
  }

  private Review createReview(int stars) {
    return new Review(stars, this);
  }

  public void reviewRestaurant(int stars, String comment, Restaurant restaurant) {
    restaurant.receiveReview(createReview(stars, comment));
  }

  public void reviewRestaurant(int stars, Restaurant restaurant) {
    restaurant.receiveReview(createReview(stars));
  }

  public void reviewItem(int stars, String comment, Item item) {
    item.receiveReview(createReview(stars, comment));
  }

  public void reviewItem(int stars, Item item) {
    item.receiveReview(createReview(stars));
  }

  public void reviewDeliveryman(int stars, String comment, Deliveryman deliveryman) {
    deliveryman.receiveReview(createReview(stars, comment));
  }

  public void reviewDeliveryman(int stars, Deliveryman deliveryman) {
    deliveryman.receiveReview(createReview(stars));
  }
}
