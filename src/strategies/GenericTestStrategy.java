package strategies;

import controller.Controller;

public class GenericTestStrategy extends StrategyAbstract{
	/** This is a strategy example. Every strategy must be constructed
	 * via the singleton pattern, otherwise it will cause problems. I can't force
	 * such creation as far as i know from the abstract, unless i restructure the methods, so 
	 * follow this comment when creating, at least for now, eventually i will find a way to force it.*/
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
