package strategies;

import java.util.Iterator;

import controller.Restaurant;
import model.MenuEntry;
import model.Order;
import model.OrderManager;

public class SetOrderDeliveredStrategy extends StrategyAbstract {
	private static SetOrderDeliveredStrategy instance = null;

	private SetOrderDeliveredStrategy(Restaurant restaurant) {
		super(restaurant);
	}

	public static SetOrderDeliveredStrategy getInstance(Restaurant restaurant) {
		if (instance == null) {
			instance = new SetOrderDeliveredStrategy(restaurant);
		}
		return instance;
	}

	@Override
	public void execute(String[] args) {
		OrderManager orderManagerInstance = Restaurant.getInstance().getOrderManager();

		Integer tableNum = Integer.parseInt(args[0]);
		MenuEntry entry = new MenuEntry(args[1]);

		Iterator<Order> iterator = orderManagerInstance.getNotDelivered().iterator();
		Order orderToBeModified = null;
		while (iterator.hasNext()) {
			orderToBeModified = iterator.next();
			if (orderToBeModified.equals(new Order(tableNum, entry))) {
				break;
			}
		}
		orderManagerInstance.deliverOrder(orderToBeModified);
	}
}
