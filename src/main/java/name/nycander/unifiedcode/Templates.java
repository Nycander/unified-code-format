package name.nycander.unifiedcode;

import java.io.File;
import java.net.URISyntaxException;

public class Templates {
	public SettingsTemplate load(String ide, Schema schema) {
		try {
			return new SettingsTemplate(getFile(ide + ".template.xml"), schema);
		} catch (Exception e) {
			throw new InvalidTemplateException(e);
		}
	}

	private File getFile(String fileName) throws URISyntaxException {
		return new File(getClass().getClassLoader().getResource(fileName).toURI());
	}

	public static class InvalidTemplateException extends RuntimeException {
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
}
