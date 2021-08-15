package waitersProgram.model;

import java.util.ArrayList;

public interface OrderManegerInterface {

	public ArrayList<Order> getNotSeen();

	public ArrayList<Order> getNotPrepared();

	public ArrayList<Order> getNotPreparable();

	public ArrayList<Order> getNotDelivered();

	public ArrayList<Order> getDelivered();

	public void addOrder(Order order);

	public void removeOrder(Order order);

	public void seeOrderToNotPrepared(Order order);

	public void seeOrderToNotPreparable(Order order);

	public void prepareOrder(Order order);

	public void deliverOrder(Order order);

	public void removeTableAllOrders(int tableNum);

}
