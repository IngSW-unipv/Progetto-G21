package model;

/**
 * The MenuEntry class. A MenuEntry object represents one entry on the menu of
 * the restaurant. It contains dishName, dishPrice.
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
	 * @param dishEntry specifies the String to parse in "dishName, dishPrice
	 *                  format. Pay attention to the separator (comma) and to double
	 *                  entries. If you don't use comma the program is killed.
	 */
	public MenuEntry(String dishEntry) {
		String[] buffer = dishEntry.split(",");
		dishName = buffer[0];
		dishPrice = Double.parseDouble(buffer[1]);
	}

	/**
	 * Simple dishName getter.
	 * 
	 * @return dishName.
	 */
	public String getDishName() {
		return dishName;
	}

	/**
	 * Simple dishPrice getter.
	 * 
	 * @return dishPrice.
	 */
	public double getDishPrice() {
		return dishPrice;
	}

	/**
	 * Method that edit MenuEntry's name.
	 * 
	 * @param newName is the new given name.
	 */
	public void editEntry(String newName) {
		this.dishName = newName;
	}

	/**
	 * Method that edit MenuEntry's price.
	 * 
	 * @param newPrice is the new given price.
	 */
	public void editEntry(double newPrice) {
		this.dishPrice = newPrice;
	}

	/**
	 * Simple toString method.
	 * 
	 * @return MenuEntry in a specified format.
	 */
	@Override
	public String toString() {
		return "MenuEntry [dishName=" + dishName + ", dishPrice=" + dishPrice + "]";
	}

}
