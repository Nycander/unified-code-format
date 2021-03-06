package name.nycander.unifiedcode;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.xml.sax.SAXException;

import name.nycander.unifiedcode.schema.Schema;
import name.nycander.unifiedcode.schema.Schemas;

public class SettingsTemplateTest {
	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private File outFile;

	@Before
	public void setup() throws IOException {
		outFile = temporaryFolder.newFile();
	}

	@Test
	public void testEclipse() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, URISyntaxException, TransformerException {
		File file = getFile("eclipse.template.xml");
		Schema schema = new Schemas().load("eclipse");

		deserializeModifyDeserialize(file, schema);
	}

	@Test
	public void testIntelliJ() throws Exception {
		File file = getFile("intellij.template.xml");
		Schema schema = new Schemas().load("intellij");

		deserializeModifyDeserialize(file, schema);
	}

	@Test
	public void testNetBeans() throws Exception {
		Schema schema = new Schemas().load("netbeans");
		File file = getFile("netbeans.template.xml");

		deserializeModifyDeserialize(file, schema);
	}

	private void deserializeModifyDeserialize(File file,
			Schema schema) throws SAXException, ParserConfigurationException, XPathExpressionException, IOException, TransformerException {
		String testValue = "test";
		modifyAndSave(file, schema, outFile, testValue);
		SettingsTemplate settingsFile = new SettingsTemplate(outFile, schema.xpathKeys(), schema
				.xpathValues());
		verifyValues(testValue, settingsFile);
	}

	private void verifyValues(String testValue, SettingsTemplate settingsFile) {
		Assert.assertTrue(!settingsFile.getNativeSettings().isEmpty());
		for (Entry<String, String> e : settingsFile.getNativeSettings().entrySet()) {
			Assert.assertEquals(e.getKey() + " should have the value of '" + testValue + "'.",
					testValue, e.getValue());
		}
	}

	private void modifyAndSave(File file,
			Schema schema,
			File outFile,
			String testValue) throws SAXException, ParserConfigurationException, XPathExpressionException, IOException, TransformerException {
		SettingsTemplate settingsFile = new SettingsTemplate(file, schema.xpathKeys(), schema
				.xpathValues());

		for (String key : settingsFile.getNativeSettings().keySet()) {
			settingsFile.getNativeSettings().put(key, testValue);
		}

		settingsFile.save(outFile);
	}

	private File getFile(String fileName) throws URISyntaxException {
		return new File(getClass().getClassLoader().getResource(fileName).toURI());
	}
}
