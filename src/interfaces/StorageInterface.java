package interfaces;
import java.util.LinkedHashMap;
import java.util.ArrayList;


public interface StorageInterface /*Maybe it will be an observable, maybe not, gotta see */ {
	
	abstract boolean isDoable(LinkedHashMap<String, Double> ingredients);//done
	abstract boolean requestSingleIngredient(String name, Double amount);//done
	abstract void rewriteManifest(String path);//done
	abstract void addIngredient(String name, Double amount, Double lowerBound, Double upperBound);//done
	abstract void editIngredientAmount(String name, Double amount); //done
	abstract void editIngredientLowerBound(String name, Double lowerBound);//done 
	abstract void editIngredientUpperBound(String name, Double upperBound); //done
	abstract ArrayList<String> checkIngredientsOnLowerBound();//done
	abstract ArrayList<String> checkIngredientsOnUpperBound();//done
	abstract ArrayList<String> checkIngredientsOnLowerBound(String[] ingredients);//done
	abstract ArrayList<String> checkIngredientsOnUpperBound(String[] ingredients);//done
	abstract void removeIngredient(String name);//done
	abstract LinkedHashMap<String, Double[]> getManifest();//done
	abstract Double getSpecificIngredientAmount(String name);//done
	abstract Double getSpecificIngredientLowerBound(String name);//done
	abstract Double getSpecificIngredientUpperBound(String name);//done
	abstract void printManifest();
	abstract void loadBackup();
	abstract void printManifest(String path);
	abstract void loadBackup(String path);
	
}
