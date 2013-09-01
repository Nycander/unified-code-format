package name.nycander.unifiedcode;

public class InvalidTemplateException extends UnifyCodeException {
	public InvalidTemplateException() {
		super();
	}

	public InvalidTemplateException(String message) {
		super(message);
	}

	public InvalidTemplateException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidTemplateException(Throwable cause) {
		super(cause);
	}
}