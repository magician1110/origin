package com.ast;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class XMLReferenceFunction {
	
	
	//XSLT을 이용한 OUT파일 생성
	public static void transform(String sourcePath, String xsltPath, String resultDir, String param) {
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(new File(xsltPath)));
			transformer.setParameter("basePath", param);			
			transformer.setParameter("modifyPath", param);			
			transformer.setOutputProperty(OutputKeys.ENCODING,"utf-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");					
			transformer.transform(new StreamSource(new File(sourcePath)), new StreamResult(new File(resultDir)));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public static void transformNotReturn(File xsl, File input, File output, String type, Map<String, String> param)
			throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException,
			TransformerException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(input);
		transformNotReturn(xsl, doc, output, type, param);
	}

	public static void transformNotReturn(File xsl, Document doc, File output, String type, Map<String, String> param)
			throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException,
			TransformerException {
		Source source = new DOMSource(doc);
		Source xslt = new StreamSource(xsl);
		TransformerFactory transformerFactory = TransformerFactory.newInstance("net.sf.saxon.TransformerFactoryImpl",
				(ClassLoader) null);
		Transformer transformer = transformerFactory.newTransformer(xslt);
		if (param != null) {
			Iterator var9 = param.keySet().iterator();

			while (var9.hasNext()) {
				String key = (String) var9.next();
				transformer.setParameter(key, param.get(key));
			}
		}

		transformer.setParameter("output", output.getName());
		transformer.setOutputProperty("indent", "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		if (type == null) {
			transformer.setOutputProperty("method", "xml");
		} else {
			transformer.setOutputProperty("method", type);
		}

		transformer.setOutputProperty("encoding", "UTF-8");
		StreamResult result = new StreamResult(output);
		transformer.transform(source, result);
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	}
	
	public static void tempParser(){
		try {			
		    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		    Document doc = docBuilder.newDocument();
			org.w3c.dom.Element rootElement = doc.createElement("dummy");
			doc.appendChild(rootElement);
           
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("C:\\DiffCounterTemp\\dummy.xml"));			
			transformer.transform(source, result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
