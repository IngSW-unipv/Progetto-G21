package storage;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import exceptions.EntryDoesNotExistException;
public class Storage {
	
	private LinkedHashMap<String, StoredIngredient> manifest;
	
	public Storage(String path) {
		manifest= new LinkedHashMap<String, StoredIngredient>();
		if(checkForExistence(path)) {
			parseManifest(path);
		}
		else
		{
			System.out.println("The object has been instantiated, but it is empty due to an error parsing the file.\n "
					+ "To make use of this Storage, run the method rewriteManifest with the correct path.");
		}
	}
	
	
	
	/**Method used to perform a global check on the manifest hashMap, returning as a string array
	 * all the names of those ingredients whose method lowerBoundCheck returned true
	 * @return buffer ArrayList containing the names of the detected entries*/
	public ArrayList<String> checkIngredientsOnLowerBound(){
		ArrayList<String> buffer= new ArrayList<String>();
		for (StoredIngredient s: manifest.values()) {
			if(s.lowerBoundCheck())
				buffer.add(s.getName());	
		}
		
		if(buffer.size()>0) 
			return buffer;
		else
			return null;
		
	}
	
	/**Overload of checkIngredientsOnLowerBound() method, performs the check on a provided string array of ingredient names
	 * @param ingredients String array containing the ingredients to check 
	 * @return buffer: Same as standard method, an array containing the name of the detected ingredients*/
	public ArrayList<String> checkIngredientsOnLowerBound(String[] ingredients){
		ArrayList<String> buffer= new ArrayList<String>();
		for (String s: ingredients) {
			try {
				checkForEntryExistence(s);
				if(manifest.get(s).lowerBoundCheck()) 
					buffer.add(s);
			} catch (EntryDoesNotExistException e) {
				buffer.add(s);
			}
		}
		
		if(buffer.size()>0) 
			return buffer;
		else
			return null;
		
	}
	
	/**Method used to perform a global check on the manifest hashMap, returning as a string array
	 * all the names of those ingredients whose method upperBoundCheck returned true
	 * @return buffer: ArrayList containing the names of the detected entries*/
	public ArrayList<String> checkIngredientsOnUpperBound(){
		ArrayList<String> buffer= new ArrayList<String>();
		for (StoredIngredient s: manifest.values()) {
			if(s.upperBoundCheck())
				buffer.add(s.getName());	
		}
		
		if(buffer.size()>0) 
			return buffer;
		else
			return null;
		
	}
	
	/**Overload of checkIngredientsOnUpperBound() method, performs the check on a provided string array of ingredient names
	 * @param ingredients String array containing the ingredients to check
	 * @return buffer: Same as standard method, an array containing the name of the detected ingredients */
	public ArrayList<String> checkIngredientsOnUpperBound(String[] ingredients){
		ArrayList<String> buffer= new ArrayList<String>();
		for (String s: ingredients) {
			try {
				checkForEntryExistence(s);
				if(manifest.get(s).upperBoundCheck()) 
					buffer.add(s);
			} catch (EntryDoesNotExistException e) {
				buffer.add(s);
			}
		}
		
		if(buffer.size()>0) 
			return buffer;
		else
			return null;
		
	}
	
	
	/** Main method of Storage, it checks if the required ingredients sent in
	 * actually exist in storage in large enough amounts to prepare the dish.
	 * @param ingredients hashMap containing the list of ingredients to check*/
	public boolean isDoable(LinkedHashMap<String, Double> ingredients) {
		for(Map.Entry<String, Double> e: ingredients.entrySet()) {
			try {
				checkForEntryExistence(e.getKey());
				if(manifest.get(e.getKey()).getAmount()<e.getValue())
					return false;
			} catch(EntryDoesNotExistException e1) {
				return false;
			}
		}
		return true;
	}
	
	/* I copypasted this too. It's becoming an habit. We should create a utilities package
	 * with an utility class with all these methods, maybe define it using generics, it's horrible to 
	 * copypaste everything */
	/*
	 *
	 */
	/**Small private method used before accessing the manifest hashMap
	 * Is needed to check if the entry exists or if it would break the hashMap. 
	 * @param name defines the concerned entry. */
	private void checkForEntryExistence(String name) throws EntryDoesNotExistException {
		StoredIngredient test= manifest.get(name);
		if(test == null) throw new EntryDoesNotExistException("Entry does not exist");
	}
	
	
	/*We might push it up to controller package, it is used by pretty much everything
	 * in this program or maybe even create a utilities package*/
	/*
	 * 
	 */
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
}
