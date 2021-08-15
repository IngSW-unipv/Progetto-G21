package waitersProgram.testers;

import org.junit.jupiter.api.Test;

import waitersProgram.model.MenuEntry;
import waitersProgram.model.Order;
import waitersProgram.model.OrderManager;

public class SimpleOrderManagerTest {

	public static void main(String[] args) {
		
		OrderManager orderManager = OrderManager.getInstance();
		
		MenuEntry entry1 = new MenuEntry("Lasagne", 11);
		MenuEntry entry2 = new MenuEntry("Verdura mista", 6);
		
		Order order1 = new Order(1, entry1);
		Order order2 = new Order(1, entry2);
		
		
		orderManager.addOrder(order1);
		orderManager.addOrder(order2);
		orderManager.prepareOrder(order2);
			
	    	    
	    orderManager.removeTableAllOrders(1);


	}

}
