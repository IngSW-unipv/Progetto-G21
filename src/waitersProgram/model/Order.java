package waitersProgram.model;

/**
 * The Order class.
 * 
 * An Order object contains a MenuEntry, the table's number that asked for it
 * and some status variables.
 */

public class Order {

	private int orderNum; // Numerical order's identifier.
	private int tableNum; // Table's identifier.
	private MenuEntry orderedEntry; // Ordered dish specified by a MenuEntry.
	private String orderedEntryStringed;
	private String orderStatusStringed;

	// Order's status flags.
	private boolean isSeen;
	private boolean isPreparable;
	private boolean isPrepared;
	private boolean isDelivered;

	// Counter that represents all the orders made during a program runtime.
	private static int ordersCounter = 0;

	/**
	 * Simple class constructor.
	 * 
	 * @param tableNum     specifies order's table.
	 * @param orderedEntry specifies the ordered item.
	 */
	public Order(int tableNum, MenuEntry orderedEntry) {
		ordersCounter++;
		this.orderNum = ordersCounter;
		this.tableNum = tableNum;
		this.orderedEntry = orderedEntry;
		this.isSeen = false;
		this.isPreparable = true;
		this.isPrepared = false;
		this.isDelivered = false;
		String[] splitted = orderedEntry.toString().split(", ");
		orderedEntryStringed = splitted[0];
		orderStatusStringed = "NOT SEEN";
	}

	/**
	 * 
	 * @return orderNum.
	 */
	public int getOrderNum() {
		return orderNum;
	}

	/**
	 * @return tableNum.
	 */
	public int getTableNum() {
		return tableNum;
	}

	/**
	 * @return orderedEntry.
	 */
	public MenuEntry getOrderedEntry() {
		return orderedEntry;
	}

	/**
	 * @return orderedEntryStringed.
	 */
	public String getOrderedEntryStringed() {
		return orderedEntryStringed;
	}

	/**
	 * @return orderStatusStringed.
	 */
	public String getOrderStatusStringed() {
		return orderStatusStringed;
	}

	/**
	 * @return isSeen flag.
	 */
	public boolean isSeen() {
		return isSeen;
	}

	/**
	 * @return isPreparable flag.
	 */
	public boolean isPreparable() {
		return isPreparable;
	}

	/**
	 * @return isPrepared flag.
	 */
	public boolean isPrepared() {
		return isPrepared;
	}

	/**
	 * @return isDelivered flag.
	 */
	public boolean isDelivered() {
		return isDelivered;
	}

	/**
	 * @return ordersCounter.
	 */
	public static int getOrdersCounter() {
		return ordersCounter;
	}

	/**
	 * @param orderNum.
	 */
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	/**
	 * @param ordersCounter.
	 */
	public static void setOrdersCounter(int ordersCounter) {
		Order.ordersCounter = ordersCounter;
	}

	/**
	 * @param isSeen flag.
	 */
	public void setSeen(boolean isSeen) {
		this.isSeen = isSeen;

		if (isSeen) {
			orderStatusStringed = "SEEN";
		}
	}

	/**
	 * @param isPreparable flag.
	 */
	public void setPreparable(boolean isPreparable) {
		this.isPreparable = isPreparable;

		if (this.isSeen && isPreparable) {
			orderStatusStringed = "PREPARABLE";
		} else if (this.isSeen == true && isPreparable == false) {
			orderStatusStringed = "NOT PREPARABLE";
		}
	}

	/**
	 * @param isPrepared flag.
	 */
	public void setPrepared(boolean isPrepared) {
		this.isPrepared = isPrepared;

		if (this.isSeen && this.isPreparable && this.isPrepared) {
			orderStatusStringed = "PREPARED";
		}
	}

	/**
	 * @param isDelivered flag.
	 */
	public void setDelivered(boolean isDelivered) {
		this.isDelivered = isDelivered;

		if (this.isSeen && this.isPreparable && this.isPrepared && isDelivered == true) {
			orderStatusStringed = "DELIVERED";
		}
	}

	/**
	 * @return Order object in a formatted String.
	 */
	@Override
	public String toString() {
		return "Order: " + orderNum + " table: " + tableNum + " entry: " + this.getOrderedEntry().getDishName();
	}
}
