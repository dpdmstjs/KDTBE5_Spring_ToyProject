package exception;

import constant.ExceptionMessage;

public class ArgumentMismatchException extends RuntimeException {
	public ArgumentMismatchException() {
		super(ExceptionMessage.ERR_MSG_INPUT.getMessage());
	}

	public ArgumentMismatchException(String message) {
		super(message);
	}
}
