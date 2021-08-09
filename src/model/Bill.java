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
	private double amount = 0;

	/**
	 * Class constructor. It allocates memory space for orders ArrayList, specifies
	 * the bill's tableNum, looks for its orders via lookForTableOrders, calculates
	 * bill's amount via setAmount and finally generates bill's file
	 * representation.1
	 *
	 * @param tableNum represents bill's table number.
	 */
	public Bill(int tableNum) {
		orders = new ArrayList<Order>(0);
		this.tableNum = tableNum;
		lookForTableOrders();
		setAmount();
		generateBillFile("Files");
	}

	/**
	 * Method that looks for all orders taken over by the system simply calling an
	 * instance of OrderSystem (Singleton). The method fills the orders ArrayList
	 * and removes all the involved orders from OrderSystem's stacks.
	 */
	private void lookForTableOrders() {
		OrderManegerInterface manager = OrderManager.getInstance();
		Iterator<Order> iNotSeen = manager.getNotSeen().iterator();
		Iterator<Order> iNotPrepared = manager.getNotPrepared().iterator();
		Iterator<Order> iNotDelivered = manager.getNotDelivered().iterator();
		Iterator<Order> iDelivered = manager.getDelivered().iterator();

		while (iNotSeen.hasNext()) {
			Order currentOrder = iNotSeen.next();
			if (currentOrder.getTableNum() == tableNum) {
				orders.add(currentOrder);
			}
		}

		while (iNotPrepared.hasNext()) {
			Order currentOrder = iNotPrepared.next();
			if (currentOrder.getTableNum() == tableNum) {
				orders.add(currentOrder);
			}
		}

		while (iNotDelivered.hasNext()) {
			Order currentOrder = iNotDelivered.next();
			if (currentOrder.getTableNum() == tableNum) {
				orders.add(currentOrder);
			}
		}

		while (iDelivered.hasNext()) {
			Order currentOrder = iDelivered.next();
			if (currentOrder.getTableNum() == tableNum) {
				orders.add(currentOrder);
			}
		}

		manager.removeTableAllOrders(tableNum);
	}

	/**
	 * @return bill orders' ArrayList.
	 */
	public ArrayList<Order> getOrders() {
		return orders;
	}

	/**
	 * @return bills' tableNum.
	 */
	public int getTableNum() {
		return tableNum;
	}

	/**
	 * @return bills' total amount.
	 */
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
	 *             to be saved. Path has to be in UNIX format!
	 */
	public void generateBillFile(String path) {
		path = path + "/" + "billTable" + this.tableNum + ".txt";
		try {
			File file = new File(path);
			if (file.exists() == true) {
				file.delete();
			}
			file.createNewFile();
			BufferedWriter stream = new BufferedWriter(new FileWriter(file));
			stream.write(this.toString());
			stream.flush();
			stream.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}

}
