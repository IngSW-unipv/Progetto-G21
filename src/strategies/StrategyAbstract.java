package strategies;

import systemcontrollers.SystemController;

/**
 * Strategy abstract, each strategy has a execute method in common, this is the
 * gateway to every other method you put in your strategy, since it's the only
 * method that is invoked by the controller.
 */

public abstract class StrategyAbstract implements StrategyInterface {
	private SystemController controller;

	public static String getStrategyName() {
		return System.class.getSimpleName();
	}

	protected StrategyAbstract(SystemController controller) {
		this.controller = controller;
	}

	public SystemController getController() {
		return controller;
	}

	@Override
	public abstract void execute(String[] args);
}
