package waitersProgram.strategies;

import java.util.Iterator;

import waitersProgram.controller.Restaurant;
import waitersProgram.model.Order;
import waitersProgram.model.OrderManager;

public class SetOrderToNotPreparableStrategy extends StrategyAbstract {
	private static SetOrderToNotPreparableStrategy instance = null;

	private SetOrderToNotPreparableStrategy(Restaurant restaurant) {
		super(restaurant);
	}

	public static SetOrderToNotPreparableStrategy getInstance(Restaurant restaurant) {
		if (instance == null) {
			instance = new SetOrderToNotPreparableStrategy(restaurant);
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
		orderManagerInstance.seeOrderToNotPreparable(currentOrder);
		// WaitersGuiController.method call
	}
}
