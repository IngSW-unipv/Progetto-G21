package waitersProgram.model;

/**
 * The MenuEntry class.
 * 
 * A MenuEntry object represents one entry on the menu of the restaurant (both
 * food and drinks).
 */
public class MenuEntry {

	private String dishName;
	private double dishPrice;

	/**
	 * Simple constructor method.
	 * 
	 * @param name  specifies MenuEntry's name.
	 * @param price specifies MenuEntry's price.
	 */
	public MenuEntry(String name, double price) {
		this.dishName = name;
		this.dishPrice = price;
	}

	/**
	 * Advanced Constructor method.
	 * 
	 * @param dishEntry specifies the String to parse in "dishName, dishPrice"
	 *                  format. dishEntry format check is delegated to Menu.java.
	 */
	public MenuEntry(String dishEntry) {
		String[] buffer = dishEntry.split(",");
		dishName = buffer[0];
		dishPrice = Double.parseDouble(buffer[1].trim());
	}

	/**
	 * @return dishName String.
	 */
	public String getDishName() {
		return dishName;
	}

	/**
	 * @return dishPrice.
	 */
	public double getDishPrice() {
		return dishPrice;
	}

	/**
	 * @param newName is the new given name.
	 */
	public void editEntryName(String newName) {
		this.dishName = newName;
	}

	/**
	 * @param newPrice is the new given price.
	 */
	public void editEntryPrice(double newPrice) {
		this.dishPrice = newPrice;
	}

	/**
	 * @return MenuEntry in "dishName, dishPrice" format.
	 */
	@Override
	public String toString() {
		if (dishPrice == (long) dishPrice) {
			return dishName + ", " + String.format("%d", (long) dishPrice);
		} else {
			return dishName + ", " + String.format("%s", dishPrice);
		}

	}

}
