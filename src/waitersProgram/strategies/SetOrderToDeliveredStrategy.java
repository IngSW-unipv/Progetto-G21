package waitersProgram.strategies;

import java.util.Iterator;

import waitersProgram.controller.ListeningPost;
import waitersProgram.controller.Restaurant;
import waitersProgram.model.Order;
import waitersProgram.model.OrderManager;

/**
 * Called from setOrderToDelivered method in WaitersOrderUpdateFrameController.
 */

public class SetOrderToDeliveredStrategy extends StrategyAbstract {
	private static SetOrderToDeliveredStrategy instance = null;

	/** Class constructor. */
	private SetOrderToDeliveredStrategy(Restaurant restaurant) {
		super(restaurant);
	}

	/**
	 * Static method that returns a SetOrderToDeliveredStrategy instance in order to
	 * follow Singleton pattern.
	 * 
	 * @return SetOrderToDeliveredStrategy instance.
	 * @param restaurant (facade controller).
	 */
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
		Order orderToBeSetted = null;
		Iterator<Order> iterator = orderManagerInstance.getNotDelivered().iterator();
		while (iterator.hasNext()) {
			currentOrder = iterator.next();
			if (currentOrder.getOrderNum() == orderNum) {
				orderToBeSetted = currentOrder;
				break;
			}
		}

		if (orderToBeSetted != null) {
			orderManagerInstance.deliverOrder(orderToBeSetted);
			ListeningPost post = ListeningPost.getInstance();
			post.sendMessage("SET_DELIVERED, " + Integer.toString(orderToBeSetted.getOrderNum()));
		}
	}
}
