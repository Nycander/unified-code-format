package name.nycander.unifiedcode;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import name.nycander.unifiedcode.schema.Schema;

public class SettingsExporter {
	public void export(Map<String, String> settings,
			SettingsTemplate template,
			File outputFile) {
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

		template.save(outputFile);
	}
}
