package com.unicamp.mc322.lab03;

import com.unicamp.mc322.lab03.hotel.Hotel;
import com.unicamp.mc322.lab03.hotel.Reservation;
import com.unicamp.mc322.lab03.hotel.Room;
import com.unicamp.mc322.lab03.hotel.Service;
import com.unicamp.mc322.lab03.user.User;
import com.unicamp.mc322.lab03.user.UserGender;

public class Booking {
  public static void main(String[] args) throws Exception {
    // Não é interessante haver todo esse código na main.
    // Código aqui presente por motivos de teste.
    // Foram colocados prints de status a cada tentativa
    // reservar/cancelar.

    Service[] servicosPraiaTropical = { Service.BREAKFAST, Service.LUNCH, Service.DINNER };
    Hotel hotelPraiaTropical = new Hotel("Hotel Praia Tropical", "Rua Tajubá, 201 - Florianópolis, SC", "3225-8997", servicosPraiaTropical, 100.0, 900.0);
    hotelPraiaTropical.addRoom(new Room(2, true, true, true));
    hotelPraiaTropical.addRoom(new Room(22, false, false, false));
    hotelPraiaTropical.addRoom(new Room(87, false, false, false));

    Service[] servicosCampoFlorestal = { Service.BREAKFAST, Service.ROOM_SERVICE, Service.SPA };
    Hotel hotelCampoFlorestal = new Hotel("Hotel Campo Florestal", "Rua Monteiro, 456 - Goiânia, GO", "3654-8974", servicosCampoFlorestal, 50.0, 2000.0);
    hotelCampoFlorestal.addRoom(new Room(13, false, false, false));
    hotelCampoFlorestal.addRoom(new Room(99, false, false, false));
    
    hotelPraiaTropical.printHotelStatus();
    hotelCampoFlorestal.printHotelStatus();

    User roberci = new User("Roberci Silva", "784.245.698-21", "12/04/1996", UserGender.MASCULINE, true);
    roberci.deposit(1000.0);

    User marcela = new User("Marcela Domingues", "269.784.061-45", "22/07/1998", UserGender.FEMININE, false);
    marcela.deposit(2000.0);

    Booking.book(roberci, hotelPraiaTropical, 2, 1);

    hotelPraiaTropical.printHotelStatus();

    Booking.book(marcela, hotelCampoFlorestal, 13, 4);

    hotelCampoFlorestal.printHotelStatus();

    try {
      Booking.book(roberci, hotelPraiaTropical, 87, 1);
    } catch (Exception ex) {
      System.out.printf("Erro: %s\n", ex.getMessage());
    }

    hotelPraiaTropical.printHotelStatus();

    try {
      Booking.cancel(marcela, hotelPraiaTropical, 22);
    } catch (Exception ex) {
      System.out.printf("Erro: %s\n", ex.getMessage());
    }

    hotelPraiaTropical.printHotelStatus();

    try {
      Booking.book(roberci, hotelCampoFlorestal, 99, 1);
    } catch (Exception ex) {
      System.out.printf("Erro: %s\n", ex.getMessage());
    }

    hotelCampoFlorestal.printHotelStatus();

    try {
      Booking.cancel(roberci, hotelCampoFlorestal, 99);
    } catch (Exception ex) {
      System.out.printf("Erro: %s\n", ex.getMessage());
    }

    hotelCampoFlorestal.printHotelStatus();

    Booking.book(marcela, hotelPraiaTropical, 87, 1);

    hotelPraiaTropical.printHotelStatus();

    Booking.cancel(marcela, hotelPraiaTropical, 87); // ADICIONAL: testando um cancelamento que funcione.

    hotelPraiaTropical.printHotelStatus();
  }

  public static void book(User user, Hotel hotel, int roomNumber, int days) {
    System.out.printf("> Reservando quarto %d para %s por %d dias no %s\n", roomNumber, user.getName(), days, hotel.getName());

    Room room = hotel.getRoom(roomNumber);

    if (room.isOccupied()) {
      throw new IllegalStateException("O quarto desejado está ocupado.");
    }

    if (user.isSmoker() && !room.acceptsSmokers()) {
      throw new IllegalArgumentException("O quarto desejado não aceita fumantes.");
    }

    Reservation reservation = new Reservation(hotel, room, user, days);

    System.out.printf("> A reserva ficou em R$%.2f.\n", reservation.getCost());
    System.out.printf("> O usuário possui R$%.2f.\n", user.getBalance());

    boolean withdrawSuccess = user.withdraw(reservation.getCost());

    if (!withdrawSuccess) {
      throw new IllegalStateException("O usuário não possui fundos para fazer a reserva.");
    }

    room.reserve(reservation);

    System.out.println("> Reserva realizada.");
  }

  public static void cancel(User user, Hotel hotel, int roomNumber) {
    System.out.printf("> Cancelando a reserva de %s no quarto %d do %s.\n", user.getName(), roomNumber, hotel.getName());

    Room room = hotel.getRoom(roomNumber);

    if (!room.isOccupied()) {
      throw new IllegalStateException("O quarto desejado não está ocupado.");
    }

    Reservation reservation = room.getReservation();

    if (reservation.getUser() != user) {
      throw new IllegalArgumentException("O quarto não está reservado por esse cliente.");
    }

    double refund = reservation.getCost() * 0.7;

    System.out.printf("> O reembolso ficou em R$%.2f.\n", refund);

    user.deposit(refund);

    room.free();

    System.out.println("> Cancelamento realizado.");
  }

}
