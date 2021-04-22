package com.unicamp.mc322.lab04;

import com.unicamp.mc322.lab04.menu.DiscountType;
import com.unicamp.mc322.lab04.menu.MenuItem;
import com.unicamp.mc322.lab04.order.Order;
import com.unicamp.mc322.lab04.user.User;

public class Runner {

	public static void main(String[] args) {
		Pidao pidaoApp = new Pidao("MARAMBAR", "123.456.789-10", 10, 2);

		User user1 = pidaoApp.addUser("Marcos Paulo", "123.789.643-11", 1, 2);
		User user2 = pidaoApp.addUser("Pereira", "123.789.643-12", 8, 4);       // CPF alterado para diferenciar usuários

		MenuItem cuscuz = new MenuItem("CCZ00", "Cuscuz com ovo", 10.00);	
		MenuItem macaxeira = new MenuItem("MXCOS", "Macaxeira com costela no bafo", 15.00);
		MenuItem coxinhaFrango = new MenuItem("CXFRA", "Coxinha de frango", 8.00);

		pidaoApp.addToMenu(cuscuz);
		pidaoApp.addToMenu(macaxeira);
		pidaoApp.addToMenu(coxinhaFrango);

		pidaoApp.applyDiscount("CCZ00", 10, DiscountType.PERCENTAGE);

		Order p1 = new Order(user1);
		p1.addItem(cuscuz);
		p1.addItem(macaxeira);
		pidaoApp.order(p1);

		Order p2 = new Order(user2);
		p2.addItem(coxinhaFrango);
		p2.addItem(coxinhaFrango);
		pidaoApp.order(p2);

		Order p3 = new Order(user2);
		p3.addItem(coxinhaFrango);
		p3.addItem(coxinhaFrango);
		pidaoApp.order(p3);

		pidaoApp.printMenu();

    System.out.println(); // Adiciona uma linha branca entre impressões

		pidaoApp.printOrders();
	}

}
