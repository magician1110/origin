package com.ast.query.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ast.query.Model.Item;
import com.ast.query.Model.Root;
import com.ast.query.Model.Span;
import com.ast.query.View.GuiManager;

public class Excel {	

	@SuppressWarnings("unused")
	public void excelManger(Root tocTest){
		try {            
			FileInputStream fileInPutS=new FileInputStream(GuiManager.getResourceAsFile("QueryXLSX.xlsx").getPath()); //엑셀 템플릿 선택			
			XSSFWorkbook workbook = new XSSFWorkbook(fileInPutS);
            XSSFSheet sheet = workbook.getSheetAt(0);      
            XSSFRow row = sheet.createRow(0);            
            XSSFCell cell = row.createCell(0);                                               
            
            XSSFCellStyle myStyle = workbook.createCellStyle();
            CellStyle cellStyle = workbook.createCellStyle();
            XSSFFont myFont = workbook.createFont();
            
           int rowNum = 5;
           if(tocTest != null){
        	   for(Item t : tocTest.getToc().getItem()){
        		row = sheet.createRow(rowNum++); 
        		
        		if ( t.getLevel() == 0 ){
        			row.setHeightInPoints(30);
        		}
        		
        		XSSFCell[] cells = new XSSFCell[13];        		        		
        		for(int i = 0 ; i < 11 ; i++){        			        			
        			cells[i] = row.createCell((i)+2);          			        			            		
            		if(t.getLevel() == 0){            			            			            			
            			myStyle.setBorderBottom(BorderStyle.THIN);
            			myStyle.setBottomBorderColor(IndexedColors.BLACK.index);
            			myStyle.setBorderLeft(BorderStyle.THIN);
            			myStyle.setLeftBorderColor(IndexedColors.BLACK.index);
            			myStyle.setBorderRight(BorderStyle.THIN);
            			myStyle.setRightBorderColor(IndexedColors.BLACK.index);
            			myStyle.setBorderTop(BorderStyle.THIN);
            			myStyle.setTopBorderColor(IndexedColors.BLACK.index);            			
            			myStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            			myStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte)217,(byte)217,(byte)217},null));
            			myStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		    			myFont.setBold(true);
		    			
		    			myStyle.setFont(myFont);		    			
                        cells[i].setCellStyle(myStyle);
            		}else{
            			cellStyle.setBorderBottom(BorderStyle.THIN);           
            			cellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
            			cellStyle.setBorderLeft(BorderStyle.THIN);             
            			cellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
            			cellStyle.setBorderRight(BorderStyle.THIN);            
            			cellStyle.setRightBorderColor(IndexedColors.BLACK.index);
            			cellStyle.setBorderTop(BorderStyle.THIN);              
            			cellStyle.setTopBorderColor(IndexedColors.BLACK.index);
            			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            			
            			cells[i].setCellStyle(cellStyle);
            		}            		
            		
        		}
        		cells[0].setCellValue(t.getTitle());      		        	
        		
   		    	if(t.getP() != null){
   		    		String cell_4_text = t.getP().getContent().get(0).toString().replaceAll("¶", "\n");   		    		
   		            XSSFRichTextString rts= new XSSFRichTextString(cell_4_text);   		            
   		            String span = null;
   		            
   		    		for(Span s : t.getP().getSpan()){
   		    			if(s.getContent().get(0) != null){		
	   		    			if(s.getStyle() != null && s.getStyle().equals("bold")){
	   		    				myFont.setBold(true);   			    			
	   			    			myStyle.setFont(myFont);   			    			
	   		    				span = s.getContent().get(0).toString().replaceAll("¶", "\n");    
	   		    				System.out.println(span);
	   		    				rts.append(span, myFont);
	   		    			}else{
	   		    				myFont.setBold(false);   			    			
	   			    			myStyle.setFont(myFont);
	   			    			//System.out.println("s.getContent().get(0).toString() ====" + s.getContent().get(0).toString());
	   		    				span = s.getContent().get(0).toString().replaceAll("¶", "\n");
	   		    				rts.append(span, myFont);   		    		
	   		    				}
   		    			}
   		    		}
   		    		
   		    		if(t.getP().getStyle() != null && t.getP().getStyle().equals("many")){
   		    			XSSFCellStyle flagStyle = workbook.createCellStyle();  		    			
   		    			flagStyle.setFillForegroundColor(new XSSFColor(new byte[]{(byte)255,(byte)255,(byte)0},null));
   		    			flagStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
   		    			flagStyle.setWrapText(true);
   		    			flagStyle.setVerticalAlignment(VerticalAlignment.CENTER);
   		    			cells[2].setCellStyle(flagStyle);
   	   		    		cells[2].setCellValue(rts);
   		    		}
   		    		else
   		    		{
	   		    		cellStyle.setWrapText(true);
		    			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		    			cells[2].setCellStyle(cellStyle);
	   		    		cells[2].setCellValue(rts);
   		    		}
   		    	}	
   		    }	              
        	   String fileSplitePath = GuiManager.selectItem;        	   
        	   int commaIdx = fileSplitePath.lastIndexOf(".");        	   
        	   String SpliteFileName = fileSplitePath.substring(0, commaIdx);
        	   File onlyFileN = new File(SpliteFileName);        	           	            	  
        	   FileOutputStream fileOutPutS = new FileOutputStream(onlyFileN + ".xlsx");
        	 
               workbook.write(fileOutPutS);
               fileOutPutS.close();
           }
            
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

	}

}
