package controller;
import java.net.*;

public class ListeningPost{

	/**ListeningPost class, once instantiated, it can dynamically change the bound controller in order to
	 * forward messages to different parts of the program.*/
	private static ListeningPost l;
	private Controller c;
	private ListeningPost() {
		ThreadedServerSocket.callServer();
	}
	
	public static ListeningPost invokeListeningPost() {
		/**This method creates, through the singleton pattern, the listening post, and 
		 * allows it to be invoked by different parts of the program*/
		if (l==null){
			l=new ListeningPost();
			return l;
		}
		else
			return l;
		
	}
	
	public void bindController(Controller c) {
		/** this method can be invoked when needed to change the bound controllers, and though it the strategies*/
		
		this.c=c;
	}
	public synchronized void notifyListeningPost(String strategyRequired, String[] args) {
		/**This method allows an object to communicate a message composed by the name of the strategy,
		 * which can be interpreted as a service provided by the bound controller, and a vector of strings as arguments
		 * to be passed to said strategy, which will be executed by the bound controller*/
		if (c== null) {
			System.out.println("Controller not bound. Bind controller to run this method.");
		}
		else
		{
			c.strategyCall(strategyRequired, args);
		}
	}
	
	public void notifyServer(String msg) {
		/** Simple method to notify the server of an outbound message*/
		ThreadedServerSocket.callServer().sendMessage(msg);
	}
}