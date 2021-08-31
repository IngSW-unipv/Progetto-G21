package chefsProgram.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** Class that launches the chef's control panel without splash screen. */
public class SimpleChefsControlPanelMain extends Application {

	/** Main method (chef's control panel main scene entry point). */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Method called by main method. (it launches the chef's control panel without
	 * splash screen).
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChefsControlPanel.fxml"));
		Parent root = fxmlLoader.load();
		primaryStage.setTitle("Chef's control panel");
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("chefsControlPanel.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
