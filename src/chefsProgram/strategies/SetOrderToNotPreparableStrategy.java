package chefsProgram.strategies;

import java.util.Iterator;

import waitersProgram.controller.Restaurant;
import waitersProgram.model.MenuEntry;
import waitersProgram.model.Order;
import waitersProgram.model.OrderManager;
import waitersProgram.strategies.StrategyAbstract;

public class SetOrderToNotPreparableStrategy extends StrategyAbstract {
	private static SetOrderToNotPreparableStrategy instance = null;

	private SetOrderToNotPreparableStrategy(Restaurant restaurant) {
		super(restaurant);
	}

	public static SetOrderToNotPreparableStrategy getInstance(Restaurant restaurant) {
		if (instance == null) {
			instance = new SetOrderToNotPreparableStrategy(restaurant);
		}
		return instance;
	}

	@Override
	public void execute(String[] args) {
		OrderManager orderManagerInstance = Restaurant.getInstance().getOrderManager();

		Integer tableNum = Integer.parseInt(args[0]);
		MenuEntry entry = new MenuEntry(args[1]);

		Iterator<Order> iterator = orderManagerInstance.getNotSeen().iterator();
		Order orderToBeModified = null;
		while (iterator.hasNext()) {
			orderToBeModified = iterator.next();
			if (orderToBeModified.equals(new Order(tableNum, entry))) {
				break;
			}
		}
		orderManagerInstance.seeOrderToNotPreparable(orderToBeModified);

		// ChefsGuiController updateOrders()
	}
}
