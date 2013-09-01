package name.nycander.unifiedcode;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

import name.nycander.unifiedcode.schema.NoSuchFieldInSchemaException;
import name.nycander.unifiedcode.schema.Schema;

/**
 * @author martin.nycander
 */
public class SchemaTest {
	private Schema schema;
	private Map<String, Object> map;

	@Before
	public void setup() {
		map = new TreeMap<String, Object>();
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
		assertEquals("test", schema.getNativeField("indentation.size"));
	}

	@Test
	public void getNativeFiledWhenComplex() throws Exception {
		HashMap<String, String> useTabMap = new HashMap<>();
		useTabMap.put("field", "USE_TAB");
		map.put("use.tab", useTabMap);

		assertEquals("USE_TAB", schema.getNativeField("use.tab"));
	}

	@Test(expected = NoSuchFieldInSchemaException.class)
	public void getNativeFieldValidatesExistance() throws Exception {
		schema.getNativeField("ashfjhsafhsaf");
	}
}
