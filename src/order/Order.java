package order;
import java.io.*;

/** The Order class.
 * An order contains a dish. */

public class Order implements Serializable {
	
	private int orderId;
	private int tableId;
	private int totalOrders = 0;
	private boolean seen;
	private boolean prepared;
	private boolean delivered;
	private Dish dish;
	private int seatNum;

	// Constructor
	public Order(Dish dish, int table, int seat) {
		
		totalOrders++;
		
		seen = false;
		prepared = false;
		delivered = false;
		
		this.dish = dish;
		orderId = totalOrders;
		tableId = table;
		seatNum = seat;
	}

	// Getters 
	public int getOrderId() {
		return orderId;
	}

	public int getTableId() {
		return tableId;
	}

	public int getTotalOrders() {
		return totalOrders;
	}

	public Dish getDish() {
		return dish;
	}

// -------------------------------------------------------------------------------------------------------------------	
	public boolean isSeen() {
		return seen;
	}

	public boolean isPrepared() {
		return prepared;
	}

	public boolean isDelivered() {
		return delivered;
	}
	
	public void setSeen() {
		seen = true;
	}
	
	public void setPrepared() {
		prepared = true;
	}
	
	public void setDelivered() {
		delivered = true;
	}
	
	public int getSeatNum() {
		return seatNum;
	}
	
	public String toString() {
		String stringedOrder = String.format("OrderNum: %d, Table: %d, Seat: %d, Dish: %s\n", 
				orderId, getTableId(), getSeatNum(), getDish());
		return stringedOrder;
	}
	
	public boolean equals(Order otherOrder) {
		if( this.orderId == otherOrder.orderId && 
			this.tableId == otherOrder.tableId &&
			this.seatNum == otherOrder.seatNum &&
			this.dish.equals(otherOrder.dish) return true;
		else return false;
	}
	
}
