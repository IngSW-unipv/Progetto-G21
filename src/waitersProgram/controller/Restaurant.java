package waitersProgram.controller;

import java.util.HashMap;
import java.util.List;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import waitersProgram.model.Menu;
import waitersProgram.model.OrderManager;
import waitersProgram.model.TableManager;
import waitersProgram.strategies.StrategyAbstract;

/**
 * The Restaurant class. Is used as the backend's facade controller.
 */
public class Restaurant {

	private static Restaurant instance = null;
	private Menu restaurantMenu;
	private OrderManager orderManager;
	private TableManager tableManager;
	private ListeningPost post;
	private HashMap<String, StrategyAbstract> strategies;

	/**
	 * Class constructor method.
	 */
	private Restaurant() {
		strategies = new HashMap<String, StrategyAbstract>();
		restaurantMenu = Menu.getInstance();
		orderManager = OrderManager.getInstance();
		tableManager = TableManager.getInstance();
		post = ListeningPost.getInstance();
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
	 * Method that auto-instantiate strategies using classgraph.
	 */
	private void createStrategies() {
		try (ScanResult sr = new ClassGraph().acceptPackages("waitersProgram.strategies").enableClassInfo().scan()) {
			ClassInfoList cil = sr.getSubclasses("waitersProgram.strategies.StrategyAbstract");
			List<Class<?>> lt = cil.loadClasses();
			for (Class<?> ct : lt) {
				strategies.put(((String) ct.getMethod("getStrategyName", ((Class<?>) null)).invoke(null)),
						((StrategyAbstract) ct.getMethod("getInstance", this.getClass()).invoke(this)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method checks if the strategy exists and, if it does, invokes it with
	 * the argument string vector passed in.
	 * 
	 * @param strategyRequired specifies the involved strategy.
	 * @param args             specifies strategies' arguments.
	 *
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
