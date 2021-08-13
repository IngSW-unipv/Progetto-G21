package controller.guicontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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

	// private ListeningPost post;

	public void addNewOrder() {

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

	}

	public void updateMenu() {

	}

	public void setOrderDelivered() {

	}

}
