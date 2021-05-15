package dish;

import java.io.*;
import java.util.*;

import menu.*;

/** The Dish class.
 * A Dish object represent an item on the restaurant's menu, 
 * all the ingredients required and the price required to pay.  */

public class Dish implements Serializable {
	
	private String name;
	private double price;
	private LinkedHashMap<String, Double> ingredients;
	
	/** Class constructor: takes a MenuEntry object and clones the required ingredients.
	 * @param menu is the MenuEntry object used to take ingredients and price.
	 * @param dishName is the name of the current dish. (dishName is also used to obtain the correct MenuEntry) */
	@SuppressWarnings("unchecked")
	public Dish(Collection<MenuEntry> menu, String dishName) {
		name = dishName;
        for (MenuEntry item : menu) {
            if (item.getDishName().equals(dishName)) {
                ingredients = (LinkedHashMap<String, Double>) item.getDishIngredients().clone();
                price = item.getDishPrice();
                break;
            }
        }
	}
        
    /** Getter of name. */
    public String getName() {
    	return name;
    }
    
    /** Getter of price. */
    public double getPrice() {
    	return price;
    }
    
    /** Getter of ingredients. */
    public LinkedHashMap<String, Double> getIngredients() {
    	return ingredients;
    }
    
    /** Equals method: it compares two Dish objects 
     * (2 dishes with the same name and price are not allowed!) 
     * @param otherDish specify one of the two dishes to compare. 
     * @return isEqual. */
	public boolean equals(Dish otherDish) {
		boolean isEqual = false;
		if(name == otherDish.name && price == otherDish.price) isEqual = true;
		return isEqual;
	}
	
	
	
	
}
