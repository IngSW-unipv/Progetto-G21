package waitersProgram.testers;

import java.util.ArrayList;

import waitersProgram.model.MenuEntry;
import waitersProgram.model.Order;
import waitersProgram.model.OrderManager;

public class SimpleOrderManagerTest {

	public static void main(String[] args) {

		OrderManager orderManager = OrderManager.getInstance();

		ArrayList<Order> notSeen = new ArrayList<Order>();
		ArrayList<Order> notPrepared = new ArrayList<Order>();
		ArrayList<Order> notPreparable = new ArrayList<Order>();
		ArrayList<Order> notDelivered = new ArrayList<Order>();
		ArrayList<Order> delivered = new ArrayList<Order>();

		MenuEntry entry1 = new MenuEntry("Lasagne", 11);
		MenuEntry entry2 = new MenuEntry("Verdura mista", 6);
		MenuEntry entry3 = new MenuEntry("Salmone al forno", 19);

		Order order1 = new Order(1, entry1);
		Order order2 = new Order(1, entry2);
		Order order3 = new Order(2, entry3);

		orderManager.addOrder(order1);
		orderManager.addOrder(order2);
		orderManager.addOrder(order3);

		/**
		 * Tester of method getNotSeen().
		 */
		notSeen.add(order1);
		notSeen.add(order2);
		notSeen.add(order3);

		if (notSeen.equals(orderManager.getNotSeen())) {
			System.out.println("OK");

			orderManager.removeOrder(order1);
			orderManager.removeOrder(order2);
			orderManager.removeOrder(order3);
			notSeen.remove(order1);
			notSeen.remove(order2);
			notSeen.remove(order3);
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method getNotPrepared().
		 */
		orderManager.seeOrderToNotPrepared(order1);
		notPrepared.add(order1);

		if (notPrepared.equals(orderManager.getNotPrepared())) {
			System.out.println("OK");

			notPrepared.remove(order1);
			orderManager.getNotPrepared().remove(order1);
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method getNotPreparable().
		 */
		orderManager.seeOrderToNotPreparable(order2);
		notPreparable.add(order2);

		if (notPreparable.equals(orderManager.getNotPreparable())) {
			System.out.println("OK");

			notPreparable.remove(order2);
			orderManager.getNotPreparable().remove(order2);
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method getNotDelivered().
		 */
		orderManager.prepareOrder(order3);
		notDelivered.add(order3);

		if (notDelivered.equals(orderManager.getNotDelivered())) {
			System.out.println("OK");

			notDelivered.remove(order3);
			orderManager.getNotDelivered().remove(order3);

		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method getDelivered().
		 */
		orderManager.deliverOrder(order3);
		delivered.add(order3);

		if (delivered.equals(orderManager.getDelivered())) {
			System.out.println("OK");

			delivered.remove(order3);
			orderManager.getDelivered().remove(order3);
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method removeTableAllOrders().
		 */
		orderManager.removeTableAllOrders(1);
		notSeen.remove(order1);
		notSeen.remove(order2);
		notSeen.remove(order3);

		if (notSeen.equals(orderManager.getNotSeen())) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

	}

}
