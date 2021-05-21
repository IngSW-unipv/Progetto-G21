/** Everything must be synchronized!!!!! */

package stagingPackage;
import java.util.HashMap;
public class Controller {

	private static Controller c;
	private ListeningPost l;
	private HashMap<String, StrategyAbstract> strategies;
	public static Controller CreateController() { 
		if (c==null) {
			c=new Controller();
			return c;
		}
		else return c;
	}
	
	private Controller() {
		strategies=new HashMap<String,StrategyAbstract>();
		strategies.put(GenericTestStrategy.getStrategyName(), new GenericTestStrategy(this));
		l=ListeningPost.invokeListeningPost();
		l.bindController(this);
	}
	
	public void strategyCall(String strategyRequired, String[] args) {
		
	}
	
	public void testMethod() {
		System.out.println("This is a test method!");
	}
	
	
	
}
