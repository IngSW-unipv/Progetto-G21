package testers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.Test;

import waitersProgram.model.Menu;
import waitersProgram.model.MenuEntry;

public class MenuTest {

	File menuFile = new File("Files/menuFile.txt");
	Menu menu = Menu.getInstance();

//	Menu menu = Menu.getInstance("Files/menuFile.txt");

	MenuEntry entry1 = new MenuEntry("Pasta al pesto", 3);

	@Test
	public void testGetSpecificMenuEntry() {
		assertEquals(entry1.toString(), menu.getSpecificMenuEntry(0).toString());
	}

	@Test
	public void testGetSpecificMenuEntryName() {
		assertEquals(entry1.getDishName(), menu.getSpecificMenuEntryName(0));
	}

	@Test
	public void testGetSpecificMenuEntryPrice() {
		assertEquals(entry1.getDishPrice(), menu.getSpecificMenuEntryPrice(0));
	}

//	@Test(expected = EntryDoesNotExistException.class)
//	public void testGetSpecificMenuEntryPrice1()
//	{
//		menu.getSpecificMenuEntryPrice(8);
//	}

	@Test
	public void testAddMenuEntry() {
		String dishEntry = "Pollo con patatine, 10";
		menu.addMenuEntry(dishEntry);
		assertEquals(dishEntry.toString(), (menu.getSpecificMenuEntry(menu.getFileLinesCounter() - 1)).toString());
	}
//	
//
//	@Test(expected = EntryDoesNotExistException.class)
//	public void testRemoveMenuEntry()
//	{
//		menu.removeMenuEntry(5);
//		menu.getSpecificMenuEntry(5);
//	}

	@Test
	public void testEditSpecificMenuEntryName() {
		String newName = "Tagliata di manzo con limone";
		menu.editSpecificMenuEntry(2, newName);
		assertEquals(newName.toString(), menu.getSpecificMenuEntryName(menu.getFileLinesCounter() - 1));

	}

	@Test
	public void testEditSpecificMenuEntryPrice() {
		double newPrice = 7;
		menu.editSpecificMenuEntry(2, newPrice);
		assertEquals(newPrice, menu.getSpecificMenuEntryPrice(menu.getFileLinesCounter() - 1));
	}
}
