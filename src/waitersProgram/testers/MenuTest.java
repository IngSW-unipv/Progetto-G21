package waitersProgram.testers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.Test;

import waitersProgram.model.Menu;
import waitersProgram.model.MenuEntry;

public class MenuTest {

	File menuFile = new File("Files/menuFile.txt");
	Menu menu = Menu.getInstance();
	MenuEntry entry1 = new MenuEntry("Pasta al pesto", 3.0);

	void resetMenu() {
		if (menuFile.exists()) {
			menuFile.delete();
		}
		menu.addMenuEntry(entry1.toString());
		menu.addMenuEntry("Pasta al pomodoro, 3");
		menu.addMenuEntry("Pizza margherita, 5");
		menu.addMenuEntry("Tiramisù, 5");
		menu.addMenuEntry("Pastiera napoletana, 4");
	}
	
	/**
	 * @Test of method getSpecificMenuEntry().
	 */
	@Test
	public void testGetSpecificMenuEntryEntryKey() {
		assertEquals(entry1.toString(), menu.getSpecificMenuEntry(0).toString());
	}

	/**
	 * @Test of method getSpecificMenuEntryName().
	 */
	@Test
	public void testGetSpecificMenuEntryName() {
		assertEquals(entry1.getDishName(), menu.getSpecificMenuEntryName(0));
	}

	/**
	 * @Test of method getSpecificMenuPrice().
	 */
	@Test
	public void testGetSpecificMenuEntryPrice() {
		assertEquals(entry1.getDishPrice(), menu.getSpecificMenuEntryPrice(0));
	}

	/**
	 * @Test of method getMenuFilePath().
	 */
	@Test
	public void testGetMenuFilePath() {
		assertEquals("Files/menuFile.txt", menu.getMenuFilePath());
	}

	/**
	 * @Test of method addMenuEntry().
	 */
	@Test
	public void testAddMenuEntry() {
		resetMenu();
		String dishEntry = "Pollo con patatine, 10";
		menu.addMenuEntry(dishEntry);
		assertEquals(dishEntry.toString(), (menu.getSpecificMenuEntry(menu.getFileLinesCounter() - 1)).toString());
	}

	/**
	 * @Test of method removeMenuEntry().
	 */
	@Test
	public void testRemoveMenuEntry() {
		menu.removeMenuEntry("Pastiera napoletana, 4");
		assertEquals(null, menu.getSpecificMenuEntry("Pastiera napoletana, 4"));
	}

	/**
	 * @Test of method EditSpecificMenuEntryName().
	 */
	@Test
	public void testEditSpecificMenuEntryName() {
		String newName = "Tagliata di manzo con limone";
		menu.editSpecificMenuEntry(1, newName);
		assertEquals(newName.toString(), menu.getSpecificMenuEntryName(menu.getFileLinesCounter() - 1));
	}

	/**
	 * @Test of method addMenuEntry().
	 */
	@Test
	public void testEditSpecificMenuEntryPrice() {
		double newPrice = 7.0;
		menu.editSpecificMenuEntry(1, newPrice);
		assertEquals(newPrice, menu.getSpecificMenuEntryPrice(menu.getFileLinesCounter() - 1));
	}

}
