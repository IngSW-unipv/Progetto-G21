package chefsProgram.controller;

import chefsProgram.model.Order;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Event class triggered by pressing the change order's status button. The
 * specified button is located in each row of the ordersTableView, inside the
 * actionTableColumn. The class contains an Order instance, necessary to provide
 * informations to ChefsOrderUpdateFrame GUI.
 */
public class ButtonClickEventHandler implements EventHandler<ActionEvent> {
	private Order order;

	/**
	 * Class constructor method.
	 * 
	 * @param order to display.
	 */
	public ButtonClickEventHandler(Order order) {
		this.order = order;
	}

	/**
	 * @return order displayed in ChefsOrderUpdateFrame.
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * Method that contains the instructions to be performed after pressing the
	 * button. It launches ChefsOrderUpdateFrame GUI in order to change the order's
	 * status.
	 */
	@Override
	public void handle(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("ChefsOrderUpdateFrame.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Update order status!");
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("chefsOrderUpdateFrame.css").toExternalForm());
			stage.setScene(scene);
			setLabels();
			setCheckBox();
			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/** Small method used to initialize labels. */
	private void setLabels() {
		String tableLabel = Integer.toString(order.getTableNum());
		String orderLabel = order.getOrderedEntry().toString();
		String orderNumberLabel = Integer.toString(order.getOrderNum());
		ChefsController chefsController = ChefsController.getInstance();
		chefsController.setTableLabel("Table: " + tableLabel);
		chefsController.setOrderLabel("Order: " + orderLabel);
		chefsController.setOrderNumberLabel("Order number: " + orderNumberLabel);
	}

	/** Small method used to initialize the checkBoxes. */
	private void setCheckBox() {
		ChefsController chefsController = ChefsController.getInstance();
		Label orderNumberLabel = chefsController.getOrderNumberLabel();
		String[] orderNumberLabelSplitted = orderNumberLabel.getText().split(" ");
		String orderNum = orderNumberLabelSplitted[1].trim();
		Order currentOrder = chefsController.searchForAnOrder(Integer.parseInt(orderNum));
		chefsController.setSeenCheckBox(currentOrder.isSeen());
		chefsController.setNotPreparableCheckBox(!currentOrder.isPreparable());
		chefsController.setSeenCheckBox(currentOrder.isPrepared());
	}
}
