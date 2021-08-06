package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Restaurant class. Is used to unify all the other model classes in order
 * to represent a real restaurant.
 */
public class Restaurant {

	private static Restaurant instance = null;
	private Menu restaurantMenu;
	private OrderManager orderManager;
	private HashSet<Integer> tables;

	String menuFilePath = "./menuFile.txt";
	String tablesFilePath = "./tablesFile.txt";

	/**
	 * Class constructor method.
	 */
	private Restaurant() {
		restaurantMenu = Menu.getInstance(menuFilePath);
		orderManager = OrderManager.getInstance();
		tables = new HashSet<Integer>(0);
		parseTablesFile();
	}

	/**
	 * Static method that returns a Restaurant instance in order to observe
	 * Singleton pattern. It calls the class constructor only if this has not
	 * happened before.
	 * 
	 * @return Restaurant instance.
	 */
	public static Restaurant getInstance() {
		if (instance == null) {
			instance = new Restaurant();
		}
		return instance;
	}

	/**
	 * @return Menu instance.
	 */
	public Menu getRestaurantMenu() {
		return restaurantMenu;
	}

	/**
	 * @return orderManager instance.
	 */
	public OrderManager getOrderManager() {
		return orderManager;
	}

	/**
	 * @return tables' HashSet.
	 */
	public HashSet<Integer> getTables() {
		return tables;
	}

	/**
	 * Method that allows the addition of a new table in the tables' ArrayList and
	 * in tablesFile.txt.
	 */
	public void addTable() {
		int initialSize = tables.size();
		tables.add(initialSize + 1);

		File tablesFile = new File(tablesFilePath);
		BufferedWriter stream = null;

		try {
			stream = new BufferedWriter(new FileWriter(tablesFile, true));
			stream.append(((Integer) (initialSize + 1)).toString());
			stream.flush();
			stream.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Method that allows the removal of a table in the tables' ArrayList and from
	 * tablesFile.txt.
	 * 
	 * @param specifies the involved table.
	 */
	public void removeTable(int tableNum) {
		tables.remove(tableNum);
		File inputFile = new File(tablesFilePath);
		File tempFile = new File("./tempTablesFile.txt");
		String line = null;
		BufferedReader readBuffer;
		BufferedWriter writeStream;

		try {
			readBuffer = new BufferedReader(new FileReader(inputFile));
			writeStream = new BufferedWriter(new FileWriter(tempFile));
			line = readBuffer.readLine();
			while (line != null) {
				if (tableNum != Integer.parseInt(line)) {
					writeStream.write(line);
				}
			}

			readBuffer.close();
			writeStream.close();
			tempFile.renameTo(inputFile);

		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Method used to check if tablesFile.txt exists.
	 *
	 * @throws FileNotFoundException.
	 */
	private void checkForTablesFileExistance() throws FileNotFoundException {
		File tablesFile = new File(tablesFilePath);
		if (tablesFile.exists() == false || tablesFile.isDirectory() == true) {
			throw new FileNotFoundException();
		}
	}

	/**
	 * Method used to check if a "stringed" table follows the pattern.
	 * 
	 * @param tableEntry specifies the String to check.
	 * @throws WrongTableEntryFormatException.
	 */
	private void checkForTableFormat(String tableEntry) throws WrongTableEntryFormatException {
		Pattern p = Pattern.compile("\\d{1,3}");
		Matcher m = p.matcher(tableEntry);
		boolean b = m.matches();

		if (b == false) {
			throw new WrongTableEntryFormatException();
		}
	}

	/**
	 * This method allows for further batch insertions in the tables' HashSet, given
	 * a tablesFile.txt.
	 */
	public void parseTablesFile() {
		BufferedReader stream = null;
		String line = null;

		try {
			checkForTablesFileExistance();
			stream = new BufferedReader(new FileReader(tablesFilePath));
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}

		try {
			line = stream.readLine();
			while (line != null) {
				checkForTableFormat(line);
				tables.add(Integer.parseInt(line));
			}
			stream.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (WrongTableEntryFormatException e) {
			System.err.println(e.getMessage());
		}

	}

}
