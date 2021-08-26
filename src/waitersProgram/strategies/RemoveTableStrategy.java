package waitersProgram.strategies;

import waitersProgram.controller.Restaurant;
import waitersProgram.model.TableManager;

/** Called from removeTable method in WaitersControlPanelController. */

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

	/** In this scenario, args[0]: tableNum. */
	@Override
	public void execute(String[] args) {
		TableManager tableManagerInstance = Restaurant.getInstance().getTableManager();
		tableManagerInstance.removeTable(Integer.parseInt(args[0]));
	}
}
