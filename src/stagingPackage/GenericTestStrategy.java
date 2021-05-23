package stagingPackage;

public class GenericTestStrategy extends StrategyAbstract{
	private static GenericTestStrategy t;
	public static GenericTestStrategy createStrategy(Controller c) {
		if (t==null)
		{t=new GenericTestStrategy(c);
		return t;}
		else
			return t;
	}
	
	public static GenericTestStrategy createStrategy() {
		if(t!= null) 
			return t;
		else
			{System.out.println("Strategy has not yet been istantiated, use createTestStrategy(Controller c) to instantiate" );
			return null;}
		
	}
	
	private GenericTestStrategy(Controller c) {
		super(c);
	}
	
	public void execute(String[] args) {
		System.out.println("This example strategy will print the contents of the args array.");
		for (String s: args) {
			System.out.println(s);
		}
		System.out.println("Now, it will grab stuff using controller methods and print it");
		super.getController().testMethod();
	}
}
