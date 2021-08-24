package waitersProgram.strategies;

import java.util.Iterator;

import waitersProgram.controller.Restaurant;
import waitersProgram.model.Order;
import waitersProgram.model.OrderManager;

public class RemoveOrderStrategy extends StrategyAbstract {
	private static RemoveOrderStrategy instance = null;

	public RemoveOrderStrategy(Restaurant restaurant) {
		super(restaurant);
	}

	public static RemoveOrderStrategy getInstance(Restaurant restaurant) {
		if (instance == null) {
			instance = new RemoveOrderStrategy(restaurant);
		}
		return instance;
	}

	/** In this scenario, args[0]: orderNum. */
	@Override
	public void execute(String[] args) {
		OrderManager orderManagerInstance = Restaurant.getInstance().getOrderManager();
		Integer orderNum = Integer.parseInt(args[0]);
		Order currentOrder = null;
		Iterator<Order> iterator = orderManagerInstance.getNotSeen().iterator();
		while (iterator.hasNext()) {
			currentOrder = iterator.next();
			if (currentOrder.getOrderNum() == orderNum) {
				break;
			}
		}
		orderManagerInstance.removeOrder(currentOrder);

		// WaitersGuiController updateOrders()
	}
}
