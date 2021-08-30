package waitersProgram.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import waitersProgram.model.Order;

public class WaitersOrderUpdateFrameController {
	private WaitersControlPanelController mainController = null;
	private Order order;

	/** WaitersOrderUpdateFrame FXML calls. */
	@FXML
	Label tableLabel, orderLabel, orderNumberLabel, removeErrorLabel;

	@FXML
	CheckBox deliveredCheckBox;

	/**
	 * @param order to be displayed.
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * @param WaitersControlPanelController.
	 */
	public void setMainController(WaitersControlPanelController mainController) {
		this.mainController = mainController;
		setLabels();
		setCheckBoxes();
	}

	/**
	 * @return order displayed in WaitersOrderUpdateFrame.
	 */
	public Order getOrder() {
		return order;
	}

	/** Small method used to initialize labels. */
	public void setLabels() {
		String tableLabelString = Integer.toString(order.getTableNum());
		String orderLabelString = order.getOrderedEntry().toString();
		String orderNumberLabelString = Integer.toString(order.getOrderNum());
		tableLabel.setText("Table: " + tableLabelString);
		orderLabel.setText("Order: " + orderLabelString);
		orderNumberLabel.setText("Order number: " + orderNumberLabelString);
	}

	/** Small method used to initialize the checkBoxes. */
	public void setCheckBoxes() {
		deliveredCheckBox.setSelected(order.isDelivered());
		if (deliveredCheckBox.isSelected()) {
			deliveredCheckBox.setDisable(true);
		}
	}

	/** Method triggered by removeOrderButton. */
	public void removeOrder() {
		String orderNumber = Integer.toString(order.getOrderNum());
		boolean isSeen = order.isSeen();
		boolean isPreparable = order.isPreparable();
		boolean isPrepared = order.isPrepared();
		boolean isDelivered = order.isDelivered();
		if ((isSeen == false) && (isPreparable == true) && (isPrepared == false) && (isDelivered == false)) {
			String[] parameters = new String[1];
			parameters[0] = orderNumber;
			mainController.getPost().notifyMainController("RemoveOrderStrategy", parameters);
			mainController.modifyOrderStatus(order.getOrderNum(), OrderStatus.DELIVERED);
			mainController.removeOrderFromTableView(order);
		} else if ((isSeen == true) && (isPreparable == false) && (isPrepared == false) && (isDelivered == false)) {
			mainController.removeOrderFromTableView(order);
		} else {
			removeErrorLabel.setText("The order can't be removed!");
		}
	}

	/** Method triggered by deliveredCheckBox. */
	public void setOrderToDelivered() {
		String orderNumber = Integer.toString(order.getOrderNum());
		boolean isSeen = order.isSeen();
		boolean isPreparable = order.isPreparable();
		boolean isPrepared = order.isPrepared();
		boolean isDelivered = order.isDelivered();
		if ((isSeen == true) && (isPreparable == true) && (isPrepared == true) && (isDelivered == false)) {
			String[] parameters = new String[1];
			parameters[0] = orderNumber;
			mainController.getPost().notifyMainController("SetOrderToDeliveredStrategy", parameters);
			mainController.modifyOrderStatus(order.getOrderNum(), OrderStatus.DELIVERED);
			mainController.removeOrderFromTableView(order);
		} else {
			deliveredCheckBox.setSelected(false);
		}
	}
}
