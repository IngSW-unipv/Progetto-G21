package waitersProgram.model;

import java.util.Collection;
import java.util.LinkedHashMap;

public interface MenuInterface {

	public LinkedHashMap<Integer, MenuEntry> getEntriesHashMap();

	public Collection<MenuEntry> getEntriesCollection();

	public MenuEntry getSpecificMenuEntry(Integer entryKey);

	public String getSpecificMenuEntryName(Integer entryKey);

	public double getSpecificMenuEntryPrice(Integer entryKey);

	public String getMenuFilePath();

	public void addMenuEntry(String dishEntry);

	public void removeMenuEntry(String dishEntry);

	public void removeMenuEntry(Integer entryKey);

	public void editSpecificMenuEntry(Integer entryKey, String newEntryName);

	public void editSpecificMenuEntry(Integer entryKey, double newPrice);
}
