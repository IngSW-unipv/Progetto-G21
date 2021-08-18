package chefsProgram.view;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class ChefsOrderUpdateFrame extends Application {
	@Override
	public void start(Stage primaryStageChefsOrderUpdateFrame) {
		try {
	        Parent rootChefsOrderUpdateFrame = FXMLLoader.load(getClass().getResource("/chefsProgram/view/ChefsOrderUpdateFrame.fxml"));
			Scene sceneChefsOrderUpdateFrame = new Scene(rootChefsOrderUpdateFrame,500,200);
			sceneChefsOrderUpdateFrame.getStylesheets().add(getClass().getResource("chefsOrderUpdateFrame.css").toExternalForm());
			primaryStageChefsOrderUpdateFrame.setScene(sceneChefsOrderUpdateFrame);
			primaryStageChefsOrderUpdateFrame.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}