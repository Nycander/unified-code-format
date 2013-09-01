package name.nycander.unifiedcode;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import name.nycander.unifiedcode.schema.Schema;
import name.nycander.unifiedcode.schema.Schemas;

public class SettingsExporterTest {
	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private SettingsExporter exporter;

	@Before
	public void setup() {
		exporter = new SettingsExporter();
	}

	@Test
	public void exportEclipse() throws Exception {
		Schema schema = new Schemas().load("eclipse");
		SettingsTemplate template = new Templates().load("eclipse", schema);

		Map<String, String> configuredSettings = new HashMap<>();
		configuredSettings.put("indentation.size", "1337");

		File file = temporaryFolder.newFile();
		exporter.export(configuredSettings, template, file);

		// Load generated file as template and inspect the defaults
		SettingsTemplate generatedSettings = new SettingsTemplate(file, schema);
		assertEquals("1337", generatedSettings.getNativeSettings()
				.get(schema.getNativeField("indentation.size")));
	}
}
