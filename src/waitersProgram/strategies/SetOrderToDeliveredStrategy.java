package waitersProgram.strategies;

import java.util.Iterator;

import waitersProgram.controller.ListeningPost;
import waitersProgram.controller.Restaurant;
import waitersProgram.model.Order;
import waitersProgram.model.OrderManager;

/**
 * Called from setOrderToDelivered method in WaitersOrderUpdateFrameController.
 * IS OK
 * 
 */

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

	/** In this scenario, args[0]: orderNum. */
	@Override
	public void execute(String[] args) {
		OrderManager orderManagerInstance = Restaurant.getInstance().getOrderManager();
		Integer orderNum = Integer.parseInt(args[0]);
		Order currentOrder = null;
		Iterator<Order> iterator = orderManagerInstance.getNotDelivered().iterator();
		while (iterator.hasNext()) {
			currentOrder = iterator.next();
			if (currentOrder.getOrderNum() == orderNum) {
				break;
			}
		}

		if (currentOrder != null) {
			orderManagerInstance.deliverOrder(currentOrder);
			ListeningPost post = ListeningPost.getInstance();
			post.sendMessage("SET_DELIVERED, " + Integer.toString(currentOrder.getOrderNum()));
		}
	}
}
