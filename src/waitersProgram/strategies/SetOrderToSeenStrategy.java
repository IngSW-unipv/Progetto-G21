package waitersProgram.strategies;

import java.util.Iterator;

import waitersProgram.controller.OrderStatus;
import waitersProgram.controller.Restaurant;
import waitersProgram.controller.WaitersControlPanelController;
import waitersProgram.model.Order;
import waitersProgram.model.OrderManager;

/**
 * Called from ListeningPost after ChefsController setOrderToSeenToPreparable
 * invocation.
 */

public class SetOrderToSeenStrategy extends StrategyAbstract {
	private static SetOrderToSeenStrategy instance = null;

	private SetOrderToSeenStrategy(Restaurant restaurant) {
		super(restaurant);
	}

	public static SetOrderToSeenStrategy getInstance(Restaurant restaurant) {
		if (instance == null) {
			instance = new SetOrderToSeenStrategy(restaurant);
		}
		return instance;
	}

	/** In this scenario, args[0]: orderNum. */
	@Override
	public void execute(String[] args) {
		OrderManager orderManagerInstance = Restaurant.getInstance().getOrderManager();
		Integer orderNum = Integer.parseInt(args[0]);
		Order currentOrder = null;
		Order orderToBeSetted = null;
		Iterator<Order> iterator = orderManagerInstance.getNotSeen().iterator();
		while (iterator.hasNext()) {
			currentOrder = iterator.next();
			if (currentOrder.getOrderNum() == orderNum) {
				orderToBeSetted = currentOrder;
				break;
			}
		}

		if (orderToBeSetted != null) {
			orderManagerInstance.seeOrderToNotPrepared(orderToBeSetted);
			WaitersControlPanelController controller = WaitersControlPanelController.getInstance();
			controller.modifyOrderStatus(Integer.parseInt(args[0]), OrderStatus.SEEN);
		}
	}
}
