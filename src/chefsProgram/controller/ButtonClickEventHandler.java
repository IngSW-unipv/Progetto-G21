package chefsProgram.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ButtonClickEventHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("ChefsOrderUpdateFrame.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Update order status!");
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("chefsOrderUpdateFrame.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
			((Node) (event.getSource())).getScene().getWindow().hide();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
