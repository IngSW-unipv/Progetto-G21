package testers;

import org.junit.jupiter.api.Test;

import model.MenuEntry;
import model.Order;
import model.OrderManager;

public class SimpleOrderManagerTest {

	public static void main(String[] args) {
		
		OrderManager orderManager = OrderManager.getInstance();
		
		MenuEntry entry1 = new MenuEntry("Lasagne", 11);
		MenuEntry entry2 = new MenuEntry("Verdura mista", 6);
		
		Order order1 = new Order(1, entry1);
		Order order2 = new Order(1, entry2);
		
		
		orderManager.addOrder(order1);
//		orderManager.addOrder(order2);
//		orderManager.prepareOrder(order2);
			
	   //orderManager.getNotSeen();
		
	   // System.out.println(orderManager.getNotSeen());
	    
	    orderManager.removeTableAllOrders(1);


	}

}
