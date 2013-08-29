package name.nycander.unifiedcode;

public class IntelliJSchema implements SettingsSchema {
	@Override
	public String keyXPath() {
		return "code_scheme/codeStyleSettings[@language=\"JAVA\"]//option/@name";
	}

	@Override
	public String valueXPath() {
		return "code_scheme/codeStyleSettings[@language=\"JAVA\"]//option/@value";
	}

	@Override
	public String getIndentationSize() {
		return "INDENT_SIZE";
	}

	@Override
	public String getTabulationSize() {
		return "TAB_SIZE";
	}
}
