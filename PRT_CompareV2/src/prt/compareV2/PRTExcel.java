package prt.compareV2;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ibm.icu.impl.UResource.Array;


public class PRTExcel {
	
/*	private FileInputStream fis = null;
	private HSSFWorkbook workbook = null;

	private DocumentBuilderFactory docFactory;
	private javax.xml.parsers.DocumentBuilder docBuilder;
	private Element variables;
    private Element model;

    private Element variablesChild;
    private Element itemWidth;
    private Element itemHeight;
    private Element itemDepth;    
    
    private HSSFSheet sheet;
    private HSSFRow row;*/
    
	public void ExcelExtract(){

	}
	
	public void xlsToPoiList(File input, File output) throws Exception{		
		FileInputStream fis = null;
		HSSFWorkbook workbook = null;

		DocumentBuilderFactory docFactory;
		javax.xml.parsers.DocumentBuilder docBuilder;
		Element variables;
		Element model;

		Element variablesChild;
		Element itemWidth;
		Element itemHeight;
		Element itemDepth;    

		HSSFSheet sheet;
		HSSFRow row;
		    
		docFactory = DocumentBuilderFactory.newInstance();
		docBuilder =  docFactory.newDocumentBuilder();              
        
        // root elements
        Document doc = docBuilder.newDocument();
        variables = doc.createElement("Variables");
        doc.appendChild(variables);
		
        Document codeDoc = docBuilder.parse(new File("XMLRegionXML\\xml\\codeList.xml"));
        NodeList codes = codeDoc.getDocumentElement().getElementsByTagName("code");
        
        List<String> valid = new ArrayList<String>();
        for (int i = 0; i < codes.getLength(); i++) {
            valid.add(codes.item(i).getTextContent().toLowerCase());
        }
       
			fis = new FileInputStream(input);
			workbook = new HSSFWorkbook(fis);
			
			sheet = workbook.getSheetAt(0);
	        int rowCount = sheet.getLastRowNum();
	        int colCount = sheet.getRow(7).getLastCellNum();
	        
			row = sheet.getRow(7);
			//int garbage = 0;
			
			for(int cCnt = 9; cCnt < colCount; cCnt++){  //모델넘버 갖고 오는 구문			
				HSSFCell myCell = row.getCell(cCnt);				
				String myStr = getStringFromCellHSSF(myCell);				
	            myStr = myStr.replaceAll("\\s+", " ").trim();	            	            	           
	            if (myStr.matches(".*[^0-9A-Z]+.*")){
	                //garbage++;
	            }else{	            	
	            	model = doc.createElement("model");	            	
	            	model.setAttribute("name", myStr);	
	            	
            		for(int rCnt = 9; rCnt <= rowCount; rCnt++){            			    	            			    	            		
        	            HSSFRow Row = sheet.getRow(rCnt);	            
        	            HSSFCell cell1 = Row.getCell(1);
        	            CellType type1 = cell1.getCellTypeEnum();		        	            
        	        	String str1 = getStringFromCellHSSF(cell1);					
        	        	str1 = str1.replaceAll("\\s+", " ").trim();
        	            
        	            HSSFCell cell3 = Row.getCell(3);
        	            String str3 = getStringFromCellHSSF(cell3);        	            
        	            str3 = str3.replaceAll("\\s+", " ").trim();
        	            
        	            HSSFRow row9 = sheet.getRow(rCnt);	            			
            			HSSFCell cell9 = (HSSFCell) row9.getCell(cCnt);	            			
            			CellType type9 = cell9.getCellTypeEnum();
      	                String str9 = getStringFromCellHSSF(cell9);	      	                
      	                str9 = str9.replaceAll("\\s+", " ").trim();	
                  	                 	                     	                
      	                //Model Name Splite      	                
					    if(type1 == CellType.STRING && valid.contains(cell1.getRichStringCellValue().toString().toLowerCase())){
					    	
					    	if(model.getAttribute("name").contains(row.getCell(cCnt).getStringCellValue())){
						        variablesChild = doc.createElement("spec");	         	               	      	             
						        variablesChild.setTextContent(doc.createTextNode(str9).getTextContent());
						        //과거 DESC가 필요하다 하여 갖고 온 상태이며, 현재는 필요 없다고 하여 주석처리 됨
						        //variablesChild.setAttribute("desc", str3); 
						        variablesChild.setAttribute("key", str1);

		                		if(str1.equals("DTS00277") || str1.equals("DTS00278") || str1.equals("DTS00556")){	
		                			
		                			if(str9.contains(" x ")){
			                			itemWidth = doc.createElement("part");
			                			itemHeight = doc.createElement("part");
			                			itemDepth = doc.createElement("part");
			                			                			
			                			String[] arr9 = str9.split(" x ");	
			                			if(arr9.length <= 2){			                				
				                			itemWidth.setTextContent(arr9[0]);//width
				                			itemHeight.setTextContent(arr9[1]);
				                			
				                			itemWidth.setAttribute("name", "W");
				                			itemHeight.setAttribute("name", "H");
				                			variablesChild.setTextContent("");
				                			
				                			variablesChild.appendChild(itemWidth);
				                			variablesChild.appendChild(itemHeight);			                				
			                			}else{
				                			itemWidth.setTextContent(arr9[0]);//width
				                			itemHeight.setTextContent(arr9[1]);
				                			itemDepth.setTextContent(arr9[2]);

				                			itemWidth.setAttribute("name", "W");
				                			itemHeight.setAttribute("name", "H");
				                			itemDepth.setAttribute("name", "D");
				                			variablesChild.setTextContent("");	
				                					                			
				                			variablesChild.appendChild(itemWidth);
				                			variablesChild.appendChild(itemHeight);
				                			variablesChild.appendChild(itemDepth);	
			                			}

		                			}else{
		                				variablesChild.setTextContent(doc.createTextNode(str9).getTextContent());
		                			}
               		
		                		}
		                		model.appendChild(variablesChild);
					    	}
					    }
            		}	            		            		          
	            	//여기다 벨류값 넣으면 될듯 
	            	variables.appendChild(model);
	            		            	
	            }	            
	                
			}

	        // write the content into xml file
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	        DOMSource source = new DOMSource(doc);
	        StreamResult result = new StreamResult(output);

	        transformer.transform(source, result);
		}		
		
	public void xlsxToPoiList(File input, File output) throws Exception{		
		
		FileInputStream fis = null;
		XSSFWorkbook workbook = null;

		DocumentBuilderFactory docFactory;
		javax.xml.parsers.DocumentBuilder docBuilder;
		Element variables;
		Element model;

		Element variablesChild;
		Element itemWidth;
		Element itemHeight;
		Element itemDepth;    

		XSSFSheet sheet;
		XSSFRow row;
		    
		
		docFactory = DocumentBuilderFactory.newInstance();
		docBuilder =  docFactory.newDocumentBuilder();              
        
        // root elements
        Document doc = docBuilder.newDocument();
        variables = doc.createElement("Variables");
        doc.appendChild(variables);
		
        Document codeDoc = docBuilder.parse(new File("XMLRegionXML\\xml\\codeList.xml"));
        NodeList codes = codeDoc.getDocumentElement().getElementsByTagName("code");
        
        List<String> valid = new ArrayList<String>();
        for (int i = 0; i < codes.getLength(); i++) {
            valid.add(codes.item(i).getTextContent().toLowerCase());
        }
       
			fis = new FileInputStream(input);
			workbook = new XSSFWorkbook(fis);
			
			sheet = workbook.getSheetAt(0);
	        int rowCount = sheet.getLastRowNum();
	        int colCount = sheet.getRow(7).getLastCellNum();
	        
			row = sheet.getRow(7);
			//int garbage = 0;
			
			for(int cCnt = 9; cCnt < colCount; cCnt++){  //모델넘버 갖고 오는 구문			
				XSSFCell myCell = row.getCell(cCnt);				
				String myStr = getStringFromCellXSSF(myCell);				
	            myStr = myStr.replaceAll("\\s+", " ").trim();	            	            	           
	            if (myStr.matches(".*[^0-9A-Z]+.*")){
	                //garbage++;
	            }else{	            	
	            	model = doc.createElement("model");	            	
	            	model.setAttribute("name", myStr);	
	            	
            		for(int rCnt = 9; rCnt <= rowCount; rCnt++){            			    	            			    	            		
        	            XSSFRow Row = sheet.getRow(rCnt);	            
        	            XSSFCell cell1 = Row.getCell(1);
        	            CellType type1 = cell1.getCellTypeEnum();		        	            
        	        	String str1 = getStringFromCellXSSF(cell1);					
        	        	str1 = str1.replaceAll("\\s+", " ").trim();
        	            
        	        	XSSFCell cell3 = Row.getCell(3);
        	            String str3 = getStringFromCellXSSF(cell3);        	            
        	            str3 = str3.replaceAll("\\s+", " ").trim();
        	            
        	            XSSFRow row9 = sheet.getRow(rCnt);	            			
        	            XSSFCell cell9 = (XSSFCell) row9.getCell(cCnt);	            			
            			CellType type9 = cell9.getCellTypeEnum();
      	                String str9 = getStringFromCellXSSF(cell9);	      	                
      	                str9 = str9.replaceAll("\\s+", " ").trim();	
                  	                 	                     	                
      	                //Model Name Splite      	                
					    if(type1 == CellType.STRING && valid.contains(cell1.getRichStringCellValue().toString().toLowerCase())){
					    	
					    	if(model.getAttribute("name").contains(row.getCell(cCnt).getStringCellValue())){
						        variablesChild = doc.createElement("spec");	         	               	      	             
						        variablesChild.setTextContent(doc.createTextNode(str9).getTextContent());
						        //과거 DESC가 필요하다 하여 갖고 온 상태이며, 현재는 필요 없다고 하여 주석처리 됨
						        //variablesChild.setAttribute("desc", str3); 
						        variablesChild.setAttribute("key", str1);
						        
		                		if(str1.equals("DTS00277") || str1.equals("DTS00278") || str1.equals("DTS00556")){

		                			if(str9.contains(" x ")){
			                			itemWidth = doc.createElement("part");
			                			itemHeight = doc.createElement("part");
			                			itemDepth = doc.createElement("part");
			                			
		                				String[] arr9 = str9.split(" x ");		                				
			                			if(arr9.length <= 2){			                				
				                			itemWidth.setTextContent(arr9[0]);//width
				                			itemHeight.setTextContent(arr9[1]);
				                			
				                			itemWidth.setAttribute("name", "W");
				                			itemHeight.setAttribute("name", "H");
				                			variablesChild.setTextContent("");
				                			
				                			variablesChild.appendChild(itemWidth);
				                			variablesChild.appendChild(itemHeight);			                				
			                			}else{
				                			itemWidth.setTextContent(arr9[0]);//width
				                			itemHeight.setTextContent(arr9[1]);
				                			itemDepth.setTextContent(arr9[2]);

				                			itemWidth.setAttribute("name", "W");
				                			itemHeight.setAttribute("name", "H");
				                			itemDepth.setAttribute("name", "D");
				                			variablesChild.setTextContent("");	
				                					                			
				                			variablesChild.appendChild(itemWidth);
				                			variablesChild.appendChild(itemHeight);
				                			variablesChild.appendChild(itemDepth);	
			                			}
			                			
		                			}else{
		                				variablesChild.setTextContent(doc.createTextNode(str9).getTextContent());
		                			}

		                					                		
		                		}
		                		model.appendChild(variablesChild);
					    	}
					    }
            		}	            		            		          
	            	//여기다 벨류값 넣으면 될듯 
	            	variables.appendChild(model);
	            		            	
	            }	            
	                
			}

	        // write the content into xml file
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	        DOMSource source = new DOMSource(doc);
	        StreamResult result = new StreamResult(output);

	        transformer.transform(source, result);
		}		
	
/*	private static int findRow(HSSFSheet sheet, String cellContent) {
	    for (Row row : sheet) {	    	
	        for (Cell cell : row) {	    	        	
	            if (cell.getCellType() == CellType.STRING) {	            	
	            	System.out.println("cell ============" + cell.getStringCellValue());
	                if (cell.getRichStringCellValue().getString().trim().equals(cellContent)){	                		                	
	                    return row.getRowNum();  
	                }
	            }
	        }
	        System.out.println("");
	    }               
	    return 0;
	}*/
	
    @SuppressWarnings("deprecation")
	public static String getStringFromCellHSSF(HSSFCell myCell) throws Exception {
        CellType type = myCell.getCellTypeEnum();
        String myStr = null;
        try {
            switch (type) {
                case STRING:// Cell.CELL_TYPE_STRING:
                    myStr = myCell.getRichStringCellValue().toString();
                    break;
                case BOOLEAN:
                    myStr = String.valueOf(myCell.getBooleanCellValue());
                    break;
                case NUMERIC:
                    myStr = String.valueOf(myCell.getNumericCellValue());
                    break;
                case FORMULA:
                    myStr = myCell.getCellFormula();
                    break;
                case BLANK:
                    myStr = "";
                    break;
                case _NONE:
                    myStr = "";
                    break;
                case ERROR:
                    myStr = "error text";
                    break;
                default:
                    throw new Exception("Unkown Cell type");
            }
            return myStr;
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    @SuppressWarnings("deprecation")
	public static String getStringFromCellXSSF(XSSFCell myCell) throws Exception {
        CellType type = myCell.getCellTypeEnum();
        String myStr = null;
        try {
            switch (type) {
                case STRING:// Cell.CELL_TYPE_STRING:
                    myStr = myCell.getRichStringCellValue().toString();
                    break;
                case BOOLEAN:
                    myStr = String.valueOf(myCell.getBooleanCellValue());
                    break;
                case NUMERIC:
                    myStr = String.valueOf(myCell.getNumericCellValue());
                    break;
                case FORMULA:
                    myStr = myCell.getCellFormula();
                    break;
                case BLANK:
                    myStr = "";
                    break;
                case _NONE:
                    myStr = "";
                    break;
                case ERROR:
                    myStr = "error text";
                    break;
                default:
                    throw new Exception("Unkown Cell type");
            }
            return myStr;
        } catch (Exception ex) {
            throw ex;
        }
    }
    
	
}
