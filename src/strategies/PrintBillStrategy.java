package strategies;

import controller.Restaurant;
import model.Bill;

public class PrintBillStrategy extends StrategyAbstract {
	private static PrintBillStrategy instance = null;

	public PrintBillStrategy(Restaurant restaurant) {
		super(restaurant);
	}

	public static PrintBillStrategy getInstance(Restaurant restaurant) {
		if (instance == null) {
			instance = new PrintBillStrategy(restaurant);
		}
		return instance;
	}

	@Override
	public void execute(String[] args) {
		new Bill(Integer.parseInt(args[0]));
	}
}
