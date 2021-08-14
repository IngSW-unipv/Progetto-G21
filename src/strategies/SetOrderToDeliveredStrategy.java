package strategies;

import java.util.Iterator;

import controller.Restaurant;
import model.MenuEntry;
import model.Order;
import model.OrderManager;

public class SetOrderToDeliveredStrategy extends StrategyAbstract {
	private static SetOrderToDeliveredStrategy instance = null;

	private SetOrderToDeliveredStrategy(Restaurant restaurant) {
		super(restaurant);
	}

	public static SetOrderToDeliveredStrategy getInstance(Restaurant restaurant) {
		if (instance == null) {
			instance = new SetOrderToDeliveredStrategy(restaurant);
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
