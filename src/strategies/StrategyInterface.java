package strategies;

public interface StrategyInterface {
	/** The interface does not apply any restrictions on strategy access method, but it is
	 * strongly suggested to use the pattern singleton to create strategies, otherwise the controller
	 * will have issues*/
	abstract void execute(String[] args);
}
