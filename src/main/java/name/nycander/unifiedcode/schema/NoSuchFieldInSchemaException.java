package name.nycander.unifiedcode.schema;

public class NoSuchFieldInSchemaException extends RuntimeException {
	public NoSuchFieldInSchemaException() {
		super();
	}

	public NoSuchFieldInSchemaException(String message) {
		super(message);
	}

	public NoSuchFieldInSchemaException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchFieldInSchemaException(Throwable cause) {
		super(cause);
	}
}
