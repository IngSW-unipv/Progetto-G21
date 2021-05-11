package interfaces;
import menu.MenuEntry; 
import java.util.LinkedHashMap;
import java.util.Collection;
public interface MenuInterface {
	
	/**This interface presents redundancy in the methods, due to Demeter's law,
	 * without these methods, we would have to write something on the lines of
	 * "menu.getSpecificMenuEntry(entryNumber).editEntry(newName)" from the controller
	 * to modify the entry name, since the controller is not in the package of menu nor has any
	 * relation to it except getting information or asking for edits. Also, as a side bonus, if
	 * something has to be changed in MenuEntry, we can just refactor the interface, which will refactor
	 * everything related to these methods.
	 * Other than that, not using the menu would violate the Controller pattern, the Creator pattern and
	 * fundamental rules of OOP, in that we would have a non encapsulated subsystem, involving part of the controller,
	 * which would create objects whose origin is not logically tied to it, using methods that aren't Controller related,
	 * inserting them in an attribute which describes what would normally be defined as an object (i.e. restaurantMenu,
	 * which by all means isn't written all over the walls of a restaurant) */
	
	abstract MenuEntry getSpecificMenuEntry(Integer entryNumber);
	abstract LinkedHashMap<String, Double> getSpecificMenuEntryIngredients(Integer entryNumber);
	abstract String getSpecificMenuEntryIngredientsStringed(Integer entryNumber);
	abstract String getSpecificMenuEntryName(Integer entryNumber);
	abstract Double getSpecificMenuEntryPrice(Integer entryNumber);
	abstract Collection<MenuEntry> getAllMenuEntries();
	abstract void editSpecificMenuEntry(Integer entryNumber, String newName);
	abstract void editSpecificMenuEntry(Integer entryNumber, Double newPrice);
	abstract void editSpecificMenuEntry(Integer entryNumber, String ingredient, Double newQuantity);
	abstract void removeSpecificMenuEntry(Integer entryNumber);
	abstract void addMenuEntry(String dishEntry);
	abstract void addBatchOfMenuEntries(String path);
	abstract void rewriteMenu(String path);
	abstract String printBackupToFile(); // Consider multithreading, or print when updating the hashmap or direct call from controller
	abstract String getLatestBackup(); // Knowing the naming system of multiple backups using a info file, or using a single one
	abstract String printBackupToFile(String path);
	abstract String getLatestBackup(String path);
	
	//quality check for last  is of paramount importance, we need to be sure that he isn't passing a wrongly formatted
	//file to the program as a backup.
}
