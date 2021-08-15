package waitersProgram.strategies;

import waitersProgram.controller.Restaurant;
import waitersProgram.model.MenuEntry;
import waitersProgram.model.Order;
import waitersProgram.model.OrderManager;

public class AddNewOrderStrategy extends StrategyAbstract {
	private static AddNewOrderStrategy instance = null;

	private AddNewOrderStrategy(Restaurant restaurant) {
		super(restaurant);
	}

	public static AddNewOrderStrategy getInstance(Restaurant restaurant) {
		if (instance == null) {
			instance = new AddNewOrderStrategy(restaurant);
		}
		return instance;
	}

	/** In this scenario, args[0]: tableNum, args[1]: dishEntry. */
	@Override
	public void execute(String[] args) {
		OrderManager orderManagerInstance = Restaurant.getInstance().getOrderManager();
		orderManagerInstance.addOrder(new Order(Integer.parseInt(args[0]), new MenuEntry(args[1])));
	}
}
