import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationText;

public class PDFunc {
	
	PDDocument document = null;
	File file;
	
	public PDFunc() {
		
	}
	
	public String getTime(){
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("HHmmssSSS");
		String strTime = dayTime.format(new Date(time)); 
		return strTime;
	}
	
	public static void testPDFBoxExtractImages(File imageFilePath) {
		try {
		    PDDocument document = PDDocument.load(imageFilePath);
		    PDPageTree list = document.getPages();
		    for (PDPage page : list) {
		        PDResources pdResources = page.getResources();
		        for (COSName c : pdResources.getXObjectNames()) {
		            PDXObject o = pdResources.getXObject(c);
		            if (o instanceof org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject) {
		                File file = new File("F:\\Project\\MC\\MC_Extractor_Image\\img\\" + System.nanoTime() + ".png");
		                ImageIO.write(((org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject)o).getImage(), "png", file);
		            }
		        }
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public boolean pdfParsing(File[] filePath) {
		boolean valueTF = false;
		
		//PDDocument document;
		try {			
			for(int i = 0; i < filePath.length; i++) {
				System.out.println("FILE PATH === : " + filePath[i] );
				document = PDDocument.load(filePath[i]);
				int numPages = document.getNumberOfPages();
				
				Object token = null;
				List<Object> tokens = null;

				int currentPage = 0;
				for(int n = 0; n  < numPages; n++) {			
					PDPage page = document.getPage(n);
					List<PDAnnotation> annotations = page.getAnnotations();
					
					//boolean flag = false;
					PDFStreamParser parser = new PDFStreamParser(page);
					parser.parse();
					tokens = parser.getTokens();		

					for(int j = 0; j < tokens.size(); j++) {
						token = tokens.get(j);
										
						if(token instanceof Operator) {
							Operator op = (Operator) token; 					
							if(op.getName().equals("SCN")) {
								Object nexttokens = null;
								Object nexttokens2 = null;

								nexttokens = tokens.get(j-2);
								nexttokens2 = tokens.get(j+2);
										
								if(nexttokens instanceof Operator)
								{										
									nexttokens = (Operator)tokens.get(j-2);																							
									
									if(nexttokens2 instanceof Operator) {
										nexttokens2 = (Operator)tokens.get(j+2);															 											
										if((((Operator) nexttokens).getName().equals("CS"))) {
										//if((((Operator) nexttokens).getName().equals("CS")) && (((Operator) nexttokens2).getName().equals("M"))) {
										//if(((Operator) nexttokens).getName().equals("CS")) {
										    //PDColor color = new PDColor(new float[] {255f, 0, 0}, PDDeviceRGB.INSTANCE);
										    //PDBorderStyleDictionary thickness = new PDBorderStyleDictionary();
										    //thickness.setWidth((float)2);						
											//PDAnnotationTextMarkup rectangle = new PDAnnotationTextMarkup(PDAnnotationTextMarkup.SUB_TYPE_HIGHLIGHT);
											PDAnnotationText text = new PDAnnotationText();
											System.out.println("currentPage ==== : " + currentPage);
										    //rectangle.setColor(color);
										    //rectangle.setBorderStyle(thickness);
											//System.out.println("rectangle == : " + rectangle.getAnnotationName());
										    PDRectangle points = new PDRectangle();
//1040094
										    points.setLowerLeftX((float) 100);
										    points.setLowerLeftY((float) 100);
										    points.setUpperRightX((float) 300);
										    points.setUpperRightY((float) 300);
										    text.setContents("Rectangle");
										    text.setRectangle(points);
										    //rectangle.setColor(color);										    
										    text.getCOSObject().setString(COSName.T, "has a Rectangle" + currentPage);

										    annotations.add(text);
										    System.out.println("Rectangle is added.");		
										}
									}

								}
								
							}
						}

					}

				}    
			    // Save the file					
				//document.save(filePath[i].getName());					
				//document.save("PDF출력물\\" + FilenameUtils.getBaseName(filePath[i].getName()) + "(OUT)" + ".pdf");
				//document.save(FilenameUtils.getBaseName(filePath[i].getName()) + "(OUT)" + ".pdf");
				int dot = filePath[i].getAbsolutePath().lastIndexOf(".");
				System.out.println("filePath[i].getAbsolutePath() == : " + filePath[i].getPath());
				String fileName = filePath[i].getPath().substring(0,dot);
				System.out.println("fileName == : " + fileName);
				document.save(fileName + "(OUT)" + ".pdf");
			    // Close the document
			    document.close();
			    valueTF = true;
			}			
		} catch (IOException e) {	
			valueTF = false;
			e.printStackTrace();			
		}
		return valueTF;

	}
}
