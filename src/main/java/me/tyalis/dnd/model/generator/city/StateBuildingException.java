package me.tyalis.dnd.model.generator.city;

/**
 *
 * @author Tyalis
 */
public class StateBuildingException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	
	public StateBuildingException () {
		super();
	}
	
	public StateBuildingException(String message) {
		super(message);
	}
	
	public StateBuildingException(Throwable cause) {
		super(cause);
	}
	
	public StateBuildingException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
