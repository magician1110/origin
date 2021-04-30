package com.ast.diff.Controller;

import com.deltaxml.cores9api.*;
import com.deltaxml.cores9api.config.LexicalPreservationConfig;
import com.deltaxml.cores9api.config.ModifiedWhitespaceBehaviour;
import com.deltaxml.cores9api.config.PreservationOutputType;
import com.deltaxml.cores9api.config.PreservationProcessingMode;
import com.deltaxml.cores9api.config.PresetPreservationMode;
import com.deltaxml.cores9api.config.ResultFormat;
import com.deltaxml.cores9api.config.ResultReadabilityOptions;
import com.deltaxml.gui.view.swt.components.DialogShell.Type;
import com.deltaxml.pipe.filters.dx2.wbw.WordBufferingFilter;

import net.sf.saxon.s9api.Serializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.el.ELContext;
import javax.ws.rs.Encoded;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;

import org.apache.xml.resolver.apps.resolver;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.certicom.ecc.a.b.a.a;
import com.certicom.ecc.a.d.o;
import com.certicom.ecc.asn1.f;
import com.deltaxml.api.DeltaXMLException;
import com.deltaxml.api.DeltaXMLProcessingException;
import com.deltaxml.api.NoLicenseInstalledException;
import com.deltaxml.api.XMLCombinerFactory;
import com.deltaxml.api.XMLComparator;
import com.deltaxml.api.XMLComparatorFactory;
import com.deltaxml.core.ComparatorInstantiationException;
import com.deltaxml.core.FilterClassInstantiationException;
import com.deltaxml.core.FilterConfigurationException;
import com.deltaxml.core.ParserInstantiationException;
import com.deltaxml.core.PipelinedComparatorError;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class DiffController{
	String hashValue = null;	
	String sha256Value = null;

	public void docCom() throws StaticPDFormatException, PDFilterConfigurationException, DynamicPDFormatException, PDAdvancedConfigException, IllegalStateException, ComparisonException, FilterProcessingException, PipelineLoadingException, PipelineSerializationException, LicenseException, PipelinedComparatorError, IOException, ParserInstantiationException, FilterConfigurationException, ComparatorInstantiationException, FilterClassInstantiationException, NoLicenseInstalledException, DeltaXMLProcessingException{
		/*File sourceF1 =  new File("C:\\DiffCount\\docA.xml");
		File sourceF2 = new File("C:\\DiffCount\\docB.xml");
		File resultR = new File("C:\\DiffCount\\delta.xml");*/
		File sourceF1 = new File("C:\\DiffCounterTemp\\docA.xml");
		File sourceF2 = new File("C:\\DiffCounterTemp\\docB.xml");
		File resultR = new File("C:\\DiffCounterTemp\\delta.xml");
		
		try {
			//System.setProperty("xml.catalog.files", "");
			DocumentComparator comparator = new DocumentComparator();			
    		comparator.getResultReadabilityOptions().setElementSplittingEnabled(false);
			comparator.getOutputFormatConfiguration().setResultFormat(ResultFormat.DELTA);		
	    // Ensure ignorable whitespace is not preserved
    	    LexicalPreservationConfig lexConfig= new LexicalPreservationConfig(PresetPreservationMode.ROUND_TRIP);
		    lexConfig.setPreserveIgnorableWhitespace(false);		    
		    lexConfig.setPreserveProcessingInstructions(true);		    
			WordBufferingFilter wbf =new WordBufferingFilter() {
				@Override
				public void setwrapUnchangedText(String value) {
					// TODO Auto-generated method stub
					super.setwrapUnchangedText(value);
				}};
		    //lexConfig.setProcessingInstructionOutputType(PreservationOutputType.AUTOMATIC);		    
		    //lexConfig.setPreserveXMLDeclaration(true);		 
			//wbf.setwrapUnchangedText("deltaxml:textGroup");
			
		    comparator.setLexicalPreservationConfig(lexConfig);		
			//comparator.setLicenseFile(getResourceAsFile("deltaxml-xml-compare.lic"));
			comparator.compare(sourceF1, sourceF2, resultR);
			
		} catch (DeltaXMLException e) {
    		e.printStackTrace();
    		e.getCause();
			e.getLocalizedMessage();
			e.getMessage();
		}

		
	}
	
	public void xmlParserA(){
		
		try {
			//File filePathA = new File("C:\\DiffCount\\docA.xml");			
			File filePathA = new File("C:\\DiffCounterTemp\\docA.xml");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filePathA);
			//doc.getDocumentElement().normalize();
			
			NodeList  tagRootsList = doc.getElementsByTagName("*");		
			
			for(int i = 0; i < tagRootsList.getLength(); i++){
				Node nodeRoots = tagRootsList.item(i);
				
				if(nodeRoots.hasAttributes()){
					org.w3c.dom.Element ele = (org.w3c.dom.Element)tagRootsList.item(i);																								
					hashValue = ele.getAttribute("hash");						
					sha256Value = SHA256(hashValue);					
					ele.setAttribute("hash",sha256Value.toUpperCase());
				}
									
			}			
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			//StreamResult result = new StreamResult(new  File("C:\\DiffCount\\docA.xml"));
			StreamResult result = new StreamResult(new  File("C:\\DiffCounterTemp\\docA.xml"));
			transformer.transform(source, result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	public void xmlParserB(){
		
		try {
			//File filePathA = new File("C:\\DiffCount\\docB.xml");			
			File filePathA = new File("C:\\DiffCounterTemp\\docB.xml");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filePathA);
			//doc.getDocumentElement().normalize();
			
			NodeList  tagRootsList = doc.getElementsByTagName("*");		
			
			for(int i = 0; i < tagRootsList.getLength(); i++){
				Node nodeRoots = tagRootsList.item(i);
				
				if(nodeRoots.hasAttributes()){
					org.w3c.dom.Element ele = (org.w3c.dom.Element)tagRootsList.item(i);																								
					hashValue = ele.getAttribute("hash");						
					sha256Value = SHA256(hashValue);					
					ele.setAttribute("hash",sha256Value.toUpperCase());
				}								
			}						
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			//StreamResult result = new StreamResult(new  File("C:\\DiffCount\\docB.xml"));
			StreamResult result = new StreamResult(new  File("C:\\DiffCounterTemp\\docB.xml"));
			transformer.transform(source, result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	public void tempParser(){
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
	
	public String SHA256(String str){
		String SHA = ""; 
		
		try{
			MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
			sh.update(str.getBytes()); 
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer(); 
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			SHA = sb.toString();
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace(); 
			SHA = null; 
		}
		return SHA;
	
	}
	
	public static File getResourceAsFile(String resourcePath) {
		try {
			InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath);

			if (in == null) {
				return null;
			}
			File tempFile = File.createTempFile(String.valueOf(in.hashCode()), ".tmp");
			tempFile.deleteOnExit(); // 해당 경로된 디렉토리 파일을 삭제함 (컴파일이 종료 되는 시점에)

			try (FileOutputStream out = new FileOutputStream(tempFile)) {
				// copy stream
				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = in.read(buffer)) != -1) {
					out.write(buffer, 0, bytesRead);
				}
			}
			return tempFile;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	
}
