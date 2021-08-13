package controller;

/**
 * The ListeningPost class. Once instantiated, it can dynamically change the
 * bound controller in order to forward messages to different parts of the
 * program.
 */

public class ListeningPost {
	private static ListeningPost post;
	private SystemController controller;

	/**
	 * ListeningPost default constructor (private in order to respect Singleton
	 * pattern).
	 */
	private ListeningPost() {
		ThreadedServerSocket.callServer();
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
		}
		return post;
	}

	/**
	 * This method can be invoked when needed to change the bound controllers, and,
	 * through these, strategies.
	 * 
	 * @param controller specifies the involved controller.
	 */
	public void bindController(SystemController controller) {
		this.controller = controller;
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
	public synchronized void notifyListeningPost(String strategyRequired, String[] args) {
		if (controller == null) {
			System.out.println("Controller not bound. Bind controller to run this method.");
		} else {
			controller.strategyCall(strategyRequired, args);
		}
	}

	/**
	 * Simple method to notify the server of an outbound message.
	 * 
	 * @param message specifies the message to send to the server.
	 */
	public void notifyServer(String message) {
		ThreadedServerSocket.callServer().sendMessage(message);
	}
}