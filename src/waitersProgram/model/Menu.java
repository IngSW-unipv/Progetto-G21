package waitersProgram.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
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

public class Menu implements MenuInterface {

	private LinkedHashMap<Integer, MenuEntry> entries;
	private static Menu instance = null;
	private static final String menuFilePath = "Files/menuFile.txt";
	private int fileLinesCounter;

	/**
	 * Class constructor method. The method calls checkForMenuFileExistence and
	 * parseMenuFile in order to check if menu.txt file exists and to parse its
	 * content.
	 */
	private Menu() {
		entries = new LinkedHashMap<Integer, MenuEntry>();
		File menuFile = new File(menuFilePath);
		fileLinesCounter = 0;
		if (menuFile.exists() == false || menuFile.isDirectory()) {
			try {
				menuFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		parseMenuFile();
	}

	/**
	 * Static method that returns a Menu instance in order to observe the Singleton
	 * pattern. It calls the class constructor only if this has not happened before.
	 * 
	 * @return menu instance.
	 */
	public static Menu getInstance() {
		if (instance == null) {
			instance = new Menu();
		}
		return instance;
	}

	/**
	 * @return entries LinkedHashMap.
	 */
	@Override
	public LinkedHashMap<Integer, MenuEntry> getEntriesHashMap() {
		return entries;
	}

	/**
	 * @return entries Collection.
	 */
	@Override
	public Collection<MenuEntry> getEntriesCollection() {
		return entries.values();
	}

	/**
	 * 
	 * @param dishName specifies the name of the MenuEntry to return.
	 * @return MenuEntry object (null if MenuEntry doesn't exist).
	 */
	public MenuEntry getSpecificMenuEntry(String dishName) {
		Collection<MenuEntry> menuCollection = getEntriesCollection();
		Iterator<MenuEntry> iterator = menuCollection.iterator();
		while (iterator.hasNext()) {
			MenuEntry entry = iterator.next();
			if (entry.getDishName() == dishName) {
				return entry;
			}
		}
		return null;
	}

	/**
	 * @param entryKey specifies the key of the MenuEntry to return.
	 * @return MenuEntry object.
	 */
	@Override
	public MenuEntry getSpecificMenuEntry(Integer entryKey) {
		try {
			checkForEntryExistence(entryKey);
			return entries.get(entryKey);
		} catch (EntryDoesNotExistException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param entryKey specifies the key of the involved MenuEntry.
	 * @return entry name String.
	 */
	@Override
	public String getSpecificMenuEntryName(Integer entryKey) {
		try {
			checkForEntryExistence(entryKey);
			return entries.get(entryKey).getDishName();
		} catch (EntryDoesNotExistException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param entryKey specifies the key of the involved MenuEntry.
	 * @return entry price.
	 */
	@Override
	public double getSpecificMenuEntryPrice(Integer entryKey) {
		try {
			checkForEntryExistence(entryKey);
			return entries.get(entryKey).getDishPrice();
		} catch (EntryDoesNotExistException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * @return menuFilePath String.
	 */
	@Override
	public String getMenuFilePath() {
		return menuFilePath;
	}

	/**
	 * Method that creates a new menu entry after checking its format with
	 * checkForEntryFormat. The method adds the entry both to entries LinkedHashMap
	 * and to menuFile.txt.
	 * 
	 * @param dishEntry represents the new entry to add.
	 */
	@Override
	public void addMenuEntry(String dishEntry) {
		try {
			checkForEntryFormat(dishEntry);
		} catch (WrongMenuEntryFormatException e) {
			e.printStackTrace();
		}

		File menuFile = new File(menuFilePath);
		BufferedWriter stream = null;

		try {
			stream = new BufferedWriter(new FileWriter(menuFile, true));
			stream.append(dishEntry + "\n");
			stream.flush();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
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
	@Override
	public void removeMenuEntry(String dishEntry) {
		File inputFile = new File(menuFilePath);
		File tempFile = new File("Files/tempMenuFile.txt");

		if (tempFile.exists() == false) {
			try {
				tempFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		BufferedReader readStream = null;
		BufferedWriter writeStream = null;
		String line = null;

		try {
			readStream = new BufferedReader(new FileReader(inputFile));
			writeStream = new BufferedWriter(new FileWriter(tempFile));

			while ((line = readStream.readLine()) != null) {
				if ((dishEntry.trim()).equals(line.trim()) == false) {
					writeStream.write(line + "\n");
				}
			}

			readStream.close();
			writeStream.close();
			inputFile.delete();

			tempFile.renameTo(new File(menuFilePath));
			rewriteMenu();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that removes a MenuEntry from entries LinkedHashMap given his key
	 * number. The method calls removeMenuEntry.
	 * 
	 * @param entryKey represents the key of the entry to be deleted.
	 */
	@Override
	public void removeMenuEntry(Integer entryKey) {
		try {
			checkForEntryExistence(entryKey);
			MenuEntry entryToBeRemoved = entries.get(entryKey);
			String toBeRemoved = entryToBeRemoved.toString();
			removeMenuEntry(toBeRemoved);
		} catch (EntryDoesNotExistException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method used to modify MenuEntry's name.
	 * 
	 * @param entryKey     specifies the key of the involved MenuEntry.
	 * @param newEntryName specifies the new name.
	 * 
	 */
	@Override
	public void editSpecificMenuEntry(Integer entryKey, String newEntryName) {
		try {
			checkForEntryExistence(entryKey);
			MenuEntry entry = entries.get(entryKey);
			String entryPrice = Double.toString(entry.getDishPrice());
			removeMenuEntry(entryKey);
			addMenuEntry(newEntryName + ", " + entryPrice);
		} catch (EntryDoesNotExistException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method used to modify MenuEntry's price.
	 * 
	 * @param entryKey specifies the key of the involved MenuEntry.
	 * @param newPrice specifies the new price.
	 * 
	 */
	@Override
	public void editSpecificMenuEntry(Integer entryKey, double newPrice) {
		try {
			checkForEntryExistence(entryKey);
			MenuEntry entry = entries.get(entryKey);
			String entryName = entry.getDishName();
			removeMenuEntry(entryKey);
			addMenuEntry(entryName + ", " + Double.toString(newPrice));
		} catch (EntryDoesNotExistException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that clears all the content of entries LinkedHashMap and calls
	 * parseMenuFile to recreate a new menu.
	 */
	private void rewriteMenu() {
		entries.clear();
		parseMenuFile();
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
		Pattern p = Pattern.compile("^[A-Za-z0-9????????????????????????'.?!:;, ]*, \\d{1,5}\\.{0,1}\\d{0,2}$");
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
	public void parseMenuFile() {
		fileLinesCounter = 0;
		BufferedReader stream = null;
		try {
			stream = new BufferedReader(new FileReader(menuFilePath));
			int index = 0;
			String line = null;
			while ((line = stream.readLine()) != null) {
				fileLinesCounter++;
				checkForEntryFormat(line);
				MenuEntry entry = new MenuEntry(line);
				index = entries.values().size();
				entries.put(index, entry);
			}
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WrongMenuEntryFormatException e) {
			e.printStackTrace();
			System.err.println("The file specified by " + menuFilePath + " has wrong format!");
		}
	}

	/** @return tablesFile fileLinesCounter. */
	public int getFileLinesCounter() {
		return fileLinesCounter;
	}
}
