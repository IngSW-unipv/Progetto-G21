package waitersProgram.strategies;

import waitersProgram.controller.Restaurant;
import waitersProgram.model.Menu;

/** Called from addNewEntry method in WaitersControlPanelController. */

public class AddNewEntryStrategy extends StrategyAbstract {
	private static AddNewEntryStrategy instance = null;

	private AddNewEntryStrategy(Restaurant restaurant) {
		super(restaurant);
	}

	public static AddNewEntryStrategy getInstance(Restaurant restaurant) {
		if (instance == null) {
			instance = new AddNewEntryStrategy(restaurant);
		}
		return instance;
	}

	/** In this scenario, args[0]: entry name, args[1]: entry price. */
	@Override
	public void execute(String[] args) {
		Menu menuInstance = Restaurant.getInstance().getRestaurantMenu();
		menuInstance.addMenuEntry(args[0].trim() + ", " + args[1].trim());
	}
}
