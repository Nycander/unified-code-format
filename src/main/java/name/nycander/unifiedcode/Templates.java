package name.nycander.unifiedcode;

import java.io.File;
import java.net.URISyntaxException;

import name.nycander.unifiedcode.schema.Schema;

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
}
