package menu;

public class EntryDoesNotExistException extends Exception {
	
	/**
	 * for beauty :)
	 */
	private static final long serialVersionUID = 4917384675103693899L;

	public EntryDoesNotExistException(String msg) {
		super(msg);
	}

}
