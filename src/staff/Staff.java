package staff;

import java.io.*;
import order.*;
import storage.*;
import leRestaurant.*;

public class Staff {
	private Order currentOrder;
	private int id;
	private String name;
	private Storage storage;
	private RestaurantController restaurant;
	
	public Staff(String name, RestaurantController restaurant) {
		this.name = name;
		this.restaurant = restaurant;
		this.storage = restaurant.getStorage();
	}
	
	public Order getCurrentOrder() {
		return currentOrder;
	}
	
	public String getName() {
		return name;
	}
	
	public Storage getStorage() {
		return storage;
	}
	
	public int getId() {
		return id;
	}
	
	public RestaurantController getRestaurant() {
		return restaurant;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setStorage(Storage storage) {
		this.storage = storage;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setRestaurant(RestaurantController restaurant) {
		this.restaurant = restaurant;
	}
	
	public void takeOrder(Order order) {
		this.currentOrder = order;
	}
	
	public void doneWithOrder() {
		this.currentOrder = null;
	}
	
	/* public boolean prepareOrder() throws IOException {

        Order order = getCurrentOrder();

        if (order == null) {

            return false;

        } else {

            boolean possible = true;

            HashMap<String, Double> currentStock = this.getStorage().getStock();

            for (HashMap.Entry<String, Double> ingredient : order.getDish().getIngredients().entrySet()) {
                String key = ingredient.getKey();
                if (!currentStock.containsKey(key) || currentStock.get(key) < ingredient.getValue()) {
                    possible = false;
                    getRestaurant().getOrderSystem().cancelOrder(order);

                    break;
                }
            }

            if (possible) {
                for (HashMap.Entry<String, Double> ingredient : order.getDish().getIngredients()
                        .entrySet()) {
                    String key = ingredient.getKey();
                    getInventory().changeIngredientAmt(key, ingredient.getValue() * -1);
                }
            } else {
                doneWithOrder();
            }

            return possible;
        }
    } */
}
