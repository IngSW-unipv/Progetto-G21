package leRestaurant;
import menu.*;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Collection;
public class RestaurantController {

	/** Using hashmap because S P E E D*/
	private Menu restaurantMenu;
	public RestaurantController() {
		
	}
	
	public void generateMenu(String path) {
		restaurantMenu.rewriteMenu(path);
	}
	
}
