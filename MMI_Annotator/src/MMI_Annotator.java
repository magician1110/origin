import java.io.*;
import java.util.*;
import java.text.NumberFormat;
import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSFloat;
import org.apache.pdfbox.cos.COSInteger;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSNumber;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.common.COSObjectable;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationRubberStamp;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationTextMarkup;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceDictionary;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;

public class MMI_Annotator extends PDFStreamEngine
{
	private static final String SAVE_GRAPHICS_STATE = "q\n";
	private static final String RESTORE_GRAPHICS_STATE = "Q\n";
	private static final String CONCATENATE_MATRIX = "cm\n";
	private static final String XOBJECT_DO = "Do\n";
	private static final String SPACE = " ";
	private static File[] staticFiles = null;
	private static final NumberFormat formatDecimal = NumberFormat.getNumberInstance( Locale.US );

	public void doIt(String[] args) throws IOException
	{
		JFrame frame = new JFrame("PDF Chooser");		
		
		JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		chooser.setCurrentDirectory(new File("/"));
		chooser.setAcceptAllFileFilterUsed(true);
		chooser.setDialogTitle("PDF Chooser");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setMultiSelectionEnabled(true);		
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF file", "pdf");
		chooser.setFileFilter(filter);	
		
		frame.add(chooser);
		frame.setAlwaysOnTop(true);
		
		int returnVal = chooser.showSaveDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			File[] files = chooser.getSelectedFiles();	
			staticFiles = files;
		}else{
			System.exit(0);
		}
		
		PDDocument document;
		int numPages;
		//BufferedImage image = ImageIO.read(this.getClass().getResource("stamp.jpg"));
		BufferedImage image = ImageIO.read(this.getClass().getResource("G-sw_general.jpg"));
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image,"jpg", outputStream);
		byte[] imageByte = outputStream.toByteArray();

		for (int idx = 0; idx < staticFiles.length; idx++)
		{
			document = null;
			String fileName = staticFiles[idx].toString();
			try
			{
				document = PDDocument.load( new File(fileName) );
				PDImageXObject xImage = PDImageXObject.createFromByteArray(document, imageByte, null);
				numPages = document.getNumberOfPages();

				Object token = null;
				List<Object> tokens = null;

				for (int n = 0; n < numPages; n++)
				{
					PDPage page = document.getPage(n);
					boolean flag = false;

					COSNumber c, m, y, k, x1, y1, w, h;
					double cd, md, yd, kd, x1d, y1d, wd, hd;
					float posXInit, posXEnd, posYInit, posYEnd, height;

					List<PDAnnotation> annotations = page.getAnnotations();
					PDColor colorG = new PDColor(new float[]{0, 170/255f, 0}, PDDeviceRGB.INSTANCE);
					PDColor colorO = new PDColor(new float[]{255/255f, 143/255f, 23/255f}, PDDeviceRGB.INSTANCE);

					PDFStreamParser parser = new PDFStreamParser(page);
					parser.parse();
					tokens = parser.getTokens();

					for (int j = 0; j < tokens.size(); j++) 
					{
						token = tokens.get(j);
						if (token instanceof Operator)
						{
							Operator op = (Operator) token;
							System.out.println("op ====" + op);							
							if (op.getName().equals("K"))
							{
								c = (COSNumber) tokens.get(j-4);
								m = (COSNumber) tokens.get(j-3);
								y = (COSNumber) tokens.get(j-2);
								k = (COSNumber) tokens.get(j-1);

								kd = k.doubleValue();
								yd = y.doubleValue();
								md = m.doubleValue();
								cd = c.doubleValue();

								if ( cd == 0.55 && md == 0 && yd == 0.75 && kd == 0 )
								{
									h = (COSNumber) tokens.get(j+1);
									hd = h.doubleValue() - 4;

									while (true)
									{
										j = j - 1;
										token = tokens.get(j);
										if ( token instanceof Operator )
										{
											op = (Operator) token;
											if (op.getName().equals("q") || op.getName().equals("Q"))
											{
												break;
											}
										}
									}

									while (true)
									{
										j = j + 1;
										token = tokens.get(j);
										if ( token instanceof Operator )
										{
											op = (Operator) token;
											if (op.getName().equals("cm"))
											{
												x1 = (COSNumber) tokens.get(j-2);
												y1 = (COSNumber) tokens.get(j-1);
												break;
											}
										}
									}

									while (true)
									{
										j = j + 1;
										token = tokens.get(j);
										if ( token instanceof Operator )
										{
											op = (Operator) token;
											if (op.getName().equals("l"))
											{
												w = (COSNumber) tokens.get(j-2);
												wd = w.doubleValue();
												break;
											}
										}
									}

									PDAnnotationTextMarkup markup = null;
									markup = new PDAnnotationTextMarkup(PDAnnotationTextMarkup.SUB_TYPE_HIGHLIGHT);

									x1d = x1.doubleValue();
									y1d = y1.doubleValue() - 5;

									posXInit = (float) x1d;
									posXEnd = (float) x1d + (float) wd;
									posYInit = (float) y1d;
									posYEnd = (float) y1d;
									height = (float) hd;

									PDRectangle position = new PDRectangle();
									position.setLowerLeftX(posXInit);
									position.setLowerLeftY(posYEnd);
									position.setUpperRightX(posXEnd);
									position.setUpperRightY(posYEnd + height);
									markup.setRectangle(position);

									float quadPoints[] = {posXInit, posYEnd + height + 2, posXEnd, posYEnd + height + 2, posXInit, posYInit - 2, posXEnd, posYEnd - 2};
									markup.setQuadPoints(quadPoints);
									markup.setColor(colorG);
									markup.setModifiedDate(Calendar.getInstance());
									annotations.add(markup);
								}
								else if ( cd == 0 && md == 0.44 && yd == 0.91 && kd == 0 )
								{
									h = (COSNumber) tokens.get(j+1);
									hd = h.doubleValue() - 4;

									while (true)
									{
										j = j - 1;
										token = tokens.get(j);
										if ( token instanceof Operator )
										{
											op = (Operator) token;
											if (op.getName().equals("q") || op.getName().equals("Q"))
											{
												break;
											}
										}
									}

									while (true)
									{
										j = j + 1;
										token = tokens.get(j);
										if ( token instanceof Operator )
										{
											op = (Operator) token;
											if (op.getName().equals("cm"))
											{
												x1 = (COSNumber) tokens.get(j-2);
												y1 = (COSNumber) tokens.get(j-1);
												break;
											}
										}
									}

									while (true)
									{
										j = j + 1;
										token = tokens.get(j);
										if ( token instanceof Operator )
										{
											op = (Operator) token;
											if (op.getName().equals("l"))
											{
												w = (COSNumber) tokens.get(j-2);
												wd = w.doubleValue();
												break;
											}
										}
									}

									PDAnnotationTextMarkup markup = null;
									markup = new PDAnnotationTextMarkup(PDAnnotationTextMarkup.SUB_TYPE_HIGHLIGHT);

									x1d = x1.doubleValue();
									y1d = y1.doubleValue() - 5;

									posXInit = (float) x1d;
									posXEnd = (float) x1d + (float) wd;
									posYInit = (float) y1d;
									posYEnd = (float) y1d;
									height = (float) hd;

									PDRectangle position = new PDRectangle();
									position.setLowerLeftX(posXInit);
									position.setLowerLeftY(posYEnd);
									position.setUpperRightX(posXEnd);
									position.setUpperRightY(posYEnd + height);
									markup.setRectangle(position);

									float quadPoints[] = {posXInit, posYEnd + height + 2, posXEnd, posYEnd + height + 2, posXInit, posYInit - 2, posXEnd, posYEnd - 2};
									markup.setQuadPoints(quadPoints);
									markup.setColor(colorO);
									markup.setModifiedDate(Calendar.getInstance());
									markup.setTitlePopup("MMI");
									annotations.add(markup);

									// ??? ?????? ????
									PDRectangle stampO = new PDRectangle();
									stampO.setLowerLeftX(1);
									stampO.setLowerLeftY(1);
									stampO.setUpperRightX(61);
									stampO.setUpperRightY(61);

									PDAnnotationRubberStamp rubberStamp = new PDAnnotationRubberStamp();
									rubberStamp.setName(PDAnnotationRubberStamp.NAME_TOP_SECRET);
									rubberStamp.setRectangle(stampO);
									rubberStamp.setModifiedDate(Calendar.getInstance());
									rubberStamp.setSubject("G-sw_general");
									
									int lowerLeftX = 495;
									int lowerLeftY = 1;
									int formWidth = 100;
									int formHeight = 29;
									int imgWidth = 100;
									int imgHeight = 29;

									PDRectangle rect = new PDRectangle();
									rect.setLowerLeftX(lowerLeftX);
									rect.setLowerLeftY(lowerLeftY);
									rect.setUpperRightX(lowerLeftX + formWidth);
									rect.setUpperRightY(lowerLeftY + formHeight);									
									
									// Create a PDFormXObject
									PDFormXObject form = new PDFormXObject(document);
									form.setResources(new PDResources());
									form.setBBox(rect);
									form.setFormType(1);

									// adjust the image to the target rectangle and add it to the stream
									try (OutputStream os = form.getStream().createOutputStream())
									{
										drawXObject(xImage, form.getResources(), os, lowerLeftX, lowerLeftY, imgWidth, imgHeight);
									}
									
									PDAppearanceStream myDic = new PDAppearanceStream(form.getCOSObject());
									PDAppearanceDictionary appearance = new PDAppearanceDictionary(new COSDictionary());
									appearance.setNormalAppearance(myDic);
									rubberStamp.setAppearance(appearance);
									rubberStamp.setRectangle(rect);
									rubberStamp.setTitlePopup("스탬프");

									// add the new RubberStamp to the document
									if ( flag == false )
									{
										//annotations.add(rubberStamp);
										flag = true;
									}								
								}
							}
						}
					}

					// remove background-color
					List<Object> newTokens = new ArrayList<>();
					for (int j = 0; j < tokens.size(); j++)
					{
						token = tokens.get(j);
						newTokens.add(token);

						if ( token instanceof Operator )
						{
							Operator op = (Operator) token;
							if (op.getName().equals("K"))
							{
								if (j > 3)
								{
									c = (COSNumber) tokens.get(j-4);
									m = (COSNumber) tokens.get(j-3);
									y = (COSNumber) tokens.get(j-2);
									k = (COSNumber) tokens.get(j-1);

									if (c instanceof COSFloat && m instanceof COSInteger && y instanceof COSFloat && k instanceof COSInteger)
									{
										if ( ((COSNumber)c).floatValue() == 0.55f && ((COSNumber)m).intValue() == 0 && ((COSNumber)y).floatValue() == 0.75f && ((COSNumber)k).intValue() == 0 )
										{
											int K_index = j;
											while (true)
											{
												newTokens.remove(newTokens.size() - 1);
												j = j - 1;
												token = tokens.get(j);
												if ( token instanceof Operator )
												{
													op = (Operator) token;
													if (op.getName().equals("q"))
													{
														break;
													}
												}
											}
											
											j = K_index;

											while (true)
											{
												j = j + 1;
												token = tokens.get(j);
												if ( token instanceof Operator )
												{
													op = (Operator) token;
													if (op.getName().equals("Q"))
													{
														break;
													}
												}
											}
										}
									}
									else if (c instanceof COSInteger && m instanceof COSFloat && y instanceof COSFloat && k instanceof COSInteger)
									{
										if ( ((COSNumber)c).intValue() == 0 && ((COSNumber)m).floatValue() == 0.44f && ((COSNumber)y).floatValue() == 0.91f && ((COSNumber)k).intValue() == 0 )
										{
											int K_index = j;
											while (true)
											{
												newTokens.remove(newTokens.size() - 1);
												j = j - 1;
												token = tokens.get(j);
												if ( token instanceof Operator )
												{
													op = (Operator) token;
													if (op.getName().equals("q"))
													{
														break;
													}
												}
											}

											j = K_index;

											while (true)
											{
												j = j + 1;
												token = tokens.get(j);
												if ( token instanceof Operator )
												{
													op = (Operator) token;
													if (op.getName().equals("Q"))
													{
														break;
													}
												}
											}
										}
									}
								}
							}
						}
					}
					PDStream updatedStream = new PDStream(document);
					OutputStream out = updatedStream.createOutputStream(COSName.FLATE_DECODE);
					ContentStreamWriter tokenWriter = new ContentStreamWriter(out);
					tokenWriter.writeTokens(newTokens);
					page.setContents(updatedStream);
					out.close();
				}
			}
			finally
			{
				if( document != null )
				{
					int dot = fileName.lastIndexOf(".");
					String filename = fileName.substring(0,dot);
					File file1 = new File(filename + "_MMI" + ".pdf");
					document.save(file1);

					// remove all annotations
					for ( PDPage page : document.getPages() )
					{
						List<PDAnnotation> annotations = new ArrayList<>();
						List<COSObjectable> objectsToRemove = new ArrayList<>();

						annotations = page.getAnnotations();
						for (PDAnnotation annotation : annotations)
						{
							if ( (!"Link".equals(annotation.getSubtype())) )
							{
								objectsToRemove.add(annotation);
							}
						}
						annotations.removeAll(objectsToRemove);
					}
					File file2 = new File(filename + "_Clean" + ".pdf");
					document.save(file2);
					document.close();
				}
			}
			JOptionPane.showMessageDialog(frame, "Successfully Annotated.");	
		}	
		System.exit(0);		
		
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

	public static void main( String[] args ) throws IOException
	{
		MMI_Annotator rubberStamp = new MMI_Annotator();
		rubberStamp.doIt(args);
	}
}