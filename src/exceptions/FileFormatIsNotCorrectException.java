package exceptions;

public class FileFormatIsNotCorrectException extends Exception {
	
	private static final long serialVersionUID = 4844031534601120182L;

	public FileFormatIsNotCorrectException(String msg) {
		super(msg);
	}

}
