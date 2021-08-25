package waitersProgram.strategies;

import java.util.Iterator;

import waitersProgram.controller.OrderStatus;
import waitersProgram.controller.Restaurant;
import waitersProgram.controller.WaitersController;
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
		Iterator<Order> iterator = orderManagerInstance.getNotSeen().iterator();
		while (iterator.hasNext()) {
			Order currentOrder = iterator.next();
			if (currentOrder.getOrderNum() == orderNum) {
				orderManagerInstance.seeOrderToNotPreparable(currentOrder);
			}
		}
		WaitersController controller = WaitersController.getInstance();
		controller.modifyOrderStatus(Integer.parseInt(args[0]), OrderStatus.NOT_PREPARABLE);
	}
}
