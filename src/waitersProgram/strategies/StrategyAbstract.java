package waitersProgram.strategies;

import waitersProgram.controller.Restaurant;

/**
 * Strategy abstract class. Each strategy has a execute method in common, this
 * is the gateway to every other method you put in your strategy, since it's the
 * only method that is invoked by the controller.
 */
public abstract class StrategyAbstract implements StrategyInterface {
	private Restaurant restaurant;

	public StrategyAbstract(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public String getStrategyName() {
		return this.getClass().getSimpleName();
	}

	public Restaurant getController() {
		return restaurant;
	}

	@Override
	public abstract void execute(String[] args);
}
