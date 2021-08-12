package testers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import model.Bill;
import model.MenuEntry;
import model.Order;
import model.OrderManager;

class BillTest {
	Order o1 = new Order(1, new MenuEntry("Pasta al pomodoro, 5"));
	Order o2 = new Order(1, new MenuEntry("Tiramisù, 8"));

	ArrayList<Order> orders = null;
	OrderManager orderManager = OrderManager.getInstance();

	Bill bill = null;

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

	@Test
	void testGetOrders() {
		addOrders();
		assertEquals(orders, bill.getOrders());
	}

	@Test
	void testGetTableNum() {
		addOrders();
		assertEquals(1, bill.getTableNum());
	}

	@Test
	void testGetAmount() {
		addOrders();
		assertEquals(13.0, bill.getAmount());
	}

	@Test
	void testToString() {
		addOrders();
		String string = "Table: 1\n" + "Pasta al pomodoro, 5\n" + "Tiramisù, 8\n" + "Total: 13,00 €";
		assertEquals(string, bill.toString());

	}

}
