package chefsProgram.view;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 * The CloseButtonEventHandler Class. It's goal is to handle window events
 * launched by ChefsControlPanel (it ends the program when close button is
 * pressed).
 */
public class CloseButtonEventHandler implements EventHandler<WindowEvent> {

	/** Method that contains the code to execute in order to handle the event. */
	@Override
	public void handle(WindowEvent event) {
		Platform.exit();
		System.exit(0);
	}
}
