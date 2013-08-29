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
}
