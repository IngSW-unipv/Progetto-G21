package bill;

import java.io.*;
import java.text.*;
import java.time.*;
import java.util.*;

import order.*;

public class Bill implements Serializable {
	private double total;
	private int tableId;
	private ArrayList<Order> orders; 
	private ZonedDateTime paymentTime;
	private static DecimalFormat rounder = new DecimalFormat("#.##");
	
	/** Bill constructor */
	public Bill(int tableId) {
		this.tableId = tableId;
		orders = new ArrayList<>(0);
		total = 0;
	}
	
	public ArrayList<Order> getOrders() {
		return orders;
	}
	
	public int getTableId() {
        return tableId;
    }
	
	public double getTotal() {
		return total;
	}
	
	public boolean contains(Order order) {
		return orders.contains(order); 
	}
	
	public void addOrder(Order order) {
		if(order.getTableId() == this.tableId && !orders.contains(order)) {
			orders.add(order);
			total += order.getDish().getPrice();
		}
	}
	
	public void removeOrder(Order order) {
		orders.remove(order);
		total -= order.getDish().getPrice();
	}
}
