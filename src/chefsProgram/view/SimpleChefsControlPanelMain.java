package chefsProgram.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SimpleChefsControlPanelMain extends Application {

	public static void main(String[] args) {
		launch(args);
	}

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