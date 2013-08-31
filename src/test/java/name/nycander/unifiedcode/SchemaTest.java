package name.nycander.unifiedcode;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

/**
 * @author martin.nycander
 */
public class SchemaTest {
	private Schema schema;
	private Map<String, String> map;

	@Before
	public void setup() {
		map = new TreeMap<String, String>();
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
	public void indentationSize() throws Exception {
		map.put("indentation.size", "test");
		assertEquals("test", schema.getIndentationSizeField());
	}

	@Test
	public void tabulationSize() throws Exception {
		map.put("tabulation.size", "test");
		assertEquals("test", schema.getTabulationSizeField());
	}
}
