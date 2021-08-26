package waitersProgram.strategies;

import java.util.Iterator;

import waitersProgram.controller.OrderStatus;
import waitersProgram.controller.Restaurant;
import waitersProgram.controller.WaitersController;
import waitersProgram.model.Order;
import waitersProgram.model.OrderManager;

/**
 * Called from ListeningPost after ChefsController setOrderToPrepared
 * invocation.
 */
public class SetOrderToPreparedStrategy extends StrategyAbstract {
	private static SetOrderToPreparedStrategy instance = null;

	private SetOrderToPreparedStrategy(Restaurant restaurant) {
		super(restaurant);
	}

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
		Iterator<Order> iterator = orderManagerInstance.getNotPrepared().iterator();
		while (iterator.hasNext()) {
			Order currentOrder = iterator.next();
			if (currentOrder.getOrderNum() == orderNum) {
				orderManagerInstance.prepareOrder(currentOrder);
			}
		}
		WaitersController controller = WaitersController.getInstance();
		controller.modifyOrderStatus(Integer.parseInt(args[0]), OrderStatus.PREPARED);
	}
}
