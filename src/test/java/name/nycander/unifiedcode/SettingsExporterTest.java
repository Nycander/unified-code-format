package name.nycander.unifiedcode;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class SettingsExporterTest {
	private SettingsExporter exporter;

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private Map<String, String> configuredSettings;

	@Before
	public void setup() {
		configuredSettings = new HashMap<>();
		exporter = new SettingsExporter(configuredSettings);
	}

	@Test
	public void exportEclipse() throws Exception {
		Schema schema = new Schemas().load("eclipse");
		SettingsTemplate template = new Templates().load("eclipse", schema);

		configuredSettings.put("indentation.size", "1337");

		File file = temporaryFolder.newFile();
		exporter.export(template, file);

		// Load generated file as template and inspect the defaults
		SettingsTemplate generatedSettings = new SettingsTemplate(file, schema);
		assertEquals("1337", generatedSettings.getNativeSettings()
				.get(schema.getNativeField("indentation.size")));
	}
}
