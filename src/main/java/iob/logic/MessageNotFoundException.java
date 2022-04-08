package iob.logic;

public class MessageNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 4135341446057363926L;
	
	public MessageNotFoundException() {

	}

	public MessageNotFoundException(String message) {
		super(message);
	}

	public MessageNotFoundException(Throwable cause) {
		super(cause);
	}

	public MessageNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
