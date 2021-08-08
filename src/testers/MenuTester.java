package testers;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import model.Menu;
import model.MenuEntry;

public class MenuTester {

	public static void main(String[] args) {

		File menuFile = new File("Files/menuFile.txt");

		if (menuFile.exists() == false) {
			try {
				menuFile.createNewFile();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}

		Menu menu = Menu.getInstance(menuFile.getAbsolutePath());

		menu.removeMenuEntry(0);

		Collection<MenuEntry> menuCollection = menu.getEntriesCollection();

		Iterator<MenuEntry> i = menuCollection.iterator();

		while (i.hasNext()) {
			System.out.println(i.next().toString());
		}

	}

}
