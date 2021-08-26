package waitersProgram.testers.model;

import java.util.ArrayList;
import java.util.Iterator;

import waitersProgram.model.Bill;
import waitersProgram.model.MenuEntry;
import waitersProgram.model.Order;
import waitersProgram.model.OrderManager;

/* add -> see -> prepare -> deliver. La sequenzialità va rispettata! */

public class SimpleBillTest {

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

		/**
		 * Tester of method getOrders().
		 */
		if (orders.equals(bill.getOrders())) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method getTableNum().
		 */
		if (bill.getTableNum() == 1) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method getAmount().
		 */
		if (bill.getAmount() == 13.0) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method toString().
		 */
		String string = "Table: 1\n" + "Pasta al pomodoro, 5\n" + "Tiramisù, 8\n" + "Total: 13,00 €";
		if (bill.toString().equals(string)) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method generateBillFile().
		 */
		bill.generateBillFile("C:/Users/didyk/Progetto-G21");

		/**
		 * Tester of method generateBillFile() generating a path not found exception.
		 */
		bill.generateBillFile("C:/Users/WrongPath");
	}

}
