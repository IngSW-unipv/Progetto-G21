package waitersProgram.controller;

import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.controlsfx.control.SearchableComboBox;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import waitersProgram.model.Menu;
import waitersProgram.model.MenuEntry;
import waitersProgram.model.Order;
import waitersProgram.model.TableManager;
import waitersProgram.strategies.PrintNewBillStrategy;

/**
 * The WaitersGuiController class. It will be used to control the waiter's
 * graphical interface and it will contain a ListeningPost instance in order to
 * ensure the communication with the SystemController class.
 */

public class WaitersControlPanelController {

	/** FXML calls for new order creation. */
	@FXML
	SearchableComboBox<Integer> newOrderTableComboBox;
	SearchableComboBox<MenuEntry> newOrderEntryComboBox;

	/** FXML calls for new table creation. */
	@FXML
	TextField addNewTableField;

	/** FXML calls for new table removal. */
	@FXML
	SearchableComboBox<Integer> removeTableComboBox;

	/** FXML calls for new bill printing. */
	@FXML
	SearchableComboBox<Integer> newBillTableComboBox;
	TextArea billTextArea;

	/** FXML calls for adding a new menu entry. */
	@FXML
	TextField newEntryNameField, newEntryPriceField;

	/** FXML calls for menu entry removal. */
	@FXML
	TextField removeEntryField;
	SearchableComboBox<MenuEntry> removeEntryComboBox;
	TextArea menuTextArea;

	/** FXML calls for prompt labels. */
	@FXML
	Label promptOrderTableLabel, promptBillLabel, promptEntryLabel;

	/** FXML calls for ChefsOrderUpdateFrame. */
	@FXML
	Label tableLabel, orderLabel, orderNumberLabel;
	CheckBox deliveredCheckBox;

	/** FXML calls for orders Table View. */
	@FXML
	TableView<Order> ordersTableView;
	TableColumn<Order, String> tableColumn;
	TableColumn<Order, String> orderColumn;
	TableColumn<Order, String> statusColumn;

	private ListeningPost post;

	private static WaitersControlPanelController instance = null;

	private ObservableList<Integer> tablesList;
	private ObservableList<MenuEntry> entriesList;
	private ObservableList<Order> ordersList;

	private WaitersControlPanelController() {
		post = ListeningPost.getInstance();
		fillTablesList();
		fillMenuEntriesList();
		tableColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("Table"));
		orderColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("Order"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("Status"));
	}

	public static WaitersControlPanelController getInstance() {
		if (instance == null) {
			instance = new WaitersControlPanelController();
		}
		return instance;
	}

	public ListeningPost getPost() {
		return post;
	}

	public void fillTablesList() {
		tablesList.clear();
		TableManager tableManager = Restaurant.getInstance().getTableManager();
		tablesList.addAll(tableManager.getTables());
	}

	public void fillMenuEntriesList() {
		entriesList.clear();
		Menu menu = Restaurant.getInstance().getRestaurantMenu();
		entriesList.addAll(menu.getEntriesCollection());
	}

	public void createNewOrder() {
		Integer selectedTableNum = newOrderTableComboBox.getValue();
		MenuEntry selectedEntry = newOrderEntryComboBox.getValue();
		String[] parameters = new String[2];
		parameters[0] = selectedTableNum.toString();
		parameters[0] = selectedEntry.toString();
		post.notifyMainController("AddNewOrderStrategy", parameters);
		ordersList.add(new Order(selectedTableNum, selectedEntry));
	}

	public void addNewTable() {
		if (checkForTableFormat(addNewTableField) == false) {
			promptOrderTableLabel.setText("ERROR!");
		} else {
			String[] parameters = new String[1];
			parameters[0] = addNewTableField.getText();
			post.notifyMainController("AddNewTableStrategy", parameters);
			fillTablesList();
		}
	}

	public void removeTable() {
		Integer selectedTableNum = removeTableComboBox.getValue();
		String[] parameters = new String[1];
		parameters[0] = selectedTableNum.toString();
		post.notifyMainController("RemoveTableStrategy", parameters);
		fillTablesList();
	}

	public void printNewBill() {
		Integer selectedTableNum = removeTableComboBox.getValue();
		String[] parameters = new String[1];
		parameters[0] = selectedTableNum.toString();
		post.notifyMainController("PrintNewBillStrategy", parameters);
		billTextArea.clear();
		billTextArea.setText(PrintNewBillStrategy.getBill().toString());
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
			fillMenuEntriesList();
		}
	}

	public void removeEntry() {
		MenuEntry selectedEntry = removeEntryComboBox.getValue();
		String[] parameters = new String[1];
		parameters[0] = selectedEntry.toString();
		post.notifyMainController("RemoveEntryStrategy", parameters);
		fillMenuEntriesList();
	}

	public void setOrderToDelivered() {
		String[] parameters = new String[1];
		parameters[0] = orderNumberLabel.getText();
		post.notifyMainController("SetOrderToDelivered", parameters);
		modifyOrderStatus(Integer.parseInt(parameters[0]), OrderStatus.DELIVERED);
	}

	public void addOrderInTableView(Integer tableNum, MenuEntry entry) {
		ordersList.add(new Order(tableNum, entry));
	}

	public void removeOrderInTableView(Order order) {
		ordersList.remove(order);
	}

	public void modifyOrderStatus(int orderNum, OrderStatus status) {
		Iterator<Order> iterator = ordersList.iterator();
		while (iterator.hasNext()) {
			Order order = iterator.next();
			if (order.getOrderNum() == orderNum) {
				switch (status) {
				case PREPARABLE:
					order.setSeen(true);
					order.setPreparable(true);
					break;
				case NOT_PREPARABLE:
					order.setSeen(true);
					order.setPreparable(false);
					break;
				case PREPARED:
					order.setPrepared(true);
					break;
				case DELIVERED:
					order.setDelivered(true);
					break;
				default:
					break;
				}
			}
		}
	}

	public void printMenuInTextArea() {
		menuTextArea.clear();
		Menu menu = Restaurant.getInstance().getRestaurantMenu();
		Collection<MenuEntry> menuEntriesCollection = menu.getEntriesCollection();
		Iterator<MenuEntry> iterator = menuEntriesCollection.iterator();
		while (iterator.hasNext()) {
			MenuEntry entryToPrint = iterator.next();
			menuTextArea.appendText(entryToPrint.toString() + "Ä");
		}
	}

	public boolean checkForTableFormat(TextField tableField) {
		Pattern p = Pattern.compile("\\d{1,3}");
		Matcher m = p.matcher(tableField.getText().trim());
		return m.matches();
	}

	public boolean checkForEntryFormat(TextField entryField) {
		Pattern p = Pattern.compile("^[A-Za-z0-9‡ËÏÚ˘·ÈÌÛ˙‚ÍÓÙ˚„Òı‰ÎÔˆ¸ˇ ]*, \\\\d{1,5}\\\\.{0,1}\\\\d{0,2}$");
		Matcher m = p.matcher(entryField.getText().trim());
		return m.matches();
	}

	public boolean checkForEntryNameFormat(TextField entryNameField) {
		Pattern p = Pattern.compile("^[A-Za-z0-9‡ËÏÚ˘·ÈÌÛ˙‚ÍÓÙ˚„Òı‰ÎÔˆ¸ˇ ]*$");
		Matcher m = p.matcher(entryNameField.getText().trim());
		return m.matches();
	}

	public boolean checkForEntryPriceFormat(TextField entryPriceField) {
		Pattern p = Pattern.compile("\\\\d{1,5}\\\\.{0,1}\\\\d{0,2}$");
		Matcher m = p.matcher(entryPriceField.getText().trim());
		return m.matches();
	}

}