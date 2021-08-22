package chefsProgram.controller;



import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import io.github.classgraph.ClassGraph;

import chefsProgram.strategies.StrategyAbstract;
import chefsProgram.strategies.StrategyInterface;

/**
 * The ChefsGuiController class. It will be used to control the chef's graphical
 * interface and it will contain a ListeningPost instance in order to ensure the
 * communication with the SystemController class.
 * 
 * This class and its GUI will be running on a different device than the others
 * in order to obtain a distributed system: that's the client, ListeningPost
 * contains the server.
 */

public class ChefsGuiController extends Thread{

	@FXML
	AnchorPane ordersScrollPane;
	private HashMap<String, StrategyAbstract> strategies;
	
	private Socket serverSocket = null; // = new Socket("localhost", 4999);
	private BufferedReader readBuffer = null;
	private BufferedWriter writeBuffer = null;
	private String serverName = "localhost";

	private static ChefsGuiController instance = null;

	private ChefsGuiController() {
		
	}

	public static ChefsGuiController getInstance() {
		if (instance == null) {
			instance = new ChefsGuiController();
		}
		return instance;
	}



	public void updateOrders() {

	}

	public void setOrderSeenToPreparable() {
		// post.notifyMainController(strategy, args)

	}

	public void setOrderSeenToNotPreparable() {

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
					if(unpackedMessage[0].compareTo("ADD")==0) 
						System.out.println("replace with method to update orders");
					else 
						System.out.println("replace with method to remove orders");
						
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
