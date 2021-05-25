package model;

import java.io.*;
import java.text.*;
import java.time.*;
import java.util.*;

import order.*;

/** The Bill class.
 * A Bill contains all seat's Orders, the total price of all Dish ordered and the time of payment. */

public class Bill implements Serializable {
	private double total;
	private int tableId;
	private ArrayList<Order> orders; 
	private ZonedDateTime paymentTime;
	private static DecimalFormat rounder = new DecimalFormat("#.##");
	
	/** Bill constructor. 
	 * When a Bill is created it contains no orders. It's total amount is 0€. */
	public Bill(int tableId) {
		this.tableId = tableId;
		orders = new ArrayList<>(0);
		total = 0;
	}
	
	/** Getter of orders. */
	public ArrayList<Order> getOrders() {
		return orders;
	}
	
	/** Getter of tableId. */
	public int getTableId() {
        return tableId;
    }
	
	/** Getter of total. */
	public double getTotal() {
		return total;
	}
	
	/** Checks for a given Order in the current Bill.
	 * @return isContained. */
	public boolean contains(Order order) {
		boolean isContained = orders.contains(order);
		return isContained;
	}
	
	/** Add an Order to the current Bill, updates the total amount.
	 * Dish class is not yet defined.... 
	 * @param order specify the order to add. */
	public void addOrder(Order order) {
		if(order.getTableId() == this.tableId && !orders.contains(order)) {
			orders.add(order);
			total += order.getDish().getPrice();
		}
	}
	
	/** Remove an Order to the current Bill, updates the total amount.
	 * Dish class is not yet defined.... 
	 * @param order specify the order to remove. */
	public void removeOrder(Order order) {
		orders.remove(order);
		total -= order.getDish().getPrice();
	}
	
	/** Sets the payment time of the current Bill according to GMT+1. */
	public void setTime() {
		paymentTime = ZonedDateTime.now(ZoneId.of("Europe/Rome"));
	}
	
	/** Returns a string representation of the payment time. */
    public String getPaymentTimeString() {

        String prelimTime = paymentTime.toString();
        String date = prelimTime.split("T")[0];
        String splitTime = prelimTime.split("-")[2];
        String time = splitTime.split("\\.")[0].substring(3);

        return date + " " + time;
    }
	
}

