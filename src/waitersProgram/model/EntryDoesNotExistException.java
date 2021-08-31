package waitersProgram.model;

public class EntryDoesNotExistException extends Exception {

	private static final long serialVersionUID = 4917384675103693899L;

	/** Exception message. */
	private String message = "The specified entry doesn't exist!";

	/** @return EntryDoesNotExistException message. */
	@Override
	public String getMessage() {
		return message;
	}
}
