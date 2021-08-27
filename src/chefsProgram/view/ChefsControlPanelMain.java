package chefsProgram.view;

import java.io.IOException;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/** The ChefsControlPanelMain class. Used to start chef's application. */
public class ChefsControlPanelMain extends Application {
	@Override
	public void start(Stage splashStage) {
		Parent rootSplash;
		try {
			splashStage.setTitle("Welcome little chef!");
			rootSplash = FXMLLoader.load(getClass().getResource("Splash.fxml"));
			Scene sceneSplash = new Scene(rootSplash);
			splashStage.setScene(sceneSplash);
			splashStage.show();

			PauseTransition pause = new PauseTransition(Duration.seconds(3));
			pause.setOnFinished(event -> {
				splashStage.hide();
				Stage mainStage = new Stage();
				mainStage.setScene(createMainScene());
				mainStage.setTitle("Chefs control panel");
				mainStage.show();
			});
			pause.play();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public Scene createMainScene() {
		Parent rootProgram = null;
		try {
			rootProgram = FXMLLoader.load(getClass().getResource("ChefsControlPanel.fxml"));
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		Scene sceneProgram = new Scene(rootProgram);
		sceneProgram.getStylesheets().add(getClass().getResource("chefsControlPanel.css").toExternalForm());
		return sceneProgram;
	}
}