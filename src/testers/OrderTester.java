package testers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import model.MenuEntry;
import model.Order;

/* @Test, @Before, @After, @Test(expected = Exception.class), @Test(timeout = 50)
 * 
 * https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/Assertions.html */

class OrderTester {

	Order order = new Order(1, new MenuEntry("Pasta al pomodoro, 3"));

	@Test
	void getOrderNumTester() {
		assertEquals(1, order.getTableNum());
	}
}
