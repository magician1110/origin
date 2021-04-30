package com.ast.query.View;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.ast.query.Controller.Excel;
import com.ast.query.Controller.Pdf;
import com.ast.query.Controller.Xml;
import com.ast.query.Model.Root;
import com.sun.glass.events.KeyEvent;

//import com.ast.delta.extractor.DeltaExtractor;

public class GuiManager {
	
	public static String selectItem = null;	
	public static String txtPath = null;
	public static String clickPath = null;
	public static String clickPathS = null;
	
	public static void GuiManager(){
		Frame f = new Frame("Query Builder");
		f.setSize(450, 230); // 500, 440
		f.setLocation(700, 200);
		f.setLayout(null);
		f.setResizable(true);
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		//filePathS2 + "\\query2XML.xml"clickPathS
/*		String output = "C:\\QueryBuilderTemp\\output.xml";
		String simplified = "C:\\QueryBuilderTemp\\simplified.xml";
		String combined = "C:\\QueryBuilderTemp\\combined.xml";
		String ranged = "C:\\QueryBuilderTemp\\ranged.xml";	*/	
		
		String simplify = com.ast.query.View.GuiManager.getResourceAsFile("simplify.xsl").getPath();
		String combine = com.ast.query.View.GuiManager.getResourceAsFile("combine.xsl").getPath();
		String range = com.ast.query.View.GuiManager.getResourceAsFile("range.xsl").getPath(); 
		
		Font statusFont = new Font("TimesRoman", Font.BOLD, 12);
		Label statusLabel = new Label();
		statusLabel.setAlignment(Label.CENTER);
		statusLabel.setSize(60, 60);		
		statusLabel.setLocation(50, 170);				
		statusLabel.setFont(statusFont);	
		
		ImageIcon img = new ImageIcon(com.ast.query.View.GuiManager.getResourceAsFile("Ast.jpg").getPath());
		JLabel lbImage1 = new JLabel(img);
		lbImage1.setSize(430, 50);
		lbImage1.setLocation(15, 40);
		
		JLabel firstQ = new JLabel();
		firstQ.setText("Select PDF: ");
		firstQ.setFont(new Font("Serif", Font.BOLD, 16));
		firstQ.setForeground(Color.DARK_GRAY);
		firstQ.setSize(85, 40);
		firstQ.setLocation(25, 105);
		
		TextField txtF = new TextField();
		txtF.setSize(295, 22);
		txtF.setLocation(115, 115);
		txtF.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(java.awt.event.KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(java.awt.event.KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(java.awt.event.KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){

						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setAcceptAllFileFilterUsed(false);
						FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF File", "pdf");
						fileChooser.addChoosableFileFilter(filter);
						fileChooser.setCurrentDirectory(new File(txtF.getText()));
						fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
						//fileChooser.setMultiSelectionEnabled(true);
						int returnVal = fileChooser.showOpenDialog(null);					

						if (returnVal == JFileChooser.APPROVE_OPTION) {
							selectItem = fileChooser.getSelectedFile().getAbsolutePath();							
							txtPath = txtF.getText();
							
							fileChooser.setCurrentDirectory(new File(txtF.getText()));
							txtF.setText(selectItem);
					}	
				}
				
			}
		});
		
		Button pathBtn = new Button("...");
		pathBtn.setSize(22, 22);
		pathBtn.setLocation(410, 115);

		pathBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
								
				if ((Button) obj == pathBtn) {					
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setAcceptAllFileFilterUsed(false);
					FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF File", "pdf");
					fileChooser.addChoosableFileFilter(filter);
					fileChooser.setCurrentDirectory(new File(txtF.getText()));
					fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					//fileChooser.setMultiSelectionEnabled(true);
					int returnVal = fileChooser.showOpenDialog(null);					

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						selectItem = fileChooser.getSelectedFile().getAbsolutePath();						
						txtPath = txtF.getText();
						txtF.setText(selectItem);
					}
				}
			}
		});

		Button clickBtn = new Button();		
		clickBtn = new Button("Click");
		clickBtn.setSize(70, 30);
		clickBtn.setLocation(150, 180);
		
		clickBtn.addActionListener(new ActionListener() {										
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				
				 if(txtF.getText().isEmpty()){
					 JOptionPane.showMessageDialog(null, " Please \n Select PDF File");
					 statusLabel.setText("");
					 return;
				 }
					
				if(selectItem == null){
					JOptionPane.showMessageDialog(null, "The PDF path is incorrect.");
					statusLabel.setText("");
					return;
				}										
					String filePathS = txtF.getText();								
					//C:\base   
					//C:\modify
/*					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setAcceptAllFileFilterUsed(false);
					fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
					fileChooser.setFileFilter(new FileNameExtensionFilter("xlsx file", "xlsx"));
					fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);*/
					
		//			int returnVal = fileChooser.showSaveDialog(null);					

		//			if (returnVal == JFileChooser.APPROVE_OPTION) {
						try {
/*							clickPath = fileChooser.getSelectedFile().getAbsolutePath();
							//clickPathS = clickPath.					
							if(!clickPath.endsWith("xlsx")){
								clickPath += ".xlsx";
							}else if(clickPath.length() == 0){
								JOptionPane.showMessageDialog(null, "You have not Path.");
								statusLabel.setText("");
								return;
							}*/
							statusLabel.setText("Working...");
							Pdf pdf = new Pdf();
							Xml xmlData = new Xml();
							Excel excel = new Excel();
						
							File fileCreate = new File("C:\\QueryBuilderTemp");
							if(fileCreate.exists()){
								deleteFile(fileCreate.getPath());
								fileCreate.mkdir();
							}else if(!fileCreate.exists()){
								fileCreate.mkdir();								
							}
							//존재하지 않아서 파일을 만들어서 오류인듯
							pdf.Manager();   //"C:\\프로젝트\\QueryBuilder\\QueryTable(필요자료)\\query3XML.xml"
							transform("C:\\QueryBuilderTemp\\output.xml", simplify, "C:\\QueryBuilderTemp\\simplified.xml");
							transform("C:\\QueryBuilderTemp\\simplified.xml", combine, "C:\\QueryBuilderTemp\\combined.xml");
							transform("C:\\QueryBuilderTemp\\combined.xml", range, "C:\\QueryBuilderTemp\\query3XML.xml");
							Root tocTest = xmlData.xmlPaser();																												
							excel.excelManger(tocTest);	 
							deleteFile(fileCreate.getPath());
							//Desktop deskTop = Desktop.getDesktop();																				 									
		 					statusLabel.setText("");
		 					JOptionPane.showMessageDialog(null, "Successfully completed.");								
						} catch (Exception e2) {
							e2.printStackTrace();
						}
						return;						
				//	}	
			}			
		});
		
		Button exitBtn = new Button();
		exitBtn = new Button("Exit");
		exitBtn.setSize(70, 30);
		exitBtn.setLocation(230, 180);
		exitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		f.add(lbImage1);
		f.add(firstQ);
		f.add(txtF);
		f.add(pathBtn);
		f.add(clickBtn);
		f.add(exitBtn);
		f.add(statusLabel);
		f.setVisible(true);
				
	}
	
	public static void transform(String sourcePath, String xsltPath, String resultDir) {
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(new File(xsltPath)));
			transformer.setOutputProperty(OutputKeys.ENCODING,"utf-8");
/*			transformer.setParameter("beforePath", selectItem);			
			transformer.setParameter("afterPath", selectItem2);
			transformer.setParameter("zzzPath", xsltPathDeltaStatic);*/
			// transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			// transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");		
			transformer.transform(new StreamSource(new File(sourcePath)), new StreamResult(new File(resultDir)));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean deleteFile(String delTarget) {
		  File delDir = new File(delTarget);
		  
		  if(delDir.isDirectory()) {
		   File[] allFiles = delDir.listFiles();
		   
		   for(File delAllDir : allFiles) {
		    deleteFile(delAllDir.getAbsolutePath());
		   }
		  }
		  
		  return delDir.delete();
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
