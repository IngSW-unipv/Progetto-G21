package waitersProgram.strategies;

import waitersProgram.controller.ListeningPost;
import waitersProgram.controller.Restaurant;
import waitersProgram.model.MenuEntry;
import waitersProgram.model.Order;
import waitersProgram.model.OrderManager;

/** Called from addNewOrder method in WaitersControlPanelController. */

public class CreateNewOrderStrategy extends StrategyAbstract {
	private static CreateNewOrderStrategy instance = null;
	private static Order orderToDisplay;

	/** Class constructor. */
	private CreateNewOrderStrategy(Restaurant restaurant) {
		super(restaurant);
	}

	/**
	 * Static method that returns a CreateNewOrderStrategy instance in order to
	 * follow Singleton pattern.
	 * 
	 * @return CreateNewOrderStrategy instance.
	 */
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
		Order currentOrder = new Order(Integer.parseInt(args[0]), new MenuEntry(args[1]));
		orderManagerInstance.addOrder(currentOrder);

		orderToDisplay = currentOrder;

		ListeningPost post = ListeningPost.getInstance();
		post.sendMessage("ADD, " + Integer.toString(currentOrder.getOrderNum()) + ", " + currentOrder.getTableNum()
				+ ", " + currentOrder.getOrderedEntry().getDishName() + ", "
				+ currentOrder.getOrderedEntry().getDishPrice());
	}

	public static Order getOrder() {
		return orderToDisplay;
	}
}
