package testers;

import model.MenuEntry;

public class MenuEntryTester {

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
