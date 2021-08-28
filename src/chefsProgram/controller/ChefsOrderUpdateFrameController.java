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
	CheckBox seenCheckBox, notPreparableCheckBox, preparedCheckBox;

	/**
	 * Class constructor method.
	 * 
	 * @param order to be displayed in ChefsOrderUpdateFrame.
	 */
	public ChefsOrderUpdateFrameController(Order order) {
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
		tableLabel.setText(tableLabelString);
		orderLabel.setText(orderLabelString);
		orderNumberLabel.setText(orderNumberLabelString);
	}

	/** Small method used to initialize the checkBoxes. */
	public void setCheckBoxes() {
		seenCheckBox.setSelected(order.isSeen());
		notPreparableCheckBox.setSelected(!order.isPreparable());
		preparedCheckBox.setSelected(order.isPrepared());

		if (seenCheckBox.isSelected()) {
			seenCheckBox.setDisable(true);
		}

		if (notPreparableCheckBox.isSelected()) {
			notPreparableCheckBox.setDisable(true);
		}

		if (preparedCheckBox.isSelected()) {
			preparedCheckBox.setDisable(true);
		}
	}

	/**
	 * Method triggered by seenCheckBox. It calls SetOrderToSeenStrategy via the
	 * sendMessage method, which sends a string to the server (ListeningPost).
	 */
	public void setOrderToSeen() {
		String[] orderNumberLabelSplitted = orderNumberLabel.getText().split(" ");
		String orderNum = orderNumberLabelSplitted[1].trim();
		Order currentOrder = mainController.searchForAnOrder(Integer.parseInt(orderNum));
		boolean isSeen = currentOrder.isSeen();
		boolean isPreparable = currentOrder.isPreparable();
		boolean isPrepared = currentOrder.isPrepared();
		boolean isDelivered = currentOrder.isDelivered();
		if ((isSeen == false) && (isPreparable == true) && (isPrepared == false) && (isDelivered == false)) {
			mainController.sendMessage("SetOrderToSeenStrategy, " + orderNum);
		} else {
			seenCheckBox.setSelected(false);
		}
	}

	/**
	 * Method triggered by notPreparableCheckBox. It calls
	 * SetOrderToNotPreparableStrategy via the sendMessage method, which sends a
	 * string to the server (ListeningPost).
	 */
	public void setOrderToNotPreparable() {
		String[] orderNumberLabelSplitted = orderNumberLabel.getText().split(" ");
		String orderNum = orderNumberLabelSplitted[1].trim();
		Order currentOrder = mainController.searchForAnOrder(Integer.parseInt(orderNum));
		boolean isSeen = currentOrder.isSeen();
		boolean isPreparable = currentOrder.isPreparable();
		boolean isPrepared = currentOrder.isPrepared();
		boolean isDelivered = currentOrder.isDelivered();
		if ((isSeen == false) && (isPreparable == true) && (isPrepared == false) && (isDelivered == false)) {
			mainController.sendMessage("SetOrderToNotPreparableStrategy, " + orderNum);
			mainController.removeOrderFromTableView(currentOrder.getOrderNum());
		} else {
			notPreparableCheckBox.setSelected(false);
		}
	}

	/**
	 * Method triggered by preparedCheckBox. It calls SetOrderToPreparedStrategy via
	 * the sendMessage method, which sends a string to the server (ListeningPost).
	 */
	public void setOrderToPrepared() {
		String[] orderNumberLabelSplitted = orderNumberLabel.getText().split(" ");
		String orderNum = orderNumberLabelSplitted[1].trim();
		Order currentOrder = mainController.searchForAnOrder(Integer.parseInt(orderNum));
		boolean isSeen = currentOrder.isSeen();
		boolean isPreparable = currentOrder.isPreparable();
		boolean isPrepared = currentOrder.isPrepared();
		boolean isDelivered = currentOrder.isDelivered();
		if ((isSeen == true) && (isPreparable == true) && (isPrepared == false) && (isDelivered == false)) {
			mainController.sendMessage("SetOrderToPreparedStrategy, " + orderNum);
		} else {
			preparedCheckBox.setSelected(false);
		}
	}
}
