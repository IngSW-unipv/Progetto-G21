/** Everything must be synchronized!!!!! */

package stagingPackage;
import java.util.HashMap;
public class Controller {

	private static Controller c;
	private ListeningPost l;
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
		strategies.put(GenericTestStrategy.getStrategyName(), GenericTestStrategy.createStrategy(this));
		l=ListeningPost.invokeListeningPost();
		l.bindController(this);
	}
	
	public void strategyCall(String strategyRequired, String[] args) {
		StrategyAbstract s;
		if((s=strategies.get(strategyRequired))!=null) 
			s.execute(args);
		else
			System.out.println("Strategy does not exist");
		
	}
	
	public void testMethod() {
		System.out.println("This is a test method!");
	}
	
	
	
}
