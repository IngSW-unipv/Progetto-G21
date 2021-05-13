package staff;

import java.util.*;

import leRestaurant.RestaurantController;
import order.*;

import java.io.*;

public class Chef extends Staff implements Serializable {
	
	private int numChefs = 0;
	
	public Chef(String name, RestaurantController restaurant) {
		super(name, restaurant);
		numChefs++;
		setId(numChefs);
		restaurant.getStaffSystem().addChef(this);
	}
	
	public void seeOrder(Order order) {
		if(getCurrentOrder() == null) {
			order.setSeen(true);
			takeOrder(order);
			getRestaurant().getOrderSystem().seenOrder(order);
			
			// EventLogger??????
		}
	}
	
	public void confirmPrepared() {
		getCurrentOrder().setPrepared(true);
		getRestaurant().getOrderSystem().preparedOrder(getCurrentOrder());
		doneWithOrder();
		
		// EventLogger?????
	}
	
	public boolean prepareOrder() throws IOException {

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
    }
	

}
