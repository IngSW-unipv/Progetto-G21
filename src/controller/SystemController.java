package controller;

import java.util.HashMap;

import model.Restaurant;
import strategies.GenericTestStrategy;
import strategies.StrategyAbstract;

/**
 * The Controller class. It utilizes the strategy pattern in order to provide
 * varied services to the bound program.
 */

public class SystemController {
	private static SystemController controller;
	private ListeningPost post;
	private HashMap<String, StrategyAbstract> strategies;
	private Restaurant restaurant;

	public static SystemController getInstance() {
		if (controller == null) {
			controller = new SystemController();
			return controller;
		} else
			return controller;
	}

	private SystemController() {
		restaurant = Restaurant.getInstance();
		strategies = new HashMap<String, StrategyAbstract>();
		post = ListeningPost.getInstance();
		post.bindController(this);
		createStrategies();
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	/**
	 * Eventually this method will auto-instantiate strategies, but until I've
	 * figured out classpath lib, it will be required to manually load the single
	 * strategies. The String is the name of the strategy, the value is the strategy
	 * itself.
	 */
	private void createStrategies() {
		strategies.put(GenericTestStrategy.getStrategyName(), GenericTestStrategy.createStrategy(this));
	}

	/**
	 * This method checks if the strategy exists and, if it does, invokes it with
	 * the argument string vector passed in.
	 */
	public void strategyCall(String strategyRequired, String[] args) {
		StrategyAbstract s;
		if ((s = strategies.get(strategyRequired)) != null) {
			s.execute(args);
		} else {
			System.out.println("Strategy does not exist");
		}
	}

	/** Just a test method to test strategy access to the controller. */
	public void testMethod() {
		System.out.println("This is a test method!");
	}

}
