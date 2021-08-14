package controller;

import java.util.HashMap;

import controller.guicontrollers.WaitersGuiController;
import model.Menu;
import model.OrderManager;
import model.TableManager;
import strategies.StrategyAbstract;

/**
 * The Restaurant class. Is used to unify all the other model classes in order
 * to represent a real restaurant. The class contains methods for managing
 * restaurant tables.
 */
public class Restaurant {

	private static Restaurant instance = null;
	private Menu restaurantMenu;
	private OrderManager orderManager;
	private TableManager tableManager;
	private ListeningPost post;
	private HashMap<String, StrategyAbstract> strategies;
	private WaitersGuiController waitersController;

	/**
	 * Class constructor method.
	 */
	private Restaurant() {
		restaurantMenu = Menu.getInstance();
		orderManager = OrderManager.getInstance();
		tableManager = TableManager.getInstance();
		post = ListeningPost.getInstance();
		waitersController = WaitersGuiController.getInstance();
		post.bindController(this);
		createStrategies();
	}

	/**
	 * Static method that returns a Restaurant instance in order to observe
	 * Singleton pattern. It calls the class constructor only if this has not
	 * happened before.
	 * 
	 * @return Restaurant instance.
	 */
	public static Restaurant getInstance() {
		if (instance == null) {
			instance = new Restaurant();
		}
		return instance;
	}

	/**
	 * @return Menu instance.
	 */
	public Menu getRestaurantMenu() {
		return restaurantMenu;
	}

	/**
	 * @return OrderManager instance.
	 */
	public OrderManager getOrderManager() {
		return orderManager;
	}

	/**
	 * @return TableManager instance.
	 */
	public TableManager getTableManager() {
		return tableManager;
	}

	/**
	 * @return ListeningPost instance.
	 */
	public ListeningPost getListeningPost() {
		return post;
	}

	/**
	 * @return WaitersGuiController instance.
	 */
	public WaitersGuiController getWaitersController() {
		return waitersController;
	}

	/**
	 * Eventually this method will auto-instantiate strategies, but until I've
	 * figured out classpath lib, it will be required to manually load the single
	 * strategies. The String is the name of the strategy, the value is the strategy
	 * itself.
	 */
	private void createStrategies() {
		// strategies.put(StrategyExample.getStrategyName(),
		// StrategyExample.createStrategy(this));
	}

	/**
	 * This method checks if the strategy exists and, if it does, invokes it with
	 * the argument string vector passed in.
	 * 
	 * @param strategyRequired specifies the involved strategy.
	 * @param args             specifies strategies' arguments.
	 */
	public void strategyCall(String strategyRequired, String[] args) {
		StrategyAbstract strategy;
		if ((strategy = strategies.get(strategyRequired)) != null) {
			strategy.execute(args);
		} else {
			System.out.println("Strategy does not exist!");
		}
	}
}
