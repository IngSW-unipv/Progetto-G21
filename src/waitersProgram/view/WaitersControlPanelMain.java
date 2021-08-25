package waitersProgram.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WaitersControlPanelMain extends Application {

	// private WaitersControlPanelController wcpController;
	// private WaitersOrderUpdateFrameController woufController;

	@Override
	public void start(Stage stage) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("WaitersControlPanel.fxml"));
			stage.setTitle("Waiters control panel");
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("waitersControlPanel.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
