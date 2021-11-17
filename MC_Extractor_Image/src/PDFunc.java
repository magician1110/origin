import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationRubberStamp;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceDictionary;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceStream;

public class PDFunc {
	PDDocument document = null;
	CommonFunc commonFunc = null;
	private boolean valueTF;
	private static final String SAVE_GRAPHICS_STATE = "q\n";
	private static final String RESTORE_GRAPHICS_STATE = "Q\n";
	private static final String CONCATENATE_MATRIX = "cm\n";
	private static final String XOBJECT_DO = "Do\n";
	private static final String SPACE = " ";
	private static final NumberFormat formatDecimal = NumberFormat.getNumberInstance( Locale.US );
	//09:30
	public boolean pdfParsing(File[] filePath) {	
		try {
			valueTF = false;			
			BufferedImage image = ImageIO.read(this.getClass().getResource("stamp-dtp-edit.png"));			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ImageIO.write(image,"png", outputStream);
			byte[] imageByte = outputStream.toByteArray();			
			
			for(int i = 0; i < filePath.length; i++) {				
				document = PDDocument.load(filePath[i]);
				PDImageXObject xImage = PDImageXObject.createFromByteArray(document, imageByte, null);
				int numPages = document.getNumberOfPages();				
				
				Object token = null;
				List<Object> tokens = null;
				
				int currentPage = 0;
				int imageCount = 1;
				for(int n = 0; n  < numPages; n++) {	

					PDPage page = document.getPage(n);
					boolean flag = false;
					//이미지 갖고 오는 부분
					PDResources pdResources = page.getResources();

					//주석 관련 처리하는 부분 (해당 프로젝트에서는 주석을 사용하지 않기 떄문에 사용하지않음)
					List<PDAnnotation> annotations = page.getAnnotations();
										
					PDFStreamParser parser = new PDFStreamParser(page);
					parser.parse();
					tokens = parser.getTokens();		
														
					for(int j = 0; j < tokens.size(); j++) {
						token = tokens.get(j);

						if(token instanceof Operator) {
							Operator op = (Operator) token; 					
							if(op.getName().equals("SCN")) {
//						        for (COSName c : pdResources.getXObjectNames()) {
//						            PDXObject o = pdResources.getXObject(c);
//						            if (o instanceof org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject) {
//						                File file = new File("F:\\Project\\MC\\MC_Extractor_Image\\img\\" + System.nanoTime() + ".png");
//						                ImageIO.write(((org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject)o).getImage(), "png", file);
//						            }
//						        }
								
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
										//if(((Operator) nexttokens).getName().equals("CS")) {
										    //PDColor color = new PDColor(new float[] {255f, 0, 0}, PDDeviceRGB.INSTANCE);
										    //PDBorderStyleDictionary thickness = new PDBorderStyleDictionary();
										    //thickness.setWidth((float)2);						
											//PDAnnotationTextMarkup rectangle = new PDAnnotationTextMarkup(PDAnnotationTextMarkup.SUB_TYPE_HIGHLIGHT);																						
									    for (COSName c : pdResources.getXObjectNames()) {
								            PDXObject o = pdResources.getXObject(c);
								            if (o instanceof org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject) {								            							            
								            	//n은 0부터 시작하여 +1 								            	
								            	currentPage = n+1;								            	
												//System.out.println("zzzzzzzzzzzz === : " + filePath[i].getParentFile());
								                //File file = new File("F:\\Project\\MC\\MC_Extractor_Image\\img\\" + System.nanoTime() + ".png");	
												if(!new File(filePath[i].getParentFile() + "\\img").exists()) {
													new File(filePath[i].getParentFile() + "\\img").mkdir();
												}													
											    //예시 : H_NAFM007_NE1 21_G5WIDE[JPN_JP]AVNT_GIMS2CB000_211019 1350 (24페이지 이미지 3개 중 빨간박스 2개만 추출 해야 하지만 3개를 추출)
												//예시 : H_NAFM007_NE1 21_G5WIDE[JPN_JP]AVNT_GIMS2CB000_211019 1350 (36페이지 이미지 2개 중 빨간박스 되있는것은 일러스트이며 하나는 빨간박스 X이면서 이미지 파일)
												//예시 : H_NAFM007_NE1 21_G5WIDE[JPN_JP]AVNT_GIMS2CB000_211019 1350 (94페이지  빨간박스가 이미지 기준으로 되어 있지 않고 해당 페이지에 텍스트에 되어 있지만 이미지 파일3개를 추출함)
												File file = new File(filePath[i].getParentFile() + "\\img\\" + filePath[i].getName() + "(" + currentPage +")" + imageCount++ +".png");
												
								                ImageIO.write(((org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject)o).getImage(), "png", file);
								                
								            }
								        }
									    	//주석을 그려주는 부분
											//PDAnnotationText text = new PDAnnotationText();
											
										    //rectangle.setColor(color);
										    //rectangle.setBorderStyle(thickness);
											//System.out.println("rectangle == : " + rectangle.getAnnotationName());
											PDRectangle stampO = new PDRectangle();
											stampO.setLowerLeftX(1);
											stampO.setLowerLeftY(1);
											stampO.setUpperRightX(50);
											stampO.setUpperRightY(50);
											
											PDAnnotationRubberStamp rubberStamp = new PDAnnotationRubberStamp();
											rubberStamp.setName(PDAnnotationRubberStamp.NAME_TOP_SECRET);
											rubberStamp.setRectangle(stampO);
											rubberStamp.setModifiedDate(Calendar.getInstance());
											rubberStamp.setTitlePopup(System.getProperty("user.name"));
											rubberStamp.setSubject("D-dtp");
											
											int lowerLeftX = 0;
											int lowerLeftY = (int) page.getMediaBox().getHeight();
											int formWidth = 85;
											int formHeight = 29;
											int imgWidth = 85;
											int imgHeight = 29;
																						
											PDRectangle rect = new PDRectangle();
											//System.out.println(lowerLeftX + "," + (lowerLeftY - formHeight) + "," + (lowerLeftX + formWidth) + "," + lowerLeftY);
											rect.setLowerLeftX(lowerLeftX);
											rect.setLowerLeftY(lowerLeftY - formHeight);
											rect.setUpperRightX(lowerLeftX + formWidth);
											rect.setUpperRightY(lowerLeftY);	
											
											// Create a PDFormXObject
											PDFormXObject form = new PDFormXObject(document);
											form.setResources(new PDResources());
											form.setBBox(rect);
											form.setFormType(1);
											
											// adjust the image to the target rectangle and add it to the stream
											try (OutputStream os = form.getStream().createOutputStream()) {
												drawXObject(xImage, form.getResources(), os, lowerLeftX, lowerLeftY - formHeight, imgWidth, imgHeight);
											}
																						
//										    text.setContents("Rectangle");
//										    text.setRectangle(points);
//										    //rectangle.setColor(color);
//										    text.getCOSObject().setString(COSName.T, "has a Rectangle");
											PDAppearanceStream myDic = new PDAppearanceStream(form.getCOSObject());
											PDAppearanceDictionary appearance = new PDAppearanceDictionary(new COSDictionary());
											appearance.setNormalAppearance(myDic);
											rubberStamp.setAppearance(appearance);
											rubberStamp.setRectangle(rect);
//											rubberStamp.setTitlePopup("스탬프");
										
											// add the new RubberStamp to the document
											if ( flag == false )
											{
												annotations.add(rubberStamp);
												flag = true;
											}
											
											
										    //annotations.add(text);
										    System.out.println("Finsh.");		
										}
									}

								}
								
							}
						}

					}

					imageCount = 1;
					
				}    
			    // Save the file					
				//document.save(filePath[i].getName());					
				//document.save("PDF출력물\\" + FilenameUtils.getBaseName(filePath[i].getName()) + "(OUT)" + ".pdf");
				//document.save(FilenameUtils.getBaseName(filePath[i].getName()) + "(OUT)" + ".pdf");				
				
				int dot = filePath[i].getAbsolutePath().lastIndexOf(".");
				
				String fileName = filePath[i].getPath().substring(0,dot);
				
				document.save(fileName + "_Stamp" + ".pdf");
			    // Close the document
			    document.close();		
			    valueTF = true;
			}		
		} catch (IOException e) {	
			e.printStackTrace();
			e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			JOptionPane.showMessageDialog(null, "이미지 파일이 존재 하지 않습니다.", "Error", JOptionPane.ERROR_MESSAGE);
		} 
		
		return valueTF;

	}
	
	public String getTime(){
		long time = System.currentTimeMillis();
		SimpleDateFormat dayTime = new SimpleDateFormat("HHmmssSSS");
		String strTime = dayTime.format(new Date(time)); 
		return strTime;
	}
	
	private void drawXObject( PDImageXObject xobject, PDResources resources, OutputStream os, float x, float y, float width, float height ) throws IOException
	{
		// This is similar to PDPageContentStream.drawXObject()
		COSName xObjectId = resources.add(xobject);

		appendRawCommands( os, SAVE_GRAPHICS_STATE );
		appendRawCommands( os, formatDecimal.format( width ) );
		appendRawCommands( os, SPACE );
		appendRawCommands( os, formatDecimal.format( 0 ) );
		appendRawCommands( os, SPACE );
		appendRawCommands( os, formatDecimal.format( 0 ) );
		appendRawCommands( os, SPACE );
		appendRawCommands( os, formatDecimal.format( height ) );
		appendRawCommands( os, SPACE );
		appendRawCommands( os, formatDecimal.format( x ) );
		appendRawCommands( os, SPACE );
		appendRawCommands( os, formatDecimal.format( y ) );
		appendRawCommands( os, SPACE );
		appendRawCommands( os, CONCATENATE_MATRIX );
		appendRawCommands( os, SPACE );
		appendRawCommands( os, "/" );
		appendRawCommands( os, xObjectId.getName() );
		appendRawCommands( os, SPACE );
		appendRawCommands( os, XOBJECT_DO );
		appendRawCommands( os, SPACE );
		appendRawCommands( os, RESTORE_GRAPHICS_STATE );
	}

	private void appendRawCommands(OutputStream os, String commands) throws IOException
	{
		os.write( commands.getBytes(StandardCharsets.ISO_8859_1));
	}
	
}
