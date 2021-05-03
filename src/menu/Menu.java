package menu;
import interfaces.MenuInterface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedHashMap;
public class Menu implements MenuInterface{
	private LinkedHashMap<Integer, MenuEntry> entries;
	
	public Menu(String path) {
		entries=new LinkedHashMap<Integer, MenuEntry>();
		if(checkForExistence(path))
			parseMenuFile(path);
		else
			System.out.println("The object has been instantiated, but it is empty due to an error parsing the file.\n To make use of this Menu, run the method rewriteMenu with the correct path.");
	}
	
	public void editSpecificMenuEntry(Integer entryNumber, String ingredient, Double newQuantity){
		/**Gateway method for 
		 * MenuEntry.public void editEntry(String ingredient, Double newQuantity) throws IngredientOperationDeniedException
		 * Checks whether the entry exists or not using the private method checkForEntryExistence(entryNumber)
		 * and, in case of positive return, calls the gated method*/	
		
		try {
				checkForEntryExistence(entryNumber);
				try {
					entries.get(entryNumber).editEntry(ingredient, newQuantity);
				} catch (IngredientOperationDeniedException e) {
					System.out.println(e.getMessage());
				}
			} catch (EntryDoesNotExistException e1) {
				System.out.println(e1.getMessage()+" - Ingredient quantity unchanged");
			}	
	}
	
	public void editSpecificMenuEntry(Integer entryNumber, Double newPrice){
		/**Gateway method for 
		 * MenuEntry.public void editEntry(double newPrice)
		 * Checks whether the entry exists or not using the private method checkForEntryExistence(entryNumber)
		 * and, in case of positive return, calls the gated method*/
			try {
				checkForEntryExistence(entryNumber);
				entries.get(entryNumber).editEntry(newPrice);
			} catch (EntryDoesNotExistException e) {
				
				System.out.println(e.getMessage()+" - Price unchanged");
			}
			
	
			
	}
	
	public void editSpecificMenuEntry(Integer entryNumber, String newName){
		/**Gateway method for 
		 * MenuEntry.public void editEntry(String newName)
		 * Checks whether the entry exists or not using the private method checkForEntryExistence(entryNumber)
		 * and, in case of positive return, calls the gated method*/
		
		try {
			checkForEntryExistence(entryNumber);
			entries.get(entryNumber).editEntry(newName);
		 } catch(EntryDoesNotExistException e) {
			System.out.println(e.getMessage()+" - Name unchanged");
		 }
	}
	
	private void checkForEntryExistence(Integer entryNumber) throws EntryDoesNotExistException {
		/**Small private method for the overloaded method editSpecificMenuEntry,
		 * It is needed to check if the entry asked for exists of if it would break the hashmap*/
		
		MenuEntry test= entries.get(entryNumber);
		if(test == null) throw new EntryDoesNotExistException("Entry does not exist");
	}

	public void addBatchOfMenuEntries(String path) {
		/**Checks for file existence through the checkForExistence(String path)
		 * method and, if true, adds the lines of the file to the menu*/
		
		if(checkForExistence(path)) parseMenuFile(path);
		else System.out.println("File was unusable - Menu unchanged");
			
	}
	
	public void rewriteMenu(String path) {
		/**Does a quick check if it's fit to delete the menu, then deletes it and rewrites it
		 * by the file passed as argument, using the private method parseMenuFile*/
		if(checkForExistence(path)) {
			entries.clear();
			parseMenuFile(path);
		}
		else System.out.println("File was unusable - Menu unchanged");
		
		
		
	}
	
	private boolean checkForExistence(String path) {
		/**Small method needed to avoid repetitions in filechecking code, it just opens the path
		 * and returns whether the file is openable or not*/
		FileReader file;
		try {
			file= new FileReader(path);
			file.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private void parseMenuFile(String path) {
		/** This method allows for further batch insertions in the menu, it can
		 * parse a file that uses the format 
		 * "dishName \t (at least 1) €dishPrice \n Ingredienti: ingredientName, ingredientAmount measurementUnit.
		 * (you can put as many ingredients as you want, as long as you follow the format after Ingredienti:*/
		/*
		 * 
		 */
		/* WARNING: There isn't as of yet a check of quality on the parsing (i will add it later), if the format is not respected,
		 * 			the program WILL crash, so don't go and purposefully mess the test file until i've finished 
		 * 			coding the Menu package, pretty please :)*/
		
		try {
			BufferedReader file= new BufferedReader(new FileReader(path));
			String toSplit;
			boolean exit=false;
			Integer index= entries.values().size()-1;
			while(!exit) {
				toSplit=file.readLine();
				String dishName=toSplit.substring(0, toSplit.indexOf("\t"));
				Double dishPrice=Double.parseDouble(toSplit.substring(toSplit.indexOf("€")+1));
				toSplit= file.readLine();
				String[] buffer= toSplit.split(",");
				String dishEntry=dishName+","+dishPrice+",";
				for(String s: buffer) {
					s=s.replace(" ", ",");
					s=s.replace(":", "");
					dishEntry+=s.substring(0, s.lastIndexOf(","));
				}
				index++;
				entries.put(index, new MenuEntry(dishEntry));
				if (file.readLine()==null) {
					exit=true;
				}
				
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
