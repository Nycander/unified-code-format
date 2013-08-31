package name.nycander.unifiedcode;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class Schema {
	private final Map<String, String> map;

	public Schema(Map<String, String> map) {
		this.map = map;
	}

	public String getIndentationSizeField() {
		return map.get("indentation.size");
	}

	public String getTabulationSizeField() {
		return map.get("tabulation.size");
	}

	public String xpathKeys() {
		return map.get("_xpath.keys");
	}

	public String xpathValues() {
		return map.get("_xpath.values");
	}

	public static final void main(String... args) {
		final List<String> accessedKeys = new ArrayList<>();
		Map<String, String> map = new TreeMap<String, String>() {
			@Override
			public String get(Object key) {
				accessedKeys.add(key.toString());
				return super.get(key);
			}
		};
		Schema schema = new Schema(map);
		for (Method m : Schema.class.getDeclaredMethods()) {
			if (Modifier.isStatic(m.getModifiers())) {
				continue;
			}
			try {
				m.invoke(schema);
			} catch (Exception e) {
				throw new RuntimeException(m.getName(), e);
			}
		}

		System.out.println("{");
		for (String key : accessedKeys) {
			System.out.print("\t\"");
			System.out.print(key);
			System.out.println("\": \"\",");
		}
		System.out.println("}");
	}
}
