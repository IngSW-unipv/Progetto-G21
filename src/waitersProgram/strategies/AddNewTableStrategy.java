package waitersProgram.strategies;

import waitersProgram.controller.Restaurant;
import waitersProgram.model.TableManager;

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

	@Override
	public void execute(String[] args) {
		TableManager tableManagerInstance = Restaurant.getInstance().getTableManager();
		tableManagerInstance.addTable(Integer.parseInt(args[0]));
	}

}
