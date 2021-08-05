package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;

/** The bill class. A bill represents all the orders of a specific table. */

public class Bill {

	private ArrayList<Order> orders;
	private int tableNum;
	private double amount;

	/**
	 * Class constructor. A Bill, when initialized, has no Orders added to it yet.
	 * Its total amount is 0.
	 *
	 * @param tableNum represents bill's table number.
	 */
	public Bill(int tableNum) {
		this.tableNum = tableNum;
		orders = new ArrayList<Order>(0);
		amount = 0;
	}

	/** Simple orders' ArrayList getter. */
	public ArrayList<Order> getOrders() {
		return orders;
	}

	/** Simple bill's table number getter. */
	public int getTableNum() {
		return tableNum;
	}

	/** Simple bill's amount getter. */
	public double getAmount() {
		setAmount();
		return amount;
	}

	/** Method used to calculate bill's amount via the ArrayList. */
	public void setAmount() {
		Iterator<Order> i = orders.iterator();
		while (i.hasNext()) {
			MenuEntry entry = i.next().getOrderedEntry();
			amount += entry.getDishPrice();
		}
	}

	/**
	 * Method used to add an order to the bill's ArrayList.
	 * 
	 * @param order specifies the involved order.
	 */
	public void addOrder(Order order) {
		if (order.getTableNum() == this.tableNum && orders.contains(order) == false) {
			orders.add(order);
			amount += order.getOrderedEntry().getDishPrice();
		}
	}

	/**
	 * Method used to remove an order from the bill's ArrayList.
	 * 
	 * @param order specifies the involved order.
	 */
	public void removeOrder(Order order) {
		orders.remove(order);
		amount -= order.getOrderedEntry().getDishPrice();
	}

	/**
	 * Method used to return a formatted string representing the bill.
	 * 
	 * @return "stringed" bill.
	 */
	@Override
	public String toString() {
		setAmount();
		String output = "Table: " + Integer.toString(tableNum) + "\n";
		Iterator<Order> i = orders.iterator();
		while (i.hasNext()) {
			MenuEntry entry = i.next().getOrderedEntry();
			output = output + entry.toString() + "\n";
		}

		NumberFormat formatter = new DecimalFormat("#0.00");
		output = output + "Total: " + formatter.format(amount) + " €";
		return output;
	}

	/**
	 * Method used to generate the bill file via toString() method.
	 * 
	 * @param path specifies the directory absolute path in which the bill file has
	 *             to be saved. path has to be in UNIX format!
	 */
	public void generateBillFile(String path) {
		path = path + "\"" + "billTable" + this.tableNum + ".txt";
		try {
			File file = new File(path);
			BufferedWriter stream = new BufferedWriter(new FileWriter(file, false));
			stream.write(this.toString());
			stream.flush();
			stream.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}

}
