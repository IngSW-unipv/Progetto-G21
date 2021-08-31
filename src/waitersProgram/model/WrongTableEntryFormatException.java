package waitersProgram.model;

public class WrongTableEntryFormatException extends Exception {

	private static final long serialVersionUID = 1L;

	/** Exception message. */
	private String message = "This line of tablesFile has wrong format!";

	/**
	 * @return WrongTableEntryFormatException message.
	 */
	@Override
	public String getMessage() {
		return message;
	}
}
