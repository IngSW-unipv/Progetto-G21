package stagingPackage;

public class GenericTestStrategy extends StrategyAbstract{
	
	public GenericTestStrategy(Controller c) {
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
