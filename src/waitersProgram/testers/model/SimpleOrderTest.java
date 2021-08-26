package waitersProgram.testers.model;

import waitersProgram.model.MenuEntry;
import waitersProgram.model.Order;

public class SimpleOrderTest {

	public static void main(String[] args) {
		MenuEntry orderedEntry1 = new MenuEntry("Pasta al pomodoro, 7");
		MenuEntry orderedEntry2 = new MenuEntry("Cotoletta alla milanese, 8");

		Order order1 = new Order(1, orderedEntry1);
		Order order2 = new Order(2, orderedEntry2);

		/**
		 * Tester of method getOrderNum().
		 */
		if (order1.getOrderNum() == 1 && order2.getOrderNum() == 2) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method getTableNum().
		 */
		if (order1.getTableNum() == 1 && order2.getTableNum() == 2) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method getOrderedEntry().
		 */
		if (orderedEntry1.equals(order1.getOrderedEntry())) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method getOrdersCounter().
		 */
		if (order2.getOrdersCounter() == 2) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method toString().
		 */
		if (order2.toString().equals("Order: 2table: 2entry: Cotoletta alla milanese")) {

			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

	}

}
