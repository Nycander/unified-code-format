package name.nycander.unifiedcode;


import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SettingsTemplate {
	private final File templateFile;
	private final Schema schema;
	private final Map<String, String> settingsMap;

	public SettingsTemplate(File templateFile,
			Schema schema) throws SAXException, ParserConfigurationException, XPathExpressionException, IOException {
		this.templateFile = templateFile;
		this.schema = schema;
		this.settingsMap = loadSettingsFromFile(templateFile, schema.xpathKeys(), schema
				.xpathValues());
	}

	public Map<String, String> getSettings() {
		return settingsMap;
	}

	public void save(File outFile) throws SAXException, ParserConfigurationException, XPathExpressionException, IOException, TransformerException {
		Node modifiedMap = modifyXmlDocumentWithMap(settingsMap,
				schema.xpathKeys(),
				schema.xpathValues());
		saveXmlFile(outFile, modifiedMap);
	}

	private Map<String, String> loadSettingsFromFile(File file,
			String keyXPath,
			String valueXPath) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
		Document document = loadXmlDocument(file);
		XPath xPath = XPathFactory.newInstance().newXPath();

		NodeList idList = (NodeList) xPath.compile(keyXPath)
				.evaluate(document, XPathConstants.NODESET);
		NodeList valueList = (NodeList) xPath.compile(valueXPath)
				.evaluate(document, XPathConstants.NODESET);

		Map<String, String> settings = new ConcurrentSkipListMap<>();
		for (int i = 0; i < idList.getLength(); i++) {
			String id = idList.item(i).getNodeValue();
			String value = valueList.item(i).getNodeValue();
			settings.put(id, value);
		}
		return settings;
	}

	private static Document loadXmlDocument(File file) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		return documentBuilder.parse(file);
	}

	private Node modifyXmlDocumentWithMap(Map<String, String> map,
			String keyXPath,
			String valueXPath) throws XPathExpressionException, IOException, SAXException, ParserConfigurationException {
		Document document = loadXmlDocument(templateFile);
		XPath xPath = XPathFactory.newInstance().newXPath();

		NodeList idList = (NodeList) xPath.compile(keyXPath)
				.evaluate(document, XPathConstants.NODESET);
		NodeList valueList = (NodeList) xPath.compile(valueXPath)
				.evaluate(document, XPathConstants.NODESET);

		for (int i = 0; i < idList.getLength(); i++) {
			String id = idList.item(i).getNodeValue();
			valueList.item(i).setNodeValue(map.get(id));
		}

		return document;
	}

	private static void saveXmlFile(File outFile,
			Node modifiedMap) throws TransformerException, IOException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(modifiedMap), new StreamResult(writer));
		Path path = FileSystems.getDefault().getPath(outFile.getAbsolutePath());
		Files.write(path, writer.toString().getBytes());
	}
}
