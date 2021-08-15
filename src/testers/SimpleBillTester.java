package testers;

import java.util.ArrayList;
import java.util.Iterator;

import waitersProgram.model.Bill;
import waitersProgram.model.MenuEntry;
import waitersProgram.model.Order;
import waitersProgram.model.OrderManager;

/* add -> see -> prepare -> deliver. La sequenzialità va rispettata! */

public class SimpleBillTester {

	public static void main(String[] args) {
		Order o1 = new Order(1, new MenuEntry("Pasta al pomodoro, 5"));
		Order o2 = new Order(1, new MenuEntry("Tiramisù, 8"));

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

		Bill bill = new Bill(1);
		orders = bill.getOrders();

		Iterator<Order> i = orders.iterator();
		while (i.hasNext()) {
			System.out.println(i.next().toString());
		}

		// Method getOrders() tester -- OK
		if (orders.equals(bill.getOrders())) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		// Method getTableNum() tester -- OK
		if (bill.getTableNum() == 1) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		// Method getAmount() tester -- OK
		if (bill.getAmount() == 13.0) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		// Method toString tester -- OK
		String string = "Table: 1\n" + "Pasta al pomodoro, 5\n" + "Tiramisù, 8\n" + "Total: 13,00 €";
		if (bill.toString().equals(string)) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		// Testing the generation of the bill file -- OK
		bill.generateBillFile("C:/Users/didyk/Progetto-G21");

		// Trying to generate path not found exception -- OK
		bill.generateBillFile("C:/Users/WrongPath");
	}

}
