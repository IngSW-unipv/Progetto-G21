package waitersProgram.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import waitersProgram.model.Menu;
import waitersProgram.model.OrderManager;
import waitersProgram.model.TableManager;
import waitersProgram.strategies.AddNewEntryStrategy;
import waitersProgram.strategies.AddNewTableStrategy;
import waitersProgram.strategies.CreateNewOrderStrategy;
import waitersProgram.strategies.PrintNewBillStrategy;
import waitersProgram.strategies.RemoveEntryStrategy;
import waitersProgram.strategies.RemoveOrderStrategy;
import waitersProgram.strategies.RemoveTableStrategy;
import waitersProgram.strategies.SetOrderToDeliveredStrategy;
import waitersProgram.strategies.SetOrderToNotPreparableStrategy;
import waitersProgram.strategies.SetOrderToPreparedStrategy;
import waitersProgram.strategies.SetOrderToSeenStrategy;
import waitersProgram.strategies.StrategyAbstract;

/**
 * The Restaurant class. Is used as the backend's facade controller.
 */
public class Restaurant {

	private static Restaurant instance = null;
	private Menu restaurantMenu;
	private OrderManager orderManager;
	private TableManager tableManager;
	private HashMap<String, StrategyAbstract> strategies;

	/**
	 * Class constructor method.
	 */
	private Restaurant() {
		strategies = new HashMap<String, StrategyAbstract>();
		restaurantMenu = Menu.getInstance();
		orderManager = OrderManager.getInstance();
		tableManager = TableManager.getInstance();
		createStrategiesWithoutClassGraph();
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
	 * Method that auto-instantiate strategies using classgraph.
	 */
	private void createStrategies() {
		try (ScanResult sr = new ClassGraph().acceptPackages("waitersProgram.strategies").enableClassInfo().scan()) {
			System.out.println(sr);
			ClassInfoList cil = sr.getSubclasses("waitersProgram.strategies.StrategyAbstract");
			List<Class<?>> lt = cil.loadClasses();
			Iterator<Class<?>> iterator = lt.iterator();
			while (iterator.hasNext()) {
				Class<?> currentClass = iterator.next();
				StrategyAbstract sa = ((StrategyAbstract) currentClass.getMethod("getInstance", Restaurant.class)
						.invoke(Restaurant.getInstance()));
				strategies.put(sa.getStrategyName(), sa);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that fills strategies HashMap without classgraph.
	 */
	private void createStrategiesWithoutClassGraph() {
		strategies.put("AddNewEntryStrategy", AddNewEntryStrategy.getInstance(this));
		strategies.put("AddNewTableStrategy", AddNewTableStrategy.getInstance(this));
		strategies.put("CreateNewOrderStrategy", CreateNewOrderStrategy.getInstance(this));
		strategies.put("PrintNewBillStrategy", PrintNewBillStrategy.getInstance(this));
		strategies.put("RemoveEntryStrategy", RemoveEntryStrategy.getInstance(this));
		strategies.put("RemoveOrderStrategy", RemoveOrderStrategy.getInstance(this));
		strategies.put("RemoveTableStrategy", RemoveTableStrategy.getInstance(this));
		strategies.put("SetOrderToDeliveredStrategy", SetOrderToDeliveredStrategy.getInstance(this));
		strategies.put("SetOrderToNotPreparableStrategy", SetOrderToNotPreparableStrategy.getInstance(this));
		strategies.put("SetOrderToPreparedStrategy", SetOrderToPreparedStrategy.getInstance(this));
		strategies.put("SetOrderToSeenStrategy", SetOrderToSeenStrategy.getInstance(this));
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
