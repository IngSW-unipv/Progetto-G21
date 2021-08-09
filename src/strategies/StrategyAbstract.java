package strategies;

import controller.Controller;

public abstract class StrategyAbstract implements StrategyInterface {

	/**Strategy abstract, each strategy has a execute method in common, this is the gateway
	 * to every other method you put in your strategy, since it's the only method that is invoked by
	 * the controller.*/
	private Controller c;
	public static String getStrategyName() {
		return System.class.getSimpleName();
	}
	
	protected StrategyAbstract(Controller c){
		this.c=c;
	}
	
	
	public Controller getController() {
		return c;
	}
	public abstract void execute(String[] args);
}
