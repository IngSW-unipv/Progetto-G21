package waitersProgram.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.controlsfx.control.textfield.TextFields;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import waitersProgram.model.Bill;
import waitersProgram.model.Menu;
import waitersProgram.model.MenuEntry;
import waitersProgram.model.Order;
import waitersProgram.model.OrderManager;
import waitersProgram.model.TableManager;
import waitersProgram.strategies.PrintBillStrategy;

/**
 * The WaitersGuiController class. It will be used to control the waiter's
 * graphical interface and it will contain a ListeningPost instance in order to
 * ensure the communication with the SystemController class.
 */

public class WaitersGuiController {

	/** FXML calls for new order creation. */
	@FXML
	TextField newOrderTableField, newOrderEntryField;

	/** FXML calls for new table creation. */
	@FXML
	TextField addNewTableField, removeTableField;

	/** FXML calls for new bill printing. */
	@FXML
	TextField newBillTableField, printBillField;

	/** FXML calls for adding a new menu entry. */
	@FXML
	TextField newEntryNameField, newEntryPriceField;

	/** FXML calls for entry removal. */
	@FXML
	TextField removeEntryField;

	/** FXML calls for order's and menu's panes. */
	@FXML
	AnchorPane ordersPane, menuPane;

	/** FXML calls for prompt labels. */
	@FXML
	Label promptOrderTableLabel, promptBillLabel, promptEntryLabel;

	private ListeningPost post;

	private static WaitersGuiController instance = null;

	private ArrayList<Order> ordersToBeDisplayed;

	private WaitersGuiController() {
		post = ListeningPost.getInstance();
		ordersToBeDisplayed = new ArrayList<Order>(0);
	}

	public static WaitersGuiController getInstance() {
		if (instance == null) {
			instance = new WaitersGuiController();
		}
		return instance;
	}

	public ListeningPost getPost() {
		return post;
	}

	public ArrayList<Order> getOrderToBeDisplayed() {
		return ordersToBeDisplayed;
	}

	public void createTablesAutocomplete() {
		Restaurant restaurant = post.getRestaurant();
		TableManager tableManager = restaurant.getTableManager();
		Collection<Integer> tablesCollection = tableManager.getTables();
		TextFields.bindAutoCompletion(newOrderTableField, tablesCollection);
		TextFields.bindAutoCompletion(removeTableField, tablesCollection);
		TextFields.bindAutoCompletion(newBillTableField, tablesCollection);
	}

	public void createMenuEntriesAutocomplete() {
		Restaurant restaurant = post.getRestaurant();
		Menu menu = restaurant.getRestaurantMenu();
		Collection<MenuEntry> menuCollection = menu.getEntriesCollection();
		TextFields.bindAutoCompletion(newOrderEntryField, menuCollection);
		TextFields.bindAutoCompletion(removeEntryField, menuCollection);
	}

	public void addNewOrder() {
		// post.notifyController -> restaurant.strategyCall -> strategy.execute.

		if (checkForTableFormat(newOrderTableField) == false || checkForEntryFormat(newOrderEntryField) == false) {
			promptOrderTableLabel.setText("ERROR!");
		} else {
			String[] parameters = new String[2];
			parameters[0] = newOrderTableField.getText();
			parameters[1] = newOrderEntryField.getText();
			post.notifyMainController("AddNewOrderStrategy", parameters);
			updateOrders();
		}
	}

	public void addNewTable() {
		if (checkForTableFormat(addNewTableField) == false) {
			promptOrderTableLabel.setText("ERROR!");
		} else {
			String[] parameters = new String[1];
			parameters[0] = addNewTableField.getText();
			post.notifyMainController("AddNewTableStrategy", parameters);
		}
	}

	public void removeTable() {
		if (checkForTableFormat(removeTableField) == false) {
			promptOrderTableLabel.setText("ERROR!");
		} else {
			String[] parameters = new String[1];
			parameters[0] = removeTableField.getText();
			post.notifyMainController("RemoveTableStrategy", parameters);
		}
	}

	public void printBill() {
		if (checkForTableFormat(newBillTableField) == false) {
			promptBillLabel.setText("ERROR!");
		} else {
			String[] parameters = new String[1];
			parameters[0] = printBillField.getText();
			post.notifyMainController("PrintBillStrategy", parameters);
			Bill billToPrint = PrintBillStrategy.getBill();
			printBillField.setText(billToPrint.toString());
		}
	}

	public void addNewEntry() {
		if (checkForEntryNameFormat(newEntryNameField) == false
				|| checkForEntryPriceFormat(newEntryPriceField) == false) {
			promptEntryLabel.setText("ERROR!");
		} else {
			String[] parameters = new String[2];
			parameters[0] = newEntryNameField.getText();
			parameters[1] = newEntryPriceField.getText();
			post.notifyMainController("AddNewEntryStrategy", parameters);
			updateMenu();
		}

	}

	public void removeEntry() {
		if (checkForEntryNameFormat(removeEntryNameField) == false
				|| checkForEntryPriceFormat(removeEntryPriceField) == false) {
			promptEntryLabel.setText("ERROR!");
		} else {
			String[] parameters = new String[2];
			parameters[0] = removeEntryNameField.getText();
			parameters[1] = removeEntryPriceField.getText();
			post.notifyMainController("RemoveEntryStrategy", parameters);
			updateMenu();
		}
	}

	private void updateOrders() {
		ordersPane.getChildren().clear();
		OrderManager orderManager = Restaurant.getInstance().getOrderManager();
		ordersToBeDisplayed = orderManager.getAllOrdersForWaitersGuiController();

		Iterator<Order> iterator = ordersToBeDisplayed.iterator();
		while (iterator.hasNext()) {
			ordersPane.getChildren().add(new Label(iterator.next().toString()));
		}
	}

	private void updateMenu() {
		menuPane.getChildren().clear();
		Restaurant restaurant = post.getRestaurant();
		Collection<MenuEntry> entryToDisplay = restaurant.getRestaurantMenu().getEntriesCollection();

		Iterator<MenuEntry> iterator = entryToDisplay.iterator();
		while (iterator.hasNext()) {
			ordersPane.getChildren().add(new Label(iterator.next().toString()));
		}
	}

	public void setOrderDelivered() {

	}

	public boolean checkForTableFormat(TextField tableField) {
		Pattern p = Pattern.compile("\\d{1,3}");
		Matcher m = p.matcher(tableField.getText());
		return m.matches();
	}

	public boolean checkForEntryFormat(TextField entryField) {
		Pattern p = Pattern.compile("^[A-Za-z0-9‡ËÏÚ˘·ÈÌÛ˙‚ÍÓÙ˚„Òı‰ÎÔˆ¸ˇ ]*, \\\\d{1,5}\\\\.{0,1}\\\\d{0,2}$");
		Matcher m = p.matcher(entryField.getText());
		return m.matches();
	}

	public boolean checkForEntryNameFormat(TextField entryNameField) {
		Pattern p = Pattern.compile("^[A-Za-z0-9‡ËÏÚ˘·ÈÌÛ˙‚ÍÓÙ˚„Òı‰ÎÔˆ¸ˇ ]*$");
		Matcher m = p.matcher(entryNameField.getText());
		return m.matches();
	}

	public boolean checkForEntryPriceFormat(TextField entryPriceField) {
		Pattern p = Pattern.compile("\\\\d{1,5}\\\\.{0,1}\\\\d{0,2}$");
		Matcher m = p.matcher(entryPriceField.getText());
		return m.matches();
	}

}