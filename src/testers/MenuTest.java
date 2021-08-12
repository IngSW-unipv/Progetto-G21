package testers;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.Test;

import model.Menu;
import model.MenuEntry;
import model.EntryDoesNotExistException;

public class MenuTest {

	Menu menu = Menu.getInstance("Files/menuFile.txt");
	
	MenuEntry entry1 = new MenuEntry("Pasta al pesto", 3);
	
	@Test
	public void testGetSpecificMenuEntry()
	{
		assertEquals(entry1.toString(), menu.getSpecificMenuEntry(0).toString());
	}
	
	
	@Test
	public void testGetSpecificMenuEntryName()
	{
		assertEquals(entry1.getDishName(), menu.getSpecificMenuEntryName(0));
	}
	
	
	@Test
	public void testGetSpecificMenuEntryPrice()
	{
		assertEquals(entry1.getDishPrice(), menu.getSpecificMenuEntryPrice(0));
	}
	
	
	@Test(expected = EntryDoesNotExistException.class)
	public void testGetSpecificMenuEntryPrice1()
	{
		menu.getSpecificMenuEntryPrice(8);
	}
	
	
	@Test
	public void testAddMenuEntry()
	{
		String dishEntry = "Pollo con patatine, 10";
		menu.addMenuEntry(dishEntry);
		assertEquals(dishEntry.toString(), menu.getSpecificMenuEntry(6).toString());
	}
	
	/* ERROR */
	@Test
	public void testRemoveMenuEntry()
	{
		menu.removeMenuEntry(5);
	//	System.out.println(menu.getSpecificMenuEntryPrice(5));
		String dishEntry = "Pollo con patatine, 10";
		menu.addMenuEntry(dishEntry);
	}
}
