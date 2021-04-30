package com.ast.diff.View;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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

import com.ast.diff.Controller.DiffController;
import com.deltaxml.api.NoLicenseInstalledException;
import com.deltaxml.core.ComparatorInstantiationException;
import com.deltaxml.core.FilterClassInstantiationException;
import com.deltaxml.core.FilterConfigurationException;
import com.deltaxml.core.ParserInstantiationException;
import com.deltaxml.core.PipelinedComparatorError;
import com.deltaxml.cores9api.ComparisonException;
import com.deltaxml.cores9api.DynamicPDFormatException;
import com.deltaxml.cores9api.FilterProcessingException;
import com.deltaxml.cores9api.LicenseException;
import com.deltaxml.cores9api.PDAdvancedConfigException;
import com.deltaxml.cores9api.PDFilterConfigurationException;
import com.deltaxml.cores9api.PipelineLoadingException;
import com.deltaxml.cores9api.PipelineSerializationException;
import com.deltaxml.cores9api.StaticPDFormatException;

public class DiffView {

    public static String selectItem = null;
    public static String selectItem2 = null;
	String txtPath = null;
	String txtPath2 = null;
	public static String clickPath = null;
	String mergeA = com.ast.diff.View.DiffView.getResourceAsFile("mergeA.xsl").getPath();
	String hash = com.ast.diff.View.DiffView.getResourceAsFile("hash.xsl").getPath();
	String mergeB = com.ast.diff.View.DiffView.getResourceAsFile("mergeB.xsl").getPath(); 			
	String clean = com.ast.diff.View.DiffView.getResourceAsFile("clean.xsl").getPath();
	String group = com.ast.diff.View.DiffView.getResourceAsFile("group.xsl").getPath();
	String mark = com.ast.diff.View.DiffView.getResourceAsFile("mark.xsl").getPath();
	String extract = com.ast.diff.View.DiffView.getResourceAsFile("extract.xsl").getPath();
	String refine = com.ast.diff.View.DiffView.getResourceAsFile("refine.xsl").getPath();
	String pilcrow = com.ast.diff.View.DiffView.getResourceAsFile("pilcrow.xsl").getPath();
	String untag = com.ast.diff.View.DiffView.getResourceAsFile("untag.xsl").getPath();
	String segment = com.ast.diff.View.DiffView.getResourceAsFile("segment.xsl").getPath();
	String tidy = com.ast.diff.View.DiffView.getResourceAsFile("tidy.xsl").getPath();
	String separate = com.ast.diff.View.DiffView.getResourceAsFile("separate.xsl").getPath();			
	String count = com.ast.diff.View.DiffView.getResourceAsFile("count.xsl").getPath();
	String report = com.ast.diff.View.DiffView.getResourceAsFile("report.xsl").getPath();  
	
	
	public void diffViewManager() throws ComparisonException, FilterProcessingException, PipelineLoadingException, PipelineSerializationException, LicenseException, NoLicenseInstalledException, StaticPDFormatException, PDFilterConfigurationException, DynamicPDFormatException, PDAdvancedConfigException, ParserInstantiationException, FilterConfigurationException, ComparatorInstantiationException, FilterClassInstantiationException, IllegalStateException, PipelinedComparatorError, IOException{
		Frame f = new Frame("Diff Counter");
		
		f.setSize(450, 230); // 500, 440
		f.setLocation(700, 200);
		f.setLayout(null);
		f.setResizable(true);
		f.setBackground(Color.RED);		
		
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}			
		});		
		
		Font statusFont = new Font("TimesRoman", Font.TYPE1_FONT, 12);
		Label statusLabel = new Label();
		statusLabel.setAlignment(Label.CENTER);
		statusLabel.setForeground(Color.DARK_GRAY);
		statusLabel.setSize(60, 60);		
		statusLabel.setLocation(50, 170);				
		statusLabel.setFont(statusFont);	
		
		ImageIcon img = new ImageIcon(DiffView.getResourceAsFile("Ast.jpg").getPath());		
		JLabel lbImage1 = new JLabel(img);
		lbImage1.setSize(430, 50);
		lbImage1.setLocation(15, 40);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image iconImg = toolkit.getImage(DiffView.getResourceAsFile("icon.png").getPath());				
		
		JLabel firstQ = new JLabel();
		firstQ.setText("Base");
		firstQ.setFont(new Font("Serif", Font.TYPE1_FONT, 16));
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
			
			@SuppressWarnings("static-access")
			@Override
			public void keyPressed(java.awt.event.KeyEvent e) {
								
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
						JFileChooser fileChooserTxtF = new JFileChooser();
						fileChooserTxtF.setAcceptAllFileFilterUsed(false);
						fileChooserTxtF.setCurrentDirectory(new File(txtF.getText()));
						fileChooserTxtF.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						int returnValTxtF = fileChooserTxtF.showOpenDialog(null);					

						if (returnValTxtF == JFileChooser.APPROVE_OPTION) {
							selectItem = fileChooserTxtF.getSelectedFile().getAbsolutePath();
							txtPath = txtF.getText();														
							txtF.setText(selectItem);
					}	
				}
				
			}
		});
		
		Button pathBtn = new Button("...");
		pathBtn.setSize(22, 22);
		pathBtn.setLocation(400, 115);
		pathBtn.setFont(new Font("Serif", Font.TYPE1_FONT, 16));
		pathBtn.setBackground(Color.LIGHT_GRAY);
		pathBtn.addActionListener(new ActionListener() {

			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
								
				if ((Button) obj == pathBtn) {					
					JFileChooser fileChooserPathBtn = new JFileChooser();
					fileChooserPathBtn.setAcceptAllFileFilterUsed(false);
					//FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel File", "xls");
					//fileChooser.addChoosableFileFilter(filter);
					fileChooserPathBtn.setCurrentDirectory(new File(System.getProperty("user.dir")));
					fileChooserPathBtn.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					//fileChooser.setMultiSelectionEnabled(true);
					int returnValpathBtn = fileChooserPathBtn.showOpenDialog(null);					

					if (returnValpathBtn == JFileChooser.APPROVE_OPTION) {
						selectItem = fileChooserPathBtn.getSelectedFile().getAbsolutePath();
						txtPath = txtF.getText();						
						txtF.setText(selectItem);
					}
				}
			}
		});
		
		JLabel secondQ = new JLabel();
		secondQ.setText("Modify");
		secondQ.setFont(new Font("Serif", Font.TYPE1_FONT, 16));
		secondQ.setForeground(Color.DARK_GRAY);
		secondQ.setSize(50, 50);
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
			
			@SuppressWarnings("static-access")
			@Override
			public void keyPressed(java.awt.event.KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					
					if(txtF2.getText().isEmpty()){
						return;
					}else{
						JFileChooser fileChoosertxtF2 = new JFileChooser();
						fileChoosertxtF2.setAcceptAllFileFilterUsed(false);
						fileChoosertxtF2.setCurrentDirectory(new File(txtF2.getText()));
						fileChoosertxtF2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						int returnValtxtF2 = fileChoosertxtF2.showOpenDialog(null);					

						if (returnValtxtF2 == JFileChooser.APPROVE_OPTION) {
							selectItem2 = fileChoosertxtF2.getSelectedFile().getAbsolutePath();							
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
		pathBtn2.setFont(new Font("Serif", Font.TYPE1_FONT, 16));
		pathBtn2.setBackground(Color.LIGHT_GRAY);
		pathBtn2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
								
				if ((Button) obj == pathBtn2) {					
					JFileChooser fileChooserPathBtn2 = new JFileChooser();
					fileChooserPathBtn2.setAcceptAllFileFilterUsed(false);
					//FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel File", "xls");
					//fileChooser.addChoosableFileFilter(filter);
					fileChooserPathBtn2.setCurrentDirectory(new File(System.getProperty("user.dir")));
					fileChooserPathBtn2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					//fileChooser.setMultiSelectionEnabled(true);
					int returnValPathBtn2 = fileChooserPathBtn2.showOpenDialog(null);					

					if (returnValPathBtn2 == JFileChooser.APPROVE_OPTION) {
						selectItem2 = fileChooserPathBtn2.getSelectedFile().getAbsolutePath();	
						txtPath2 = txtF2.getText();
						System.out.println("pathBtn2 selectItem2 ====" + selectItem2);
						txtF2.setText(selectItem2);

					}
				}
			}
		});
		
		Button clickBtn = new Button("Save");		
		clickBtn.setSize(70, 30);
		clickBtn.setLocation(150, 180);
		clickBtn.setFont(new Font("Serif", Font.TYPE1_FONT, 14));		
		clickBtn.setBackground(Color.LIGHT_GRAY);
		
		clickBtn.addActionListener(new ActionListener() {
			
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				
				if ((Button) obj == clickBtn) {
					if(txtF.getText().isEmpty() || txtF2.getText().isEmpty()){
						JOptionPane.showMessageDialog(null, "Please \n Select TextPath", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}else if(selectItem == null || selectItem2 == null){
						JOptionPane.showMessageDialog(null, "Use the ... button to select.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}	
					
					JFileChooser fileChooserClickBtn = new JFileChooser();
					DiffController diffController = new DiffController();				
					FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter("xml files (*.xml)", "xml");
					fileChooserClickBtn.addChoosableFileFilter(xmlFilter);
					fileChooserClickBtn.setFileFilter(xmlFilter);
					fileChooserClickBtn.setCurrentDirectory(new File(System.getProperty("user.dir")));
					fileChooserClickBtn.setFileSelectionMode(JFileChooser.FILES_ONLY);
					int returnValClickBtn = fileChooserClickBtn.showSaveDialog(null);
					
						if(returnValClickBtn == JFileChooser.APPROVE_OPTION){						
							clickPath = fileChooserClickBtn.getSelectedFile().toString();
							if(!clickPath.endsWith("xml")){
								clickPath += ".xml";
							}else if(clickPath.length() == 0){
								JOptionPane.showMessageDialog(null, "You have not Path.", "Error", JOptionPane.ERROR_MESSAGE);
								statusLabel.setText("");
								return;
							}else if(returnValClickBtn == JFileChooser.CANCEL_OPTION){
								statusLabel.setText("");
								return;
							}else{
								statusLabel.setText("");
								System.out.println("===== Cancel =====");
							}
							statusLabel.setText("Working...");
							File tempFilePath = new File("C:\\DiffCounterTemp");						
							if(tempFilePath.exists()){
								deleteDirectory(tempFilePath);
							}else if(!tempFilePath.exists()){
								tempFilePath.mkdir();																
							}													
							diffController.tempParser();												
							try {
								transform(tempFilePath + "\\dummy.xml", mergeA, tempFilePath + "\\mergedA.xml"); 
								transform(tempFilePath + "\\mergedA.xml", hash, tempFilePath + "\\docA.xml");
								transform(tempFilePath + "\\dummy.xml", mergeB, tempFilePath + "\\mergedB.xml");
								transform(tempFilePath + "\\mergedB.xml", hash, tempFilePath + "\\docB.xml");
						//---------------------------------------------------------------------------------------------------------------					    								
								diffController.xmlParserA();
								diffController.xmlParserB();								
							
								diffController.docCom();
								
   							    transform("C:\\DiffCounterTemp\\delta.xml", clean, "C:\\DiffCounterTemp\\cleaned.xml");
								transform("C:\\DiffCounterTemp\\cleaned.xml", group, "C:\\DiffCounterTemp\\grouped.xml");
								transform("C:\\DiffCounterTemp\\grouped.xml", mark, "C:\\DiffCounterTemp\\marked.xml");
								transform("C:\\DiffCounterTemp\\marked.xml", extract, "C:\\DiffCounterTemp\\extracted.xml");
								transform("C:\\DiffCounterTemp\\extracted.xml", refine, "C:\\DiffCounterTemp\\refined.xml");
								transform("C:\\DiffCounterTemp\\refined.xml", pilcrow, "C:\\DiffCounterTemp\\pilcrowed.xml");
								transform("C:\\DiffCounterTemp\\pilcrowed.xml", untag, "C:\\DiffCounterTemp\\untagged.xml");
								transform("C:\\DiffCounterTemp\\untagged.xml", segment, "C:\\DiffCounterTemp\\segmented.xml");
								transform("C:\\DiffCounterTemp\\segmented.xml", tidy, "C:\\DiffCounterTemp\\tidied.xml");
								transform("C:\\DiffCounterTemp\\tidied.xml", separate, "C:\\DiffCounterTemp\\separated.xml");
								transform("C:\\DiffCounterTemp\\separated.xml", count,"C:\\DiffCounterTemp\\counted.xml");	
								transform("C:\\DiffCounterTemp\\counted.xml", report, clickPath);

								statusLabel.setText("");
								System.out.println("===========================The End====================== ");
								JOptionPane.showMessageDialog(null, "Successfully completed.", "information", JOptionPane.INFORMATION_MESSAGE);
								//deleteDirectory(tempFilePath);
							} catch (Exception e2) {
								e2.printStackTrace();
							}
							return;	
						}	
				}
			}
		});
		
		Button exitBtn = new Button();
		exitBtn = new Button("Exit");
		exitBtn.setSize(70, 30);
		exitBtn.setLocation(230, 180);
		exitBtn.setFont(new Font("Serif", Font.TYPE1_FONT, 14));
		exitBtn.setBackground(Color.LIGHT_GRAY);
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
		f.add(secondQ);
		f.add(txtF2);
		f.add(pathBtn2);
		f.add(clickBtn);
		f.add(exitBtn);
		f.add(statusLabel);	
		f.setIconImage(iconImg);
		f.setBackground(Color.WHITE);
		
		f.setVisible(true);
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
	
	public static void transform(String sourcePath, String xsltPath, String resultDir) {
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(new File(xsltPath)));
			transformer.setParameter("basePath", selectItem);			
			transformer.setParameter("modifyPath", selectItem2);			
			transformer.setOutputProperty(OutputKeys.ENCODING,"utf-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");					
			transformer.transform(new StreamSource(new File(sourcePath)), new StreamResult(new File(resultDir)));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

}
