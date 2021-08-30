package chefsProgram.controller;

import chefsProgram.model.Order;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class ChefsOrderUpdateFrameController {
	private ChefsControlPanelController mainController = null;
	private Order order;

	/** ChefsOrderUpdateFrame FXML calls. */
	@FXML
	Label tableLabel, orderLabel, orderNumberLabel;

	@FXML
	CheckBox seenCheckBox, notPreparableCheckBox, preparedCheckBox;

	/**
	 * @param order to be displayed.
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * @param ChefsControlPanelController.
	 */
	public void setMainController(ChefsControlPanelController mainController) {
		this.mainController = mainController;
		setLabels();
		setCheckBoxes();
	}

	/**
	 * @return order displayed in ChefsOrderUpdateFrame.
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
		seenCheckBox.setSelected(order.isSeen());
		notPreparableCheckBox.setSelected(!order.isPreparable());
		preparedCheckBox.setSelected(order.isPrepared());

		if (seenCheckBox.isSelected()) {
			seenCheckBox.setDisable(true);
			notPreparableCheckBox.setDisable(true);
		}

		if (!seenCheckBox.isSelected() && !notPreparableCheckBox.isSelected()) {
			preparedCheckBox.setDisable(true);
		}

		if (notPreparableCheckBox.isSelected()) {
			seenCheckBox.setDisable(true);
			notPreparableCheckBox.setDisable(true);
			preparedCheckBox.setDisable(true);
		}

		if (preparedCheckBox.isSelected()) {
			seenCheckBox.setDisable(true);
			notPreparableCheckBox.setDisable(true);
			preparedCheckBox.setDisable(true);
		}
	}

	/**
	 * Method triggered by seenCheckBox. It calls SetOrderToSeenStrategy via the
	 * sendMessage method, which sends a string to the server (ListeningPost).
	 */
	@FXML
	public void setOrderToSeen() {
		String orderNumber = Integer.toString(order.getOrderNum());
		boolean isSeen = order.isSeen();
		boolean isPreparable = order.isPreparable();
		boolean isPrepared = order.isPrepared();
		boolean isDelivered = order.isDelivered();
		if ((isSeen == false) && (isPreparable == true) && (isPrepared == false) && (isDelivered == false)) {
			mainController.sendMessage("SetOrderToSeenStrategy, " + orderNumber);
			mainController.modifyOrderStatus(order.getOrderNum(), OrderStatus.SEEN);
		} else {
			seenCheckBox.setSelected(false);
		}
	}

	/**
	 * Method triggered by notPreparableCheckBox. It calls
	 * SetOrderToNotPreparableStrategy via the sendMessage method, which sends a
	 * string to the server (ListeningPost).
	 */
	@FXML
	public void setOrderToNotPreparable() {
		String orderNumber = Integer.toString(order.getOrderNum());
		boolean isSeen = order.isSeen();
		boolean isPreparable = order.isPreparable();
		boolean isPrepared = order.isPrepared();
		boolean isDelivered = order.isDelivered();
		if ((isSeen == false) && (isPreparable == true) && (isPrepared == false) && (isDelivered == false)) {
			mainController.sendMessage("SetOrderToNotPreparableStrategy, " + orderNumber);
			mainController.modifyOrderStatus(order.getOrderNum(), OrderStatus.NOT_PREPARABLE);
			mainController.removeOrderFromTableView(order.getOrderNum());
		} else {
			notPreparableCheckBox.setSelected(false);
		}
	}

	/**
	 * Method triggered by preparedCheckBox. It calls SetOrderToPreparedStrategy via
	 * the sendMessage method, which sends a string to the server (ListeningPost).
	 */
	@FXML
	public void setOrderToPrepared() {
		String orderNumber = Integer.toString(order.getOrderNum());
		boolean isSeen = order.isSeen();
		boolean isPreparable = order.isPreparable();
		boolean isPrepared = order.isPrepared();
		boolean isDelivered = order.isDelivered();
		if ((isSeen == true) && (isPreparable == true) && (isPrepared == false) && (isDelivered == false)) {
			mainController.sendMessage("SetOrderToPreparedStrategy, " + orderNumber);
			mainController.modifyOrderStatus(order.getOrderNum(), OrderStatus.PREPARED);
		} else {
			preparedCheckBox.setSelected(false);
		}
	}
}
