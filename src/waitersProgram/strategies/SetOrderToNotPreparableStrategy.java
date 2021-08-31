package waitersProgram.strategies;

import java.util.Iterator;

import waitersProgram.controller.OrderStatus;
import waitersProgram.controller.Restaurant;
import waitersProgram.controller.WaitersControlPanelController;
import waitersProgram.model.Order;
import waitersProgram.model.OrderManager;

/**
 * Called from ListeningPost after ChefsController setOrderToNotPreparable
 * invocation.
 */

public class SetOrderToNotPreparableStrategy extends StrategyAbstract {
	private static SetOrderToNotPreparableStrategy instance = null;

	/** Class constructor. */
	private SetOrderToNotPreparableStrategy(Restaurant restaurant) {
		super(restaurant);
	}

	/**
	 * Static method that returns a SetOrderToNotPreparableStrategy instance in
	 * order to follow Singleton pattern.
	 * 
	 * @return SetOrderToNotPreparableStrategy instance.
	 */
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
		Order orderToBeSetted = null;
		Iterator<Order> iterator = orderManagerInstance.getNotSeen().iterator();
		while (iterator.hasNext()) {
			currentOrder = iterator.next();
			if (currentOrder.getOrderNum() == orderNum) {
				orderToBeSetted = currentOrder;
			}
		}

		if (orderToBeSetted != null) {
			orderManagerInstance.seeOrderToNotPreparable(orderToBeSetted);
			WaitersControlPanelController controller = WaitersControlPanelController.getInstance();
			controller.modifyOrderStatus(orderToBeSetted.getOrderNum(), OrderStatus.NOT_PREPARABLE);
		}
	}
}
