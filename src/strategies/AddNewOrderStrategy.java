package strategies;

import controller.Restaurant;

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

	@Override
	public void execute(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * In this scenario, args[0]: tableNum, args[1]:
	 * 
	 * @Override public void execute(String[] args) { SystemController
	 *           controllerInstance = super.getController(); Menu menuInstance =
	 *           super.getController().getRestaurant().getRestaurantMenu();
	 *           controllerInstance.getRestaurant().getOrderManager() .addOrder(new
	 *           Order(Integer.parseInt(args[0]), )));
	 * 
	 *           }
	 */

}
