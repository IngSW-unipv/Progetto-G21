package waitersProgram.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import waitersProgram.model.Order;

public class WaitersOrderUpdateFrameController {

	@FXML
	Label tableLabel, orderLabel;

	@FXML
	CheckBox deliveredCheckBox;

	private static WaitersOrderUpdateFrameController instance = null;

	public WaitersOrderUpdateFrameController() {

	}

	public static WaitersOrderUpdateFrameController getInstance() {
		if (instance == null) {
			instance = new WaitersOrderUpdateFrameController();
		}
		return instance;
	}

	public void updateTableLabel(String tableNum) {
		tableLabel.setText("Table: " + tableNum);
	}

	public void updateOrderLabel(String order) {
		orderLabel.setText("Order: " + order);
	}

	public void setOrderToDelivered(Order order) {
		String[] parameters = new String[1];
		parameters[0] = Integer.toString(order.getOrderNum());
	}

}
