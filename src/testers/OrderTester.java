package testers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import model.MenuEntry;
import model.Order;

/* @Test, @Before, @After, @Test(expected = Exception.class), @Test(timeout = 50)
 * 
 * https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/Assertions.html */

class OrderTester {
	MenuEntry orderedEntry = new MenuEntry("Pasta al pomodoro, 3");
	Order order = new Order(1, orderedEntry);
	Order orderNumTwo = new Order(2, new MenuEntry("Pane, 1"));

	@Test
	void getTableNumTester() {
		assertEquals(1, order.getTableNum());
		assertEquals(2, orderNumTwo.getTableNum());
	}

	@Test
	void getOrderedEntryTester() {
		assertEquals(orderedEntry, order.getOrderedEntry());
	}

	/*
	 * @Test void getOrdersCounterTester() {
	 * System.out.println(Order.getOrdersCounter()); assertEquals(2,
	 * Order.getOrdersCounter());
	 * 
	 * }
	 * 
	 * @Test void getOrderNumTester() { System.out.println(order.getOrderNum());
	 * System.out.println(orderNumTwo.getOrderNum()); assertEquals(1,
	 * order.getOrderNum()); }
	 */

}
