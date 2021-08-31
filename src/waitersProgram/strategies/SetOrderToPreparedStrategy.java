package waitersProgram.strategies;

import java.util.Iterator;

import waitersProgram.controller.OrderStatus;
import waitersProgram.controller.Restaurant;
import waitersProgram.controller.WaitersControlPanelController;
import waitersProgram.model.Order;
import waitersProgram.model.OrderManager;

/**
 * Called from ListeningPost after ChefsController setOrderToPrepared
 * invocation.
 */
public class SetOrderToPreparedStrategy extends StrategyAbstract {
	private static SetOrderToPreparedStrategy instance = null;

	/** Class constructor. */
	private SetOrderToPreparedStrategy(Restaurant restaurant) {
		super(restaurant);
	}

	/**
	 * Static method that returns a SetOrderToPreparedStrategy instance in order to
	 * follow Singleton pattern.
	 * 
	 * @return SetOrderToPreparedStrategy instance.
	 */
	public static SetOrderToPreparedStrategy getInstance(Restaurant restaurant) {
		if (instance == null) {
			instance = new SetOrderToPreparedStrategy(restaurant);
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
		Iterator<Order> iterator = orderManagerInstance.getNotPrepared().iterator();
		while (iterator.hasNext()) {
			currentOrder = iterator.next();
			if (currentOrder.getOrderNum() == orderNum) {
				orderToBeSetted = currentOrder;
			}
		}

		if (orderToBeSetted != null) {
			orderManagerInstance.prepareOrder(currentOrder);
			WaitersControlPanelController controller = WaitersControlPanelController.getInstance();
			controller.modifyOrderStatus(currentOrder.getOrderNum(), OrderStatus.PREPARED);
		}
	}
}
