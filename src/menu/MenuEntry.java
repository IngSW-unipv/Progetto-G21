package menu;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The MenuEntry class. 
 * A MenuEntry object represents one entry on the menu of the restaurant. 
 * It contains dishName, dishPrice and the ingredients required. */
public class MenuEntry {
	
	private String dishName;
	private double dishPrice;
	private LinkedHashMap<String, Double> dishIngredients;
	
	/** Simple constructor method. */
	public MenuEntry(String name, double price, LinkedHashMap<String, Double> ingredients) {
		this.dishName=name;
		this.dishPrice=price;
		this.dishIngredients=ingredients;
	}
	
	/** Advanced Constructor method. 
	 *  @param dishEntry specifies the String to parse in "dishName, dishPrice, Ingredients, dishIngredients" format.
	 *  dishIngredients are saved in a LinkedHashMap using the string ingredientName and the double value quantity.   
	    Pay attention to the separator (comma) and to double entries. If you don't use comma the program is killed. */
	public MenuEntry(String dishEntry) {
		
		String[] buffer=dishEntry.split(",");
		
		dishName=buffer[0];
		dishPrice=Double.parseDouble(buffer[1]);
		dishIngredients= new LinkedHashMap<String,Double>();
		// why i starts from 3???? 
		// Because in buffer[2] there is Ingredients, my bad didn't write it in the format
		for (int i=3; i<buffer.length; i+=2) {	
			dishIngredients.put(buffer[i], Double.parseDouble(buffer[i+1]));
		}
	}
	
	/** Standard String return method .
	 * @return dishName. */
	public String getDishName() {
		return dishName;
	}
	
	/** Standard double return method.
	 *  @return dishPrice. */
	public double getDishPrice() {
		return dishPrice;
	}
	
	/** Kinda brutal hashmap return method, gonna make a cuter one.. */
	public LinkedHashMap<String,Double> getDishIngredients() {
		return dishIngredients;
	}
	
	/** Method that returns dish ingredients in a String with the constructor's format. 
	 * @return stringed MenuEntry. */
	public String getDishIngredientsStringed() {
		
		String buffer= "Ingredienti: ";
		
		for(Map.Entry<String, Double> e: dishIngredients.entrySet()) {
			//Mock formatting with space and comma i have to think about saving measurement units too for backup's sake.
			buffer+=e.getKey()+" "+e.getValue()+" ,";
		}
		
		return buffer;
	}
	
	/** Method that edit the name of the dish represented by the MenuEntry. 
	 * @param newName is the new given name. */
	public void editEntry(String newName) {
		this.dishName=newName;
	}
	
	/** First overload of editEntry method, updates price of listed dish. 
	 * @param newPrice is the new given price. */
	public void editEntry(double newPrice) {
		this.dishPrice=newPrice;
	}
	
	/** Second overload of editEntry method, updates ingredient's amount. 
	 * It's not supposed to add or remove ingredients from the Map! If this condition is violated, throws an exception. 
	 * @param ingredient is the name of the ingredient whose quantity has to be modified.
	 * @param newQuantity is the new given quantity. */
	public void editEntry(String ingredient, Double newQuantity) throws IngredientOperationDeniedException {
		if((dishIngredients.get(ingredient))!=null) {
			dishIngredients.put(ingredient, newQuantity);
		} else {
			throw new IngredientOperationDeniedException("It's not possible to add an ingredient: "
					+ "if you want to do it, please create a new MenuEntry!!!");
		}
	}
	
	
}
