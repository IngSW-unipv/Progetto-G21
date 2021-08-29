package waitersProgram.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.controlsfx.control.SearchableComboBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import waitersProgram.model.Menu;
import waitersProgram.model.MenuEntry;
import waitersProgram.model.Order;
import waitersProgram.model.TableManager;
import waitersProgram.strategies.CreateNewOrderStrategy;
import waitersProgram.strategies.PrintNewBillStrategy;

/**
 * The WaitersController class. It will be used to control the waiter's
 * graphical interface (both WaitersControlPanel and WaitersOrderUpdateFrame)
 * and it will contain a ListeningPost instance in order to ensure the
 * communication with the Restaurant class (backend's facade controller).
 * 
 * This class and its GUI will be running on the same device of ListeningPost
 * class (server), so it's not necessary to establish a socket communication (in
 * ChefsController that was mandatory).
 */

public class WaitersControlPanelController {

	/** WaitersControlPanel's first tab FXML calls. */
	@FXML
	SearchableComboBox<String> newOrderTableComboBox, newOrderEntryComboBox, removeTableComboBox, newBillTableComboBox,
			removeEntryComboBox;
	@FXML
	TextField addNewTableField, newEntryNameField, newEntryPriceField;

	@FXML
	TableView<Order> ordersTableView;

	@FXML
	TableColumn<Order, String> tableColumn, orderColumn, statusColumn;

	@FXML
	Label promptOrderTableLabel, promptBillLabel, promptEntryLabel;

	@FXML
	TextArea billTextArea, menuTextArea;

	private ListeningPost post;
	private static WaitersControlPanelController instance = null;

	private ObservableList<String> tablesList = FXCollections.observableArrayList();
	private ObservableList<String> entriesList = FXCollections.observableArrayList();
	private ObservableList<Order> ordersList = FXCollections.observableArrayList();

	/**
	 * Method required to initialize FXML elements.
	 */
	@FXML
	private void initialize() {
		setInstance();
		fillTablesList();
		fillMenuEntriesList();
		newOrderTableComboBox.setItems(tablesList);
		removeTableComboBox.setItems(tablesList);
		newBillTableComboBox.setItems(tablesList);
		newOrderEntryComboBox.setItems(entriesList);
		removeEntryComboBox.setItems(entriesList);
		ordersTableView.setItems(ordersList);

		// For test purposes
		ordersList.add(new Order(32, new MenuEntry("Pasta al pomodoro, 4")));
		ordersList.add(new Order(32, new MenuEntry("Lasagne, 5")));

		tableColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("tableNum"));
		orderColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("orderedEntryStringed"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("orderStatusStringed"));

		// post = ListeningPost.getInstance();
	}

	/**
	 * Method used to show WaitersOrderUpdateFrame. Triggered by mouse click on
	 * table rows.
	 */
	@FXML
	private void handleRowSelect() {
		Order row = ordersTableView.getSelectionModel().getSelectedItem();
		if (row != null) {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource("/waitersProgram/view/WaitersOrderUpdateFrame.fxml"));
			try {
				Parent root = fxmlLoader.load();
				Stage stage = new Stage();
				stage.setTitle("Update order status!");
				Scene scene = new Scene(root);
				scene.getStylesheets().add(
						getClass().getResource("/waitersProgram/view/waitersOrderUpdateFrame.css").toExternalForm());
				stage.setScene(scene);
				// WaitersOrderUpdateFrameController updateFrameController =
				// fxmlLoader.getController();
				// updateFrameController.setMainController(this);
				// updateFrameController.setOrder(row);
				System.out.println(row.toString());
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Private method called in the constructor and in addNewTable(), removeTable().
	 * It's used to update tables ObservableList.
	 */
	public void fillTablesList() {
		tablesList.clear();
		TableManager tableManager = Restaurant.getInstance().getTableManager();
		Iterator<Integer> iterator = tableManager.getTables().iterator();
		while (iterator.hasNext()) {
			Integer tableNum = iterator.next();
			tablesList.add(Integer.toString(tableNum));
		}
	}

	/**
	 * Private method called in the constructor and in addNewEntry(), removeEntry().
	 * It's used to update entries ObservableList.
	 */
	public void fillMenuEntriesList() {
		entriesList.clear();
		Menu menu = Restaurant.getInstance().getRestaurantMenu();
		Iterator<MenuEntry> iterator = menu.getEntriesCollection().iterator();
		while (iterator.hasNext()) {
			MenuEntry menuEntry = iterator.next();
			entriesList.add(menuEntry.toString());
		}
	}

	/**
	 * Private method used to check tableField format.
	 * 
	 * @param tableField.
	 * @return boolean variable.
	 */
	private boolean checkForTableFormat(String tableField) {
		Pattern p = Pattern.compile("\\d{1,3}");
		Matcher m = p.matcher(tableField.trim());
		return m.matches();
	}

	/**
	 * Private method used to check entryNameField format.
	 * 
	 * @param entryNameField.
	 * @return boolean variable.
	 */
	private boolean checkForEntryNameFormat(String entryNameField) {
		Pattern p = Pattern.compile("^[A-Za-z0-9‡ËÏÚ˘·ÈÌÛ˙‚ÍÓÙ˚„Òı‰ÎÔˆ¸ˇ ]*$");
		Matcher m = p.matcher(entryNameField.trim());
		return m.matches();
	}

	/**
	 * Private method used to check entryPriceField format.
	 * 
	 * @param entryPriceField.
	 * @return boolean variable.
	 */
	private boolean checkForEntryPriceFormat(String entryPriceField) {
		Pattern p = Pattern.compile("\\\\d{1,5}\\\\.{0,1}\\\\d{0,2}$");
		Matcher m = p.matcher(entryPriceField.trim());
		return m.matches();
	}

	/** Simple current instance setter. */
	public void setInstance() {
		instance = this;
	}

	/**
	 * @return used ListeningPost instance.
	 */
	public ListeningPost getPost() {
		return post;
	}

	/**
	 * @return current instance.
	 */
	public static WaitersControlPanelController getInstance() {
		return instance;
	}

	/**
	 * Private method used to get an order from the orders ObservableList.
	 * 
	 * @param orderNum specify the order number.
	 * @return order instance.
	 */
	public Order searchForAnOrder(int orderNum) {
		Iterator<Order> iterator = ordersList.iterator();
		Order currentOrder = null;
		while (iterator.hasNext()) {
			currentOrder = iterator.next();
			if (currentOrder.getOrderNum() == orderNum) {
				break;
			}
		}
		return currentOrder;
	}

	/** Method triggered by createNewOrderButton. */
	public void createNewOrder() {
		String[] parameters = new String[2];
		parameters[0] = newOrderTableComboBox.getValue().trim();
		parameters[1] = newOrderEntryComboBox.getValue().trim();
		post.notifyMainController("CreateNewOrderStrategy", parameters);
		addOrderToTableView(CreateNewOrderStrategy.getOrder());
	}

	/** Method triggered by addNewTableButton. */
	public void addNewTable() {
		if (checkForTableFormat(addNewTableField.getText().trim()) == false) {
			promptOrderTableLabel.setText("ERROR!");
		} else {
			String[] parameters = new String[1];
			parameters[0] = addNewTableField.getText().trim();
			post.notifyMainController("AddNewTableStrategy", parameters);
			fillTablesList();
		}
	}

	/** Method triggered by removeTableButton. */
	public void removeTable() {
		String[] parameters = new String[1];
		parameters[0] = removeTableComboBox.getValue().trim();
		post.notifyMainController("RemoveTableStrategy", parameters);
		fillTablesList();
	}

	/** Method triggered by printBillButton. */
	public void printNewBill() {
		billTextArea.clear();
		String[] parameters = new String[1];
		parameters[0] = newBillTableComboBox.getValue().trim();
		post.notifyMainController("PrintNewBillStrategy", parameters);
		ArrayList<Order> orderToBeRemoved = new ArrayList<Order>(0);
		Iterator<Order> iterator = ordersList.iterator();
		while (iterator.hasNext()) {
			Order currentOrder = iterator.next();
			if (currentOrder.getTableNum() == Integer.parseInt(parameters[0])) {
				orderToBeRemoved.add(currentOrder);
			}
		}
		ordersList.removeAll(orderToBeRemoved);
		billTextArea.setText(PrintNewBillStrategy.getBill().toString());
	}

	/** Method triggered by addNewEntryButton. */
	public void addNewEntry() {
		if (checkForEntryNameFormat(newEntryNameField.getText()) == false
				|| checkForEntryPriceFormat(newEntryPriceField.getText()) == false) {
			promptEntryLabel.setText("ERROR!");
		} else {
			String[] parameters = new String[2];
			parameters[0] = newEntryNameField.getText().trim();
			parameters[1] = newEntryPriceField.getText().trim();
			post.notifyMainController("AddNewEntryStrategy", parameters);
			fillMenuEntriesList();
			printMenuInTextArea();
		}
	}

	/** Method triggered by removeMenuEntryButton. */
	public void removeEntry() {
		String[] parameters = new String[1];
		parameters[0] = removeEntryComboBox.getValue().trim();
		post.notifyMainController("RemoveEntryStrategy", parameters);
		fillMenuEntriesList();
		printMenuInTextArea();
	}

	/** Method called by addNewEntry() & removeEntry(). */
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

	/**
	 * Method used to add a new order in orders' TableView.
	 * 
	 * @param order to add.
	 */
	public void addOrderToTableView(Order order) {
		ordersList.add(order);
	}

	/**
	 * Method used to remove an order from orders' TableView.
	 * 
	 * @param order's tableNum.
	 */
	public void removeOrderFromTableView(Order order) {
		ordersList.remove(order);
	}

	/**
	 * Method called in setOrderToDelivered() and in strategies in order to change
	 * the order status.
	 * 
	 * @param order's tableNum.
	 * @param new     order's status.
	 */
	public void modifyOrderStatus(int orderNum, OrderStatus status) {
		Iterator<Order> iterator = ordersList.iterator();
		while (iterator.hasNext()) {
			Order order = iterator.next();
			if (order.getOrderNum() == orderNum) {
				switch (status) {
				case SEEN:
					order.setSeen(true);
					order.setPreparable(true);
					order.setPrepared(false);
					order.setDelivered(false);
					break;
				case NOT_PREPARABLE:
					order.setSeen(true);
					order.setPreparable(false);
					order.setPrepared(false);
					order.setDelivered(false);
					break;
				case PREPARED:
					order.setSeen(true);
					order.setPreparable(true);
					order.setPrepared(true);
					order.setDelivered(false);
					break;
				case DELIVERED:
					order.setSeen(true);
					order.setPreparable(true);
					order.setPrepared(true);
					order.setDelivered(true);
					break;
				default:
					break;
				}
				break;
			}
		}
	}
}