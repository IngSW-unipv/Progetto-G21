package chefsProgram.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.regex.Pattern;

import chefsProgram.model.MenuEntry;
import chefsProgram.model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * The ChefsGuiController class. It will be used to control the chef's graphical
 * interface (both ChefsControlPanel and ChefsOrderUpdateFrame) and it will
 * contain a ListeningPost instance in order to ensure the communication with
 * the Restaurant class (backend's facade controller).
 * 
 * This class and its GUI will be running on a different device than the others
 * in order to obtain a distributed system: that's the client, ListeningPost
 * contains the server.
 */

public class ChefsController extends Thread {

	/** ChefsControlPanel FXML calls. */
	@FXML
	TableView<Order> ordersTableView;
	TableColumn<Order, String> tableColumn, orderColumn, statusColumn;
	TableColumn<Order, Void> actionColumn;

	/** ChefsOrderUpdateFrame FXML calls. */
	@FXML
	Label tableLabel, orderLabel, orderNumberLabel;
	CheckBox seenCheckBox, notPreparableCheckBox, preparedCheckBox;

	private ObservableList<Order> ordersList;

	private Socket serverSocket = null; // = new Socket("localhost", 4999);
	private BufferedReader readBuffer = null;
	private BufferedWriter writeBuffer = null;
	private String serverName = "localhost";

	private static ChefsController instance = null;

	/**
	 * Class constructor method. (look initialize method for FXML elements).
	 */
	private ChefsController() {

	}

	/**
	 * Method required to initialize FXML elements.
	 */
	@FXML
	private void initialize() {
		ordersList = FXCollections.observableArrayList();
		connect();
		tableColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("Table"));
		orderColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("Order"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("Status"));

		actionColumn.setCellFactory(col -> new TableCell<Order, Void>() {

			private final Button actionButton;

			{
				actionButton = new Button("Change status");
				actionButton.setOnAction(new ButtonClickEventHandler(getTableRow().getItem()));
			}

			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				setGraphic(empty ? null : actionButton);
			}
		});

		ordersTableView.setItems(ordersList);
	}

	/**
	 * Static method that returns a ChefsController instance in order to observe the
	 * Singleton pattern. It calls the class constructor only if this has not
	 * happened before.
	 * 
	 * @return ChefsController instance.
	 */
	public static ChefsController getInstance() {
		if (instance == null) {
			instance = new ChefsController();
		}
		return instance;
	}

	/**
	 * @return orderNumberLabel.
	 */
	public Label getOrderNumberLabel() {
		return orderNumberLabel;
	}

	/** @param tableLabel text. */
	public void setTableLabel(String text) {
		tableLabel.setText(text);
	}

	/** @param orderLabel text. */
	public void setOrderLabel(String text) {
		orderLabel.setText(text);
	}

	/** @param orderNumberLabel text. */
	public void setOrderNumberLabel(String text) {
		orderNumberLabel.setText(text);
	}

	/**
	 * @return serverName.
	 */
	public String getServerName() {
		return serverName;
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

	/**
	 * @param check (true or false if you have to check or uncheck the checkBox).
	 */
	public void setSeenCheckBox(boolean check) {
		seenCheckBox.setSelected(check);
		if (check == true) {
			seenCheckBox.setDisable(check);
		}
	}

	/**
	 * @param check (true or false if you have to check or uncheck the checkBox).
	 */
	public void setNotPreparableCheckBox(boolean check) {
		notPreparableCheckBox.setSelected(check);
		if (check == true) {
			notPreparableCheckBox.setDisable(check);
		}
	}

	/**
	 * @param check (true or false if you have to check or uncheck the checkBox).
	 */
	public void setPreparedCheckBox(boolean check) {
		preparedCheckBox.setSelected(check);
		if (check == true) {
			preparedCheckBox.setDisable(check);
		}
	}

	/**
	 * Method triggered by seenCheckBox. It calls SetOrderToSeenStrategy via the
	 * sendMessage method, which sends a string to the server (ListeningPost).
	 */
	public void setOrderToSeen() {
		String[] orderNumberLabelSplitted = orderNumberLabel.getText().split(" ");
		String orderNum = orderNumberLabelSplitted[1].trim();
		Order currentOrder = searchForAnOrder(Integer.parseInt(orderNum));
		boolean isSeen = currentOrder.isSeen();
		boolean isPreparable = currentOrder.isPreparable();
		boolean isPrepared = currentOrder.isPrepared();
		boolean isDelivered = currentOrder.isDelivered();
		if ((isSeen == false) && (isPreparable == true) && (isPrepared == false) && (isDelivered == false)) {
			sendMessage("SetOrderToSeenStrategy, " + orderNum);
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
		Order currentOrder = searchForAnOrder(Integer.parseInt(orderNum));
		boolean isSeen = currentOrder.isSeen();
		boolean isPreparable = currentOrder.isPreparable();
		boolean isPrepared = currentOrder.isPrepared();
		boolean isDelivered = currentOrder.isDelivered();
		if ((isSeen == false) && (isPreparable == true) && (isPrepared == false) && (isDelivered == false)) {
			sendMessage("SetOrderToNotPreparableStrategy, " + orderNum);
			ordersList.remove(currentOrder);
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
		Order currentOrder = searchForAnOrder(Integer.parseInt(orderNum));
		boolean isSeen = currentOrder.isSeen();
		boolean isPreparable = currentOrder.isPreparable();
		boolean isPrepared = currentOrder.isPrepared();
		boolean isDelivered = currentOrder.isDelivered();
		if ((isSeen == true) && (isPreparable == true) && (isPrepared == false) && (isDelivered == false)) {
			sendMessage("SetOrderToPreparedStrategy, " + orderNum);
		} else {
			preparedCheckBox.setSelected(false);
		}
	}

	/**
	 * @param serverName.
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 * Method called in run(). It's used to add a specified order to ordersTableView
	 * through ordersList.
	 * 
	 * @param orderNum specify the involved order's number.
	 * @param tableNum specify the involved order's table number.
	 * @param entry    specify the involved order's menu entry.
	 */
	public void addOrderToTableView(int orderNum, int tableNum, MenuEntry entry) {
		Order orderToAdd = new Order(tableNum, entry);
		orderToAdd.setOrderNum(orderNum);
		ordersList.add(new Order(tableNum, entry));
	}

	/**
	 * Method called in run(). It's used to remove a specified order to
	 * ordersTableView through ordersList.
	 * 
	 * @param orderNum specify the involved order's number.
	 */
	public void removeOrderFromTableView(int orderNum) {
		Iterator<Order> iterator = ordersList.iterator();
		Order currentOrder = null;
		Order orderToBeRemoved = null;
		while (iterator.hasNext()) {
			currentOrder = iterator.next();
			if (currentOrder.getOrderNum() == orderNum) {
				orderToBeRemoved = currentOrder;
				break;
			}
		}
		if (orderToBeRemoved != null) {
			ordersList.remove(orderToBeRemoved);
		}
	}

	/**
	 * Method called in run(). It's used to change status to a specified order in
	 * ordersTableView.
	 * 
	 * @param orderNum specify the involved order's number.
	 * @param status   specify the new order status (OrderStatus object).
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

	/**
	 * Method used to establish a connection with the server. Through the start
	 * method it starts a thread which is always listening for messages from the
	 * server.
	 */
	public void connect() {
		boolean isFailed = false;
		try {
			serverSocket = new Socket(serverName, 4999);

			readBuffer = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			writeBuffer = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));

			this.start();

		} catch (Exception e) {
			isFailed = true;
			System.err.println(e.getMessage());
		} finally {
			if (isFailed && serverSocket.isConnected()) {
				try {
					serverSocket.close();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}

	/**
	 * Small method that writes a message to the server's socket.
	 * 
	 * @param message specify the message to send to the server.
	 */
	public synchronized void sendMessage(String message) {
		try {
			if (serverSocket.isConnected())
				writeBuffer.write(message + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Client thread main method. It's used to intercept messages from the server
	 * and, based on these, update the GUI.
	 */
	@Override
	public void run() {
		try {
			Pattern p = Pattern.compile("^([a-zA-Z0-9]+, )+[a-zA-Z0-9]+$");
			while (serverSocket.isConnected()) {
				String[] unpackedMessage;
				String message;
				while ((message = readBuffer.readLine()) == null && serverSocket.isConnected())
					;
				if (p.matcher(message).matches()) {
					unpackedMessage = message.split(", ");
					if (unpackedMessage[0].equals("ADD") == true) {
						// ADD, orderNum, tableNum, entryName, entryPrice
						int orderNum = Integer.parseInt(unpackedMessage[1].trim());
						int tableNum = Integer.parseInt(unpackedMessage[2].trim());
						String entryName = unpackedMessage[3].trim();
						String entryPrice = unpackedMessage[4].trim();
						addOrderToTableView(orderNum, tableNum, new MenuEntry(entryName + ", " + entryPrice));
					} else if (unpackedMessage[0].equals("REMOVE") == true) {
						// REMOVE, orderNum
						int orderNum = Integer.parseInt(unpackedMessage[1].trim());
						removeOrderFromTableView(orderNum);
					} else if (unpackedMessage[0].equals("SET_DELIVERED") == true) {
						// SET_DELIVERED, orderNum
						int orderNum = Integer.parseInt(unpackedMessage[1].trim());
						modifyOrderStatus(orderNum, OrderStatus.DELIVERED);
						removeOrderFromTableView(orderNum);
					}
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
	}
}
