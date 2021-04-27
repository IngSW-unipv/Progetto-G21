package menu;
import java.util.LinkedHashMap;
import java.util.Map;
public class MenuEntry {
	private String dishName;
	private double dishPrice;
	private LinkedHashMap<String, Double> dishIngredients;
	public MenuEntry(String dishEntry) {
		/** Parse input string dishEntry as format: dishName, dishPrice, 
		 Ingredients: String ingredientName, Double quantity in HashMap <String, Double>
		 pay attention to double entries, if you don't use dot, you kill the program*/
		String[] buffer=dishEntry.split(",");
		this.dishName=buffer[0];
		dishPrice=Double.parseDouble(buffer[1]);
		dishIngredients= new LinkedHashMap<String,Double>();
		for (int i=3; i<buffer.length; i+=2) {
			dishIngredients.put(buffer[i], Double.parseDouble(buffer[i+1]));
		}
	}
	
	public String getDishName() {
		/** Standard String return method*/
		return dishName;
	}
	
	public double getDishPrice() {
		/** Standard double return method*/
		return dishPrice;
	}
	
	public LinkedHashMap<String,Double> getDishIngredients() {
		/** Kinda brutal hashmap return method, gonna make a cuter one */
		return dishIngredients;
	}
	
	public String getDishIngredientsStringed() {
		/**The cuter method mentioned in getDishIngredients, strings
		 * the ingredients hashmap and returns it as the format specified
		 * in the MenuEntry constructor. This is why i use linked hashmaps */
		String buffer= "Ingredienti: ";
		for(Map.Entry<String, Double> e: dishIngredients.entrySet()) {
			buffer+=e.getKey()+" "+e.getValue()+" ";
		}
		return buffer;
	}
	
	public void editEntry(String newName) {
		/** editEntry method to edit the name of the dish represented */
		this.dishName=newName;
	}
	
	public void editEntry(double newPrice) {
		/**First overload of editEntry method, updates price of listed dish*/
		this.dishPrice=newPrice;
	}
	
	public void editEntry(String ingredient, Double newQuantity) throws IngredientOperationDeniedException {
		/** Second overload of editEntry method, updates value of existing
		 * ingredient. It is supposed to be used to modify the ingredient amounts,
		 * not to add other ingredients. If this condition is violated, throws exception*/
		if((dishIngredients.get(ingredient))!=null) {
			dishIngredients.put(ingredient, newQuantity);
		}
		else
		{
			throw new IngredientOperationDeniedException("Non è possibile aggiungere un ingrediente alla lista, per creare una variazione del piatto creare una nuova riga di menu.");
		}
		
		
	}
	
	
}
