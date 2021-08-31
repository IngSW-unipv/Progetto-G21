package waitersProgram.model;

public class WrongMenuEntryFormatException extends Exception {

	private static final long serialVersionUID = 1L;

	/** Exception message. */
	private String message = "The entry String has to follow dishName, dishPrice format!";

	/** @return WrongMenuEntryFormatException message. */
	@Override
	public String getMessage() {
		return message;
	}

}
