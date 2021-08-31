package waitersProgram.strategies;

import java.util.Iterator;

import waitersProgram.controller.ListeningPost;
import waitersProgram.controller.Restaurant;
import waitersProgram.model.Order;
import waitersProgram.model.OrderManager;

/** Called from removeOrder method in WaitersControlPanelController. */

public class RemoveOrderStrategy extends StrategyAbstract {
	private static RemoveOrderStrategy instance = null;

	/**
	 * Class constructor.
	 * 
	 * @param restaurant (facade controller).
	 */
	public RemoveOrderStrategy(Restaurant restaurant) {
		super(restaurant);
	}

	/**
	 * Static method that returns a RemoveOrderStrategy instance in order to follow
	 * Singleton pattern.
	 * 
	 * @return RemoveOrderStrategy instance.
	 * @param restaurant (facade controller).
	 */
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
		Order orderToBeRemoved = null;
		Iterator<Order> iterator = orderManagerInstance.getNotSeen().iterator();
		while (iterator.hasNext()) {
			currentOrder = iterator.next();
			if (currentOrder.getOrderNum() == orderNum) {
				orderToBeRemoved = currentOrder;
				break;
			}
		}

		if (orderToBeRemoved != null) {
			orderManagerInstance.removeOrder(orderToBeRemoved);
			ListeningPost post = ListeningPost.getInstance();
			post.sendMessage("REMOVE, " + Integer.toString(orderToBeRemoved.getOrderNum()));
		}
	}
}
