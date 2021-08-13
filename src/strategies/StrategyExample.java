package strategies;

import controller.SystemController;

/**
 * This is a strategy example. Every strategy must be constructed via the
 * singleton pattern, otherwise it will cause problems. I can't force such
 * creation as far as i know from the abstract, unless i restructure the
 * methods, so follow this comment when creating, at least for now, eventually i
 * will find a way to force it.
 */

public class StrategyExample extends StrategyAbstract {
	private static StrategyExample instance;

	public static StrategyExample createStrategy(SystemController controller) {
		if (instance == null) {
			instance = new StrategyExample(controller);
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

	private StrategyExample(SystemController controller) {
		super(controller);
	}

	@Override
	public void execute(String[] args) {
		System.out.println("This example strategy will print the contents of the args array.");
		for (String s : args) {
			System.out.println(s);
		}
		System.out.println("Now, it will grab stuff using controller methods and print it");
		super.getController().testMethod();
	}
}
