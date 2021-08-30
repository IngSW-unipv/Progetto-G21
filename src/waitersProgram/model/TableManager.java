package waitersProgram.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableManager {

	private static TableManager instance = null;
	private HashSet<Integer> tables; // HashSet because duplicates are not allowed!
	private static final String tablesFilePath = "Files/tablesFile.txt";

	/**
	 * Class constructor method.
	 */
	private TableManager() {
		tables = new HashSet<Integer>(0);

		File tablesFile = new File(tablesFilePath);
		if (tablesFile.exists() == false || tablesFile.isDirectory()) {
			try {
				tablesFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		parseTablesFile();
	}

	/**
	 * Static method that returns a TableManager instance in order to observe
	 * Singleton pattern. It calls the class constructor only if this has not
	 * happened before.
	 * 
	 * @return TableManager instance.
	 */
	public static TableManager getInstance() {
		if (instance == null) {
			instance = new TableManager();
		}
		return instance;
	}

	/**
	 * @return tables' HashSet.
	 */
	public HashSet<Integer> getTables() {
		return tables;
	}

	/**
	 * Method that allows the addition of a new table in tables' HashSet and to
	 * tablesFile.txt via the addTableWithoutCheck method. addTable checks if the
	 * specified tableNum already exists calling checkForTableExistance method. If
	 * true, it creates a new table with tableNum just next the maximum tableNum
	 * contained in the HashSet.
	 * 
	 * @param tableNum specifies the involved tableNum.
	 */
	public void addTable(int tableNum) {
		try {
			checkForTableExistance(tableNum);
			Iterator<Integer> i = tables.iterator();
			int max = 0;

			while (i.hasNext()) {
				int currentTableNum = i.next();
				if (currentTableNum > max) {
					max = currentTableNum;
				}
			}

			addTableWithoutCheck(max + 1);
		} catch (TableDoesNotExistsException e) {
			addTableWithoutCheck(tableNum);
		}
	}

	/**
	 * Method used to add a new table to tables' HashSet and to tablesFile.txt
	 * without checking if the specified table already exists. Used in addTable
	 * method.
	 * 
	 * @param tableNum specifies the involved tableNum.
	 */
	private void addTableWithoutCheck(int tableNum) {

		File tablesFile = new File(tablesFilePath);
		BufferedWriter stream = null;

		try {
			stream = new BufferedWriter(new FileWriter(tablesFile, true));
			stream.append(((Integer) tableNum).toString() + "\n");
			stream.flush();
			stream.close();
			tables.clear();
			parseTablesFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method that allows the removal of a table in the tables' HashSet and from
	 * tablesFile.txt.
	 * 
	 * @param specifies the involved table.
	 */
	public void removeTable(int tableNum) {
		try {
			checkForTableExistance(tableNum);
		} catch (TableDoesNotExistsException e) {
			e.printStackTrace();
		}

		tables.remove(tableNum);

		File inputFile = new File(tablesFilePath);
		File tempFile = new File("Files/tempTablesFile.txt");

		if (tempFile.exists() == false) {
			try {
				tempFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		BufferedReader readStream;
		BufferedWriter writeStream;
		String line = null;

		try {
			readStream = new BufferedReader(new FileReader(inputFile));
			writeStream = new BufferedWriter(new FileWriter(tempFile));

			while ((line = readStream.readLine()) != null) {
				if (tableNum != Integer.parseInt(line)) {
					writeStream.write(line + "\n");
				}
			}

			readStream.close();
			writeStream.close();
			inputFile.delete();

			tempFile.renameTo(new File(tablesFilePath));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that is used to check if a specified table exists.
	 * 
	 * @param tableNum specifies the involved table.
	 * @throws TableDoesNotExistsException.
	 */
	private void checkForTableExistance(int tableNum) throws TableDoesNotExistsException {
		if (tables.contains(tableNum) == false) {
			throw new TableDoesNotExistsException();
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
		try {
			stream = new BufferedReader(new FileReader(tablesFilePath));
			String line = null;
			while ((line = stream.readLine()) != null) {
				checkForTableFormat(line);
				tables.add(Integer.parseInt(line));
			}
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WrongTableEntryFormatException e) {
			e.printStackTrace();
		}

	}
}