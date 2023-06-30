package exception;

import constant.ExceptionMessage;

public class MethodNotFoundException extends RuntimeException {

	public MethodNotFoundException() {
		super(ExceptionMessage.ERR_MSG_METHOD_NOT_FOUND.getMessage());
	}

	public MethodNotFoundException(String message) {
		super(message);
	}
}
