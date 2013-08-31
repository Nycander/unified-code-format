package name.nycander.unifiedcode;

import java.io.File;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.gson.Gson;

/**
 * @author martin.nycander
 */
public class SettingsExporterTest {
	private SettingsExporter exporter;

	private String testSettings = "";

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Before
	public void setup() {
		Map<String, String> map = new Gson().fromJson(testSettings, Map.class);
		exporter = new SettingsExporter(map);
	}

	@Test
	public void exportEclipse() throws Exception {
		Schema schema = new Schemas().loadEclipseSchema();
		SettingsTemplate template = new Templates().load("eclipse", schema);

		File file = temporaryFolder.newFile();

		exporter.export(template, file);

		// TODO: assert on expected behaviour. perhaps load as template and look at different values?
	}
}
