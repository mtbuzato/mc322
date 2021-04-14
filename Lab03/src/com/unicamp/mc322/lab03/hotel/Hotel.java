package com.unicamp.mc322.lab03.hotel;

import java.util.HashMap;

public class Hotel {

  private static int countAvailableRooms(HashMap<Integer, Room> rooms) {
    int available = 0;

    for (Room r : rooms.values()) {
      if (!r.isOccupied()) {
        available++;
      }
    }

    return available;
  }
  
  private String name;
  private String address;
  private String phone;
  private HashMap<Integer, Room> normalRooms;
  private HashMap<Integer, Room> vipRooms;
  private Service[] services;
  private double normalRate;
  private double vipRate;

  public Hotel(String name, String address, String phone, Service[] services, double normalRate, double vipRate) {
    this.name = name;
    this.address = address;
    this.phone = phone;
    this.normalRooms = new HashMap<Integer, Room>();
    this.vipRooms = new HashMap<Integer, Room>();
    this.services = services;
    this.normalRate = normalRate;
    this.vipRate = vipRate;
  }

  public String getName() {
    return name;
  }

  public Room getRoom(int num) {
    if (normalRooms.containsKey(num)) {
      return normalRooms.get(num);
    }

    if (vipRooms.containsKey(num)) {
      return vipRooms.get(num);
    }

    throw new IllegalArgumentException("Quarto inexistente.");
  }

  public void addRoom(Room room) {
    try {
      getRoom(room.getNumber());
    } catch (IllegalArgumentException ex) {
      if (room.isVIP()) {
        if (vipRooms.size() >= 10) {
          throw new IllegalStateException("Já existem 10 quartos VIPs.");
        }

        vipRooms.put(room.getNumber(), room);
      } else {
        if (normalRooms.size() >= 90) {
          throw new IllegalStateException("Já existem 90 quartos normais.");
        }

        normalRooms.put(room.getNumber(), room);
      }

      return;
    }

    throw new IllegalArgumentException("Já existe quarto com esse número.");
  }

  public boolean isRoomAvailable(int num) {
    return getRoom(num).isOccupied();
  }

  public int countAvailableVIPRooms() {
    return Hotel.countAvailableRooms(vipRooms);
  }

  public int countAvailableNormalRooms() {
    return Hotel.countAvailableRooms(normalRooms);
  }

  public int countAllAvailableRooms() {
    return countAvailableVIPRooms() + countAvailableNormalRooms();
  }

  public double getRoomDailyRate(int num) {
    Room room = getRoom(num);

    if (room.isVIP()) {
      return vipRate;
    }

    return normalRate;
  }

  public void printHotelStatus() {
    System.out.printf("---------- %s ----------\n", name);
    System.out.printf("Quartos disponíveis: %d (%d VIPs)\n", countAllAvailableRooms(), countAvailableVIPRooms());
    System.out.println("Preços:");
    System.out.printf("   - Diária normal: R$%.2f\n", normalRate);
    System.out.printf("   - Diária VIP: R$%.2f\n", vipRate);
    System.out.println("Serviços oferecidos:");
    for (Service s : services) {
      System.out.printf("   - %s\n", s.getOutput());
    }

    String normalRoomsList = "";
    String vipRoomsList = "";

    for (Room r : normalRooms.values()) {
      if (!r.isOccupied()) {
        normalRoomsList += ", " + r.getNumber();
      }
    }

    for (Room r : vipRooms.values()) {
      if (!r.isOccupied()) {
        vipRoomsList += ", " + r.getNumber();
      }
    }

    if (normalRoomsList.length() != 0 || vipRoomsList.length() != 0) {
      System.out.println("Lista de quartos disponíveis:");
    }

    if (normalRoomsList.length() != 0) {
      System.out.printf("   - Normais: %s\n", normalRoomsList.substring(2));
    }

    if (vipRoomsList.length() != 0) {
      System.out.printf("   - VIPs: %s\n", vipRoomsList.substring(2));
    }
  }

}
