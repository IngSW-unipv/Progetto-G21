package menu;
import interfaces.MenuInterface;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;

import exceptions.EntryDoesNotExistException;
import exceptions.FileFormatIsNotCorrectException;

import java.util.Collection;

/**The Menu class.
 * The menu contains all the dishes and drinks offered by the restaurant and is saved in a menu.txt file. */
public class Menu implements MenuInterface{
	private LinkedHashMap<Integer, MenuEntry> entries;
	
	/**Class constructor method.
	 * The method calls checkForExistance and parseMenuFile in order to check if menu.txt exists and to parse its content. 
	 * @param path specifies the location of menu.txt file. */
	public Menu(String path) {
		entries=new LinkedHashMap<Integer, MenuEntry>();
		if(checkForExistence(path)) parseMenuFile(path);
		else System.out.println("The object has been instantiated, but it is empty due to an error parsing the file.\n "
				+ "To make use of this Menu, run the method rewriteMenu with the correct path.");
	}
	
	/**This method removes a specific MenuEntry, after is sure of its existence. 
	 * If this action is carried out is necessary to rebuild the HashMap to maintain its array-like structure. 
	 * @param entryNumber defines the concerned entry. */
	public void removeSpecificMenuEntry(Integer entryNumber) {
		try {
			checkForEntryExistence(entryNumber);
			
			for (; entryNumber < (entries.values().size() - 1) ; entryNumber++)
				entries.put(entryNumber, entries.get(entryNumber + 1));
			
			entries.remove(entries.values().size() - 1);
		}
		
		catch (EntryDoesNotExistException e){ 
			System.out.println(e.getMessage());
		}
		
	}
	
	/** Simple method that returns a Collection<MenuEntry> object containing all the values of the hashMap entries.
	 * @return AllEntries. */
	public Collection<MenuEntry> getAllMenuEntries() { 		
		return entries.values();
	}
	
	/**Simple method that returns a specific entry given it's number in the hashMap entries. 
	 * @param entryNumber defines the concerned entry.
	 * @return SpecificMenuEntry. */
	public MenuEntry getSpecificMenuEntry(Integer entryNumber) {
		try {
			checkForEntryExistence(entryNumber);
			return entries.get(entryNumber);
		} catch (EntryDoesNotExistException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	/**Method that creates a new entry. 
	 * It calls the current size of the entries hashMap and creates the new entry given the defined stringed input format. 
	 * @param dishEntry specifies the entry to add. */
	/* WARNING: same as parseMenuFile, we don't have a quality control, if the string is in the wrong format we are going to kill the program. 
	 * We might be able to do something calling the parsing method with such string, but i haven't thought about it yet, and no quality controls are implemented as of yet. */ 
	public void addMenuEntry(String dishEntry) {
		
		// Need to add an existence check
		
		entries.put(entries.values().size(), new MenuEntry(dishEntry));
	}
	
	/**Gateway method for
	 * public String getDishIngredientsStringed() [in class MenuEntry]
	 * Checks for the existence of the entry, and if it does, calls the method from the targeted entry.
	 * @param entryNumber defines the concerned entry. 
	 * @return dishIngredientsStringed. */
	public String getSpecificMenuEntryIngredientsStringed(Integer entryNumber) {		
		try {
			checkForEntryExistence(entryNumber);
			return entries.get(entryNumber).getDishIngredientsStringed();
		} catch (EntryDoesNotExistException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	/**Gateway method for 
	 * public LinkedHashMap<String,Double> getDishIngredients() [in class MenuEntry]
	 * Checks for the existence of the entry, and if it does, calls the method from the targeted entry.
	 * @param entryNumber defines the concerned entry.
	 * @return dishIngredients. */
	public LinkedHashMap<String,Double> getSpecificMenuEntryIngredients(Integer entryNumber){	
		try {
			checkForEntryExistence(entryNumber);
			return entries.get(entryNumber).getDishIngredients();
		} catch (EntryDoesNotExistException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	/**Gateway method for 
	 * public String getDishName() [in class MenuEntry]
	 * Checks for the existence of the entry, and if it does, calls the method from the targeted entry.
	 * @param entryNumber defines the concerned entry.
	 * @return dishName. */
	public String getSpecificMenuEntryName(Integer entryNumber) {
		
		try {
			checkForEntryExistence(entryNumber);
			return entries.get(entryNumber).getDishName();
		} catch (EntryDoesNotExistException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	/**Gateway method for 
	 * public double getDishPrice() [in class MenuEntry]
	 * Checks for the existence of the entry, and if it does, calls the method from the targeted entry.
	 * @param entryNumber defines the concerned entry. 
	 * @return dishPrice. */
	public Double getSpecificMenuEntryPrice(Integer entryNumber){
		
		try {
			checkForEntryExistence(entryNumber);
			return entries.get(entryNumber).getDishPrice();
		} catch(EntryDoesNotExistException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	/**Gateway method for 
	 * public void editEntry(String ingredient, Double newQuantity) throws IngredientOperationDeniedException [in class MenuEntry]
	 * Checks whether the entry exists or not using the private method checkForEntryExistence(entryNumber) and, 
	 * in case of positive return, calls the gated method. 
	 * @param entryNumber defines the concerned entry. */
	public void editSpecificMenuEntry(Integer entryNumber, String ingredient, Double newQuantity){	
		
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
	
	/**Gateway method for 
	 * public void editEntry(double newPrice) [in class MenuEntry]
	 * Checks whether the entry exists or not using the private method checkForEntryExistence(entryNumber) and, 
	 * in case of positive return, calls the gated method. 
	 * @param entryNumber defines the concerned entry. */
	public void editSpecificMenuEntry(Integer entryNumber, Double newPrice){
			try {
				checkForEntryExistence(entryNumber);
				entries.get(entryNumber).editEntry(newPrice);
			} catch (EntryDoesNotExistException e) {
				
				System.out.println(e.getMessage()+" - Price unchanged");
			}		
	}
	
	/**Gateway method for 
	 * public void editEntry(String newName) [in class MenuEntry]
	 * Checks whether the entry exists or not using the private method checkForEntryExistence(entryNumber) and, 
	 * in case of positive return, calls the gated method.
	 * @param entryNumber defines the concerned entry. */
	public void editSpecificMenuEntry(Integer entryNumber, String newName){
		
		try {
			checkForEntryExistence(entryNumber);
			entries.get(entryNumber).editEntry(newName);
		 } catch(EntryDoesNotExistException e) {
			System.out.println(e.getMessage()+" - Name unchanged");
		 }
	}
	
	/**Small private method for the overloaded method editSpecificMenuEntry.
	 * Is needed to check if the entry exists or if it would break the hashMap. 
	 * @param entryNumber defines the concerned entry. */
	private void checkForEntryExistence(Integer entryNumber) throws EntryDoesNotExistException {
		MenuEntry test= entries.get(entryNumber);
		if(test == null) throw new EntryDoesNotExistException("Entry does not exist");
	}

	/**Checks for file existence through the checkForExistence(String path) method and, if true, adds the lines of the file to the menu.
	 * @param path specifies file's location. */
	public void addBatchOfMenuEntries(String path) {
		
		if(checkForExistence(path)) parseMenuFile(path);
		else System.out.println("File was unusable - Menu unchanged");
	}
	
	/**Does a quick check if it's fit to delete the menu, then deletes it and rewrites it by the file passed as argument, 
	 * using the private method parseMenuFile.
	 * @param path specifies file's location. */
	public void rewriteMenu(String path) {
		if(checkForExistence(path)) {
			entries.clear();
			parseMenuFile(path);
		}
		else System.out.println("File was unusable - Menu unchanged");
	}
	
	/**Small method needed to avoid repetitions in filechecking code, 
	 * it just opens the path and returns whether the file is openable or not.
	 * @param path specifies file's location. */
	private boolean checkForExistence(String path) {
		FileReader file;
		try {
			file= new FileReader(path);
			file.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/** This method allows for further batch insertions in the menu, it can parse a file that uses the format 
	 * "dishName \t (at least 1) ï¿½dishPrice \n Ingredienti: ingredientName, ingredientAmount measurementUnit.
	 * (you can put as many ingredients as you want, as long as you follow the format after Ingredienti:
	 * 
	 *  WARNING: There isn't as of yet a check of quality on the parsing (i will add it later), if the format is not respected,
	 * 	the program WILL crash, so don't go and purposefully mess the test file until i've finished coding the Menu package, pretty please :) 
	 * 
	 * @param path specifies file.txt location. */
	private void parseMenuFile(String path) {	
		try {
			BufferedReader file=new BufferedReader(new FileReader(path));
			String toSplit;
			boolean exit=false;
			Integer index=entries.values().size()-1;
			while(!exit) {
				toSplit=file.readLine();
				String dishName=toSplit.substring(0, toSplit.indexOf("\t"));
				Double dishPrice=Double.parseDouble(toSplit.substring(toSplit.indexOf("â‚¬")+1));
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
	
	
	/** This method prints on a backup file (MenuBackup) every MenuEntry and returns the path of
	 * that file */
	public String printBackupToFile()
	{
		try {

			FileWriter fileout = new FileWriter("./backup/MenuBackup.txt");
			int j;
			String buffer;
			
	
			for (Integer i : entries.keySet())
			{
				buffer = getSpecificMenuEntryName(i) + '\t' + 
						 getSpecificMenuEntryPrice(i) + '\n' +
						 getSpecificMenuEntryIngredientsStringed(i);
				
				for (j = 0; j < (buffer.length()); j++)
				{
					fileout.write(buffer.charAt(j));
				}
				
				if (i < entries.values().size() - 1) 
					fileout.write('\n' + 'n');
				else 
					fileout.close();
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return "./backup/MenuBackup.txt";
	}
	
	
	/** This method prints on a backup file every MenuEntry and returns the path of
	 * that file */
	public String printBackupToFile(String path) 
	{
		try {

			FileWriter fileout = new FileWriter(path);
			int j;
			String buffer;
			
	
			for (Integer i : entries.keySet())
			{
				buffer = getSpecificMenuEntryName(i) + '\t' + 
						 getSpecificMenuEntryPrice(i) + '\n' +
						 getSpecificMenuEntryIngredientsStringed(i);
				
				for (j = 0; j < (buffer.length()); j++)
				{
					fileout.write(buffer.charAt(j));
				}
				
				if (i < entries.values().size() - 1) 
					fileout.write('\n' + 'n');
				else 
					fileout.close();
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return path;
	}
	
	/** This method returns the path of the backup file if it has a right format */
	public String getLatestBackup(String path)
	{
		try {
			if (checkForMenuFormat(path)) 
				return printBackupToFile(path);
			else return "Unable to return the path because the file doesn't have a right format";
		} catch (FileFormatIsNotCorrectException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	/** This method returns the path of the backup file if it has a right format */
	public String getLatestBackup()
	{
		try {
			if (checkForMenuFormat(printBackupToFile())) 
				return printBackupToFile();
			else return "Unable to return the path because the file doesn't have a right format";
		} catch (FileFormatIsNotCorrectException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/* TO BE CHECKED!!!!!!!! */
	/** This method checks if the format of the file is correct and throws an exception 
	 * (FileFormatIsNotCorrectException) in case of negative outcome*/
	private boolean checkForMenuFormat(String path) throws FileFormatIsNotCorrectException {
		
		 try {
		      try (FileReader testFile = new FileReader(path)) {
				try (BufferedReader br = new BufferedReader(new FileReader(path))) {
					String s1 = "";
					  String s2 = "";
					  String s2WithoutIngredient = "";
					  String s3 = "";
					  
					  int i;
					  boolean exit = false;
   
					
					  while (!exit)
					  {
						  
						  s1 = br.readLine();
						  
						  String dishName = s1.substring(0, s1.indexOf('\t'));
						  Double dishPrice = Double.parseDouble(s1.substring(s1.indexOf('€') + 1));
						  
						
						  if (s1.compareTo(dishName + '\t' + '€' + dishPrice.toString()) == 0)
						  {
							  s2 = br.readLine();
							  String ingredient = "Ingredienti: ";
							  String[] ingredientName = {};
							  Double[] ingredientQunatity = {};
							  String[] measurementUnit = {};
							  String ingredientsListStringed = "";
							  
							  s2WithoutIngredient = s2.replace("Ingredienti: ", "");
							  
							  String[] buffer= s2WithoutIngredient.split(",");
					   
							  
							  for (i = 0; i < buffer.length; i++)
							  {
								  ingredientName[i] = s2WithoutIngredient.substring(0, s2WithoutIngredient.indexOf(' ') - 1);
					    		  ingredientQunatity[i] = Double.parseDouble(s2WithoutIngredient.substring(s2WithoutIngredient.indexOf(' ') + 1, s2WithoutIngredient.indexOf(' ') - 1));
					    		  measurementUnit[i] = s2WithoutIngredient.substring(s2WithoutIngredient.indexOf(' ') + 1, s2WithoutIngredient.indexOf(',') - 1);
					    		  ingredientsListStringed += ingredientName[i] + " " +
					    				  					ingredientQunatity[i].toString() + " " + 
					    				  					measurementUnit[i];
					    		  if (i != buffer.length - 1) 
					    			  ingredientsListStringed += ", ";
					    		  else 
					    			  ingredientsListStringed += ".";	  					
							  }
							  		    		   
							  if (s2.compareTo(ingredient + ingredientsListStringed) == 0) 
							  {
								  if ((s3 =  br.readLine()) != "")
								  {
									  throw new FileFormatIsNotCorrectException("Wrong format!");
								  //  return false;
								  }
									  
								  if ((s3 =  br.readLine()) == null)
						    	  {
						    		  exit = true;
						    		  return true;
						    	  }
							  }
							}
						    else throw new FileFormatIsNotCorrectException("Wrong format!");
						  		return false;
						  }	  
					  		      
					  testFile.close();
					  br.close();
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		      return false;
		    } catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		return false;
		  }
	}