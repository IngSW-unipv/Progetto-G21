package waitersProgram.testers.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import waitersProgram.model.MenuEntry;

public class MenuEntryTest {

	MenuEntry entry1 = new MenuEntry("Risotto al pesce persico", 18);
	MenuEntry entry2 = new MenuEntry("Risotto alla milanese", 14);

	/**
	 * Tester of method getDishName().
	 */
	@Test
	public void testGetDishName() {
		assertEquals("Risotto al pesce persico", entry1.getDishName());
		assertEquals("Risotto alla milanese", entry2.getDishName());
	}

	/**
	 * Tester of method getDishPrice().
	 */
	@Test
	public void testGetDishPrice() {
		assertEquals(18, entry1.getDishPrice());
		assertEquals(14, entry2.getDishPrice());
	}

	/**
	 * Tester of method EditEntryName().
	 */
	@Test
	public void testEditEntryName() {
		entry1.editEntryName("Risotto al pesce spada");
		assertEquals("Risotto al pesce spada", entry1.getDishName());

		entry2.editEntryName("Risotto alla milanese con funghi");
		assertEquals("Risotto alla milanese con funghi", entry2.getDishName());
	}

	/**
	 * Tester of method EditEntryPrice().
	 */
	@Test
	public void testEditEntryPrice() {
		entry1.editEntryPrice(17);
		assertEquals(17, entry1.getDishPrice());

		entry2.editEntryPrice(15);
		assertEquals(15, entry2.getDishPrice());
	}

	/**
	 * Tester of method toString().
	 */
	@Test
	public void testToString() {
		assertEquals("Risotto al pesce persico, 18", entry1.toString());
		assertEquals("Risotto alla milanese, 14", entry2.toString());
	}
}
