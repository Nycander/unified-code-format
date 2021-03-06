package name.nycander.unifiedcode;

import java.io.File;
import java.util.List;
import java.util.Map;

import name.nycander.unifiedcode.schema.Schema;

public class SettingsExporter {
	public void export(Map<String, String> settings,
			Schema outputSchema,
			SettingsTemplate outputTemplate,
			File outputFile) {
		Map<String, String> settingsToModify = outputTemplate.getNativeSettings();

		for (String field : outputSchema.getFields()) {
			List<String> nativeFields = outputSchema.getNativeFields(field);
			for (String nativeField : nativeFields) {
				String value = settings.get(field);
				if (value != null) {
					settingsToModify.put(nativeField,
							outputSchema.transformValue(field, value));
				}
			}
		}

		outputTemplate.save(outputFile);
	}
}
