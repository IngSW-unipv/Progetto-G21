package chefsProgram.strategies;

import java.util.Iterator;

import waitersProgram.controller.Restaurant;
import waitersProgram.model.MenuEntry;
import waitersProgram.model.Order;
import waitersProgram.model.OrderManager;
import waitersProgram.strategies.StrategyAbstract;

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

	@Override
	public void execute(String[] args) {
		OrderManager orderManagerInstance = Restaurant.getInstance().getOrderManager();

		Integer tableNum = Integer.parseInt(args[0]);
		MenuEntry entry = new MenuEntry(args[1]);

		Iterator<Order> iterator = orderManagerInstance.getNotPrepared().iterator();
		Order orderToBeModified = null;
		while (iterator.hasNext()) {
			orderToBeModified = iterator.next();
			if (orderToBeModified.equals(new Order(tableNum, entry))) {
				break;
			}
		}
		orderManagerInstance.prepareOrder(orderToBeModified);
	}

}
