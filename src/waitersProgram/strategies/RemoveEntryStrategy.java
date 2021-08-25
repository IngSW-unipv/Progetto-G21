package waitersProgram.strategies;

import waitersProgram.controller.Restaurant;
import waitersProgram.model.Menu;

/** Called from removeEntry method in WaitersControlPanelController. */

public class RemoveEntryStrategy extends StrategyAbstract {
	private static RemoveEntryStrategy instance = null;

	private RemoveEntryStrategy(Restaurant restaurant) {
		super(restaurant);
	}

	public static RemoveEntryStrategy getInstance(Restaurant restaurant) {
		if (instance == null) {
			instance = new RemoveEntryStrategy(restaurant);
		}
		return instance;
	}

	/** In this scenario, args[0]: dishEntry (name, price). */
	@Override
	public void execute(String[] args) {
		Menu menuInstance = Restaurant.getInstance().getRestaurantMenu();
		menuInstance.removeMenuEntry(args[0]);
	}
}
