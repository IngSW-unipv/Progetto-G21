package strategies;

import controller.Restaurant;
import model.Menu;

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

	@Override
	public void execute(String[] args) {
		Menu menuInstance = Restaurant.getInstance().getRestaurantMenu();
		menuInstance.addMenuEntry(args[0]);
	}
}
