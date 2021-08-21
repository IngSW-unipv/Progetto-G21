package chefsProgram.controller;



import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import java.util.HashMap;
import java.util.List;

import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import io.github.classgraph.ClassGraph;

import chefsProgram.strategies.StrategyAbstract;
import chefsProgram.strategies.StrategyInterface;

/**
 * The ChefsGuiController class. It will be used to control the chef's graphical
 * interface and it will contain a ListeningPost instance in order to ensure the
 * communication with the SystemController class.
 * 
 * This class and its GUI will be running on a different device than the others
 * in order to obtain a distributed system: that's the client, ListeningPost
 * contains the server.
 */

public class ChefsGuiController implements ControllerInterface {

	@FXML
	AnchorPane ordersScrollPane;
	private HashMap<String, StrategyAbstract> strategies;

	private static ChefsGuiController instance = null;

	private ChefsGuiController() {
		strategies=new HashMap<String,StrategyAbstract>();
		createStrategies();
	}

	public static ChefsGuiController getInstance() {
		if (instance == null) {
			instance = new ChefsGuiController();
		}
		return instance;
	}



	public void updateOrders() {

	}

	public void setOrderSeenToPreparable() {
		// post.notifyMainController(strategy, args)

	}

	public void setOrderSeenToNotPreparable() {

	}

	public void setOrderToPrepared() {

	}
	
	private void createStrategies() {
		try (ScanResult sr= new ClassGraph()
									.acceptPackages("chefsProgram.strategies")
									.enableClassInfo()
									.scan()) {
			ClassInfoList cl= sr.getSubclasses("chefsProgram.Strategies.StrategyAbstract");
			List<Class<?>> l=cl.loadClasses();
			for(Class<?> c : l) {
				strategies.put(((String) c.getMethod("getStrategyName", ((Class<?>)null)).invoke(null)),
								((StrategyAbstract)c.getMethod("getInstance", StrategyInterface.class).invoke(this)));
			}
		}
		catch (Exception e){
			System.err.println("Error in the creation of the strategies, program aborting./n" + e.getMessage());
		}
	}
	
	public void executeStrategy(String strategyName, String[] args) {
		StrategyAbstract s= strategies.get(strategyName);
		if(s!=null)
			strategies.get(strategyName).execute(args);
		else
			System.out.println("Strategy does not exist");
	}

}
