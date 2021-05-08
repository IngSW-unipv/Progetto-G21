package interfaces;
import java.util.LinkedHashMap;
import java.util.ArrayList;

public interface StorageInterface /*Maybe it will be an observable, maybe not, gotta see */ {
	
	abstract boolean isDoable(LinkedHashMap<String, Double> ingredients);
	abstract boolean requestSingleIngredient(String name, Double amount);
	abstract void rewriteManifest(String path);
	abstract void addIngredient(String name, Double amount);
	abstract void editIngredientAmount(String name, Double amount);
	abstract ArrayList<String> checkIngredientsOnLowerBound();
	abstract ArrayList<String> checkIngredientsOnUpperBound();
	abstract ArrayList<String> checkIngredientsOnLowerBound(String[] ingredients);
	abstract ArrayList<String> checkIngredientsOnUpperBound(String[] ingredients);
	abstract void removeIngredient(String name);
	abstract LinkedHashMap<String, Double> getManifest();
	abstract Double getSpecificIngredientAmount(String name);
	abstract Double getSpecificIngredientLowerBound(String name);
	abstract Double getSpecificIngredientUpperBound(String name);
	abstract void printManifest();
	abstract void loadBackup();
	abstract void printManifest(String path);
	abstract void loadBackup(String path);
	
}
