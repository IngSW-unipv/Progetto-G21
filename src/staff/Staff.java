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
	
	public void receiveIncomingOrders() throws IOException {

        try (BufferedReader incomingOrders = new BufferedReader(new FileReader("incomingOrders.txt"))) {
            String orderLine = incomingOrders.readLine();
            while (orderLine != null) {

                String[] ingredientToQuantity = orderLine.trim().split(",");

                String ingredient = ingredientToQuantity[0].trim();                
                int quantity = Integer.parseInt(ingredientToQuantity[1].trim());         

                getStorage().changeIngredientAmt(ingredient, quantity);               
                orderLine = incomingOrders.readLine();
            }

            new PrintWriter("incomingOrders.txt").close();      

        } catch (IOException e) {
            throw new IOException("incomingOrders.txt not found!");
        }
    }
}
