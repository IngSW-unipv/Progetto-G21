package testers;

import java.util.ArrayList;

import model.Bill;
import model.MenuEntry;
import model.Order;
import model.OrderManager;

public class SimpleBillTester {

	public static void main(String[] args) {
		Order o1 = new Order(1, new MenuEntry("Pasta al pomodoro, 5"));
		Order o2 = new Order(1, new MenuEntry("Tiramisù, 8"));
		Bill bill = new Bill(1);

		ArrayList<Order> orders = new ArrayList<Order>();
		OrderManager orderManager = OrderManager.getInstance();
		orderManager.seeOrderToNotPrepared(o1);
		orderManager.seeOrderToNotPrepared(o2);
		orderManager.addOrder(o1);
		orderManager.addOrder(o2);
		orderManager.prepareOrder(o1);
		orderManager.prepareOrder(o2);
		orderManager.deliverOrder(o1);
		orderManager.deliverOrder(o2);

		orders.add(o1);
		orders.add(o2);

		System.out.println(orders);
		System.out.println(bill.getOrders());

		if (orders.equals(bill.getOrders())) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}
	}

}
