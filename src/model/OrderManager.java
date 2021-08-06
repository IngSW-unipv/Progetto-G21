package model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The OrderManager class.
 * 
 * This class is created in order to efficiently manage every order processed by
 * the restaurant system. Each order is stored in a specific ArrayList (a stack)
 * according to its status. The class provides some methods that move orders
 * from a stack to another and change their status.
 */

public class OrderManager {

	private ArrayList<Order> notSeen;
	private ArrayList<Order> notPrepared; // unPrepared = seen.
	private ArrayList<Order> notPreparable;
	private ArrayList<Order> notDelivered; // unDelivered = prepared.
	private ArrayList<Order> delivered;
	private static OrderManager instance = null;

	/** Private constructor method. (Singleton pattern). */
	private OrderManager() {
		this.notSeen = new ArrayList<Order>(0);
		this.notPrepared = new ArrayList<Order>(0);
		this.notPreparable = new ArrayList<Order>(0);
		this.notDelivered = new ArrayList<Order>(0);
		this.delivered = new ArrayList<Order>(0);
	}

	/**
	 * Static method that returns an OrderManager instance in order to observe the
	 * Singleton pattern. It calls the class constructor only if this has not
	 * happened before.
	 * 
	 * @return OrderManager's instance.
	 */
	public static OrderManager getInstance() {
		if (instance == null) {
			return instance = new OrderManager();
		} else {
			return instance;
		}
	}

	/**
	 * @return notSeen orders' ArrayList.
	 */
	public ArrayList<Order> getNotSeenOrders() {
		return notSeen;
	}

	/**
	 * @return notPrepared orders' ArrayList.
	 */
	public ArrayList<Order> getNotPrepared() {
		return notPrepared;
	}

	/**
	 * @return notPreparable orders' ArrayList.
	 */
	public ArrayList<Order> getNotPreparable() {
		return notPreparable;
	}

	/**
	 * @return notDelivered orders' ArrayList.
	 */
	public ArrayList<Order> getNotDelivered() {
		return notDelivered;
	}

	/**
	 * @return delivered orders' ArrayList.
	 */
	public ArrayList<Order> getDelivered() {
		return delivered;
	}

	/**
	 * Method used to add an order to "notSeen" stack.
	 * 
	 * @param order specifies the involved order.
	 */
	public void addOrder(Order order) {
		notSeen.add(order);
	}

	/**
	 * Method used to remove an order to "notSeen" stack.
	 * 
	 * @param order specifies the involved order.
	 */
	public void removeOrder(Order order) {
		notSeen.remove(order);
	}

	/**
	 * Method used to add an order to "notPrepared" stack and to change its status.
	 * 
	 * @param order specifies the involved order.
	 */
	public void seeOrderToPreparable(Order order) {
		order.setSeen(true);
		notSeen.remove(order);
		notPrepared.add(order);
	}

	/**
	 * Method used to add an order to "notPreparable" stack and to change its
	 * status.
	 * 
	 * @param order specifies the involved order.
	 */
	public void seeOrderToNotPreparable(Order order) {
		order.setSeen(true);
		order.setPreparable(false);
		notSeen.remove(order);
		notPreparable.add(order);
	}

	/**
	 * Method used to add an order to "notDelivered" stack and to change its status.
	 * 
	 * @param order specifies the involved order.
	 */
	public void prepareOrder(Order order) {
		order.setPrepared(true);
		notPrepared.remove(order);
		notDelivered.add(order);
	}

	/**
	 * Method used to add an order to "Delivered" stack and to change its status.
	 * 
	 * @param order specifies the involved order.
	 */
	public void deliverOrder(Order order) {
		order.setDelivered(true);
		notDelivered.remove(order);
		delivered.add(order);
	}

	/**
	 * Method used to remove all the orders related to a table from all the stacks.
	 * 
	 * @param tableNum specifies the involved table.
	 */
	public void removeTablesAllOrders(int tableNum) {
		Iterator<Order> i1 = notSeen.iterator();
		Iterator<Order> i2 = notPrepared.iterator();
		Iterator<Order> i3 = notPreparable.iterator();
		Iterator<Order> i4 = notDelivered.iterator();
		Iterator<Order> i5 = delivered.iterator();

		while (i1.hasNext()) {
			Order order = i1.next();
			if (order.getTableNum() == tableNum) {
				notSeen.remove(order);
			}
		}

		while (i2.hasNext()) {
			Order order = i2.next();
			if (order.getTableNum() == tableNum) {
				notPrepared.remove(order);
			}
		}

		while (i3.hasNext()) {
			Order order = i3.next();
			if (order.getTableNum() == tableNum) {
				notPreparable.remove(order);
			}
		}

		while (i4.hasNext()) {
			Order order = i4.next();
			if (order.getTableNum() == tableNum) {
				notDelivered.remove(order);
			}
		}

		while (i5.hasNext()) {
			Order order = i5.next();
			if (order.getTableNum() == tableNum) {
				delivered.remove(order);
			}
		}
	}
}
