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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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

public class ChefsControlPanelController extends Thread {

	/** ChefsControlPanel FXML calls. */
	@FXML
	TableView<Order> ordersTableView;

	@FXML
	TableColumn<Order, String> tableColumn, orderColumn, statusColumn;

	private ObservableList<Order> ordersList = FXCollections.observableArrayList();

	private Socket serverSocket = null; // = new Socket("localhost", 4999);
	private BufferedReader readBuffer = null;
	private BufferedWriter writeBuffer = null;
	private String serverName = "localhost";

	/**
	 * Method required to initialize FXML elements.
	 */
	@FXML
	private void initialize() {
		ordersTableView.setItems(ordersList);

		// For test purposes
		ordersList.add(new Order(32, new MenuEntry("Pasta al pomodoro, 4")));
		Order order = new Order(32, new MenuEntry("Lasagne, 5"));
		ordersList.add(order);
		modifyOrderStatus(order.getOrderNum(), OrderStatus.NOT_PREPARABLE);

		tableColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("tableNum"));
		orderColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("orderedEntryStringed"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("orderStatusStringed"));

		// connect();
	}

	/**
	 * Method used to show ChefsOrderUpdateFrame. Triggered by mouse click on table
	 * rows.
	 */
	@FXML
	private void handleRowSelect() {
		Order row = ordersTableView.getSelectionModel().getSelectedItem();
		if (row != null) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(
						getClass().getResource("/chefsProgram/view/ChefsOrderUpdateFrame.fxml"));
				Parent root = fxmlLoader.load();
				Stage stage = new Stage();
				stage.setTitle("Update order status!");
				Scene scene = new Scene(root);
				scene.getStylesheets()
						.add(getClass().getResource("/chefsProgram/view/chefsOrderUpdateFrame.css").toExternalForm());
				stage.setScene(scene);
				ChefsOrderUpdateFrameController updateFrameController = fxmlLoader.getController();
				updateFrameController.setOrder(row);
				updateFrameController.setMainController(this);
				stage.show();
				System.out.println(row);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return serverName.
	 */
	public String getServerName() {
		return serverName;
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
			e.printStackTrace();
		} finally {
			if (isFailed && serverSocket.isConnected()) {
				try {
					serverSocket.close();
				} catch (Exception e) {
					e.printStackTrace();
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
			e.printStackTrace();
		}

		finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
