package strategies;

import controller.Restaurant;
import model.TableManager;

public class RemoveTableStrategy extends StrategyAbstract {
	private static RemoveTableStrategy instance = null;

	private RemoveTableStrategy(Restaurant restaurant) {
		super(restaurant);
	}

	public static RemoveTableStrategy getInstance(Restaurant restaurant) {
		if (instance == null) {
			instance = new RemoveTableStrategy(restaurant);
		}
		return instance;
	}

	@Override
	public void execute(String[] args) {
		TableManager tableManagerInstance = Restaurant.getInstance().getTableManager();
		tableManagerInstance.removeTable(Integer.parseInt(args[0]));
	}

}
