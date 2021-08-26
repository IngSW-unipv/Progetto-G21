package waitersProgram.testers.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import waitersProgram.model.MenuEntry;
import waitersProgram.model.Order;

public class OrderTest {

	MenuEntry orderedEntry1 = new MenuEntry("Pasta al pomodoro, 7");
	MenuEntry orderedEntry2 = new MenuEntry("Cotoletta alla milanese, 8");
	MenuEntry orderedEntry3 = new MenuEntry("Antipasto misto, 7");

	Order order1 = new Order(1, orderedEntry1);
	Order order2 = new Order(1, orderedEntry2);
	Order order3 = new Order(2, orderedEntry3);

	/**
	 * Tester of method getOrderNum().
	 */
	@Test
	public void testGetOrderNum() {
		assertEquals(1, order1.getOrderNum());
		assertEquals(2, order2.getOrderNum());
		assertEquals(3, order3.getOrderNum());
		Order.setOrdersCounter(0);
	}

	/**
	 * Tester of method getTableNum().
	 */
	@Test
	public void testGetTableNum() {
		assertEquals(1, order1.getTableNum());
		assertEquals(1, order2.getTableNum());
		assertEquals(2, order3.getTableNum());
		Order.setOrdersCounter(0);
	}

	/**
	 * Tester of method getOrderedEntry().
	 */
	@Test
	public void testGetOrderedEntry() {
		assertEquals(orderedEntry1, order1.getOrderedEntry());
		assertEquals(orderedEntry2, order2.getOrderedEntry());
		assertEquals(orderedEntry3, order3.getOrderedEntry());
		Order.setOrdersCounter(0);
	}

	/**
	 * Tester of method getOrdersCounter().
	 */
	@Test
	public void testGetOrdersCounter() {
		assertEquals(3, Order.getOrdersCounter());
		assertEquals(3, Order.getOrdersCounter());
		assertEquals(3, Order.getOrdersCounter());
	}

	/**
	 * Tester of method toString().
	 */
	@Test
	public void testToString() {
		assertEquals("Order: 1table: 1entry: Pasta al pomodoro", order1.toString());
		assertEquals("Order: 2table: 1entry: Cotoletta alla milanese", order2.toString());
	}
}
