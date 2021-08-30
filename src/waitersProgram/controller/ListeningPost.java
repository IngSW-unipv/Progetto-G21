package waitersProgram.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The ListeningPost class. It's used to connect the backend to the GUI
 * controllers via strategies invocation. The "connected" backend controller is
 * the Restaurant class (backend's facade controller). The "connected" frontend
 * controllers are WaitersController and ChefsController. Through a
 * ListeningPost instance WaitersController is able to directly invoke
 * strategies. ChefsController, on the other hand, being located on a different
 * device, will call the strategies by sending messages to ListeningPost over
 * the network: in this case ListeningPost will be the Server, ChefsController
 * the only connected client.
 * 
 * Even if it won't be done during system runtime, the bound backend controller
 * can be changed in order to forward messages to new additions in the backend.
 * This choice was made to ensure greater code modularity and allow easier
 * future changes in project's architecture.
 */
public class ListeningPost extends Thread {
	private static ListeningPost post;
	private Restaurant restaurant;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private Scanner inputStream;
	private PrintWriter outputStream;

	/**
	 * Class constructor method. (private in order to respect Singleton pattern).
	 * Starts the server to allow connection and communication with ChefsController.
	 */
	private ListeningPost() {
		restaurant = Restaurant.getInstance();
		bindController(restaurant);

		boolean isFailed = false;
		try {
			serverSocket = new ServerSocket(6789);
			System.out.println("The program is running!");
			clientSocket = serverSocket.accept();
			inputStream = new Scanner(new InputStreamReader(clientSocket.getInputStream()));
			outputStream = new PrintWriter(new DataOutputStream(clientSocket.getOutputStream()));
			System.out.println("Server running!\n");
			System.out.println(serverSocket.getLocalSocketAddress());
			System.out.println(serverSocket.getLocalPort());
		} catch (Exception e) {
			isFailed = true;
			System.err.println(e.getMessage());
		} finally {
			if (isFailed && clientSocket.isConnected()) {
				try {
					inputStream.close();
					outputStream.close();
					clientSocket.close();
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}

	/**
	 * This method returns a ListeningPost object following the Singleton pattern.
	 * 
	 * @return ListeningPost instance.
	 *
	 */
	public static ListeningPost getInstance() {
		if (post == null) {
			post = new ListeningPost();
			post.start();
		}
		return post;
	}

	/**
	 * @return backend controller's instance.
	 */
	public Restaurant getRestaurant() {
		return restaurant;
	}

	/**
	 * This method can be invoked when needed to change the bound backend
	 * controller.
	 * 
	 * @param controller specifies the involved controller.
	 */
	public void bindController(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	/**
	 * This method allows an object to communicate a message to the backend
	 * controller in order to call a strategy. The message is composed by the name
	 * of the strategy and a vector of strings (strategy arguments). Strategies has
	 * to be interpreted as services provided by the bound backend controller.
	 * 
	 * @param strategyRequired specifies the involved strategy.
	 * @param args             specifies strategies' arguments.
	 */
	public synchronized void notifyMainController(String strategyRequired, String[] args) {
		if (restaurant == null) {
			System.out.println("Controller not bound. Bind controller to run this method.");
		} else {
			restaurant.strategyCall(strategyRequired, args);
		}
		System.out.println("LOCK LIBERO!");
	}

	/**
	 * The thread in this server is used to keep a constant, unhindered, eye on the
	 * input buffer, so as to immediately perform the required services when the
	 * message arrives, through invocation of the ListeningPost.
	 */
	@Override
	public void run() {
		try {
			while (clientSocket != null && clientSocket.isConnected()) {
				String[] unpackedMessage;
				String message;
				while (!inputStream.hasNextLine())
					;
				message = inputStream.nextLine();
				unpackedMessage = message.split(", ");
				notifyMainController(unpackedMessage[0],
						Arrays.copyOfRange(unpackedMessage, 1, unpackedMessage.length));
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				clientSocket.close();
			} catch (IOException e1) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Small method that writes a message to the socket.
	 * 
	 * @param message specify the message to send.
	 */
	public synchronized void sendMessage(String message) {
		if (clientSocket.isConnected()) {
			outputStream.println(message);
			outputStream.flush();
		}
	}
}