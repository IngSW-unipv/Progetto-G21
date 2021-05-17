package stagingPackage;
import java.util.HashMap;
public class Controller {

	private static Controller c;
	private HashMap<String, StrategyAbstract> strategies;
	public static Controller CreateController() { 
		if (c==null) {
			c=new Controller();
			return c;
		}
		else return c;
	}
	
	private Controller() {
		
	}
}
