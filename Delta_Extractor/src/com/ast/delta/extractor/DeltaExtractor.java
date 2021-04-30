package com.ast.delta.extractor;

import java.awt.Button;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class DeltaExtractor {
	
	public static String selectItem = null;
	public static String selectItem2 = null;
	public static String txtPath = null;
	public static String txtPath2 = null;
	public static String clickPath = null;
	public static String clickPathS = null;
	public static String xsltPathDeltaStatic = null;
	
	public static void DeltaExtractor(){
		Frame f = new Frame("DeltaExtractor");
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
		
		File zzzPath = new File("zzz").getAbsoluteFile(); 	
		System.out.println("zzzPath =====" + zzzPath);
/*		int tempidx = zzzPath.indexOf(0);
		int tempidx2 = zzzPath.lastIndexOf("\\");				
		String zzzPathSplite = zzzPath.substring(0, tempidx2);
		*/
		xsltPathDeltaStatic = zzzPath.toString();		
		System.out.println("xsltPathDeltaStatic ===" + xsltPathDeltaStatic);		
		String xsltPathDelta = DeltaExtractor.getResourceAsFile("delta.xsl").getPath();
		String xsltPathFlattenA =  DeltaExtractor.getResourceAsFile("flattenA.xsl").getPath();
		String xsltPathFlattenB = DeltaExtractor.getResourceAsFile("flattenB.xsl").getPath();		
		String xsltMergeA = DeltaExtractor.getResourceAsFile("mergeA.xsl").getPath(); 		
		String xsltMergeB = DeltaExtractor.getResourceAsFile("mergeB.xsl").getPath();		
		String xsltUniqueA = DeltaExtractor.getResourceAsFile("uniqueA.xsl").getPath(); 
		String dummy = DeltaExtractor.getResourceAsFile("dummy.xml").getPath();
		
		Font statusFont = new Font("TimesRoman", Font.BOLD, 12);
		Label statusLabel = new Label();
		statusLabel.setAlignment(Label.CENTER);
		statusLabel.setSize(60, 60);		
		statusLabel.setLocation(30, 130);				
		statusLabel.setFont(statusFont);		
		
		ImageIcon img = new ImageIcon(DeltaExtractor.getResourceAsFile("Ast.jpg").getPath());		
		JLabel lbImage1 = new JLabel(img);
		lbImage1.setSize(430, 50);
		lbImage1.setLocation(15, 40);
		
		JLabel firstQ = new JLabel();
		firstQ.setText("Before");
		firstQ.setFont(new Font("Serif", Font.BOLD, 16));
		firstQ.setForeground(Color.DARK_GRAY);
		firstQ.setSize(50, 40);
		firstQ.setLocation(25, 105);
		
		TextField txtF = new TextField();
		txtF.setSize(320, 22);
		txtF.setLocation(80, 115);
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
						//FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel File", "xml");
						//fileChooser.addChoosableFileFilter(filter);
						fileChooser.setCurrentDirectory(new File(txtF.getText()));
						fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						//fileChooser.setMultiSelectionEnabled(true);
						int returnVal = fileChooser.showOpenDialog(null);					

						if (returnVal == JFileChooser.APPROVE_OPTION) {
							selectItem = fileChooser.getSelectedFile().getAbsolutePath();
							txtPath = txtF.getText();
							System.out.println("txtPath =====" + txtPath);
							fileChooser.setCurrentDirectory(new File(txtF.getText()));
							txtF.setText(selectItem);
					}	
				}
				
			}
		});
		
		Button pathBtn = new Button("...");
		pathBtn.setSize(22, 22);
		pathBtn.setLocation(400, 115);

		pathBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
								
				if ((Button) obj == pathBtn) {					
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setAcceptAllFileFilterUsed(false);
					//FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel File", "xls");
					//fileChooser.addChoosableFileFilter(filter);
					fileChooser.setCurrentDirectory(new File(txtF.getText()));
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
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
		
		
		JLabel secondQ = new JLabel();
		secondQ.setText("After");
		secondQ.setFont(new Font("Serif", Font.BOLD, 16));
		secondQ.setForeground(Color.DARK_GRAY);
		secondQ.setSize(40, 50);
		secondQ.setLocation(25, 130);
		
		
		TextField txtF2 = new TextField();
		txtF2.setSize(320, 22);
		txtF2.setLocation(80, 145);
		txtF2.addKeyListener(new KeyListener() {
			
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
					
					if(txtF2.getText().isEmpty()){
						return;
					}else{
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setAcceptAllFileFilterUsed(false);
						//FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel File", "xml");
						//fileChooser.addChoosableFileFilter(filter);
						fileChooser.setCurrentDirectory(new File(txtF2.getText()));
						fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						//fileChooser.setMultiSelectionEnabled(true);
						int returnVal = fileChooser.showOpenDialog(null);					

						if (returnVal == JFileChooser.APPROVE_OPTION) {
							selectItem2 = fileChooser.getSelectedFile().getAbsolutePath();
							txtPath2 = txtF2.getText();														
							txtF2.setText(selectItem2);
						}
					}
				}
				
			}
		});
		
		Button pathBtn2 = new Button("...");
		pathBtn2.setSize(22, 22);
		pathBtn2.setLocation(400, 145);
		pathBtn2.setEnabled(true);
		pathBtn2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
								
				if ((Button) obj == pathBtn2) {					
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setAcceptAllFileFilterUsed(false);
					//FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel File", "xls");
					//fileChooser.addChoosableFileFilter(filter);
					fileChooser.setCurrentDirectory(new File(txtF2.getText()));
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					//fileChooser.setMultiSelectionEnabled(true);
					int returnVal = fileChooser.showOpenDialog(null);					

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						selectItem2 = fileChooser.getSelectedFile().getAbsolutePath();	
						txtPath2 = txtF2.getText();
						txtF2.setText(selectItem2);

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
					 JOptionPane.showMessageDialog(null, " Please \n Select Before");
					 return;
				 }else if(txtF2.getText().isEmpty()){
					 JOptionPane.showMessageDialog(null, " Please \n Select After");
					 return;
				 }
				 
				if(selectItem == null){
					JOptionPane.showMessageDialog(null, "Use the ... button to select.");
					statusLabel.setText("");
					return;
				}
					
				//if ((Button) obj == clickBtn) {				
					String filePathS = txtF.getText();					
					int idx = filePathS.indexOf(0);
					int idx2 = filePathS.lastIndexOf("\\");				
					String filePathS2 = filePathS.substring(0, idx2);	
					
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setAcceptAllFileFilterUsed(false);
					//FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel File", "xls");
					//fileChooser.addChoosableFileFilter(filter);
					fileChooser.setCurrentDirectory(new File(filePathS2));
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					//fileChooser.setMultiSelectionEnabled(true);
					int returnVal = fileChooser.showSaveDialog(null);					

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						clickPath = fileChooser.getSelectedFile().getAbsolutePath();
						statusLabel.setText("Extracting...");
						Desktop deskTop = Desktop.getDesktop();						
						
						try {		
							//old XMLs					
							//System.out.println("zzzPath ===" + zzzPathSplite + "\\mergedA.xml");
							transform(dummy, xsltMergeA, zzzPath + "\\mergedA.xml"); 						
							transform(zzzPath + "\\mergedA.xml", xsltPathFlattenA, zzzPath + "\\flattenA.xml");
							transform(zzzPath + "\\flattenA.xml", xsltUniqueA, zzzPath + "\\uniqueA.xml");
							//new XMLs
							transform(dummy, xsltMergeB, zzzPath + "\\mergedB.xml");						
							transform(zzzPath + "\\mergedB.xml", xsltPathFlattenB, zzzPath + "\\flattenB.xml");
		 					transform(zzzPath + "\\flattenB.xml", xsltPathDelta, clickPath + "\\delta.xml");				
		 					statusLabel.setText("");
		 					JOptionPane.showMessageDialog(null, "Successfully completed.");												
							deleteDirectory(new File(filePathS2 + "\\zzz"));
							//deskTop.open(new File(clickPath + "\\delta.xml"));
							
						} catch (Exception e2) {
							e2.printStackTrace();
						}
						return;
						
					}
				//}

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
				
		f.add(firstQ);
		f.add(txtF);
		f.add(pathBtn);
		f.add(secondQ);
		f.add(txtF2);
		f.add(pathBtn2);
		f.add(lbImage1);
		f.add(statusLabel);
		f.add(clickBtn);
		f.add(exitBtn);
		f.setVisible(true);

	}
	
	public static void transform(String sourcePath, String xsltPath, String resultDir) {
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(new File(xsltPath)));
			transformer.setOutputProperty(OutputKeys.ENCODING,"utf-8");
			transformer.setParameter("beforePath", selectItem);			
			transformer.setParameter("afterPath", selectItem2);
			transformer.setParameter("zzzPath", xsltPathDeltaStatic);
			// transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			// transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");		
			transformer.transform(new StreamSource(new File(sourcePath)), new StreamResult(new File(resultDir)));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
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
	
	public static boolean deleteDirectory(File path){ 
		if(!path.exists()){			
			return false; 
			} 
		File[] files = path.listFiles(); 
		for (File file : files){
			if (file.isDirectory()){
				deleteDirectory(file); 
				}else{ 
					file.delete(); 
				} 
			} return path.delete(); 
		}
	
	public static void main(String[] args) {
		DeltaExtractor();
	}
}
