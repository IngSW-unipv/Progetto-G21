package waitersProgram.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** Class that launches the waiter's control panel without splash screen. */
public class SimpleWaitersControlPanelMain extends Application {

	/** Main method (waiter's control panel main scene entry point). */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Method called by main method. (it launches the waiter's control panel without
	 * splash screen).
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WaitersControlPanel.fxml"));
		Parent root = fxmlLoader.load();
		primaryStage.setTitle("Waiter's control panel");
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("waitersControlPanel.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
