package interfaces;
import java.util.LinkedHashMap;

public interface StorageInterface /*Maybe it will be an observable, maybe not, gotta see */ {
	
	abstract boolean isDoable(LinkedHashMap<String, Double> ingredients);
	abstract boolean requestSingleIngredient(String name, Double amount);
	abstract void rewriteManifest(String path);
	abstract void addIngredient(String name, Double amount);
	abstract void editIngredientAmount(String name, Double amount);
	abstract String[] checkIngredientsOnLowerBound();
	abstract String[] checkIngredientsOnUpperBound();
	abstract String[] checkIngredientsOnLowerBound(String[] ingredients);
	abstract String[] checkIngredientsOnUpperBound(String[] ingredients);
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
