package order;
import java.io.*;

/** The Order class.
 * An Order contains a dish, its table number, its seat number, 
 * preparation flags and the progressive number used to identify it. */

public class Order implements Serializable {
	
	private int orderId;
	private int tableId;
	private int totalOrders = 0;
	private boolean seen;
	private boolean prepared;
	private boolean delivered;
	private Dish dish;
	private int seatNum;

	/**Main constructor. An order is created with all of its flags as false (seen, prepared...) and contains a Dish object.
	 * When an Order is created, totalOrders is incremented by one. 
	 * The order's id is assigned to the progressive number totalOrders. 
	 * @param dish specify the dish assigned to the Order. 
	 * @param table specify the table number has made the Order. 
	 * @param seat specify the seat has made the Order. */
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

	/** Getter of orderId. */
	public int getOrderId() {
		return orderId;
	}
	
	/** Getter of tableId. */
	public int getTableId() {
		return tableId;
	}
	
	/** Getter of totalOrders. */
	public int getTotalOrders() {
		return totalOrders;
	}
	
	/** Getter of dish.	
	 * Seems ugly.... has to be fixed. */
	public Dish getDish() {
		return dish;
	}

	/** Getter of seen. */
	public boolean isSeen() {
		return seen;
	}
	
	/** Getter of prepared. */
	public boolean isPrepared() {
		return prepared;
	}
	
	/** Getter of delivered. */
	public boolean isDelivered() {
		return delivered;
	}
	
	/** Getter of seat. */
	public int getSeatNum() {
		return seatNum;
	}
	
	/** Setter for seen.
	 * @param input specify the value to use. */
	public void setSeen(boolean input) {
		seen = input;
	}
	
	/** Setter for prepared.
	 * @param input specify the value to use. */
	public void setPrepared(boolean input) {
		prepared = input;
	}
	
	/** Setter for delivered.
	 * @param input specify the value to use. */
	public void setDelivered(boolean input) {
		delivered = input;
	}
	
	/** toString method: displays informations about the order in the specified format.
	 * @return stringedOrder. 
	 * The format has to be fixed, following getDish method correct implementation! */
	public String toString() {
		String stringedOrder = String.format("OrderNum: %d, Table: %d, Seat: %d, Dish: %s\n", 
				orderId, getTableId(), getSeatNum(), getDish());
		return stringedOrder;
	}
	
	/** equals method: it compares two Orders respecting the following rules.
	 * @ return isEquals. 
	 * Dish class is not yet defined! (equals method doesn't work right now) */
	public boolean equals(Order otherOrder) {
		boolean isEquals = false;
		if( this.orderId == otherOrder.orderId && 
			this.tableId == otherOrder.tableId &&
			this.seatNum == otherOrder.seatNum &&
			this.dish.equals(otherOrder.dish) isEquals = true;
		else isEquals = false;
		
		return isEquals;
	}
	
}
