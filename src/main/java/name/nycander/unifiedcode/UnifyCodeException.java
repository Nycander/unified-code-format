package name.nycander.unifiedcode;

public class UnifyCodeException extends RuntimeException {
	public UnifyCodeException() {
		super();
	}

	public UnifyCodeException(String message) {
		super(message);
	}

	public UnifyCodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnifyCodeException(Throwable cause) {
		super(cause);
	}
}
