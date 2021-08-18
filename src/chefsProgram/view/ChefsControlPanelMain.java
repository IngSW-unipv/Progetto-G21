package chefsProgram.view;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class ChefsControlPanelMain extends Application {
	@Override
	public void start(Stage primaryStageChefsControlPanel) {
		try {
	        Parent rootChefsControlPanel = FXMLLoader.load(getClass().getResource("/chefsProgram/view/ChefsControlPanel.fxml"));
			Scene sceneChefsControlPanel = new Scene(rootChefsControlPanel,800,600);
			sceneChefsControlPanel.getStylesheets().add(getClass().getResource("chefsControlPanel.css").toExternalForm());
			primaryStageChefsControlPanel.setScene(sceneChefsControlPanel);
			primaryStageChefsControlPanel.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}