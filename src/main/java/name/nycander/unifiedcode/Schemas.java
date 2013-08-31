package name.nycander.unifiedcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import com.google.gson.Gson;

/**
 * TODO: Remove hardcoded schemas, make loading dynamic.
 */
public class Schemas {
	final String defaultNetbeansFilePath = "netbeans.schema.json";
	final String defaultEclipseFilePath = "eclipse.schema.json";
	final String defaultIntelliJFilePath = "intellij.schema.json";

	private final Gson gson = new Gson();
	private final File netbeans;
	private final File eclipse;
	private final File intellij;

	public Schemas() {
		ClassLoader loader = getClass().getClassLoader();
		this.netbeans = getFile(loader, defaultNetbeansFilePath);
		this.eclipse = getFile(loader, defaultEclipseFilePath);
		this.intellij = getFile(loader, defaultIntelliJFilePath);
	}

	public Schemas(File netbeans, File eclipse, File intellij) {
		this.netbeans = netbeans;
		this.eclipse = eclipse;
		this.intellij = intellij;
	}

	private File getFile(ClassLoader loader, String filepath) {
		URL netbeansResource = loader.getResource(filepath);
		if (netbeansResource == null) {
			throw new SchemaNotFoundException("Schema file '" + filepath + "' was not found.");
		}
		try {
			return new File(netbeansResource.toURI());
		} catch (URISyntaxException e) {
			throw new SchemaNotFoundException(e);
		}
	}

	public Schema loadNetbeansSchema() {
		try {
			return new Schema(gson.fromJson(new FileReader(netbeans), Map.class));
		} catch (FileNotFoundException e) {
			throw new SchemaNotFoundException(e);
		}
	}

	public Schema loadEclipseSchema() {
		try {
			return new Schema(gson.fromJson(new FileReader(eclipse), Map.class));
		} catch (FileNotFoundException e) {
			throw new SchemaNotFoundException(e);
		}
	}

	public Schema loadIntelliJSchema() {
		try {
			return new Schema(gson.fromJson(new FileReader(intellij), Map.class));
		} catch (FileNotFoundException e) {
			throw new SchemaNotFoundException(e);
		}
	}

	public static class SchemaNotFoundException extends RuntimeException {
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
}
