package testers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import model.Menu;
import model.OrderManager;
import model.Restaurant;

class RestaurantTest {

	Restaurant restaurant = Restaurant.getInstance();
	OrderManager orderManager = OrderManager.getInstance();
	Menu menu = Menu.getInstance("/Progetto-G21/Files/menuFile.txt");
	String tablesFilePath = "Files/tablesFile.txt";
	HashSet<Integer> tables = new HashSet<Integer>();

	/**
	 * Method that clones the HashSet Tables in Restaurant.
	 */
	void cloneHashSet() {
		tables = restaurant.getTables();
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
	 * Tester of method getRestaurantMenu() in Restaurant.java
	 */
	@Test
	void testGetRestaurantMenu() {
		assertEquals(menu, restaurant.getRestaurantMenu());
	}

	/**
	 * Tester of method getOrderManager() in Restaurant.java
	 */
	@Test
	void testGetOrderManager() {
		assertEquals(orderManager, restaurant.getOrderManager());
	}

	/**
	 * Tester of method getTable() in Restaurant.java
	 */
	@Test
	void testGetTable() {
		cloneHashSet();
		assertEquals(tables, restaurant.getTables());
	}

	/**
	 * Method used to add a table at the end of the file.
	 * 
	 */
	@Test
	void testAddTableAtTheEnd() {
		restaurant.addTable(findLastTable() + 1);
		cloneHashSet();
		assertEquals(tables, restaurant.getTables());
	}

	/**
	 * Method used to remove the last table inserted.
	 * 
	 * @param tableToRemove specifies the last entry of the file.
	 */
	@Test
	void testRemoveLastTable() {
		restaurant.removeTable(findLastTable());
		cloneHashSet();
		assertEquals(tables, restaurant.getTables());
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
		restaurant.addTable(rand);
		tables.add(rand);
		assertEquals(tables, restaurant.getTables());
	}

}
