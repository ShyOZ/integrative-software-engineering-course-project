package iob.logic;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnauthorizedUserException extends RuntimeException {

	private static final long serialVersionUID = -4175511148518938575L;

	public UnauthorizedUserException() {
		super();
	}

	public UnauthorizedUserException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnauthorizedUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnauthorizedUserException(String message) {
		super(message);
	}

	public UnauthorizedUserException(Throwable cause) {
		super(cause);
	}

}
