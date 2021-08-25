package chefsProgram.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.regex.Pattern;

import chefsProgram.model.MenuEntry;
import chefsProgram.model.Order;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * The ChefsGuiController class. It will be used to control the chef's graphical
 * interface and it will contain a ListeningPost instance in order to ensure the
 * communication with the SystemController class.
 * 
 * This class and its GUI will be running on a different device than the others
 * in order to obtain a distributed system: that's the client, ListeningPost
 * contains the server.
 */

public class ChefsGuiController extends Thread {

	@FXML
	TableView<Order> ordersTableView;
	TableColumn<Order, String> tableColumn;
	TableColumn<Order, String> orderColumn;
	TableColumn<Order, String> statusColumn;

	@FXML
	Label tableLabel, orderLabel, orderNumberLabel;

	@FXML
	CheckBox seenCheckBox, notPreparableCheckBox, preparedCheckBox;

	private ObservableList<Order> ordersList;

	private Socket serverSocket = null; // = new Socket("localhost", 4999);
	private BufferedReader readBuffer = null;
	private BufferedWriter writeBuffer = null;
	private String serverName = "localhost";

	private static ChefsGuiController instance = null;

	private ChefsGuiController() {
		connect();
		tableColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("Table"));
		orderColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("Order"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("Status"));
	}

	public static ChefsGuiController getInstance() {
		if (instance == null) {
			instance = new ChefsGuiController();
		}
		return instance;
	}

	public void addOrderToChef(int tableNum, MenuEntry entry) {
		ordersList.add(new Order(tableNum, entry));
	}

	public void removeOrderToChef(int tableNum, MenuEntry entry) {
		ordersList.remove(new Order(tableNum, entry));
	}

	public void setOrderSeenToPreparable() {
		// sendMessage();
	}

	public void setOrderToNotPreparable() {
		sendMessage("SetOrderToNotPreparable, ");

	}

	public void setOrderToPrepared() {

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

			System.out.println("Client is connected!\n");

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
					if (unpackedMessage[0].equals("ADD") == true)
						System.out.println("replace with method to update orders");
					else if (unpackedMessage[0].equals("REMOVE") == true) {
						System.out.println("replace with method to remove orders");
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
