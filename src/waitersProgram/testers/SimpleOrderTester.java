package waitersProgram.testers;

import waitersProgram.model.MenuEntry;
import waitersProgram.model.Order;

public class SimpleOrderTester {

	public static void main(String[] args) {
		Order o1 = new Order(3, new MenuEntry("Pasta al pomodoro, 5"));
		Order o2 = new Order(8, new MenuEntry("Tiramisù, 8"));
		Order o3 = new Order(8, new MenuEntry("Tiramisù, 8"));

		System.out.println(Order.getOrdersCounter());
	}

}
