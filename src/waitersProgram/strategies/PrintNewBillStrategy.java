package waitersProgram.strategies;

import waitersProgram.controller.Restaurant;
import waitersProgram.model.Bill;

/** Called from printBill method in WaitersControlPanelController. */

public class PrintNewBillStrategy extends StrategyAbstract {
	private static PrintNewBillStrategy instance = null;
	private static Bill billToPrint;

	public PrintNewBillStrategy(Restaurant restaurant) {
		super(restaurant);
	}

	public static PrintNewBillStrategy getInstance(Restaurant restaurant) {
		if (instance == null) {
			instance = new PrintNewBillStrategy(restaurant);
		}
		return instance;
	}

	/** In this scenario, args[0]: tableNum. */
	@Override
	public void execute(String[] args) {
		billToPrint = new Bill(Integer.parseInt(args[0]));
	}

	public static Bill getBill() {
		return billToPrint;
	}
}
