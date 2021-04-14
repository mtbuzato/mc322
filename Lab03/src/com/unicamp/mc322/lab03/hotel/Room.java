package com.unicamp.mc322.lab03.hotel;

public class Room {
  
  private int num;
  private boolean vip;
  private Reservation reservation;
  private boolean acceptsSmokers;
  private boolean ac;

  public Room(int num, boolean vip, boolean acceptsSmokers, boolean ac) {
    this.num = num;
    this.vip = vip;
    this.acceptsSmokers = acceptsSmokers;
    this.reservation = null;
    this.ac = false;
  }

  public void reserve(Reservation reservation) {
    this.reservation = reservation;
  }

  public void free() {
    this.reservation = null;
  }

  public boolean isOccupied() {
    return reservation != null;
  }

  public Reservation getReservation() {
    return reservation;
  }

  public int getNumber() {
    return num;
  }

  public boolean isVIP() {
    return vip;
  }

  public boolean acceptsSmokers() {
    return acceptsSmokers;
  }

  public boolean hasAC() {
    return ac;
  }

}
