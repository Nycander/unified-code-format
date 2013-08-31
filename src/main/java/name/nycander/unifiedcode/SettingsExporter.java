package name.nycander.unifiedcode;

import java.io.File;
import java.util.Map;

public class SettingsExporter {
	private final Map<String, String> map;

	public SettingsExporter(Map<String, String> map) {
		this.map = map;
	}

	public void export(SettingsTemplate schema, File file) {
	}
}
