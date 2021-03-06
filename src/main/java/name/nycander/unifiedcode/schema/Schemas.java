package name.nycander.unifiedcode.schema;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import com.google.gson.Gson;

public class Schemas {
	private final Gson gson = new Gson();
	private String prefix, postfix;

	public Schemas() {
		this("", ".schema.json");
	}

	public Schemas(String prefix, String postfix) {
		this.prefix = prefix;
		this.postfix = postfix;
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

	@SuppressWarnings("unchecked")
	public Schema load(String name) {
		try {
			return new Schema(gson.fromJson(new FileReader(getFile(getClass().getClassLoader(),
					prefix + name + postfix)), Map.class));
		} catch (FileNotFoundException e) {
			throw new SchemaNotFoundException(e);
		}
	}
}
