package testers;

import java.util.ArrayList;
import java.util.Iterator;

import model.Bill;
import model.MenuEntry;
import model.Order;
import model.OrderManager;

/* add -> see -> prepare -> deliver. La sequenzialità va rispettata! */

public class SimpleBillTester {

	public static void main(String[] args) {
		Order o1 = new Order(1, new MenuEntry("Pasta al pomodoro, 5"));
		Order o2 = new Order(1, new MenuEntry("Tiramisù, 8"));
		// Bill bill = new Bill(1);

		ArrayList<Order> orders = null;
		OrderManager orderManager = OrderManager.getInstance();
		orderManager.addOrder(o1);
		orderManager.addOrder(o2);
		orderManager.seeOrderToNotPrepared(o1);
		orderManager.seeOrderToNotPrepared(o2);
		orderManager.prepareOrder(o1);
		orderManager.prepareOrder(o2);
		orderManager.deliverOrder(o1);
		orderManager.deliverOrder(o2);

		// orders.add(o1);
		// orders.add(o2);

		Bill bill = new Bill(1);
		orders = bill.getOrders();

		Iterator<Order> i = orders.iterator();
		while (i.hasNext()) {
			System.out.println(i.next().toString());
		}

		/*
		 * System.out.println(orders); System.out.println(bill.getOrders());
		 * 
		 * if (orders.equals(bill.getOrders())) { System.out.println("OK"); } else {
		 * System.out.println("TEST FAILED"); }
		 */
	}

}
