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
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("WaitersControlPanel.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("iry.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
