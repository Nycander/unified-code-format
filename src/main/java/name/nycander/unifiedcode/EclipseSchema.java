package name.nycander.unifiedcode;

public class EclipseSchema implements SettingsSchema {
	@Override
	public String keyXPath() {
		return "profiles/profile/setting/@id";
	}

	@Override
	public String valueXPath() {
		return "profiles/profile/setting/@value";
	}

	@Override
	public String getIndentationSize() {
		return "org.eclipse.jdt.core.formatter.indentation.size";
	}

	@Override
	public String getTabulationSize() {
		return "org.eclipse.jdt.core.formatter.tabulation.size";
	}
}
