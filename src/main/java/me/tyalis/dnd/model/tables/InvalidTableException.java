package me.tyalis.dnd.model.tables;

/**
 *
 * @author Tyalis
 */
public class InvalidTableException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	
	public InvalidTableException() {
		super();
	}
	
	public InvalidTableException(String message) {
		super(message);
	}
	
	public InvalidTableException(Throwable cause) {
		super(cause);
	}
	
	public InvalidTableException(String message, Throwable cause) {
		super(message, cause);
	}
}
