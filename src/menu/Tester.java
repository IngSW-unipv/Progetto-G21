package menu;
import leRestaurant.RestaurantController;
import java.io.*;
import java.util.*;

public class Tester {

	public static void main(String[] args) {
	
		
//		String testing= "Spaghetti alle vongole,15.2,Ingredienti,Spaghetti,100,Vongole,50,Sale,5,Olio,10";
//  	MenuEntry entryTest = new MenuEntry();
//      System.out.println(entryTest.getDishName());
//		System.out.println(entryTest.getDishPrice());
//		System.out.println(entryTest.getDishIngredientsStringed());
//		entryTest.editEntry("spaget");
//		entryTest.editEntry(10.10);
//		try {
//			Double get= 20.00;
//			entryTest.editEntry("Vongole", get);
//		}
//		catch(IngredientOperationDeniedException e) {
//			System.out.println(e.getMessage());
//		}
//		
//		System.out.println(entryTest.getDishName());
//		System.out.println(entryTest.getDishPrice());
//		System.out.println(entryTest.getDishIngredientsStringed());
//		
//		String toSplit= "Spaghetti alle vongole		€15.20";
//		String dishName=toSplit.substring(0, toSplit.indexOf("\t"));
//		Double dishPrice=Double.parseDouble(toSplit.substring(toSplit.indexOf("€")+1));
//		System.out.println(dishName);
//		System.out.println(dishPrice);
//		toSplit="Ingredienti: Spaghetti 100 g, Vongole 50 g, Sale 5 g, Olio 10 cl.";
//		String[] buffer= toSplit.split(",");
//		String dishEntry=dishName+","+dishPrice+",";
//		for(String s: buffer) {
//			System.out.println(s);
//			s=s.replace(" ", ",");
//			System.out.println(s);
//			s=s.replace(":", "");
//			System.out.println(s);
//			dishEntry+=s.substring(0, s.lastIndexOf(","));
//			System.out.println(s.substring(0, s.lastIndexOf(",")));
//			System.out.println(dishEntry);
//			
//		}
		
//		RestaurantController r= new RestaurantController();
//		r.generateMenu("/home/daniele/Progetto-G21/src/menu/MenuTest");
//		Collection<MenuEntry> c=r.getMenu();
//		for(MenuEntry me:c) {
//			System.out.println(me.getDishName()+"\t \t"+me.getDishPrice()+"\n"+me.getDishIngredientsStringed()+"\n");
//		}
//		
			
//	}	
		
//		try {
//		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Gaia\\Progetto-G21\\src\\menu\\MenuTest"));
//		br.close();
//	    } catch (Exception e) {
//		e.printStackTrace();
//	    }	
		
	  Menu menuTest = new Menu("C:\\Users\\Gaia\\Progetto-G21\\src\\menu\\MenuTest");
		
	  menuTest.rewriteMenu("C:\\Users\\Gaia\\Progetto-G21\\src\\menu\\MenuTest");
	
	  menuTest.getAllMenuEntries();
	  
	  for (Integer i : menuTest.entries.keySet())
	  {
		   System.out.println(menuTest.getSpecificMenuEntryName(i) + '\t' + 
				   			  menuTest.getSpecificMenuEntryPrice(i) + '\n' +
				   			  menuTest.getSpecificMenuEntryIngredientsStringed(i) + '\n');
	  }
	  
      menuTest.removeSpecificMenuEntry(1);
      
      System.out.println("MenuEntry 1 has been removed!!!\n");
      
      menuTest.getAllMenuEntries();
      
      for (Integer i : menuTest.entries.keySet())
	  {
		   System.out.println(menuTest.getSpecificMenuEntryName(i) + '\t' + 
				   			  menuTest.getSpecificMenuEntryPrice(i) + '\n' +
				   			  menuTest.getSpecificMenuEntryIngredientsStringed(i) + '\n');
	  }
      
	}
	
}