package chefsProgram.controller;

public interface ClientListeningPostInterface {

	abstract void notifyMainController(String strategyName, String[] args);
	abstract void sendMessage(String msg);
	abstract void connect();
	abstract String getServerName();
	abstract void setServerName(String serverName);
	abstract void bind(ControllerInterface c);
}
