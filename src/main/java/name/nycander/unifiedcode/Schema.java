package name.nycander.unifiedcode;

import java.util.Map;
import java.util.Map.Entry;


public class Schema {
	private final Map<String, String> map;

	public Schema(Map<String, String> map) {
		this.map = map;
	}

	public String xpathKeys() {
		return map.get("_xpath.keys");
	}

	public String xpathValues() {
		return map.get("_xpath.values");
	}

	public String getNativeField(String field) {
		if (!map.containsKey(field)) {
			throw new NoSuchFieldInSchemaException("Field '" + field + "' does not exist in Schema.");
		}
		return map.get(field);
	}

	public Iterable<? extends Entry<String, String>> getSchema() {
		return map.entrySet();
	}
}
