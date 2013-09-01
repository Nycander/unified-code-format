package name.nycander.unifiedcode;

public class SchemaNotFoundException extends UnifyCodeException {
	public SchemaNotFoundException() {
		super();
	}

	public SchemaNotFoundException(String message) {
		super(message);
	}

	public SchemaNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public SchemaNotFoundException(Throwable cause) {
		super(cause);
	}
}