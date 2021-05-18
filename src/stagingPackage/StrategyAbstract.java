package stagingPackage;

public abstract class StrategyAbstract implements StrategyInterface {

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
