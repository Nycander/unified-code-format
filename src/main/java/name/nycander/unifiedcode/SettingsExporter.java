package name.nycander.unifiedcode;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettingsExporter {
	private final Map<String, String> settings;
	private final Logger logger = LoggerFactory.getLogger(SettingsExporter.class);

	public SettingsExporter(Map<String, String> settings) {
		this.settings = settings;
	}

	public void export(SettingsTemplate template, File file) {
		Map<String, String> settingsToModify = template.getNativeSettings();

		Schema schema = template.getSchema();

		for (Entry<String, String> e : schema.getSchema()) {
			String field = e.getKey();
			String nativeField = e.getValue();

			String value = settings.get(field);
			if (value != null) {
				settingsToModify.put(nativeField, value);
			}
		}

		template.save(file);
	}
}
