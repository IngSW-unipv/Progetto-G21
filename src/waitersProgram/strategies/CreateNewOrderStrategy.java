package waitersProgram.strategies;

import waitersProgram.controller.Restaurant;
import waitersProgram.model.MenuEntry;
import waitersProgram.model.Order;
import waitersProgram.model.OrderManager;

/**
 * Called from addNewOrder method in WaitersControlPanelController. IS OK
 */

public class CreateNewOrderStrategy extends StrategyAbstract {
	private static CreateNewOrderStrategy instance = null;

	private CreateNewOrderStrategy(Restaurant restaurant) {
		super(restaurant);
	}

	public static CreateNewOrderStrategy getInstance(Restaurant restaurant) {
		if (instance == null) {
			instance = new CreateNewOrderStrategy(restaurant);
		}
		return instance;
	}

	/** In this scenario, args[0]: tableNum, args[1]: dishEntry. */
	@Override
	public void execute(String[] args) {
		OrderManager orderManagerInstance = Restaurant.getInstance().getOrderManager();
		orderManagerInstance.addOrder(new Order(Integer.parseInt(args[0]), new MenuEntry(args[1])));

		// post.sendMessage()
	}
}
