package waitersProgram.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import waitersProgram.model.Order;

/**
 * Event class triggered by pressing the change order's status button. The
 * specified button is located in each row of the ordersTableView, inside the
 * actionTableColumn. The class contains an Order instance, necessary to provide
 * informations to WaitersOrderUpdateFrame GUI.
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
	 * @return order displayed in WaitersOrderUpdateFrame.
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * Method that contains the instructions to be performed after pressing the
	 * button. It launches WaitersOrderUpdateFrame GUI in order to change the
	 * order's status.
	 */
	@Override
	public void handle(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("WaitersOrderUpdateFrame.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Update order status!");
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("waitersOrderUpdateFrame.css").toExternalForm());
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
		WaitersController waitersController = WaitersController.getInstance();
		waitersController.setTableLabel("Table: " + tableLabel);
		waitersController.setOrderLabel("Order: " + orderLabel);
		waitersController.setOrderNumberLabel("Order number: " + orderNumberLabel);
	}

	/** Small method used to initialize the checkBoxes. */
	private void setCheckBox() {
		WaitersController waitersController = WaitersController.getInstance();
		Label orderNumberLabel = waitersController.getOrderNumberLabel();
		String[] orderNumberLabelSplitted = orderNumberLabel.getText().split(" ");
		String orderNum = orderNumberLabelSplitted[1].trim();
		Order currentOrder = waitersController.searchForAnOrder(Integer.parseInt(orderNum));
		waitersController.setDeliveredCheckBox(currentOrder.isDelivered());
	}
}
