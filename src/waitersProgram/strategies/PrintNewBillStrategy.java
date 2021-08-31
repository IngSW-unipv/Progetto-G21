package waitersProgram.strategies;

import java.util.ArrayList;
import java.util.Iterator;

import waitersProgram.controller.ListeningPost;
import waitersProgram.controller.Restaurant;
import waitersProgram.model.Bill;
import waitersProgram.model.Order;
import waitersProgram.model.OrderManager;

/** Called from printBill method in WaitersControlPanelController. */

public class PrintNewBillStrategy extends StrategyAbstract {
	private static PrintNewBillStrategy instance = null;
	private static Bill billToPrint;

	/**
	 * Class constructor.
	 * 
	 * @param restaurant (facade controller).
	 */
	public PrintNewBillStrategy(Restaurant restaurant) {
		super(restaurant);
	}

	/**
	 * Static method that returns a PrintNewBillStrategy instance in order to follow
	 * Singleton pattern.
	 * 
	 * @return PrintNewBillStrategy instance.
	 * @param restaurant (facade controller).
	 */
	public static PrintNewBillStrategy getInstance(Restaurant restaurant) {
		if (instance == null) {
			instance = new PrintNewBillStrategy(restaurant);
		}
		return instance;
	}

	/** In this scenario, args[0]: tableNum. */
	@Override
	public void execute(String[] args) {
		OrderManager orderManager = Restaurant.getInstance().getOrderManager();
		ListeningPost post = ListeningPost.getInstance();
		ArrayList<Order> ordersToBeRemoved = orderManager.getTableAllOrders(Integer.parseInt(args[0].trim()));
		Iterator<Order> iterator = ordersToBeRemoved.iterator();

		while (iterator.hasNext()) {
			Order currentOrder = iterator.next();
			post.sendMessage("REMOVE, " + Integer.toString(currentOrder.getOrderNum()));
		}

		billToPrint = new Bill(Integer.parseInt(args[0]));
	}

	/** @return billToPrint. */
	public static Bill getBill() {
		return billToPrint;
	}
}
