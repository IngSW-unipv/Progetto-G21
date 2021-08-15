package chefsProgram.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

/**
 * The ChefsGuiController class. It will be used to control the chef's graphical
 * interface and it will contain a ListeningPost instance in order to ensure the
 * communication with the SystemController class.
 * 
 * This class and its GUI will be running on a different device than the others
 * in order to obtain a perfect distributed system: that's the client,
 * ListeningPost contains the server.
 */

public class ChefsGuiController {

	@FXML
	AnchorPane ordersScrollPane;

	private static ChefsGuiController instance = null;

	private ChefsGuiController() {

	}

	public static ChefsGuiController getInstance() {
		if (instance == null) {
			instance = new ChefsGuiController();
		}
		return instance;
	}

	// private ListeningPost post;

	public void updateOrders() {

	}

	public void setOrderSeenToPreparable() {
		// post.notifyMainController(strategy, args)

	}

	public void setOrderSeenToNotPreparable() {

	}

	public void setOrderToPrepared() {

	}

}
