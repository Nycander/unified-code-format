package name.nycander.unifiedcode;

public class BadSchemaException extends UnifyCodeException {
	public BadSchemaException() {
		super();
	}

	public BadSchemaException(String message) {
		super(message);
	}

	public BadSchemaException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadSchemaException(Throwable cause) {
		super(cause);
	}
}
