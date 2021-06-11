package com.unicamp.mc322.lab10;

import com.unicamp.mc322.lab10.person.Customer;
import com.unicamp.mc322.lab10.person.Deliveryman;
import com.unicamp.mc322.lab10.restaurant.Restaurant;
import com.unicamp.mc322.lab10.restaurant.discount.Discount;
import com.unicamp.mc322.lab10.restaurant.discount.DiscountType;
import com.unicamp.mc322.lab10.restaurant.order.Order;
import com.unicamp.mc322.lab10.restaurant.order.OrderState;
import com.unicamp.mc322.lab10.restaurant.order.OrderType;
import com.unicamp.mc322.lab10.restaurant.order.item.Item;
import com.unicamp.mc322.lab10.util.Position;

public class Runner {
	public static void main(String[] args) {
		// Idealmente não há código aqui
		// entretanto, irei implementar a rotina de testes
		// aqui para evitar a criação de uma função separada
		// somente pra isso, que não faz parte do código real.
		
		Pidao app = new Pidao();

		// O aplicativo permite cadastrar vários restaurantes.
		// Os dados são: nome, CNPJ, e posição no mapa.
		Restaurant restA = app.createRestaurant("Restaurante A", "92.625.887/0001-21", new Position(30, 20));
		Restaurant restB = app.createRestaurant("Restaurante B", "85.935.006/0001-03", new Position(-40, 50));

		// O aplicativo permite cadastrar clientes e entregadores. 

		// O cliente possui nome, CPF e endereço de entrega.
		Customer custA = app.createCustomer("Elisabete Vasconcelos Maior", "146.058.760-06", new Position(10, 20));
		Customer custB = app.createCustomer("Arnaldo Alvim Canela", "637.951.520-89", new Position(30, -50));

		// O entregador possui nome e CPF.
		Deliveryman deliA = app.createDeliveryman("Piedade Magalhães Soeiro", "384.182.290-80");
		Deliveryman deliB = app.createDeliveryman("Angelina Leal Lisboa", "008.937.570-04");

		// Os restaurantes tem uma lista de entregadores que ficam disponíveis para realizar as entregasdaquele restaurante.
		restA.addDeliveryman(deliA);
		restB.addDeliveryman(deliB);

		// Cada item contém nome, preço e um identificador alfanumérico de 5 dígitos.
		Item hamburguer = new Item("HMBGR", "Hamburguer", 15.50);
		Item pizza = new Item("PIZZA", "Pizza", 40.00);
		Item milkshake = new Item("MLSHK", "Milkshake", 8.90);

		// O aplicativo permite adicionar e remover lanches/pratos ao cardápio dos restaurantes.
		restA.addItem(hamburguer);
		restA.addItem(pizza);
		restA.removeItem("HMBGR");

		restB.addItem(hamburguer);
		restB.addItem(milkshake);

		// Um pedido pode ser para entrega ou para retirada.
		// Caso seja para entrega, o sistema aloca elepara um dos entregadores do restaurante.
		Order orderA = restA.createOrder(OrderType.DELIVERY, custA);
		orderA.addItem("PIZZA");

		Order orderB = restB.createOrder(OrderType.PICKUP, custB);
		orderB.addItem("HMBGR", 2);
		orderB.addItem("MLSHK");

		// O aplicativo permite definir (e remover) um desconto temporário para itens específicos do cardápio, que pode ser em porcentagem do preço base ou em valor fixo.
		restB.addItemDiscount("MLSHK", new Discount(15, DiscountType.PERCENTAGE));

		// Finalizando a etapa de pedidos
		orderA.setState(OrderState.IN_PREPARATION);
		orderB.setState(OrderState.IN_PREPARATION);

		// Imagine aqui uma lógica de preparação.
		orderA.setState(OrderState.READY);
		orderB.setState(OrderState.READY);
		
		// Imagine aqui uma lógica de entrega/retirada.
		deliA.finishDelivery();
		custB.pickup(orderB);

		// Para cada pedido é permitido o cliente avaliar os lanches, restaurantes e entregadores.
		// A avaliação é feita com estrelas (de 0 a 5) e eventualmente comentários.
		orderA.reviewRestaurant(5);
		orderB.reviewRestaurant(4, "A comida não é tão saborosa.");

		orderA.reviewDeliveryman(5, "Super amigável.");

		orderA.reviewItem(5, pizza);
		orderB.reviewItem(4, "O hamburguer poderia vir mais mal-passado.", hamburguer);
		orderB.reviewItem(5, "Adorei a promoção!", milkshake);

		// O sistema é capaz de imprimir um resumo de todos os pedidos.
		app.printOrderSummary();

		// O sistema é capaz de imprimir o cardápio completo por restaurante.
		restA.printMenu();
		restB.printMenu();

		// O sistema é capaz de imprimir um resumo das avaliações de entregadores, restaurantes e lanches, mostrando a nota média das avaliações.
		app.printDeliverymenReviewSummary();
		app.printRestaurantReviewSummary();
		app.printItemReviewSummary();
	}
}
