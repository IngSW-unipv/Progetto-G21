package waitersProgram.controller;

import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.controlsfx.control.SearchableComboBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import waitersProgram.model.Menu;
import waitersProgram.model.MenuEntry;
import waitersProgram.model.Order;
import waitersProgram.model.TableManager;
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

public class WaitersController {

	/** WaitersControlPanel's first tab FXML calls. */
	@FXML
	SearchableComboBox<Integer> newOrderTableComboBox;
	SearchableComboBox<MenuEntry> newOrderEntryComboBox;
	TextField addNewTableField;
	SearchableComboBox<Integer> removeTableComboBox;
	TableView<Order> ordersTableView;
	TableColumn<Order, String> tableColumn, orderColumn, statusColumn;
	TableColumn<Order, Void> actionColumn;
	Label promptOrderTableLabel;

	/** WaitersControlPanel's second tab FXML calls. */
	@FXML
	SearchableComboBox<Integer> newBillTableComboBox;
	TextArea billTextArea;
	Label promptBillLabel;

	/** WaitersControlPanel's third tab FXML calls. */
	@FXML
	TextField newEntryNameField, newEntryPriceField;
	SearchableComboBox<MenuEntry> removeEntryComboBox;
	TextArea menuTextArea;
	Label promptEntryLabel;

	/** WaitersOrderUpdateFrame FXML calls. */
	@FXML
	Label tableLabel, orderLabel, orderNumberLabel;
	CheckBox deliveredCheckBox;

	private ListeningPost post;

	private static WaitersController instance = null;

	private ObservableList<Integer> tablesList;
	private ObservableList<MenuEntry> entriesList;
	private ObservableList<Order> ordersList;

	/**
	 * Class constructor method. It's intended as the initialization method of the
	 * controller.
	 */
	private WaitersController() {
		tablesList = FXCollections.observableArrayList();
		entriesList = FXCollections.observableArrayList();
		ordersList = FXCollections.observableArrayList();
		post = ListeningPost.getInstance();

		fillTablesList();
		fillMenuEntriesList();

		tableColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("Table"));
		orderColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("Order"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("Status"));
		addButtonToTable();
	}

	/**
	 * Method used to add the order's status changing button to each row of
	 * ordersTableView. It invokes actionButton.setOnAction in order to bind the
	 * same EventHandler to each created button.
	 */
	private void addButtonToTable() {
		Callback<TableColumn<Order, Void>, TableCell<Order, Void>> cellFactory = new Callback<TableColumn<Order, Void>, TableCell<Order, Void>>() {
			@Override
			public TableCell<Order, Void> call(final TableColumn<Order, Void> param) {
				final TableCell<Order, Void> cell = new TableCell<Order, Void>() {

					private final Button actionButton = new Button("Action");

					{
						actionButton.setOnAction(new ButtonClickEventHandler());
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(actionButton);
						}
					}
				};
				return cell;
			}
		};
		actionColumn.setCellFactory(cellFactory);
	}

	/**
	 * Private method called in the constructor and in addNewTable(), removeTable().
	 * It's used to update tables ObservableList.
	 */
	private void fillTablesList() {
		tablesList.clear();
		TableManager tableManager = Restaurant.getInstance().getTableManager();
		tablesList.addAll(tableManager.getTables());
	}

	/**
	 * Private method called in the constructor and in addNewEntry(), removeEntry().
	 * It's used to update entries ObservableList.
	 */
	private void fillMenuEntriesList() {
		entriesList.clear();
		Menu menu = Restaurant.getInstance().getRestaurantMenu();
		entriesList.addAll(menu.getEntriesCollection());
	}

	/**
	 * Private method used to check tableField format.
	 * 
	 * @param tableField.
	 * @return boolean variable.
	 */
	private boolean checkForTableFormat(TextField tableField) {
		Pattern p = Pattern.compile("\\d{1,3}");
		Matcher m = p.matcher(tableField.getText().trim());
		return m.matches();
	}

	/**
	 * Private method used to check entryNameField format.
	 * 
	 * @param entryNameField.
	 * @return boolean variable.
	 */
	private boolean checkForEntryNameFormat(TextField entryNameField) {
		Pattern p = Pattern.compile("^[A-Za-z0-9‡ËÏÚ˘·ÈÌÛ˙‚ÍÓÙ˚„Òı‰ÎÔˆ¸ˇ ]*$");
		Matcher m = p.matcher(entryNameField.getText().trim());
		return m.matches();
	}

	/**
	 * Private method used to check entryPriceField format.
	 * 
	 * @param entryPriceField.
	 * @return boolean variable.
	 */
	private boolean checkForEntryPriceFormat(TextField entryPriceField) {
		Pattern p = Pattern.compile("\\\\d{1,5}\\\\.{0,1}\\\\d{0,2}$");
		Matcher m = p.matcher(entryPriceField.getText().trim());
		return m.matches();
	}

	/**
	 * Static method that returns a WaitersController instance in order to observe
	 * the Singleton pattern. It calls the class constructor only if this has not
	 * happened before.
	 * 
	 * @return WaitersController instance.
	 */
	public static WaitersController getInstance() {
		if (instance == null) {
			instance = new WaitersController();
		}
		return instance;
	}

	/**
	 * @return used ListeningPost instance.
	 */
	public ListeningPost getPost() {
		return post;
	}

	/** Method triggered by createNewOrderButton. */
	public void createNewOrder() {
		Integer selectedTableNum = newOrderTableComboBox.getValue();
		MenuEntry selectedEntry = newOrderEntryComboBox.getValue();
		String[] parameters = new String[2];
		parameters[0] = selectedTableNum.toString();
		parameters[1] = selectedEntry.toString();
		post.notifyMainController("AddNewOrderStrategy", parameters);
		ordersList.add(new Order(selectedTableNum, selectedEntry));
	}

	/** Method triggered by addNewTableButton. */
	public void addNewTable() {
		if (checkForTableFormat(addNewTableField) == false) {
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
		Integer selectedTableNum = removeTableComboBox.getValue();
		String[] parameters = new String[1];
		parameters[0] = selectedTableNum.toString();
		post.notifyMainController("RemoveTableStrategy", parameters);
		fillTablesList();
	}

	/** Method triggered by printBillButton. */
	public void printNewBill() {
		billTextArea.clear();
		Integer selectedTableNum = removeTableComboBox.getValue();
		String[] parameters = new String[1];
		parameters[0] = selectedTableNum.toString();
		post.notifyMainController("PrintNewBillStrategy", parameters);
		billTextArea.setText(PrintNewBillStrategy.getBill().toString());
	}

	/** Method triggered by addNewEntryButton. */
	public void addNewEntry() {
		if (checkForEntryNameFormat(newEntryNameField) == false
				|| checkForEntryPriceFormat(newEntryPriceField) == false) {
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
		MenuEntry selectedEntry = removeEntryComboBox.getValue();
		String[] parameters = new String[1];
		parameters[0] = selectedEntry.toString();
		post.notifyMainController("RemoveEntryStrategy", parameters);
		fillMenuEntriesList();
		printMenuInTextArea();
	}

	/** Method triggered by deliveredCheckBox. */
	public void setOrderToDelivered() {
		String[] orderNumberLabelSplitted = orderNumberLabel.getText().split(" ");
		String orderNum = orderNumberLabelSplitted[1].trim();
		String[] parameters = new String[1];
		parameters[0] = orderNum;
		post.notifyMainController("SetOrderToDeliveredStrategy", parameters);
		modifyOrderStatus(Integer.parseInt(parameters[0]), OrderStatus.DELIVERED);
	}

	/**
	 * Method used to add a new order in orders' TableView.
	 * 
	 * @param order's tableNum.
	 * @param order's entry.
	 */
	public void addOrderInTableView(Integer tableNum, MenuEntry entry) {
		ordersList.add(new Order(tableNum, entry));
	}

	/**
	 * Method used to remove an order from orders' TableView.
	 * 
	 * @param order's tableNum.
	 */
	public void removeOrderInTableView(Order order) {
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
			}
			break;
		}
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
}