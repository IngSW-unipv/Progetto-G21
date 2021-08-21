package chefsProgram.controller;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.regex.Pattern;

public class ClientListeningPost 
				extends Thread 
				implements ClientListeningPostInterface {
	private ControllerInterface chefController;
	private Socket serverSocket = null; // = new Socket("localhost", 4999);
	private BufferedReader readBuffer = null;
	private BufferedWriter writeBuffer = null;
	private String serverName = "localhost";
	private static ClientListeningPost listeningPost;
	
	public static ClientListeningPost getInstance(){
		if(listeningPost==null)
			listeningPost=new ClientListeningPost();
		return listeningPost;
	}
	
	private ClientListeningPost() {
		this.run();
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
	
	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
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
	
	public void bind(ControllerInterface c) {
		this.chefController=c;
	}
	
	public void notifyMainController(String strategyName, String[] args) {
		if (chefController==null)
			System.out.println("Controller unbound. Please bind the controller before notifying a strategy.");
		else
			chefController.executeStrategy(strategyName, args);
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
					notifyMainController(unpackedMessage[0], Arrays.copyOfRange(unpackedMessage, 1, unpackedMessage.length-1));
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
