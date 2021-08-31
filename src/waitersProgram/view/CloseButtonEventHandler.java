package waitersProgram.view;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class CloseButtonEventHandler implements EventHandler<WindowEvent> {

	@Override
	public void handle(WindowEvent event) {
		Platform.exit();
		System.exit(0);
	}
}
