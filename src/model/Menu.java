package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Menu class.
 * 
 * The menu contains all the dishes and drinks offered by the restaurant. The
 * items are both stored in a LinkedHashMap (during program runtime), and in a
 * .txt file in order to ensure persistance.
 * 
 * Each line of menuFile.txt must respect "dishName, dishPrice format. The
 * methods for adding and removing an item update both the LinkedHashMap and the
 * .txt file."
 */

public class Menu {

	private LinkedHashMap<Integer, MenuEntry> entries;
	private static Menu instance = null;
	private String menuFilePath;

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
			menuFilePath = path;
			checkForMenuFileExistence();
			parseMenuFile();
		} catch (FileNotFoundException e) {
			menuFilePath = null;
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Static method that returns a Menu instance in order to observe the Singleton
	 * pattern. It calls the class constructor only if this has not happened before.
	 * 
	 * @param path is needed in order to call the constructor.
	 * @return menu instance.
	 */
	public static Menu getInstance(String path) {
		if (instance == null) {
			instance = new Menu(path);
		}
		return instance;
	}

	/**
	 * @return entries LinkedHashMap.
	 */
	public LinkedHashMap<Integer, MenuEntry> getEntriesHashMap() {
		return entries;
	}

	/**
	 * @return entries Collection.
	 */
	public Collection<MenuEntry> getEntriesCollection() {
		return entries.values();
	}

	/**
	 * @param entryKey specifies the key of the MenuEntry to return.
	 * @return MenuEntry object.
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
	 * @param entryKey specifies the key of the involved MenuEntry.
	 * @return entry name String.
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
	 * @param entryKey specifies the key of the involved MenuEntry.
	 * @return entry price.
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
	 * @return menuFilePath String.
	 */
	public String getMenuFilePath() {
		return menuFilePath;
	}

	/**
	 * @param menuFilePath String.
	 */
	public void setMenuFilePath(String menuFilePath) {
		this.menuFilePath = menuFilePath;
	}

	/**
	 * Method that creates a new menu entry after checking its format with
	 * checkForEntryFormat. The method adds the entry both to entries LinkedHashMap
	 * and to menuFile.txt.
	 * 
	 * @param dishEntry represents the new entry to add.
	 */
	public void addMenuEntry(String dishEntry) {
		try {
			checkForEntryFormat(dishEntry);
		} catch (WrongMenuEntryFormatException e) {
			System.err.println(e.getMessage());
		}

		File menuFile = new File(menuFilePath);
		BufferedWriter stream = null;

		try {
			stream = new BufferedWriter(new FileWriter(menuFile, true));
			stream.append(dishEntry);
			stream.flush();
			stream.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		rewriteMenu();
	}

	/**
	 * Method that removes a MenuEntry from menuFile.txt given his "stringed"
	 * representation and calls rewriteMenu() in order to recreate entries
	 * LinkedHashMap.
	 * 
	 * @param dishEntry specifies the entry to be removed in dishName, dishPrice
	 *                  format.
	 * 
	 */
	public void removeMenuEntry(String dishEntry) {
		File inputFile = new File(menuFilePath);
		File tempFile = new File("./tempMenuFile.txt");
		String line = null;
		BufferedReader readBuffer;
		BufferedWriter writeStream;

		try {
			readBuffer = new BufferedReader(new FileReader(inputFile));
			writeStream = new BufferedWriter(new FileWriter(tempFile));
			line = readBuffer.readLine();
			while (line != null) {
				if (dishEntry.equals(line) == false) {
					writeStream.write(dishEntry);
				}
			}

			readBuffer.close();
			writeStream.close();

			tempFile.renameTo(inputFile);
			rewriteMenu();

		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Method that removes a MenuEntry from entries LinkedHashMap given his key
	 * number. The method calls removeMenuEntry.
	 * 
	 * @param entryKey represents the key of the entry to be deleted.
	 */
	public void removeMenuEntry(Integer entryKey) {
		try {
			checkForEntryExistence(entryKey);
			MenuEntry entryToBeRemoved = entries.get(entryKey);
			removeMenuEntry(entryToBeRemoved.toString());
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
	 * Method that clears all the content of entries LinkedHashMap and calls
	 * parseMenuFile to recreate a new menu.
	 */
	public void rewriteMenu() {
		try {
			checkForMenuFileExistence();
			entries.clear();
			parseMenuFile();
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Method used to check if menuFile.txt exists.
	 * 
	 * @throws FileNotFoundException.
	 */
	private void checkForMenuFileExistence() throws FileNotFoundException {
		File menuFile = new File(menuFilePath);
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
	private void checkForEntryFormat(String dishEntry) throws WrongMenuEntryFormatException {
		Pattern p = Pattern.compile("^[a-zA-Z]+, \\\\d{1,5}.{0,1}\\\\d{0,2}");
		Matcher m = p.matcher(dishEntry);
		boolean b = m.matches();

		if (b == false) {
			throw new WrongMenuEntryFormatException();
		}
	}

	/**
	 * This method allows for further batch insertions in the entries'
	 * LinkedHashMap, given a menuFile.txt.
	 */
	private void parseMenuFile() {
		BufferedReader stream = null;
		String line = null;
		int index = 0;

		try {
			checkForMenuFileExistence();
			stream = new BufferedReader(new FileReader(menuFilePath));
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}

		try {
			line = stream.readLine();
			while (line != null) {
				checkForEntryFormat(line);
				MenuEntry entry = new MenuEntry(line);
				index = entries.values().size();
				entries.put(index, entry);
			}
			stream.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (WrongMenuEntryFormatException e) {
			System.err.println("The file specified by " + menuFilePath + " has wrong format!");
		}
	}
}
