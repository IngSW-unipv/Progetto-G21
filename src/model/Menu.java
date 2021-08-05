package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * The Menu class. The menu contains all the dishes and drinks offered by the
 * restaurant, is saved in a menu.txt file.
 */

public class Menu {

	private LinkedHashMap<Integer, MenuEntry> entries;
	private static Menu instance = null;

	/**
	 * Class constructor method. The method calls checkForMenuFileExistence and
	 * parseMenuFile in order to check if menu.txt file exists and to parse its
	 * content.
	 * 
	 * @param path specifies the location of menu.txt file.
	 */
	private Menu(String path) {
		entries = new LinkedHashMap<Integer, MenuEntry>();
		try {
			checkForMenuFileExistence(path);
			parseMenuFile(path);
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Static method that returns a Menu instance in order to observe the Singleton
	 * pattern. It calls the class constructor only if this has not happened before.
	 * 
	 * @param path is needed in order to call the constructor.
	 */
	public static Menu getInstance(String path) {
		if (instance == null) {
			instance = new Menu(path);
		}
		return instance;
	}

	/**
	 * Simple entries' hashmap getter.
	 * 
	 * @return the entries' linkedHashMap.
	 */
	public LinkedHashMap<Integer, MenuEntry> getEntriesHashMap() {
		return entries;
	}

	/**
	 * Simple entries' collection getter.
	 * 
	 * @return a Collection that contains all MenuEntry objects.
	 */
	public Collection<MenuEntry> getEntriesCollection() {
		return entries.values();
	}

	/**
	 * Simple MenuEntry getter.
	 * 
	 * @param entryKey specifies the key of the MenuEntry to return.
	 * @return MenuEntry.
	 */
	public MenuEntry getSpecificMenuEntry(Integer entryKey) {
		try {
			checkForEntryExistence(entryKey);
			return entries.get(entryKey);
		} catch (EntryDoesNotExistException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Simple MenuEntry's name getter.
	 * 
	 * @param entryKey specifies the key of the involved MenuEntry.
	 * @return MenuEntry's name.
	 */
	public String getSpecificMenuEntryName(Integer entryKey) {
		try {
			checkForEntryExistence(entryKey);
			return entries.get(entryKey).getDishName();
		} catch (EntryDoesNotExistException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Simple MenuEntry's price getter.
	 * 
	 * @param entryKey specifies the key of the involved MenuEntry.
	 * @return MenuEntry's price.
	 */
	public double getSpecificMenuEntryPrice(Integer entryKey) {
		try {
			checkForEntryExistence(entryKey);
			return entries.get(entryKey).getDishPrice();
		} catch (EntryDoesNotExistException e) {
			System.err.println(e.getMessage());
			return 0;
		}
	}

	/**
	 * Method that creates a new menu entry after checking its format with
	 * checkForEntryFormat.
	 * 
	 * Not finished yet! We must add some code in order to change menuFile.txt!
	 * 
	 * @param dishEntry represents the new entry to add.
	 */
	public void addMenuEntry(String dishEntry) throws WrongEntryFormatException {
		try {
			checkForEntryFormat(dishEntry);
		} catch (WrongEntryFormatException e) {
			System.err.println(e.getMessage());
		}
		entries.put(entries.values().size(), new MenuEntry(dishEntry));
	}

	/**
	 * Method that removes a menu entry from entries hashMap given his key number.
	 * 
	 * Not finished yet! We must add some code in order to change menuFile.txt!
	 * 
	 * @param entryKey represents the key of the entry to be deleted.
	 */
	public void removeMenuEntry(Integer entryKey) {
		try {
			checkForEntryExistence(entryKey);
			entries.remove(entryKey);
		} catch (EntryDoesNotExistException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Method used to modify MenuEntry's name.
	 * 
	 * @param entryKey     specifies the key of the involved MenuEntry.
	 * @param newEntryName specifies the new name.
	 * 
	 */
	public void editSpecificMenuEntry(Integer entryKey, String newEntryName) {
		try {
			checkForEntryExistence(entryKey);
			entries.get(entryKey).editEntry(newEntryName);
		} catch (EntryDoesNotExistException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Method used to modify MenuEntry's price.
	 * 
	 * @param entryKey specifies the key of the involved MenuEntry.
	 * @param newPrice specifies the new price.
	 * 
	 */
	public void editSpecificMenuEntry(Integer entryKey, double newPrice) {
		try {
			checkForEntryExistence(entryKey);
			entries.get(entryKey).editEntry(newPrice);
		} catch (EntryDoesNotExistException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Method that deletes all current menu entries and calls parseMenuFile to
	 * recreate a new menu.
	 * 
	 * @param path specifies the file to parse.
	 */
	public void rewriteMenu(String path) {
		try {
			checkForMenuFileExistence(path);
			entries.clear();
			parseMenuFile(path);
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Method used to check if menuFile.txt exists.
	 * 
	 * @param path specifies the file to check.
	 * @throws FileNotFoundException.
	 */
	private void checkForMenuFileExistence(String path) throws FileNotFoundException {
		File menuFile = new File(path);
		if (menuFile.exists() == false || menuFile.isDirectory() == true) {
			throw new FileNotFoundException();
		}
	}

	/**
	 * Method used to check if a specified MenuEntry exists.
	 * 
	 * @param entryKey specifies the key of the involved MenuEntry.
	 * @throws EntryDoesNotExistsException.
	 */
	private void checkForEntryExistence(Integer entryKey) throws EntryDoesNotExistException {
		if (entries.containsKey(entryKey) == false) {
			throw new EntryDoesNotExistException();
		}
	}

	/**
	 * Method used to check if a "stringed" MenuEntry follows the "dishName,
	 * dishPrice" pattern.
	 * 
	 * @param dishEntry specifies the String to check.
	 * @throws WrongEntryFormatException.
	 */
	private void checkForEntryFormat(String dishEntry) throws WrongEntryFormatException {
		StringTokenizer st = new StringTokenizer(dishEntry);
		int counter = st.countTokens();

		if (counter != 2) {
			throw new WrongEntryFormatException();
		}

		String[] buffer = new String[2];
		buffer[0] = st.nextToken();
		buffer[1] = st.nextToken();

		if (buffer[0].charAt(buffer[0].length()) != ',') {
			throw new WrongEntryFormatException();
		}

		try {
			Double.parseDouble(buffer[1]);
		} catch (NumberFormatException e) {
			throw new WrongEntryFormatException();
		}
	}

	/**
	 * This method allows for further batch insertions in the entries' hashMap,
	 * given a menuFile.txt.
	 * 
	 * @param path specifies the file to parse.
	 */
	private void parseMenuFile(String path) {
		try {
			checkForMenuFileExistence(path);
			File menuFile = new File(path);
			Scanner scanner = new Scanner(menuFile);
			int index = 0;

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				checkForEntryFormat(line);
				MenuEntry entry = new MenuEntry(line);
				index = entries.values().size();
				entries.put(index, entry);
			}

			scanner.close();

		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (WrongEntryFormatException e) {
			System.err.println("The file specified by " + path + "has wrong format!");
		}
	}
}
