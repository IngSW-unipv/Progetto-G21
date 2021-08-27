package waitersProgram.strategies;

import waitersProgram.controller.ListeningPost;
import waitersProgram.controller.Restaurant;
import waitersProgram.controller.WaitersController;
import waitersProgram.model.MenuEntry;
import waitersProgram.model.Order;
import waitersProgram.model.OrderManager;

/** Called from addNewOrder method in WaitersControlPanelController. */

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
		Order currentOrder = new Order(Integer.parseInt(args[0]), new MenuEntry(args[1]));
		orderManagerInstance.addOrder(currentOrder);

		WaitersController controller = WaitersController.getInstance();
		controller.addOrderToTableView(currentOrder);

		ListeningPost post = ListeningPost.getInstance();
		post.sendMessage("ADD, " + Integer.toString(currentOrder.getOrderNum()) + ", " + currentOrder.getTableNum()
				+ ", " + currentOrder.getOrderedEntry().getDishName() + ", "
				+ currentOrder.getOrderedEntry().getDishPrice());
	}
}
