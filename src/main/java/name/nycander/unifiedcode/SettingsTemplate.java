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
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import name.nycander.unifiedcode.schema.BadSchemaException;

public class SettingsTemplate {
	private final File templateFile;
	private final Map<String, String> settingsMap;
	private final XPathExpression xpathKeysExpr, xpathValuesExpr;

	public SettingsTemplate(File templateFile,
			String xpathKeys, String xpathValues) {
		this.templateFile = templateFile;

		try {
			final XPath xPath = XPathFactory.newInstance().newXPath();
			xpathKeysExpr = xPath.compile(xpathKeys);
			xpathValuesExpr = xPath.compile(xpathValues);
			this.settingsMap = loadSettingsFromFile(templateFile, xpathKeysExpr,
					xpathValuesExpr);
		} catch (XPathExpressionException e) {
			throw new InvalidTemplateException("Invalid xpath in template", e);
		}
	}

	public Map<String, String> getNativeSettings() {
		return settingsMap;
	}

	public void save(File outFile) {
		try {
			Node modifiedMap = modifyXmlDocumentWithMap(settingsMap,
					xpathKeysExpr,
					xpathValuesExpr);
			saveXmlFile(outFile, modifiedMap);
		} catch (XPathExpressionException e) {
			throw new BadSchemaException("XPath expression in xPathProvider file could not be compiled.", e);
		}
	}

	private Map<String, String> loadSettingsFromFile(File file,
			XPathExpression keyXPath,
			XPathExpression valueXPath) throws XPathExpressionException {
		Document document = loadXmlDocument(file);

		NodeList idList = (NodeList) keyXPath.evaluate(document, XPathConstants.NODESET);
		NodeList valueList = (NodeList) valueXPath.evaluate(document, XPathConstants.NODESET);

		Map<String, String> settings = new ConcurrentSkipListMap<>();
		for (int i = 0; i < idList.getLength(); i++) {
			String id = idList.item(i).getNodeValue();
			String value = valueList.item(i).getNodeValue();
			settings.put(id, value);
		}
		return settings;
	}

	private static Document loadXmlDocument(File file) {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			return documentBuilder.parse(file);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new InvalidTemplateException("Failed to load template '" + file + "'.", e);
		}
	}

	private Node modifyXmlDocumentWithMap(Map<String, String> map,
			XPathExpression keyXPath,
			XPathExpression valueXPath) throws XPathExpressionException {
		Document document = loadXmlDocument(templateFile);

		NodeList idList = (NodeList) keyXPath.evaluate(document, XPathConstants.NODESET);
		NodeList valueList = (NodeList) valueXPath.evaluate(document, XPathConstants.NODESET);

		for (int i = 0; i < idList.getLength(); i++) {
			String id = idList.item(i).getNodeValue();
			valueList.item(i).setNodeValue(map.get(id));
		}

		return document;
	}

	private static void saveXmlFile(File outFile,
			Node modifiedMap) {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(modifiedMap), new StreamResult(writer));
			Path path = FileSystems.getDefault().getPath(outFile.getAbsolutePath());
			Files.write(path, writer.toString().getBytes());
		} catch (TransformerException | IOException e) {
			throw new UnifyCodeException("Could not save file '" + outFile + "'.", e);
		}
	}
}
