package waitersProgram.strategies;

import waitersProgram.controller.Restaurant;

/**
 * This is a strategy example. Every strategy must be constructed via the
 * singleton pattern, otherwise it will cause problems. I can't force such
 * creation as far as i know from the abstract, unless i restructure the
 * methods, so follow this comment when creating, at least for now, eventually i
 * will find a way to force it.
 */

public class StrategyExample extends StrategyAbstract {
	private static StrategyExample instance;

	public static StrategyExample createStrategy(Restaurant restaurant) {
		if (instance == null) {
			instance = new StrategyExample(restaurant);
			return instance;
		} else
			return instance;
	}

	public static StrategyExample createStrategy() {
		if (instance != null)
			return instance;
		else {
			System.out.println(
					"Strategy has not yet been istantiated, use createTestStrategy(Controller c) to instantiate");
			return null;
		}

	}

	private StrategyExample(Restaurant restaurant) {
		super(restaurant);
	}

	@Override
	public void execute(String[] args) {

	}
}
