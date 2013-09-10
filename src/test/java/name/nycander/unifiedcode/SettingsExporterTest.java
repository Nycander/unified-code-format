package name.nycander.unifiedcode;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import name.nycander.unifiedcode.schema.Schema;
import name.nycander.unifiedcode.schema.Schemas;

public class SettingsExporterTest {
	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Test
	public void exportEclipse() throws Exception {
		Schema outputSchema = new Schemas().load("eclipse");
		SettingsExporter exporter = new SettingsExporter();

		File file = temporaryFolder.newFile();

		SettingsTemplate outputTemplate = new Templates().load("eclipse", outputSchema);

		String indentationSize = "indentation.size";
		String spacesAroundAssignments = "space.around_assignment";

		Map<String, String> configuredSettings = new HashMap<>();
		configuredSettings.put(indentationSize, "1337");
		configuredSettings.put(spacesAroundAssignments, "true");

		exporter.export(configuredSettings, outputSchema, outputTemplate, file);

		// Load generated file as template and inspect the defaults
		SettingsTemplate generatedSettings = new SettingsTemplate(file,
				outputSchema.xpathKeys(), outputSchema
				.xpathValues());

		assertEquals(Arrays.asList("1337"), generatedSettings.getNativeSettings(
				(outputSchema.getNativeFields(indentationSize))));
	}
}
