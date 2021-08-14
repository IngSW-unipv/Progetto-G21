package strategies;

import controller.Restaurant;
import model.MenuEntry;
import model.Order;
import model.OrderManager;

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

	/*
	 * In this scenario,args[0]:tableNum,args[1]:
	 * 
	 * @Override public void execute(String[] args) { SystemController
	 * controllerInstance = super.getController(); Menu menuInstance =
	 * super.getController().getRestaurant().getRestaurantMenu();
	 * controllerInstance.getRestaurant().getOrderManager() .addOrder(new
	 * Order(Integer.parseInt(args[0]), )));
	 * 
	 * }
	 */

}
