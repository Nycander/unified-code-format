package name.nycander.unifiedcode.schema;

import java.util.Collection;
import java.util.Map;


public class Schema implements SchemaXPathProvider {
	private final Map<String, Object> map;

	public Schema(Map<String, Object> map) {
		this.map = map;
	}

	public Collection<String> getFields() {
		return map.keySet();
	}

	@Override
	public String xpathKeys() {
		return get("_xpath.keys");
	}

	@Override
	public String xpathValues() {
		return get("_xpath.values");
	}

	public String getNativeField(String field) {
		if (!map.containsKey(field)) {
			throw new NoSuchFieldInSchemaException("Field '" + field + "' does not exist in Schema.");
		}
		return get(field);
	}

	public String transformValue(String field, String rawValue) {
		Object value = map.get(field);
		if (value instanceof Map) {
			Map<String, String> fieldMap = (Map<String, String>) value;
			assert (field.equals(fieldMap.get("field")));
			assert (fieldMap.containsKey(rawValue));
			return fieldMap.get(rawValue);
		} else {
			return rawValue;
		}
	}

	private String get(String key) {
		Object value = map.get(key);
		if (value instanceof String) {
			return (String) value;
		} else if (value instanceof Map) {
			return ((Map<String, String>) value).get("field");
		} else {
			throw new BadSchemaException("Unrecognized type '" + value.getClass() + "' in field '" + key + "'.");
		}
	}
}
