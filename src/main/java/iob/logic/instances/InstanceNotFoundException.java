package iob.logic.instances;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InstanceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6044043795432461317L;

	public InstanceNotFoundException() {
		super();
	}

	public InstanceNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InstanceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public InstanceNotFoundException(String message) {
		super(message);
	}

	public InstanceNotFoundException(Throwable cause) {
		super(cause);
	}

}
