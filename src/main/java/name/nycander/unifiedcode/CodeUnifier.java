package name.nycander.unifiedcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Map;

import name.nycander.unifiedcode.schema.Schema;
import name.nycander.unifiedcode.schema.Schemas;

import com.google.gson.Gson;

public class CodeUnifier {
	public static void main(String... args) throws FileNotFoundException {
		SettingsExporter exporter = new SettingsExporter();

		Map<String, String> settings = new Gson().fromJson(new FileReader(args[0]), Map.class);

		for (String ide : Arrays.asList("eclipse", "netbeans", "intellij")) {
			Schema outputSchema = new Schemas().load(ide);
			SettingsTemplate outputTemplate = new Templates().load(ide, outputSchema);
			File outputFile = new File(ide + ".output.xml");

			exporter.export(settings, outputSchema, outputTemplate, outputFile);
		}
	}
}
