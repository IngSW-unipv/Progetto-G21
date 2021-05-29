package testers;

import model.*;

public class MenuTester {

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
		
	  Menu menuTest = new Menu("C:\\Users\\Gaia\\Progetto-G21\\src\\testers\\MenuTest");
		
	  menuTest.rewriteMenu("C:\\Users\\Gaia\\Progetto-G21\\src\\testers\\MenuTest");
	
	  menuTest.getAllMenuEntries();
	  
	  for (Integer i : menuTest.getEntries().keySet())
	  {
		   System.out.println(menuTest.getSpecificMenuEntryName(i) + '\t' + 
				   			  menuTest.getSpecificMenuEntryPrice(i) + '\n' +
				   			  menuTest.getSpecificMenuEntryIngredientsStringed(i) + '\n');
	  }
	  
      menuTest.removeSpecificMenuEntry(1);  
           
      System.out.println("MenuEntry 1 has been removed!!!\n");
      
      menuTest.getAllMenuEntries();
      
      for (Integer i : menuTest.getEntries().keySet())
	  {
		   System.out.println(menuTest.getSpecificMenuEntryName(i) + '\t' + 
				   			  menuTest.getSpecificMenuEntryPrice(i) + '\n' +
				   			  menuTest.getSpecificMenuEntryIngredientsStringed(i) + '\n');
	  }
      
      System.out.println("MenuEntry 1");
      
      menuTest.getSpecificMenuEntry(3);
      System.out.println(menuTest.getSpecificMenuEntryName(3) + '\t' + 
   	  menuTest.getSpecificMenuEntryPrice(3) + '\n' +
   	  menuTest.getSpecificMenuEntryIngredientsStringed(3) + '\n');
      
      String testing= "Spaghetti panna e prosciutto,10.2,Ingredienti,Spaghetti,100,Panna,10, Prosciutto,25,Sale,5,Olio,10";
      String testing2= "Spaghetti panna e prosciutto,10.2,Spaghetti,100,Panna,10, Prosciutto,25,Sale,5,Olio,10";      
      
      menuTest.addMenuEntry(testing);
      
      System.out.println("A new MenuEntry has been added!!!");
      
      for (Integer i : menuTest.getEntries().keySet())
	  {
		   System.out.println(menuTest.getSpecificMenuEntryName(i) + '\t' + 
				   			  menuTest.getSpecificMenuEntryPrice(i) + '\n' +
				   			  menuTest.getSpecificMenuEntryIngredientsStringed(i) + '\n');
	  }
      
     menuTest.addMenuEntry(testing2);   // format not correct 
      
      
      menuTest.editSpecificMenuEntry(0, 16.0);
      System.out.println(menuTest.getSpecificMenuEntryName(0) + '\t' + 
      menuTest.getSpecificMenuEntryPrice(0) + '\n' +
      menuTest.getSpecificMenuEntryIngredientsStringed(0) + '\n');
      
      System.out.println("Price has been changed!!\n");
      
      menuTest.editSpecificMenuEntry(1, "Melanzane", 120.0);
      System.out.println(menuTest.getSpecificMenuEntryName(1) + '\t' + 
      menuTest.getSpecificMenuEntryPrice(1) + '\n' +
      menuTest.getSpecificMenuEntryIngredientsStringed(1) + '\n');
      
      System.out.println("Qunatity has been changed!!\n");
      
      menuTest.editSpecificMenuEntry(1, "Melanzane alla Parmigiana Speciali");
      System.out.println(menuTest.getSpecificMenuEntryName(1) + '\t' + 
      menuTest.getSpecificMenuEntryPrice(1) + '\n' +
      menuTest.getSpecificMenuEntryIngredientsStringed(1) + '\n');
      
      System.out.println("Name has been changed!!\n");
      
      menuTest.printBackupToFile();
	}
	
	
	
}