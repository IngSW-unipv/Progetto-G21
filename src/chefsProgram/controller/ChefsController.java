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
import javafx.util.Callback;
import waitersProgram.controller.OrderStatus;

/**
 * The ChefsGuiController class. It will be used to control the chef's graphical
 * interface and it will contain a ListeningPost instance in order to ensure the
 * communication with the SystemController class.
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

	private ChefsController() {
		ordersList = FXCollections.observableArrayList();
		connect();
		tableColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("Table"));
		orderColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("Order"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("Status"));
		addButtonToTable();
	}

	public static ChefsController getInstance() {
		if (instance == null) {
			instance = new ChefsController();
		}
		return instance;
	}

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

	/** Method called in run(). */
	public void addOrderToChef(int orderNum, int tableNum, MenuEntry entry) {
		Order orderToAdd = new Order(tableNum, entry);
		orderToAdd.setOrderNum(orderNum);
		ordersList.add(new Order(tableNum, entry));
	}

	/** Method called in run(). */
	public void removeOrderToChef(int orderNum) {
		Iterator<Order> iterator = ordersList.iterator();
		Order currentOrder = null;
		while (iterator.hasNext()) {
			currentOrder = iterator.next();
			if (currentOrder.getOrderNum() == orderNum) {
				break;
			}
		}
		ordersList.remove(currentOrder);
	}

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

	/** Method triggered by seenCheckBox. */
	public void setOrderSeenToPreparable() {
		String[] orderNumberLabelSplitted = orderNumberLabel.getText().split(" ");
		String orderNum = orderNumberLabelSplitted[1].trim();
		sendMessage("SetOrderToSeenStrategy, " + orderNum);
	}

	/** Method triggered by notPreparableCheckBox. */
	public void setOrderToNotPreparable() {
		String[] orderNumberLabelSplitted = orderNumberLabel.getText().split(" ");
		String orderNum = orderNumberLabelSplitted[1].trim();
		sendMessage("SetOrderToNotPreparableStrategy, " + orderNum);
	}

	/** Method triggered by preparedCheckBox. */
	public void setOrderToPrepared() {
		String[] orderNumberLabelSplitted = orderNumberLabel.getText().split(" ");
		String orderNum = orderNumberLabelSplitted[1].trim();
		sendMessage("SetOrderToPreparedStrategy, " + orderNum);
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

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

	/** Small method that writes a message to the socket. */
	public synchronized void sendMessage(String message) {
		try {
			if (serverSocket.isConnected())
				writeBuffer.write(message + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
						addOrderToChef(orderNum, tableNum, new MenuEntry(entryName + ", " + entryPrice));
					} else if (unpackedMessage[0].equals("REMOVE") == true) {
						// REMOVE, orderNum
						int orderNum = Integer.parseInt(unpackedMessage[1].trim());
						removeOrderToChef(orderNum);
					} else if (unpackedMessage[0].equals("SET_DELIVERED") == true) {
						// SET_DELIVERED, orderNum
						int orderNum = Integer.parseInt(unpackedMessage[1].trim());
						modifyOrderStatus(orderNum, OrderStatus.DELIVERED);
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
