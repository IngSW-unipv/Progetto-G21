package model;

import java.util.ArrayList;

public class Table {
	private static int tableCount = 0;
	private int capacity;
	private int tableNum;
	private Bill[] bills;
	private int numCustomers;
	private boolean occupied;
	private ArrayList<Order> receivedOrders;
	
	
	public Table(int capacity) {
		this.capacity = capacity;
		this.tableNum = tableCount++;
		bills = new Bill[capacity];
		
		for(int i = 0; i < capacity; i++) {
			bills[i] = new Bill(tableNum);
		}
		
		numCustomers = 0;
		occupied = false;
		// Link receivedOrders with the controller.. 
	}

	public int getTableNum() {
		return tableNum;
	}

	public Bill[] getBills() {
		return bills;
	}

	public int getNumCustomers() {
		return numCustomers;
	}
	
	public int getCapacity() {
		return capacity;
	}

	public boolean isOccupied() {
		return occupied;
	}
	
	// public Bill[] payBills()
	
	// public void receiveOrder(Order order)
	
	// public Bill findBillWithOrder(Order order)
	
	public String toString() {
		return "Table #" + tableNum;
	}
	
	// public ObservableList<Order> getOrdersBySeat(int seatNum)
	
	// public void combineBills(int seatOne, int seatTwo)
	
	private void clearBill(int seatNum) {
		bills[seatNum] = new Bill(tableNum);
	}
}
