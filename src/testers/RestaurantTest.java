package testers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

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

	void removeAllTables() {
		for (int i = 1; i < restaurant.getTables().size(); i++) {
			restaurant.removeTable(i);
		}
	}

	void addHashSetTables(int j) {
		for (int i = 1; i <= j; i++) {
			tables.add(i);
		}
	}

	@Test
	void testGetRestaurantMenu() {
		assertEquals(menu, restaurant.getRestaurantMenu());
	}

	@Test
	void testGetOrderManager() {
		assertEquals(orderManager, restaurant.getOrderManager());
	}

	@Test
	void testGetTable() {
		addHashSetTables(3);
		assertEquals(tables, restaurant.getTables());
	}

	@Test
	void testAddTable() {
		restaurant.addTable(4);
		addHashSetTables(4);

		assertEquals(tables, restaurant.getTables());
	}

	@Test
	void testRemoveTable() {
		addHashSetTables(3);
		tables.remove(3);
		restaurant.removeTable(3);

		assertEquals(tables, restaurant.getTables());
	}

}
