package waitersProgram.model;

public class WrongMenuEntryFormatException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message = "The entry String has to follow dishName, dishPrice format!";

	@Override
	public String getMessage() {
		return message;
	}

}
