package controller;

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
	// private WaitersGuiController waiterController;
	// private ChefsGuiController chefsController;

	private ServerSocket serverSocket;
	private Socket chefsClientSocket;
	private BufferedReader bufferRead;
	private BufferedWriter bufferWrite;

	/**
	 * ListeningPost default constructor (private in order to respect Singleton
	 * pattern).
	 */
	private ListeningPost() {
		try {
			chefsClientSocket = serverSocket.accept();
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(chefsClientSocket.getInputStream()));
			BufferedWriter bufferWrite = new BufferedWriter(
					new OutputStreamWriter(chefsClientSocket.getOutputStream()));
			System.out.println("Server running!\n");
			System.out.println(serverSocket.getLocalSocketAddress());
			System.out.println(serverSocket.getLocalPort());
		} catch (Exception e) {
			e.printStackTrace();
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
		while (true) {
			Pattern p = Pattern.compile("^([a-zA-Z0-9]+, )+[a-zA-Z0-9]+$");
			String[] unpackedMessage;
			try {
				String message;
				while ((message = bufferRead.readLine()) == null)
					; // is ok????
				/*
				 * yes, the readline returns a null value if the file it's // empty, putting
				 * this construct here blocks the thread while the buffer is empty, as soon //
				 * as a new message comes through the input channel, the thread restarts.
				 * -Daniele
				 */
				if (p.matcher(message).matches()) {
					unpackedMessage = message.split(", ");
					notifyMainController(unpackedMessage[0],
							Arrays.copyOfRange(unpackedMessage, 1, unpackedMessage.length - 1));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/** Small method that writes a message to the socket. */
	public synchronized void sendMessage(String message) {
		try {
			bufferWrite.write(message + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}