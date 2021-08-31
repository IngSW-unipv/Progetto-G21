package waitersProgram.strategies;

import waitersProgram.controller.Restaurant;

/**
 * Strategy abstract class. Each strategy has a execute method in common, this
 * is the gateway to every other method you put in your strategy, since it's the
 * only method that is invoked by the controller.
 */
public abstract class StrategyAbstract implements StrategyInterface {
	private Restaurant restaurant;

	/** Class constructor. */
	public StrategyAbstract(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	/** @return strategy name. */
	public String getStrategyName() {
		return this.getClass().getSimpleName();
	}

	/** @return Restaurant instance (facade controller). */
	public Restaurant getController() {
		return restaurant;
	}

	/** Abstract method used to launch the strategy. */
	@Override
	public abstract void execute(String[] args);
}
