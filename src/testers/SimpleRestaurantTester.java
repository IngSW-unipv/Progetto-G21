package testers;

import java.util.HashSet;
import java.util.Iterator;

import model.Menu;
import model.OrderManager;
import model.Restaurant;

public class SimpleRestaurantTester {

	public static void main(String[] args) {

		Restaurant restaurant = Restaurant.getInstance();
		OrderManager orderManager = OrderManager.getInstance();
		Menu menu = Menu.getInstance("/Progetto-G21/Files/menuFile.txt");
		HashSet<Integer> tables = new HashSet<Integer>();
		Iterator<Integer> iterator = null;

		tables = restaurant.getTables();
		int lastTableInsert = 0;

		/**
		 * Tester of method getRestaurantMenu().
		 */
		if (menu.equals(restaurant.getRestaurantMenu())) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method getOrderManager().
		 */
		if (orderManager.equals(restaurant.getOrderManager())) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method getTable().
		 */
		if (tables.equals(restaurant.getTables())) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method addTable().
		 */
		iterator = tables.iterator();
		while (iterator.hasNext()) {
			lastTableInsert = iterator.next();
		}
		restaurant.addTable(lastTableInsert + 1);
		tables.add(lastTableInsert + 1);

		if (tables.equals(restaurant.getTables())) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method removeTable(), removing the last but one table.
		 */
		iterator = tables.iterator();
		while (iterator.hasNext()) {
			lastTableInsert = iterator.next();
		}

		restaurant.removeTable(lastTableInsert - 1);
		tables.remove(lastTableInsert - 1);

		if (tables.equals(restaurant.getTables())) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

	}

}
