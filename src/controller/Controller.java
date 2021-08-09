

package controller;
import java.util.HashMap;
import strategies.*;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassGraphClassLoader;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

public class Controller {
	
	/** Controller class, utilizes the strategy pattern in order to provide varied services
	 * to the bound program.*/
	
	private static Controller c;
	private ListeningPost post;
	private HashMap<String, StrategyAbstract> strategies;
	protected static Controller createController() { 
		if (c==null) {
			c=new Controller();
			return c;
		}
		else return c;
	}
	
	private Controller() {
		strategies=new HashMap<String,StrategyAbstract>();
		post=ListeningPost.invokeListeningPost();
		post.bindController(this);
		createStrategies();
	}
	
	private void createStrategies() {
		/** Eventually this method will auto-instantiate strategies, but until i've figured out classpath
		 * lib, it will be required to manually load the single strategies. the String is the name of the strategy, 
		 * the value is the strategy itself.*/
		
		strategies.put(GenericTestStrategy.getStrategyName(), GenericTestStrategy.createStrategy(this));
	}
	public void strategyCall(String strategyRequired, String[] args) {
		/** This method checks if the strategy exists and, if it does, invokes it with the 
		 * argument string vector passed in*/
		StrategyAbstract s;
		if((s=strategies.get(strategyRequired))!=null) 
			s.execute(args);
		else
			System.out.println("Strategy does not exist");
		
	}
	
	public void testMethod() {
		/** Just a test method to test strategy access to the controller.*/
		System.out.println("This is a test method!");
	}
	
	
	
}
