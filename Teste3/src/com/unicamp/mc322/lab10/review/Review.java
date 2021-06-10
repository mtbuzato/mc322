package com.unicamp.mc322.lab10.review;

import com.unicamp.mc322.lab10.person.Customer;

public class Review {
  private int stars;
  private String comment;
  private Customer author;

  public Review(int stars, Customer author) {
    if (stars < 0 || stars > 5) {
      throw new IllegalArgumentException("Uma avaliação só pode ter de 0 a 5 estrelas.");
    }

    this.stars = stars;
    this.author = author;
    this.comment = "Não houve comentário.";
  }

  public Review(int stars, String comment, Customer author) {
    if (stars < 0 || stars > 5) {
      throw new IllegalArgumentException("Uma avaliação só pode ter de 0 a 5 estrelas.");
    }

    this.stars = stars;
    this.comment = comment;
    this.author = author;
  }

  public int getStars() {
    return stars;
  }

  public String getComment() {
    return comment;
  }

  public Customer getAuthor() {
    return author;
  }
}
