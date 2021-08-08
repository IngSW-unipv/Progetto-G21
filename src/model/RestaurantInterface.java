package model;

import java.util.HashSet;

public interface RestaurantInterface {

	public Menu getRestaurantMenu();

	public OrderManager getOrderManager();

	public HashSet<Integer> getTables();

	public void addTable(int tableNum);

	public void removeTable(int tableNum);

	public void parseTablesFile();
}
