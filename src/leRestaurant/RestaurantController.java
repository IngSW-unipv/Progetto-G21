package leRestaurant;
import menu.*;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Collection;
public class RestaurantController {

	/** Using hashmap because S P E E D*/
	private LinkedHashMap<Integer, MenuEntry > restaurantMenu;
	public RestaurantController() {
		restaurantMenu=new LinkedHashMap<Integer, MenuEntry>();
	}
	
	public void generateMenu(String path) {
		/**method for first generation of menu, call this with right pathname
		 * and it will create the entries in the restaurantMenu hashmap by reading the file*/
		try {
			BufferedReader file= new BufferedReader(new FileReader(path));
			String toSplit;
			boolean exit=false;
			Integer index= restaurantMenu.values().size()-1;
			while(!exit) {
				toSplit=file.readLine();
				String dishName=toSplit.substring(0, toSplit.indexOf("\t"));
				Double dishPrice=Double.parseDouble(toSplit.substring(toSplit.indexOf("€")+1));
				toSplit= file.readLine();
				String[] buffer= toSplit.split(",");
				String dishEntry=dishName+","+dishPrice+",";
				for(String s: buffer) {
					s=s.replace(" ", ",");
					s=s.replace(":", "");
					dishEntry+=s.substring(0, s.lastIndexOf(","));
				}
				index++;
				restaurantMenu.put(index, new MenuEntry(dishEntry));
				if (file.readLine()==null) {
					exit=true;
				}
				
			}
			file.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
	}
	
	public MenuEntry getSpecificMenuEntry(Integer i) {
		MenuEntry buffer;
		if((buffer=restaurantMenu.get(i))!= null) {
			return buffer;
		}
		else
		{
			System.out.println("Entry does not exist");
			return null;
		}
	}
	
	public Collection<MenuEntry> getMenu() {
		return restaurantMenu.values();
	}
}
