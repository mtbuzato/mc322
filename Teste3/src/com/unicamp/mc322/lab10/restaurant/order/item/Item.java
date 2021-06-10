package com.unicamp.mc322.lab10.restaurant.order.item;

import java.util.ArrayList;
import java.util.regex.Pattern;

import com.unicamp.mc322.lab10.review.Review;

public class Item {
  private String id;
  private String name;
  private double price;
  private ArrayList<Review> reviews;

  public Item(String id, String name, double price) {
    if (id.length() != 5 || Pattern.matches("[^a-zA-Z0-9]", id)) {
      throw new IllegalArgumentException("IDs devem ser compostos por 5 caracteres alfanuméricos.");
    }

    this.id = id;
    this.name = name;
    this.price = price;
    this.reviews = new ArrayList<Review>();
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
}
