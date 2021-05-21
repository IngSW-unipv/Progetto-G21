package leRestaurant;

import storage.*;
import menu.*;
import java.io.*;
import java.util.*;

/** The Restaurant class, facade controller. */

public class RestaurantController {
	
	private Storage storage;
	private Menu restaurantMenu;
	private ArrayList<Table> tables;
	private ArrayList<Table> emptyTables;
	private RevenueSystem revenueSystem; 
}
