package waitersProgram.testers;

import waitersProgram.model.MenuEntry;

public class SimpleMenuEntryTester {

	public static void main(String[] args) {
		MenuEntry entry = new MenuEntry("Pasta alla carbonara", 7.50);

		System.out.println(entry.getDishName());
		System.out.println(entry.getDishPrice());

		entry.editEntryName("Pasta al pesto");
		entry.editEntryPrice(6.50);

		System.out.println(entry.getDishName());
		System.out.println(entry.getDishPrice());
	}

}