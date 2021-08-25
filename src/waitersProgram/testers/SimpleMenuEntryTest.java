package waitersProgram.testers;

import waitersProgram.model.MenuEntry;

public class SimpleMenuEntryTest {

	public static void main(String[] args) {
		MenuEntry entry = new MenuEntry("Pasta alla carbonara", 7.50);

		/**
		 * Tester of method getDishName().
		 */
		if (entry.getDishName().equals("Pasta alla carbonara")) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method getDishPrice().
		 */
		if (entry.getDishPrice() == 7.50) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method EditEntryName().
		 */
		entry.editEntryName("Pasta al pesto");

		if (entry.getDishName().equals("Pasta al pesto")) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method EditEntryPrice().
		 */
		entry.editEntryPrice(6.50);

		if (entry.getDishPrice() == 6.50) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}
	}

}
