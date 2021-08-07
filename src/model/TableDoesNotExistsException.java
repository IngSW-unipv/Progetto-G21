package model;

public class TableDoesNotExistsException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message = "The specified table doesn't exist!";

	@Override
	public String getMessage() {
		return message;
	}
}
