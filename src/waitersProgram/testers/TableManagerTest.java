package waitersProgram.testers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import waitersProgram.model.TableManager;

class TableManagerTest {

	TableManager tableManager = TableManager.getInstance();
	HashSet<Integer> tables = new HashSet<Integer>();

	/**
	 * Method that clones the HashSet Tables in Restaurant.
	 */
	void cloneHashSet() {
		tables = tableManager.getTables();
	}

	/**
	 * Method that finds the last table inserted.
	 * 
	 * @return tableToRemove, which specifies the last table in the file.
	 */
	@Test
	int findLastTable() {
		int tableToRemove = 0;
		cloneHashSet();
		Iterator<Integer> iterator = tables.iterator();
		while (iterator.hasNext()) {
			tableToRemove = iterator.next();
		}
		return tableToRemove;
	}

	/**
	 * Tester of method getTable() in Restaurant.java
	 */
	@Test
	void testGetTable() {
		cloneHashSet();
		assertEquals(tables, tableManager.getTables());
	}

	/**
	 * Method used to add a table at the end of the file.
	 * 
	 */
	@Test
	void testAddTableAtTheEnd() {
		tableManager.addTable(findLastTable() + 1);
		cloneHashSet();
		assertEquals(tables, tableManager.getTables());
	}

	/**
	 * Method used to remove the last table inserted.
	 * 
	 * @param tableToRemove specifies the last entry of the file.
	 */
	@Test
	void testRemoveLastTable() {
		tableManager.removeTable(findLastTable());
		cloneHashSet();
		assertEquals(tables, tableManager.getTables());
	}

	/**
	 * Method used to add a random table between 1 and 31. If the number extracted
	 * is already in the file it adds one table at the end of the HashSet.
	 * 
	 * @param rand generates the random variable.
	 */
	@Test
	void testAddRandomTable() {
		cloneHashSet();
		int rand = (int) Math.floor(Math.random() * (31));
		tableManager.addTable(rand);
		tables.add(rand);
		assertEquals(tables, tableManager.getTables());
	}

}
