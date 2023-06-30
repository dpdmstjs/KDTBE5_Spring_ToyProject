package exception;

import constant.ExceptionMessage;

public class ElementNotFoundException extends RuntimeException {
	public ElementNotFoundException(String message) {
		super(message);
	}

	public String getMessage(ExceptionMessage exceptionMessage) {
		return exceptionMessage.getMessage();
	}
}
