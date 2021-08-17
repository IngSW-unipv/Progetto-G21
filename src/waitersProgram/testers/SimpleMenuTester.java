package waitersProgram.testers;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import waitersProgram.model.Menu;
import waitersProgram.model.MenuEntry;

public class SimpleMenuTester {

	public static void main(String[] args) {
		File menuFile = new File("Files/menuFile.txt");
		Menu menu = Menu.getInstance();

		if (menuFile.exists()) {
			menuFile.delete();
		}

		menu.addMenuEntry("Pasta al pomodoro, 3");
		menu.addMenuEntry("Pasta al pesto, 3");
		menu.addMenuEntry("Tagliata di manzo, 8");
		menu.addMenuEntry("Pizza margherita, 3.5");
		menu.addMenuEntry("Tiramisù, 5");
		menu.addMenuEntry("Pastiera napoletana, 4");

		/**
		 * Tester of method getSpecificMenuEntry().
		 */
		if (menu.getSpecificMenuEntry(0).toString().equals("Pasta al pomodoro, 3")) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method getSpecificMenuEntryName().
		 */
		if (menu.getSpecificMenuEntryName(0).toString().equals("Pasta al pomodoro")) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method getSpecificMenuEntryPrice().
		 */
		if (menu.getSpecificMenuEntryPrice(0) == 3) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method getMenuFilePath().
		 */
		if (menu.getMenuFilePath().equals("Files/menuFile.txt")) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method addMenuEntry().
		 */
		String dishEntry = "Pollo con patatine, 10";
		menu.addMenuEntry(dishEntry);

		if (menu.getSpecificMenuEntry(menu.getFileLinesCounter() - 1).toString().equals(dishEntry.toString())) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method removeMenuEntry(). -- OK
		 */

		menu.removeMenuEntry("Pastiera napoletana, 4");
		menu.removeMenuEntry("Pasta al pesto, 3");
		menu.removeMenuEntry(0);

		/**
		 * Catching the exception
		 */
		menu.removeMenuEntry("Pastiera napoletana, 4");
		menu.removeMenuEntry("Pastiera napoletana, 4");
	}

	public static void showMenu(Menu menu) {
		Collection<MenuEntry> menuCollection = menu.getEntriesCollection();
		Iterator<MenuEntry> i = menuCollection.iterator();
		while (i.hasNext()) {
			System.out.println(i.next().toString());
		}
		System.out.println("\n");
	}

}
