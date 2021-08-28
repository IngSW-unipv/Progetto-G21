package chefsProgram.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Event class triggered by pressing the change order's status button. The
 * specified button is located in each row of the ordersTableView, inside the
 * actionTableColumn. The class contains an Order instance, necessary to provide
 * informations to ChefsOrderUpdateFrame GUI.
 */
public class ButtonClickEventHandler implements EventHandler<ActionEvent> {
	private ChefsControlPanelController mainController;

	/**
	 * Class constructor method.
	 * 
	 * @param mainController.
	 */
	public ButtonClickEventHandler(ChefsControlPanelController mainController) {
		super();
		this.mainController = mainController;
	}

	/**
	 * Method that contains the instructions to be performed after pressing the
	 * button. It launches ChefsOrderUpdateFrame GUI in order to change the order's
	 * status.
	 */
	@Override
	public void handle(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WaitersOrderUpdateFrame.fxml"));
			Parent root = fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("Update order status!");
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("waitersOrderUpdateFrame.css").toExternalForm());
			stage.setScene(scene);
			ChefsOrderUpdateFrameController updateFrameController = fxmlLoader.getController();
			updateFrameController.setMainController(mainController);
			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
