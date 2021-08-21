package waitersProgram.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * The ListeningPost class. Once instantiated, it can dynamically change the
 * bound controller in order to forward messages to different parts of the
 * program.
 */

public class ListeningPost extends Thread {
	private static ListeningPost post;
	private Restaurant restaurant;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private BufferedReader readBuffer;
	private BufferedWriter writeBuffer;

	/**
	 * ListeningPost default constructor (private in order to respect Singleton
	 * pattern).
	 */
	private ListeningPost() {
		boolean isFailed = false;
		try {
			serverSocket = new ServerSocket(4999);
			clientSocket = serverSocket.accept();

			readBuffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			writeBuffer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

			System.out.println("Server running!\n");
			System.out.println(serverSocket.getLocalSocketAddress());
			System.out.println(serverSocket.getLocalPort());
		} catch (Exception e) {
			isFailed = true;
			System.err.println(e.getMessage());
		} finally {
			if (isFailed && clientSocket.isConnected()) {
				try {
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

	public Restaurant getRestaurant() {
		return restaurant;
	}

	/**
	 * This method can be invoked when needed to change the bound controllers, and,
	 * through these, strategies.
	 * 
	 * @param controller specifies the involved controller.
	 */
	public void bindController(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	/**
	 * This method allows an object to communicate a message composed by the name of
	 * the strategy, which can be interpreted as a service provided by the bound
	 * controller, and a vector of strings as arguments to be passed to said
	 * strategy, which will be executed by the bound controller.
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
	}

	/**
	 * The thread in this server is used to keep a constant, unhindered, eye on the
	 * input buffer, so as to immediately perform the required services when the
	 * message arrives, through invocation of the ListeningPost.
	 */
	@Override
	public void run() {
		try {
			while (clientSocket.isConnected()) {
				Pattern p = Pattern.compile("^([a-zA-Z0-9]+, )+[a-zA-Z0-9]+$");
				String[] unpackedMessage;
				String message;
				while ((message = readBuffer.readLine()) == null);
				if (p.matcher(message).matches()) {
					unpackedMessage = message.split(", ");
					notifyMainController(unpackedMessage[0],
							Arrays.copyOfRange(unpackedMessage, 1, unpackedMessage.length - 1));
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			try {clientSocket.close();}
			catch(IOException e) {e.printStackTrace();}
		}
	}

	/** Small method that writes a message to the socket. */
	public synchronized void sendMessage(String message) {
		try {
			if (clientSocket.isConnected())
				writeBuffer.write(message + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}