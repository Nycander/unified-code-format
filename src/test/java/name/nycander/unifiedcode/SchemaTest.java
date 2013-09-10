package name.nycander.unifiedcode;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

import name.nycander.unifiedcode.schema.NoSuchFieldInSchemaException;
import name.nycander.unifiedcode.schema.Schema;

import com.google.common.collect.ImmutableMap;

/**
 * @author martin.nycander
 */
public class SchemaTest {
	private Schema schema;
	private Map<String, Object> map;

	@Before
	public void setup() {
		map = new TreeMap<>();
		schema = new Schema(map);
	}

	@Test
	public void keysXPathQuery() throws Exception {
		map.put("_xpath.keys", "test/test");
		assertEquals("test/test", schema.xpathKeys());
	}

	@Test
	public void valuesXPathQuery() throws Exception {
		map.put("_xpath.values", "test/test");
		assertEquals("test/test", schema.xpathValues());
	}

	@Test
	public void getNativeField() throws Exception {
		map.put("indentation.size", "test");
		assertEquals(Arrays.asList("test"), schema.getNativeFields("indentation.size"));
	}

	@Test
	public void getNativeFiedWhenComplex() throws Exception {
		HashMap<String, String> useTabMap = new HashMap<>();
		useTabMap.put("field", "USE_TAB");
		map.put("use.tab", useTabMap);

		assertEquals(Arrays.asList("USE_TAB"), schema.getNativeFields("use.tab"));
	}

	@Test
	public void getNativeFieldsWhenList() throws Exception {
		map.put("use.tab", Arrays.asList("use.tab", "tab.use"));

		assertEquals(Arrays.asList("use.tab", "tab.use"),
				schema.getNativeFields("use.tab"));
	}

	@Test
	public void getNativeFieldsWhenSingleValue() throws Exception {
		map.put("use.tab", "true");

		assertEquals(Arrays.asList("true"), schema.getNativeFields("use.tab"));
	}

	@Test
	public void getNativeFieldsWhenComplexValue() throws Exception {
		map.put("use.tab", Arrays.asList(ImmutableMap.of("field", "USE_TAB"),
				ImmutableMap.of("field", "TAB_USE")));
		assertEquals(Arrays.asList("USE_TAB", "TAB_USE"),
				schema.getNativeFields("use.tab"));
	}

	@Test(expected = NoSuchFieldInSchemaException.class)
	public void getNativeFieldValidatesExistance() throws Exception {
		schema.getNativeFields("ashfjhsafhsaf");
	}
}
