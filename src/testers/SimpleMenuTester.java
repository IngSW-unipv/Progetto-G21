package testers;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import waitersProgram.model.Menu;
import waitersProgram.model.MenuEntry;

public class SimpleMenuTester {

	public static void main(String[] args) {

		File menuFile = new File("Files/menuFile.txt");

		if (menuFile.exists()) {
			menuFile.delete();
		}

		Menu menu = Menu.getInstance();

		showMenu(menu);

		menu.addMenuEntry("Pasta al pomodoro, 3");
		menu.addMenuEntry("Pasta al pesto, 3");
		menu.addMenuEntry("Tagliata di manzo, 8");
		menu.addMenuEntry("Pizza margherita, 3.5");
		menu.addMenuEntry("Tiramisù, 5");
		menu.addMenuEntry("Pastiera napoletana, 4");

		showMenu(menu);

		menu.removeMenuEntry(0);

		showMenu(menu);

		menu.removeMenuEntry("Tagliata di manzo, 8");

		showMenu(menu);
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
