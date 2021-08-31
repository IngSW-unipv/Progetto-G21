package waitersProgram.model;

public class TableDoesNotExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	/** Exception message. */
	private String message = "The specified table doesn't exist!";

	/** @return TableDoesNotExistsException message. */
	@Override
	public String getMessage() {
		return message;
	}
}
