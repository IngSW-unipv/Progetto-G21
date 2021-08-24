package waitersProgram.strategies;

import waitersProgram.controller.Restaurant;
import waitersProgram.model.TableManager;

/**
 * Called from addNewTable method in WaitersControlPanelController. IS OK
 */

public class AddNewTableStrategy extends StrategyAbstract {
	private static AddNewTableStrategy instance = null;

	private AddNewTableStrategy(Restaurant restaurant) {
		super(restaurant);
	}

	public static AddNewTableStrategy getInstance(Restaurant restaurant) {
		if (instance == null) {
			instance = new AddNewTableStrategy(restaurant);
		}
		return instance;
	}

	/** In this scenario, args[0]: tableNum. */
	@Override
	public void execute(String[] args) {
		TableManager tableManagerInstance = Restaurant.getInstance().getTableManager();
		tableManagerInstance.addTable(Integer.parseInt(args[0]));
	}

}
