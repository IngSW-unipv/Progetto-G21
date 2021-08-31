package waitersProgram.view;

import java.io.IOException;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/** The WaitersControlPanelMain class. Used to start waiter's application. */
public class WaitersControlPanelMain extends Application {

	/** Method called by main method. (it launches the entire program). */
	@Override
	public void start(Stage splashStage) {
		Parent rootSplash;
		try {
			splashStage.setTitle("Welcome Linguini!");
			rootSplash = FXMLLoader.load(getClass().getResource("Splash.fxml"));
			Scene sceneSplash = new Scene(rootSplash);
			splashStage.setScene(sceneSplash);
			splashStage.show();

			PauseTransition pause = new PauseTransition(Duration.seconds(3));
			pause.setOnFinished(event -> {
				splashStage.hide();
				Stage mainStage = new Stage();
				mainStage.setScene(createMainScene());
				mainStage.setTitle("Waiter's control panel");
				mainStage.setOnCloseRequest(new CloseButtonEventHandler());
				mainStage.show();
			});
			pause.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Main method (waiter's control panel splash and main scene entry point). */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Method called by start in order to create main scene (waiter's control
	 * panel).
	 */
	public Scene createMainScene() {
		Parent rootProgram = null;
		try {
			rootProgram = FXMLLoader.load(getClass().getResource("WaitersControlPanel.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene sceneProgram = new Scene(rootProgram);
		sceneProgram.getStylesheets().add(getClass().getResource("waitersControlPanel.css").toExternalForm());
		return sceneProgram;
	}
}
