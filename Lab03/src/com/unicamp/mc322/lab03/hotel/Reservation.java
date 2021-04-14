package com.unicamp.mc322.lab03.hotel;

import com.unicamp.mc322.lab03.user.User;

public class Reservation {
  
  private Hotel hotel;
  private Room room;
  private User user;
  private int days;

  public Reservation(Hotel hotel, Room room, User user, int days) {
    this.hotel = hotel;
    this.room = room;
    this.user = user;
    this.days = days;
  }

  public double getCost() {
    return hotel.getRoomDailyRate(room.getNumber()) * days;
  }

  public User getUser() {
    return user;
  }

}
