package chefsProgram.view;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class ChefsOrderUpdateFrame extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
	        Parent root = FXMLLoader.load(getClass().getResource("/chefsProgram/view/ChefsOrderUpdateFrame.fxml"));
			Scene scene = new Scene(root,500,200);
			scene.getStylesheets().add(getClass().getResource("chefsOrderUpdateFrame.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}