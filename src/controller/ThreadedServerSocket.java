package controller;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import java.util.Arrays;
public class ThreadedServerSocket extends Thread {
/**Basic server that manages communication with the chef client. It allows for buffered and automated
 * strategy invocation through tcp messages on format "String strategyName, String[] args"(said vector must be expressed
 * through a chain of comma-separated blocks in order for it to function), and outputs towards the client.
 * Singleton pattern based.
 * This server does not perform any encryption/decryption of the messages, nor it check the outbound messages for any specific pattern, users advised.*/
	private static ThreadedServerSocket socket;
	private ServerSocket server;
	private Socket kitchenClient;
	private BufferedReader bufferRead;
	private BufferedWriter bufferWrite;
	
	protected static ThreadedServerSocket callServer() {
		/**Due to the security dangers associated with gaining direct access to this object, the 
		 * static call method is protected, if you want to access it, you must be in the controller package,
		 * later on it will even be stricter, as soon as i create the subpackages*/
		if (socket != null) {
			return socket;
		}
		else
		{
			socket= new ThreadedServerSocket();
			return socket;
		}
	}
	
	private ThreadedServerSocket(){
		/**Constructor of the server, opens the connection and the read and write channels*/
		try{
			kitchenClient=server.accept();
			BufferedReader bufferRead= new BufferedReader(new InputStreamReader(kitchenClient.getInputStream()));
			BufferedWriter bufferWrite=new BufferedWriter(new OutputStreamWriter(kitchenClient.getOutputStream())); 
			System.out.println("Server running!\n");
			System.out.println(server.getLocalSocketAddress());
			System.out.println(server.getLocalPort());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		/** The thread in this server is used to keep a constant, unhindered, eye on the input buffer,
		 * so as to immediately perform the required services when the message arrives, through invocation of the ListeningPost*/
		while(true) {
			Pattern p=Pattern.compile("^([a-zA-Z0-9]+, )+[a-zA-Z0-9]+$");
			String[] unpackedMessage;
			try {
				String message;
				while((message=bufferRead.readLine())==null);
				if(p.matcher(message).matches()) {
					unpackedMessage=message.split(", ");
					ListeningPost.invokeListeningPost().notifyListeningPost(unpackedMessage[0], Arrays.copyOfRange(unpackedMessage, 1, unpackedMessage.length-1));
				}	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	protected synchronized void sendMessage(String msg) {
		/**Small method that writes a message to the socket.*/
		try {
			bufferWrite.write(msg+"\n");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
