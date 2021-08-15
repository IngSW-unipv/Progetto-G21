package waitersProgram.model;

public class WrongTableEntryFormatException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message = "This line of tablesFile has wrong format!";

	@Override
	public String getMessage() {
		return message;
	}
}
