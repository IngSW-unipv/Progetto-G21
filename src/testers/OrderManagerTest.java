
package testers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.MenuEntry;
import model.Order;
import model.OrderManager;

public class OrderManagerTest {
	
	OrderManager orderManager = OrderManager.getInstance();
	
	ArrayList<Order> notSeen = new ArrayList<Order> ();
	ArrayList<Order> notPrepared = new ArrayList<Order> (); 
	ArrayList<Order> notPreparable = new ArrayList<Order> ();
	ArrayList<Order> notDelivered = new ArrayList<Order> (); 
	ArrayList<Order> delivered = new ArrayList<Order> ();
	
	MenuEntry entry1 = new MenuEntry("Lasagne", 11);
	MenuEntry entry2 = new MenuEntry("Verdura mista", 6);
	MenuEntry entry3 = new MenuEntry("Salmone al forno", 19);
	
	Order order1 = new Order(1, entry1);
	Order order2 = new Order(1, entry2);
	Order order3 = new Order(2, entry3);
	
	
	@Test
	public void testGetNotSeen()
	{
		orderManager.addOrder(order1);
		notSeen.add(order1);
		orderManager.addOrder(order2);
    	notSeen.add(order2);
    	orderManager.addOrder(order3);
    	notSeen.add(order3);
    	orderManager.removeOrder(order1);
    	notSeen.remove(order1);
		assertEquals(notSeen, orderManager.getNotSeen());
		
		orderManager.removeOrder(order1);
		orderManager.removeOrder(order2);
		orderManager.removeOrder(order3);
		notSeen.remove(order1);
		notSeen.remove(order2);
		notSeen.remove(order3);
	}
	
	
	@Test
	public void testGetNotPrepared()
	{
    	orderManager.seeOrderToNotPrepared(order1);
    	notPrepared.add(order1);
		assertEquals(notPrepared, orderManager.getNotPrepared());
		
		notPrepared.remove(order1);
		orderManager.getNotPrepared().remove(order1);
	}
  
	
	@Test
	public void testGetNotPreparable()
	{
    	orderManager.seeOrderToNotPreparable(order2);
    	notPreparable.add(order2);
		assertEquals(notPreparable, orderManager.getNotPreparable());
		
    	notPreparable.remove(order2);
		orderManager.getNotPreparable().remove(order2);
	}
	
	
	@Test
	public void testGetNotDelivered()
	{
    	orderManager.prepareOrder(order3);
    	notDelivered.add(order3);
		assertEquals(notDelivered, orderManager.getNotDelivered());
		
    	notDelivered.remove(order3);
		orderManager.getNotDelivered().remove(order3);
	}
	
	
	@Test
	public void testGetDelivered()
	{
    	orderManager.deliverOrder(order3);
    	delivered.add(order3);
		assertEquals(delivered, orderManager.getDelivered());
		
    	delivered.remove(order3);
		orderManager.getDelivered().remove(order3);
	}
	
	
	@Test
	public void generalTest()
	{
		orderManager.addOrder(order1);
		notSeen.add(order1);
		assertEquals(notSeen, orderManager.getNotSeen());
		
		orderManager.seeOrderToNotPrepared(order1);
		notPrepared.add(order1);
		notSeen.remove(order1);
		assertEquals(notPrepared, orderManager.getNotPrepared());
		assertEquals(notSeen, orderManager.getNotSeen());
		
		orderManager.prepareOrder(order1);
		notDelivered.add(order1);
		notPrepared.remove(order1);
		assertEquals(notDelivered, orderManager.getNotDelivered());
		assertEquals(notPrepared, orderManager.getNotPrepared());
		
		orderManager.deliverOrder(order1);
		delivered.add(order1);
		notDelivered.remove(order1);
		assertEquals(delivered, orderManager.getDelivered());
		assertEquals(notDelivered, orderManager.getNotDelivered());
	}
}
