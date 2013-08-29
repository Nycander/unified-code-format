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

public class XmlImporterTest {
	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private File outFile;

	@Before
	public void setup() throws IOException {
		outFile = temporaryFolder.newFile();
	}

	@Test
	public void testEclipse() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, URISyntaxException, TransformerException {
		File file = getFile("eclipse.xml");
		EclipseSchema schema = new EclipseSchema();

		deserializeModifyDeserialize(file, schema);
	}

	@Test
	public void testIntelliJ() throws Exception {
		File file = getFile("intellij.xml");
		SettingsSchema schema = new IntelliJSchema();

		deserializeModifyDeserialize(file, schema);
	}

	@Test
	public void testNetBeans() throws Exception {
		File file = getFile("netbeans.xml");
		SettingsSchema schema = new NetbeansSchema();

		deserializeModifyDeserialize(file, schema);
	}

	private void deserializeModifyDeserialize(File file,
			SettingsSchema schema) throws SAXException, ParserConfigurationException, XPathExpressionException, IOException, TransformerException {
		String testValue = "test";
		modifyAndSave(file, schema, outFile, testValue);
		XmlSettingsFile settingsFile = new XmlSettingsFile(outFile, schema);
		verifyValues(testValue, settingsFile);
	}

	private void verifyValues(String testValue, XmlSettingsFile settingsFile) {
		Assert.assertTrue(!settingsFile.getSettings().isEmpty());
		for (Entry<String, String> e : settingsFile.getSettings().entrySet()) {
			Assert.assertEquals(e.getKey() + " should have the value of '" + testValue + "'.",
					testValue, e.getValue());
		}
	}

	private void modifyAndSave(File file,
			SettingsSchema schema,
			File outFile,
			String testValue) throws SAXException, ParserConfigurationException, XPathExpressionException, IOException, TransformerException {
		XmlSettingsFile settingsFile = new XmlSettingsFile(file, schema);

		for (String key : settingsFile.getSettings().keySet()) {
			settingsFile.getSettings().put(key, testValue);
		}

		settingsFile.save(outFile);
	}

	private File getFile(String fileName) throws URISyntaxException {
		return new File(getClass().getClassLoader().getResource(fileName).toURI());
	}
}
