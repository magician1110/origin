package com.ast.query.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode;

import com.ast.query.View.GuiManager;


public class Pdf{
	
	public void Manager() throws IOException, XMLStreamException{
		System.out.println("GuiManager.selectItem ====" + GuiManager.selectItem);
		File myFile = new File(GuiManager.selectItem); //PDF를 선택
		PDDocument pdf = PDDocument.load(myFile);
		PDDocumentOutline outline = pdf.getDocumentCatalog().getDocumentOutline();		
		XMLOutputFactory factory = XMLOutputFactory.newInstance();		
		factory.setProperty("escapeCharacters", false);
		
		XMLStreamWriter writer = factory.createXMLStreamWriter(new FileOutputStream("C:\\QueryBuilderTemp\\output.xml"), "UTF-8"); //XML파일 생성
		//select path 한곳에 넣기
		int level = 0;

		writer.writeStartDocument();
		writer.writeCharacters("\r");
		writer.writeStartElement("root");
		writer.writeCharacters("\r\t");
		writer.writeStartElement("toc");
		printBookmark(outline, level, pdf, writer);
		writer.writeCharacters("\r\t");
		writer.writeEndElement();
		// ************************
		writer.writeCharacters("\r\t");
		writer.writeStartElement("cmt");
		getAnnotations(pdf, writer);
		writer.writeCharacters("\r\t");
		writer.writeEndElement();
		writer.writeCharacters("\r");
		writer.writeEndElement();
		writer.writeEndDocument();

		writer.close();
		pdf.close();
	}

	public static void printBookmark(PDOutlineNode bookmark, int depth, PDDocument pdf, XMLStreamWriter writer) throws IOException, XMLStreamException
	{
	    PDOutlineItem current = bookmark.getFirstChild();

	    while (current != null)
	    {
			int pageIndex = 0;
			int pageNumber = 1;

			for ( PDPage page : pdf.getPages() ) 
			{
				if (page.equals(current.findDestinationPage(pdf))) 
				{
					break;
				}
				pageNumber++;
				pageIndex++;
			}
			
			if (depth != 2)
	    	{
				writer.writeCharacters("\n\t\t");
	    		writer.writeStartElement("item");
		    	writer.writeAttribute("level", Integer.toString(depth));
		    	writer.writeAttribute("page", Integer.toString(pageNumber));
				writer.writeCharacters(current.getTitle());
	    		writer.writeEndElement();
	    	}
	        printBookmark(current, depth + 1, pdf, writer);
	        current = current.getNextSibling();
	    }
	}

	public static void getAnnotations(PDDocument pdf, XMLStreamWriter writer) throws XMLStreamException, IOException
	{
		int pageNum = 1;
		for ( PDPage page : pdf.getPages() )
		{
			List<PDAnnotation> annotations = new ArrayList<>();

			annotations = page.getAnnotations();
			for (PDAnnotation annotation : annotations)
			{
                COSDictionary annotationDict = annotation.getCOSObject();
                COSString contents = (COSString) annotationDict.getDictionaryObject(COSName.RC);
				String subtype = annotation.getSubtype();

				if ( !"Popup".equals(subtype) && !"Link".equals(subtype) )
				{
		    		writer.writeCharacters("\n\t\t");
 					writer.writeStartElement("item");
		    		writer.writeAttribute("page", Integer.toString(pageNum));
		    		if (contents != null)
		    		{
						writer.writeCharacters(contents.getString().replace("<?xml version=\"1.0\"?>", ""));
					}
					else
					{
						writer.writeStartElement("body");
						writer.writeNamespace("", "http://www.w3.org/1999/xhtml");
						writer.writeStartElement("p");
						writer.writeStartElement("span");
						writer.writeCharacters("Empty Comment");
						writer.writeEndElement();
						writer.writeEndElement();
						writer.writeEndElement();
					}
 					writer.writeEndElement();
 				}
			}
			pageNum++;
		}
	}
}
