package name.nycander.unifiedcode;

public class NetbeansSchema implements SettingsSchema {
	@Override
	public String keyXPath() {
		return "editor-preferences/entry/@name";
	}

	@Override
	public String valueXPath() {
		return "editor-preferences/entry/value/text()";
	}

	@Override
	public String getIndentationSize() {
		return "tab-size";
	}

	@Override
	public String getTabulationSize() {
		return "spaces-per-tab";
	}
}
