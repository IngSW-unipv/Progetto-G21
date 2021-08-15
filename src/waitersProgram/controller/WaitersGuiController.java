package waitersProgram.controller;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import waitersProgram.model.Order;
import waitersProgram.model.OrderManager;

/**
 * The WaitersGuiController class. It will be used to control the waiter's
 * graphical interface and it will contain a ListeningPost instance in order to
 * ensure the communication with the SystemController class.
 */

public class WaitersGuiController {

	/** FXML calls for new order creation. */
	@FXML
	TextField newOrderTableField;
	@FXML
	TextField newOrderEntryField;

	/** FXML calls for new table creation. */
	@FXML
	TextField addNewTableField;

	/** FXML calls for table removal. */
	@FXML
	TextField removeTableField;

	/** FXML calls for new bill printing. */
	@FXML
	TextField newBillTableField;
	@FXML
	TextField printBillField;

	/** FXML calls for adding a new menu entry. */
	@FXML
	TextField newEntryNameField;
	@FXML
	TextField newEntryPriceField;

	/** FXML calls for entry removal. */
	@FXML
	TextField removeEntryNameField;
	@FXML
	TextField removeEntryPriceField;

	/** FXML calls for order's and menu's panes. */
	@FXML
	AnchorPane ordersPane;
	@FXML
	AnchorPane menuPane;

	/** FXML calls for prompt labels. */
	@FXML
	Label promptOrderTableLabel;
	@FXML
	Label promptBillLabel;
	@FXML
	Label promptEntryLabel;

	private ListeningPost post;

	private static WaitersGuiController instance = null;

	private WaitersGuiController() {
		post = ListeningPost.getInstance();
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

	public void addNewOrder() {
		// post.notifyController -> restaurant.strategyCall -> strategy.execute.

	}

	public void addNewTable() {

	}

	public void removeTable() {

	}

	public void printBill() {

	}

	public void addNewEntry() {

	}

	public void removeEntry() {

	}

	public void updateOrders() {
		ordersPane.getChildren().clear();
		OrderManager orderManager = Restaurant.getInstance().getOrderManager();
		ArrayList<Order> ordersToDisplay = orderManager.getAllOrdersForWaitersGuiController();

		Iterator<Order> iterator = ordersToDisplay.iterator();
		while (iterator.hasNext()) {
			ordersPane.getChildren().add(new Label(iterator.next().toString()));
		}
	}

	public void updateMenu() {

	}

	public void setOrderDelivered() {

	}

}
