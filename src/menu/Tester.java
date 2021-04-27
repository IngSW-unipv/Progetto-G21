package menu;

public class Tester {

	public static void main(String[] args) {
		String testing= "Spaghetti alle vongole,20.56,Ingredienti,Vongole,50,Sale,20,Zucchero,5,Spaghetti,100";
		MenuEntry entryTest= new MenuEntry(testing);
		System.out.println(entryTest.getDishName());
		System.out.println(entryTest.getDishPrice());
		System.out.println(entryTest.getDishIngredientsStringed());
		entryTest.editEntry("spaget");
		entryTest.editEntry(10.10);
		try {
			Double get= 20.00;
			entryTest.editEntry("Vongole", get);
		}
		catch(IngredientOperationDeniedException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println(entryTest.getDishName());
		System.out.println(entryTest.getDishPrice());
		System.out.println(entryTest.getDishIngredientsStringed());
	}

}
