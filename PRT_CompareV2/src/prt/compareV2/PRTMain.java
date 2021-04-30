package prt.compareV2;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


public class PRTMain extends JFrame{
	private Container contentPane;
	private JPanel pane;
	private JButton startButton;
	private JButton cancelButton;
	private JButton pdfButton;
	private JButton excelButton;
	private JTextField pdfJTextField;
	private JTextField excelJTextField;
	private JComboBox<String> regionListBox;
	private JLabel pdfJLabel;
	private JLabel excelJLabel;
	private JLabel regionJLabel;
	private PRTExcel prtController;
	//ArrayList<String> region_Code_List; 
	private File region_Code_List;
	private Document regionxml = null;	
	private FileNameExtensionFilter excelFilter;
	private FileNameExtensionFilter pdfFilter;
	private FileNameExtensionFilter xmlFilter;
	private JFileChooser excelOpenFile = null;
	private JFileChooser pdfOpenFile = null;
	private JFileChooser xmlOpenFile = null;
	private String savefilePath = "";	
	private PRTExcel prtExcel;	
	
	private static String selectedRegion = "";
	PRTPdf prtPDF = new PRTPdf();
	
	public PRTMain(){				
		initView();
		
	}
	
	private void initView(){									
		
		setTitle("PRT Compare PDF Version");
		setBounds(400, 300, 540, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Frame load 시 읽어옴
		addWindowListener(new WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
            	formWindowOpend(evt);
            }
		});
		
		
		contentPane = this.getContentPane();
		pane = new JPanel();
		prtExcel = new PRTExcel();
		prtPDF = new PRTPdf();
		//Start Button 
		startButton = new JButton("Start");
		startButton.setMnemonic('S');
		startButton.setBounds(200,220,65,30);
		startButton.setBackground(Color.white);
		//startButton.setFont(new Font("HY엽서L",  Font.BOLD, 10));
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {				
				jButton_startActionPerformed(evt);							
			}
		});
		//Cancel Button
		cancelButton = new JButton("Cancel");
		cancelButton.setMnemonic('C');
		cancelButton.setBounds(290,220,75,30);
		cancelButton.setBackground(Color.white);
		//cancelButton.setFont(new Font("HY엽서L",  Font.BOLD, 10));
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {								
				System.exit(0); 
				
			}
		});
		
		//pdfButton
		pdfJTextField = new JTextField();
		pdfJTextField.setBounds(70, 30, 300, 25);
		pdfJTextField.setBackground(Color.white);
		
		pdfJTextField.setDropTarget(new DropTarget(){
			/**
			 * 
			 */
			
			private static final long serialVersionUID = 1L;

			@Override
            public synchronized void dragEnter(DropTargetDragEvent evt) {                					
				try {
					@SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>) evt
					        .getTransferable().getTransferData(
					                DataFlavor.javaFileListFlavor);
					int s = droppedFiles.size();                    
                    if(s != 1){
                        evt.rejectDrag();
                    }else if(!droppedFiles.get(0).getName().endsWith(".pdf")){
                        evt.rejectDrag();
                	}
				} catch (UnsupportedFlavorException | IOException ex) {
                    Logger.getLogger(PRTMain.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}			
            };
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    @SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>) evt
                            .getTransferable().getTransferData(
                                    DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                    	pdfJTextField.setText(file.getAbsolutePath());
                        //pdfOpenFile.setSelectedFile(file);
                    }
                }catch (UnsupportedFlavorException | IOException ex) {
                    Logger.getLogger(PRTMain.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
		});
		
		//excelJTextField
		excelJTextField = new JTextField();
		excelJTextField.setBounds(70, 90, 300, 25);
		excelJTextField.setBackground(Color.white);
		excelJTextField.setDragEnabled(true);		
        
		excelJTextField.setDropTarget(new DropTarget(){
            /**
			 * 
			 */
			
			private static final long serialVersionUID = 1L;

			@Override
            public synchronized void dragEnter(DropTargetDragEvent evt) {                					
				try {
					@SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>) evt
					        .getTransferable().getTransferData(
					                DataFlavor.javaFileListFlavor);
					int s = droppedFiles.size();                    
                    if(s != 1){
                        evt.rejectDrag();
                    }else if(!droppedFiles.get(0).getName().endsWith(".xlsx") && !droppedFiles.get(0).getName().endsWith(".xls")){
                        evt.rejectDrag();
                	}
				} catch (UnsupportedFlavorException | IOException ex) {
                    Logger.getLogger(PRTMain.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}			
            };
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    @SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>) evt
                            .getTransferable().getTransferData(
                                    DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                    	excelJTextField.setText(file.getAbsolutePath());
                        //excelOpenFile.setSelectedFile(file);
                    }
                }catch (UnsupportedFlavorException | IOException ex) {
                    Logger.getLogger(PRTMain.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
		});
		
		//regionListBox
		regionListBox = new JComboBox<String>();			
		regionListBox.setBounds(70, 150, 300, 25);
		regionListBox.setBackground(Color.white);		
		regionListBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(regionListBox.getSelectedItem().equals(null) || regionListBox.getSelectedItem().equals("")){
						selectedRegion = regionListBox.getItemAt(0);						
					}else{
						selectedRegion = regionListBox.getSelectedItem().toString();																
					}

				} catch (Exception ex) {
					Logger.getLogger(PRTMain.class.getName()).log(Level.SEVERE, null, ex);
				}
				
			}
		});

/*		prtController.XmlPaser("C:\\Project\\PRT_Compare_V2\\XMLRegion_List\\region-code.xml");
		for(String str : prtController.region_Code_List){
			regionListBox.addItem(str);
		}*/

		//pdfJLabel
		pdfJLabel = new JLabel("PDF");
		pdfJLabel.setBounds(10, 27, 65, 30);  //(int x, int y, int width, int height)
		//pdfJLabel.setFont(new Font("HY엽서L",  Font.BOLD, 15));		
		
		excelJLabel = new JLabel("Excel");
		excelJLabel.setBounds(10, 88, 65, 30);
		//excelJLabel.setFont(new Font("HY엽서L",  Font.BOLD, 15));
		
		regionJLabel = new JLabel("Region");
		regionJLabel.setBounds(10, 150, 65, 30);
		//regionJLabel.setFont(new Font("HY엽서L",  Font.BOLD, 15));		
		
		pdfButton = new JButton("Select PDF"); 	//pdfJTextField.setBounds(70, 30, 300, 25);
		pdfButton.setBounds(390, 30, 120, 24);
		pdfButton.setBackground(Color.white);
		//pdfButton.setFont(new Font("HY엽서L",  Font.BOLD, 12));
		pdfButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				jButton_pdfActionPerformed(evt);
				
			}
		});
		
		excelButton = new JButton("Select Excel");
		excelButton.setBounds(390, 90, 120, 24);
		excelButton.setBackground(Color.white);
		//excelButton.setFont(new Font("HY엽서L",  Font.BOLD, 12));
		excelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				jButton_excelActionPerformed(evt);
			}
		});
		
		pane.setLayout(null);
		pane.setBackground(Color.white);
		
		pane.add(startButton);
		pane.add(cancelButton);	
		pane.add(pdfJTextField);
		pane.add(excelJTextField);
		pane.add(regionListBox);
		pane.add(pdfJLabel);
		pane.add(excelJLabel);
		pane.add(regionJLabel);
		pane.add(pdfButton);
		pane.add(excelButton);
		
		contentPane.add(pane);
		
		setVisible(true);
		
	}
	
	public void formWindowOpend(java.awt.event.WindowEvent evt){		
		
        try {
            // TODO add your handling code here:
        	//region_Code_List = new File("C:\\Project\\PRT_Compare_V2\\XMLRegion_List\\region-code.xml");
        	region_Code_List = new File("XMLRegionXML\\xml\\region-code.xml");        	
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            regionxml = builder.parse(region_Code_List);
            
            XPath xpath = XPathFactory.newInstance().newXPath();
            String exp = "//region";
            NodeList regions = (NodeList)xpath.evaluate(exp, regionxml, XPathConstants.NODESET);
            
            regionListBox.removeAllItems();            
            for (int i = 0 ; i < regions.getLength() ; i++) {            	
                regionListBox.addItem(regions.item(i).getAttributes().getNamedItem("type").getNodeValue());             	
            }
			
		} catch (Exception e) {
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
	
	private void jButton_pdfActionPerformed(java.awt.event.ActionEvent evt) {
        pdfFilter = new FileNameExtensionFilter("Adobe Acrobat(.pdf)", "pdf");

        pdfOpenFile = new javax.swing.JFileChooser();
        pdfOpenFile.setCurrentDirectory(new File("/"));
        pdfOpenFile.setAcceptAllFileFilterUsed(true);
        pdfOpenFile.setMultiSelectionEnabled(false);   
        pdfOpenFile.setFileSelectionMode(JFileChooser.FILES_ONLY);      
        pdfOpenFile.setFileFilter(pdfFilter);
        
        int returnVal = pdfOpenFile.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION){
        	pdfJTextField.setText(pdfOpenFile.getSelectedFile().getAbsolutePath());
        }else if(returnVal == JFileChooser.CANCEL_OPTION){
        	pdfJTextField.setText("");
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
	    
	private void jButton_excelActionPerformed(java.awt.event.ActionEvent evt) {
        excelFilter = new FileNameExtensionFilter("Excel file", "xlsx", "xls");

        excelOpenFile = new javax.swing.JFileChooser();
        excelOpenFile.setCurrentDirectory(new File("/"));
        excelOpenFile.setAcceptAllFileFilterUsed(true);
        excelOpenFile.setMultiSelectionEnabled(false);   
        excelOpenFile.setFileSelectionMode(JFileChooser.FILES_ONLY);      
        excelOpenFile.setFileFilter(excelFilter);
        excelOpenFile.setSelectedFile(new File(pdfJTextField.getText()));
        
        int returnVal = excelOpenFile.showOpenDialog(null);
        //excelOpenFile.setSelectedFile(new File(pdfOpenFile.getCurrentDirectory() + "\\excelToxml.xml"));
        if(returnVal == JFileChooser.APPROVE_OPTION){
        	excelJTextField.setText(excelOpenFile.getSelectedFile().getAbsolutePath());
        }else if(returnVal == JFileChooser.CANCEL_OPTION){
        	excelJTextField.setText("");
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
	
	private void jButton_startActionPerformed(java.awt.event.ActionEvent evt) {		
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		if(pdfJTextField.getText().equals("") || pdfJTextField.getText().isEmpty() || pdfJTextField.getText() == null
				|| excelJTextField.getText().equals("") || excelJTextField.getText().isEmpty() || excelJTextField.getText() == null){
			JOptionPane.showMessageDialog(null, "No path specified.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
			this.setCursor(Cursor.getDefaultCursor());
			return;
		}
/*		xmlOpenFile = new JFileChooser();
		xmlFilter = new FileNameExtensionFilter("XML File(*.xml)", "xml");	
		xmlOpenFile.setFileFilter(xmlFilter);*/
		//파일 탐색기 
/*		savefile = new JFileChooser();
		savefile.setCurrentDirectory(new File("/"));
		savefile.setMultiSelectionEnabled(false);
		savefile.setAcceptAllFileFilterUsed(true);
		savefile.setFileFilter(filter);				
		savefile.setSelectedFile(new File(savefile.getCurrentDirectory() + "\\excelToxml.xml"));*/
		
//		int returnVal = savefile.showSaveDialog(null);		
		//savefilePath = savefile.getSelectedFile().getPath();
		//해당 패스의 값이 .xml(확장자)가 들어가있지 않는 경우를 방지 해놓음.
		savefilePath = excelJTextField.getText();		
		if(!savefilePath.endsWith(".xml") && !savefilePath.endsWith(".XML")){				
			savefilePath += ".xml";			
			String excelFilePath = excelJTextField.getText();					
			try {
				if(excelFilePath.endsWith("xlsx")){				//XMLRegionXML\\xml\\region-code.xml"				
					prtExcel.xlsxToPoiList(new File(excelFilePath), new File("temp\\variableList.xml"));
					//prtPDF.pdfToList("table-p2h.exe", "--input=Q120-X0_QSG_UTU7000K 43-55inch-Bezel_ZW_TPE_200623.0.pdf");
					prtPDF.pdfToList("table-p2h.exe", "--input=" + pdfJTextField.getText());
					transform("temp\\normalized.xml", "xsls\\01-refine.xsl", "temp\\01-refined.xml");
					transform("temp\\01-refined.xml", "xsls\\02-check.xsl", "temp\\02-checked.xml");
					transform("temp\\02-checked.xml", "xsls\\03-reduce.xsl", "temp\\03-reduced.xml");
					transform("temp\\03-reduced.xml", "xsls\\04-cascade.xsl", "temp\\04-cascaded.xml");
					transform("temp\\04-cascaded.xml", "xsls\\05-compare.xsl", "compare.html");
					openFile("compare.html");
					JOptionPane.showMessageDialog(null, "Sucess", "INFORMATION_MESSAGE", JOptionPane.INFORMATION_MESSAGE);
				}else if(excelFilePath.endsWith("xls")){
					prtExcel.xlsToPoiList(new File(excelFilePath), new File("temp\\variableList.xml"));
					//prtPDF.pdfToList("table-p2h.exe", "--input=Q120-X0_QSG_UTU7000K 43-55inch-Bezel_ZW_TPE_200623.0.pdf");
					prtPDF.pdfToList("table-p2h.exe", "--input=" + pdfJTextField.getText());
					transform("temp\\normalized.xml", "xsls\\01-refine.xsl", "temp\\01-refined.xml");
					transform("temp\\01-refined.xml", "xsls\\02-check.xsl", "temp\\02-checked.xml");
					transform("temp\\02-checked.xml", "xsls\\03-reduce.xsl", "temp\\03-reduced.xml");
					transform("temp\\03-reduced.xml", "xsls\\04-cascade.xsl", "temp\\04-cascaded.xml");
					transform("temp\\04-cascaded.xml", "xsls\\05-compare.xsl", "compare.html");
					openFile("compare.html");
					JOptionPane.showMessageDialog(null, "Sucess", "INFORMATION_MESSAGE", JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "Only xlsx, xls are supported.", "Error", JOptionPane.ERROR_MESSAGE);
				}		
			} catch (Exception ex) {
                Logger.getLogger(PRTMain.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		}else{//해당 패스의 값이 .xml(확장자)이 들어가 있으면 상관없으므로 
			if(excelJTextField.getText().equals("") || excelJTextField.getText().isEmpty() || excelJTextField.getText() == null){
				JOptionPane.showMessageDialog(null, "There is no path to the Excel file.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
			}else{					
				String excelFilePath = excelJTextField.getText();					
				try {
					if(excelFilePath.endsWith("xlsx")){								
						prtExcel.xlsxToPoiList(new File(excelFilePath), new File("temp\\variableList.xml"));
						//prtPDF.pdfToList("table-p2h.exe", "--input=Q120-X0_QSG_UTU7000K 43-55inch-Bezel_ZW_TPE_200623.0.pdf");
						prtPDF.pdfToList("table-p2h.exe", "--input=" + pdfJTextField.getText());
						transform("temp\\normalized.xml", "xsls\\01-refine.xsl", "temp\\01-refined.xml");
						transform("temp\\01-refined.xml", "xsls\\02-check.xsl", "temp\\02-checked.xml");
						transform("temp\\02-checked.xml", "xsls\\03-reduce.xsl", "temp\\03-reduced.xml");
						transform("temp\\03-reduced.xml", "xsls\\04-cascade.xsl", "temp\\04-cascaded.xml");
						transform("temp\\04-cascaded.xml", "xsls\\05-compare.xsl", "compare.html");
						openFile("compare.html");
						JOptionPane.showMessageDialog(null, "Sucess", "INFORMATION_MESSAGE", JOptionPane.INFORMATION_MESSAGE);
					}else if(excelFilePath.endsWith("xls")){
						prtExcel.xlsToPoiList(new File(excelFilePath), new File("temp\\variableList.xml"));
						//prtPDF.pdfToList("table-p2h.exe", "--input=Q120-X0_QSG_UTU7000K 43-55inch-Bezel_ZW_TPE_200623.0.pdf");
						prtPDF.pdfToList("table-p2h.exe", "--input=" + pdfJTextField.getText());
						transform("temp\\normalized.xml", "xsls\\01-refine.xsl", "temp\\01-refined.xml");
						transform("temp\\01-refined.xml", "xsls\\02-check.xsl", "temp\\02-checked.xml");
						transform("temp\\02-checked.xml", "xsls\\03-reduce.xsl", "temp\\03-reduced.xml");
						transform("temp\\03-reduced.xml", "xsls\\04-cascade.xsl", "temp\\04-cascaded.xml");
						transform("temp\\04-cascaded.xml", "xsls\\05-compare.xsl", "compare.html");
						openFile("compare.html");
						JOptionPane.showMessageDialog(null, "Sucess", "INFORMATION_MESSAGE", JOptionPane.INFORMATION_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(null, "Only xlsx, xls are supported.", "Error", JOptionPane.ERROR_MESSAGE);
					}		
				} catch (Exception ex) {
	                Logger.getLogger(PRTMain.class.getName()).log(Level.SEVERE, null, ex);
	                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		this.setCursor(Cursor.getDefaultCursor());		

    }
	
	public static void transform(String sourcePath, String xsltPath, String resultDir) {				
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(new File(xsltPath)));
			transformer.setOutputProperty(OutputKeys.ENCODING,"utf-8");
			// transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			// transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.setParameter("region", selectedRegion);					
			transformer.transform(new StreamSource(new File(sourcePath)), new StreamResult(new File(resultDir)));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public void openFile(String filePath){
		File file = new File(filePath);		
		Desktop desktop = Desktop.getDesktop();
		if(file.exists()){
			try {
				desktop.open(file);
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		PRTMain pm = new PRTMain();  		
		/*PRTPdf ppd = new PRTPdf();
		ppd.pdfToList("C:\\prt_compareV2\\table-p2h.exe", "--input=C:\\prt_compareV2\\Q120-X0_QSG_UTU7000K 43-55inch-Bezel_ZW_TPE_200623.0.pdf");*/
	}

}
