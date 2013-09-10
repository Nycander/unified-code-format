package name.nycander.unifiedcode.schema;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.sun.istack.internal.Nullable;


@SuppressWarnings("unchecked")
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

	public List<String> getNativeFields(String field) {
		if (!map.containsKey(field)) {
			throw new NoSuchFieldInSchemaException(
					"Field '" + field + "' does not exist in Schema.");
		}
		return getList(field);
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
		return getField(value);
	}

	private List<String> getList(final String key) {
		Object value = map.get(key);
		if (value instanceof String || value instanceof Map) {
			return Arrays.asList(getField(value));
		} else if (value instanceof List) {
			return Lists.transform((List<Object>) value, new Function<Object, String>() {
				@Override
				public String apply(@Nullable Object input) {
					try {
						return Schema.this.getField(input);
					} catch (BadSchemaException e) {
						throw new BadSchemaException(e.getMessage() + " in field '" +
								key +
								"'.");
					}
				}
			});
		} else {
			throw new BadSchemaException("Unrecognized type '" +
					value.getClass() +
					"' in field '" +
					key +
					"'.");
		}
	}

	private String getField(Object value) {
		if (value instanceof String) {
			return (String) value;
		} else if (value instanceof Map) {
			return ((Map<String, String>) value).get("field");
		} else {
			throw new BadSchemaException("Unrecognized type '" +
					value.getClass() +
					"'");
		}
	}
}
