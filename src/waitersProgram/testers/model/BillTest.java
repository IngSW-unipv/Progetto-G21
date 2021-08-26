package waitersProgram.testers.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import waitersProgram.model.Bill;
import waitersProgram.model.MenuEntry;
import waitersProgram.model.Order;
import waitersProgram.model.OrderManager;

class BillTest {
	Order o1 = new Order(1, new MenuEntry("Pasta al pomodoro, 5"));
	Order o2 = new Order(1, new MenuEntry("Tiramisù, 8"));

	ArrayList<Order> orders = null;
	OrderManager orderManager = OrderManager.getInstance();

	Bill bill = null;

	/**
	 * Simple method that adds two orders (o1, o2) in the table 1 and creates his
	 * bill.
	 * 
	 * @return ArrayList of orders.
	 */
	void addOrders() {
		orderManager.addOrder(o1);
		orderManager.addOrder(o2);
		orderManager.seeOrderToNotPrepared(o1);
		orderManager.seeOrderToNotPrepared(o2);
		orderManager.prepareOrder(o1);
		orderManager.prepareOrder(o2);
		orderManager.deliverOrder(o1);
		orderManager.deliverOrder(o2);

		bill = new Bill(1);
		orders = bill.getOrders();
		Iterator<Order> i = orders.iterator();
		while (i.hasNext()) {
			System.out.println(i.next().toString());
		}

	}

	/**
	 * Tester of method getOrders().
	 */
	@Test
	void testGetOrders() {
		addOrders();
		assertEquals(orders, bill.getOrders());
	}

	/**
	 * Tester of method getTableNum().
	 */
	@Test
	void testGetTableNum() {
		addOrders();
		assertEquals(1, bill.getTableNum());
	}

	/**
	 * Tester of method getAmount().
	 */
	@Test
	void testGetAmount() {
		addOrders();
		assertEquals(13.0, bill.getAmount());
	}

	/**
	 * Tester of method toString().
	 */
	@Test
	void testToString() {
		addOrders();
		String string = "Table: 1\n" + "Pasta al pomodoro, 5\n" + "Tiramisù, 8\n" + "Total: 13,00 €";
		assertEquals(string, bill.toString());
	}

}
