import java.awt.EventQueue;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

public class VSEAConverter {

	private JFrame frame;
	private JTextField z1SourceHTMTextField;
	private JTextField z2SourceHTMTextField;
	private JTextField modelTextField;
	private ImageIcon ImageIcon;
	private JPanel z1SourceHTMPane;
	private JLabel z1SourceHTMLabel;
	private JButton z1SourceHTMSelectBT;
	private JPanel z2SourceHTMPane;
	private JLabel z2SourceHTMLabel;
	private JButton z2SourceHTMSelectBT;
	private JButton z1StartButton;
	private JButton z2StartButton;
	private JPanel ModelPane;
	private JLabel modelLabel;
	private JCheckBox enCheckBox;
	private JCheckBox spCheckBox;
	private ButtonGroup buttonGroup;
	private TitledBorder Z1tileBorder;
	private TitledBorder Z2tileBorder;
	private JPanel z1BorderPane;
	private JPanel z2BorderPane;
	private JDialog dialog;
	
	private ArrayList<String> z1HTMSelectPath;
	private ArrayList<String> z2HTMSelectPath;
	private ArrayList<String> z1HTMSelectResultList;
	private ArrayList<String> z2HTMSelectResultList; 	
	private String modelTextFieldValue;
	private String enCheckBoxValue;
	private String spCheckBoxValue;
	private File tempFile;
	private File resourceFile;
	private File indexFile;
	private File finalizeFile;
	
	/**
	 * Create the application.
	 */
	public VSEAConverter() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 507, 228);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("SEA Converter");					
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		dialog = new JDialog();
		ImageIcon = new ImageIcon(getResourceAsFile("ast-logo.png").getPath());
		z1SourceHTMPane = new JPanel();
		z1SourceHTMLabel = new JLabel("Z1SourceHTM");
		z1SourceHTMSelectBT = new JButton("...");
		z1SourceHTMTextField = new JTextField();
		z2SourceHTMPane = new JPanel();
		z2SourceHTMLabel = new JLabel("Z2SourceHTM");
		z2SourceHTMSelectBT = new JButton("...");
		z2SourceHTMTextField = new JTextField();
		z1StartButton = new JButton("Z1 Start");
		z2StartButton = new JButton("Z2 Start");
		ModelPane = new JPanel();
		modelLabel = new JLabel("Model");
		modelTextField = new JTextField();
		enCheckBox = new JCheckBox("English");
		spCheckBox = new JCheckBox("Spanish");
		buttonGroup = new ButtonGroup();
		Z1tileBorder = new TitledBorder(new LineBorder(Color.black), "Z1");
		Z2tileBorder = new TitledBorder(new LineBorder(Color.black), "Z2");
		z1BorderPane = new JPanel();
		z2BorderPane = new JPanel();
		z1HTMSelectPath = new ArrayList<String>();
		z2HTMSelectPath = new ArrayList<String>();
		z1HTMSelectResultList = new ArrayList<String>();
		z2HTMSelectResultList = new ArrayList<String>();
		//---------------------------------
		Image Image = ImageIcon.getImage();
		frame.setIconImage(Image);
		
		z1SourceHTMPane.setBounds(19, 19, 453, 41);
		frame.getContentPane().add(z1SourceHTMPane);
		z1SourceHTMPane.setLayout(null);		

		z1SourceHTMLabel.setBounds(8, 12, 81, 15);
		z1SourceHTMPane.add(z1SourceHTMLabel);
		
		z1SourceHTMSelectBT.setBounds(425, 8, 24, 23);
		z1SourceHTMSelectBT.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jButton_z1HTMSelectActionPerformed(e);
				
			}
		});
		
		z1SourceHTMPane.add(z1SourceHTMSelectBT);		

		z1SourceHTMTextField.setBounds(93, 8, 330, 23);
		z1SourceHTMTextField.setDropTarget(new DropTarget() {
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
                    }else if(!droppedFiles.get(0).getName().endsWith(".htm")){
                        evt.rejectDrag();
                	}
				} catch (UnsupportedFlavorException | IOException ex) {					
                    JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);   
                   
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
                    if(!droppedFiles.iterator().next().isFile()) {
                    	JOptionPane.showMessageDialog(dialog, "파일이 아닙니다.", "It is not a File.", JOptionPane.ERROR_MESSAGE);
                    	return;
                    }else {
                    	for (File file : droppedFiles) {
                    		z1SourceHTMTextField.setText(file.getAbsolutePath());
                        }	
                    }
                    
                }catch (UnsupportedFlavorException | IOException ex) {
                	JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);                    
                }
            }
		});
		z1SourceHTMPane.add(z1SourceHTMTextField);
		z1SourceHTMTextField.setColumns(10);		

		z2SourceHTMPane.setLayout(null);
		z2SourceHTMPane.setBounds(19, 111, 453, 41);		
		//frame.getContentPane().add(z2SourceHTMPane);
		

		z2SourceHTMLabel.setBounds(8, 12, 81, 15);
		z2SourceHTMPane.add(z2SourceHTMLabel);		

		z2SourceHTMSelectBT.setBounds(425, 8, 24, 23);
		z2SourceHTMSelectBT.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jButton_z2HTMSelectActionPerformed(e);
				
			}
		});
		z2SourceHTMPane.add(z2SourceHTMSelectBT);		

		
		z2SourceHTMTextField.setColumns(10);
		z2SourceHTMTextField.setBounds(93, 8, 330, 23);
//		z2SourceHTMTextField.setDropTarget(new DropTarget() {
//			@Override
//            public synchronized void dragEnter(DropTargetDragEvent evt) {                					
//				try {
//					@SuppressWarnings("unchecked")
//					List<File> droppedFiles = (List<File>) evt
//					        .getTransferable().getTransferData(
//					                DataFlavor.javaFileListFlavor);
//					int s = droppedFiles.size();                    
//                    if(s != 1){
//                        evt.rejectDrag();
//                    }/*else if(!droppedFiles.get(0).getName().endsWith(".pdf")){
//                        evt.rejectDrag();
//                	}*/
//				} catch (UnsupportedFlavorException | IOException ex) {					
//                    JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);   
//                   
//				}			
//            };
//       
//			@Override
//            public synchronized void drop(DropTargetDropEvent evt) {
//                try {
//                    evt.acceptDrop(DnDConstants.ACTION_COPY);
//                    @SuppressWarnings("unchecked")
//					List<File> droppedFiles = (List<File>) evt
//                            .getTransferable().getTransferData(
//                                    DataFlavor.javaFileListFlavor);
//                    if(!droppedFiles.iterator().next().isFile()) {
//                    	JOptionPane.showMessageDialog(dialog, "파일이 아닙니다.", "It is not a File.", JOptionPane.ERROR_MESSAGE);
//                    	return;
//                    }else {
//                    	for (File file : droppedFiles) {
//                    		z2SourceHTMTextField.setText(file.getAbsolutePath());
//                        }	
//                    }
//                    
//                }catch (UnsupportedFlavorException | IOException ex) {
//                	JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);                    
//                }
//            }
//		});
		z2SourceHTMPane.add(z2SourceHTMTextField);
		

		z1StartButton.setBounds(386, 62, 86, 23);
		z1StartButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jButton_z1StartButtonActionPerformed(e);

			}
		});
		frame.getContentPane().add(z1StartButton);
		

		z2StartButton.setBounds(386, 149, 86, 23);
		z2StartButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					jButton_z2StartButtonActionPerformed(e);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		frame.getContentPane().add(z2StartButton);
		

		ModelPane.setLayout(null);
		ModelPane.setBounds(58, 112, 411, 35);
		frame.getContentPane().add(ModelPane);
		

		modelLabel.setBounds(12, 10, 35, 15);
		ModelPane.add(modelLabel);
		

		modelTextField.setBounds(53, 7, 203, 23);	
		modelTextField.setColumns(10);	

		ModelPane.add(modelTextField);
		

		enCheckBox.setBounds(264, 6, 71, 23);
		enCheckBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {				
				if(e.getStateChange() == 1) { // 체크 했을때 1
					enCheckBoxValue = enCheckBox.getText();					
				}else if(e.getStateChange() == 2){ //체크 하지 않았을때 2
					enCheckBoxValue = "";					
				}
				else { //아무 것도 체크 하지 않았을때
					enCheckBoxValue = "";
					
				}				
				
			}
		});
		ModelPane.add(enCheckBox);
		

		spCheckBox.setBounds(339, 6, 71, 23);
		spCheckBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {			
				if(e.getStateChange() == 1) { // 체크 했을때 1
					spCheckBoxValue = spCheckBox.getText();					
				}else if(e.getStateChange() == 2){ //체크 하지 않았을때 2
					spCheckBoxValue = "";
				}
				else { //아무 것도 체크 하지 않았을때
					spCheckBoxValue = "";					
				}
				
			}
		});
		ModelPane.add(spCheckBox);
		

		Z1tileBorder.setTitleColor(Color.black);
		
		Z2tileBorder.setTitleColor(Color.black);


		z1BorderPane.setBounds(12, 2, 467, 94);
		frame.getContentPane().add(z1BorderPane);
		z1BorderPane.setBorder(Z1tileBorder);


		z2BorderPane.setBounds(12, 96, 467, 88);
		frame.getContentPane().add(z2BorderPane);
		z2BorderPane.setBorder(Z2tileBorder);
		
		buttonGroup.add(enCheckBox);
		buttonGroup.add(spCheckBox);
	}
	
	private void jButton_z1HTMSelectActionPerformed(ActionEvent e) {
		Object obj = e.getSource();		
		if(obj == z1SourceHTMSelectBT) {
			JFileChooser jc = new JFileChooser(FileSystemView.getFileSystemView());
			jc.setAcceptAllFileFilterUsed(false);
			jc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			
			FileNameExtensionFilter filter = new FileNameExtensionFilter("HTM Files", "htm");
			jc.setFileFilter(filter);
			jc.setCurrentDirectory(new File("/"));
			
			int returnVal = jc.showOpenDialog(frame);
			if(returnVal == JFileChooser.FILES_ONLY) {
				//z1HTMSelectPath = jc.getSelectedFile().getAbsolutePath();
				z1HTMSelectPath.add(jc.getSelectedFile().getAbsolutePath());
				z1SourceHTMTextField.setText(jc.getSelectedFile().getAbsolutePath());
			}else if(returnVal == JFileChooser.CANCEL_OPTION){
				z1HTMSelectPath.clear();
				z1SourceHTMTextField.setText("");
	        }
		}
	}
	
	private void jButton_z2HTMSelectActionPerformed(ActionEvent e) {
		Object obj = e.getSource();		
		if(obj == z2SourceHTMSelectBT) {
			JFileChooser jc = new JFileChooser(FileSystemView.getFileSystemView());
			jc.setAcceptAllFileFilterUsed(false);
			jc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			//jc.setCurrentDirectory(new File(textPane.getText()));
			int returnVal = jc.showOpenDialog(frame);
			if(returnVal == JFileChooser.FILES_ONLY) {
				z2HTMSelectPath.add(jc.getSelectedFile().getAbsolutePath());
				z2SourceHTMTextField.setText(jc.getSelectedFile().getAbsolutePath());
			}else if(returnVal == JFileChooser.CANCEL_OPTION){
				z2HTMSelectPath.clear();
				z2SourceHTMTextField.setText("");
	        }
		}
	}
	
	private void jButton_z1StartButtonActionPerformed(ActionEvent e){
		Object obj = e.getSource();
		for(int i = 0; i < z1HTMSelectPath.size(); i++) {
			if(!z1HTMSelectResultList.contains(z1HTMSelectPath.get(i))) {
				z1HTMSelectResultList.add(z1HTMSelectPath.get(i));
			}
		}
		
		if(obj == z1StartButton) {
			if(z1HTMSelectResultList.isEmpty() || z1HTMSelectResultList == null 
					|| z1SourceHTMTextField.getText() == null || z1SourceHTMTextField.getText().isEmpty()) {				
				JOptionPane.showMessageDialog(dialog, "Z1 Source 파일을 선택하세요.", "INFORMATION_MESSAGE", JOptionPane.INFORMATION_MESSAGE);
				return;
			}else {				
				z1TransFormEngin();
			}
			
		}
		
	}
	
	private void jButton_z2StartButtonActionPerformed(ActionEvent e) throws Exception{
		Object obj = e.getSource();
		modelTextFieldValue = modelTextField.getText();
		finalizeFile = new File("temp");
		if(!finalizeFile.exists()) {
			JOptionPane.showMessageDialog(dialog, "temp 폴더가 존재 하지 않습니다. \n "
					+ "Z1을 실행하여 주세요.", "INFORMATION MESSAGE", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		boolean finalizeBoolean = showFileList("temp");
				
		for(int i = 0; i < z1HTMSelectPath.size(); i++) {
			if(!z1HTMSelectResultList.contains(z1HTMSelectPath.get(i))) {
				z1HTMSelectResultList.add(z1HTMSelectPath.get(i));
			}
		}
		
		if(obj == z2StartButton) {			
			if(enCheckBox.isSelected()) {			
				if(modelTextFieldValue.isEmpty() || modelTextFieldValue == null) {
					JOptionPane.showMessageDialog(dialog, "Model을 입력하세요.", "INFORMATION MESSAGE", JOptionPane.INFORMATION_MESSAGE);
					return;					
				}

				if(finalizeBoolean == true) {
					z2TransFormEngin(enCheckBoxValue, modelTextField.getText());
				}else {
					JOptionPane.showMessageDialog(dialog, "finalize.html 파일이 존재 하지 않습니다. \n "
							+ "Z1을 실행하여 주세요.", "INFORMATION MESSAGE", JOptionPane.INFORMATION_MESSAGE);
				}
							
			}else if(spCheckBox.isSelected()) {							
				if(modelTextFieldValue.isEmpty() || modelTextFieldValue == null) {
					JOptionPane.showMessageDialog(dialog, "Model을 입력하세요.", "INFORMATION MESSAGE", JOptionPane.INFORMATION_MESSAGE);
					return;					
				}

				if(finalizeBoolean == true) {
					z2TransFormEngin(spCheckBoxValue, modelTextFieldValue);	
				}else {
					JOptionPane.showMessageDialog(dialog, "finalize.html 파일이 존재 하지 않습니다. \n "
							+ "Z1을 실행하여 주세요.", "INFORMATION MESSAGE", JOptionPane.INFORMATION_MESSAGE);
				}
								
			}else {
				JOptionPane.showMessageDialog(dialog, "해당 언어를 선택하세요.", "ERROR MESSAGE", JOptionPane.INFORMATION_MESSAGE);
				return;	
			}


//			else {
//				JOptionPane.showMessageDialog(dialog, "해당 언어를 선택하세요.", "INFORMATION_MESSAGE", JOptionPane.INFORMATION_MESSAGE);
//				return;	
//			}
		}
	}
	
	private void z1TransFormEngin() {
		frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		for(int i = 0; i < z1HTMSelectResultList.size(); i++) {			
			Transform("xsls2\\dummy.xml", "xsls2\\Flare-xsls\\00-merge-chapters.xsl", "temp\\dummy.xml", z1HTMSelectPath.get(i));
			TransformNonParam("temp\\mergedXML.xml", "xsls2\\Flare-xsls\\00-make-one-html.xsl", "temp\\00-make-one-html.html");
			TransformNonParam("temp\\00-make-one-html.html", "xsls2\\Flare-xsls\\01-make-html.xsl", "temp\\01-make-html.html");
			TransformNonParam("temp\\01-make-html.html", "xsls2\\Flare-xsls\\02-href_define.xsl", "temp\\02-href_define.html");
			TransformNonParam("temp\\02-href_define.html", "xsls2\\Flare-xsls\\03-make-review-html.xsl", "temp\\03-review.html");
			TransformNonParam("temp\\03-review.html", "xsls2\\Flare-xsls\\04-nest-bullet-1.xsl", "temp\\04-nested1.html");
			TransformNonParam("temp\\04-nested1.html", "xsls2\\Flare-xsls\\05-nest-bullet-body.xsl", "temp\\05-nested2.html");
			TransformNonParam("temp\\05-nested2.html", "xsls2\\Flare-xsls\\06-group-bullet-1.xsl", "temp\\06-grouped1.html");
			TransformNonParam("temp\\06-grouped1.html", "xsls2\\Flare-xsls\\07-img_wrap.xsl", "temp\\07-img_wrap.html");
			TransformNonParam("temp\\07-img_wrap.html", "xsls2\\Flare-xsls\\08-nest-bullet-2.xsl", "temp\\08-nested3.html");
			TransformNonParam("temp\\08-nested3.html", "xsls2\\Flare-xsls\\09-nest-graphic.xsl", "temp\\09-nested4.html");
			TransformNonParam("temp\\09-nested4.html", "xsls2\\Flare-xsls\\10-nest-between-ols.xsl", "temp\\10-nested5.html");
			TransformNonParam("temp\\10-nested5.html", "xsls2\\Flare-xsls\\11-group-ol.xsl", "temp\\11-grouped3.html");
			TransformNonParam("temp\\11-grouped3.html", "xsls2\\Flare-xsls\\12-temporary_check.xsl", "temp\\finalize.html");
			frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			JOptionPane.showMessageDialog(dialog, "완료 하였습니다.", "INFORMATION MESSAGE", JOptionPane.INFORMATION_MESSAGE);
			
		}
	}
	
	private void z2TransFormEngin(String lanParam, String modelParam) {	
			frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		//for(int i = 0; i < sourcePath.size(); i++) {
//			System.out.println("sourcePath ===== : " + sourcePath.size());
//			System.out.println("sourcePath ====2222= : " + sourcePath.get(i));
			Transform("temp\\finalize.html", "xsls2\\13-find-and-replace.xsl", "temp\\13-find-and-replace.xml", lanParam, modelParam);
			TransformNonParam("temp\\13-find-and-replace.xml", "xsls2\\14-olul-grouping.xsl", "temp\\14-olul-grouping.xml");
			TransformNonParam("temp\\14-olul-grouping.xml", "xsls2\\15-chapterize.xsl", "temp\\15-chapterize.xml");
			TransformNonParam("temp\\15-chapterize.xml", "xsls2\\16-toc_chap_group.xsl", "temp\\16-toc_chap_group.xml");
			TransformNonParam("temp\\16-toc_chap_group.xml", "xsls2\\17-class_blue_group.xsl", "temp\\17-class_blue_group.xml");
			TransformNonParam("temp\\17-class_blue_group.xml", "xsls2\\18-sectionize.xsl", "temp\\18-sectionize.xml");
			TransformNonParam("temp\\18-sectionize.xml", "xsls2\\19-grouping-topic.xsl", "temp\\19-grouping-topic.xml");
			TransformNonParam("temp\\19-grouping-topic.xml", "xsls2\\20-define-Header_href_table.xsl", "temp\\20-define-Header_href_table.xml");
			
			//REM rem **************************************************************************************
			TransformNonParam("temp\\20-define-Header_href_table.xml", "xsls2\\21-minitoc.xsl", "temp\\dummy.xml");
			TransformNonParam("temp\\20-define-Header_href_table.xml", "xsls2\\22-splitting.xsl", "temp\\dummy.xml");
			TransformNonParam("temp\\20-define-Header_href_table.xml", "xsls2\\23-making-toc.xsl", "temp\\dummy.xml");
			TransformNonParam("temp\\20-define-Header_href_table.xml", "xsls2\\24-making-start-here.xsl", "temp\\dummy.xml");
			TransformNonParam("temp\\19-grouping-topic.xml", "xsls2\\25-making-search-html.xsl", "temp\\dummy.xml");
			TransformNonParam("temp\\19-grouping-topic.xml", "xsls2\\26-making-json.xsl", "temp\\dummy.xml");
			
			TransformNonParam("temp\\19-grouping-topic.xml", "xsls2\\27-making-app.xsl", "temp\\dummy.xml");
			//REM rem **************** start-here.html 로 접속 되기 위한 index.html**************************	
			resourceFile = new File("resource");
			if(!resourceFile.exists()) {
				JOptionPane.showMessageDialog(dialog, "resource 폴더가 존재하지 않아 \n " + 
			 "resource 폴더가 복사 되지 않습니다.", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
			}
			
			indexFile = new File("xsls2");
			if(!indexFile.exists()) {
				JOptionPane.showMessageDialog(dialog, "xsls2  폴더가 존재하지 않아 \n " + 
						 "xsls2 폴더가 복사 되지 않습니다.", "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
			}
			
			folderCopy(new File("resource"), new File("Source-Flare\\output\\"));
			fileCopy("xsls2\\index.html", "Source-Flare\\output\\index.html");			
			
			
			//if exist temp rmdir temp /s /q >NUL
//			tempFile = new File("temp");
//			if(tempFile.exists()) {
//				deleteAllFiles("temp");	
//			}
		//}
			frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			JOptionPane.showMessageDialog(dialog, "완료 하였습니다.", "INFORMATION MESSAGE", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	private void Transform(String sourcePath, String xsltPath, String resultDir, String param) {
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(new File(xsltPath)));					
			transformer.setParameter("SourcePath", param);
			transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");					
			transformer.transform(new StreamSource(new File(sourcePath)), new StreamResult(new File(resultDir)));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	private void Transform(String sourcePath, String xsltPath, String resultDir, String Param_1, String Param_2) {
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(new File(xsltPath)));								
			transformer.setParameter("language", Param_1);
			transformer.setParameter("model", Param_2);
			//transformer.setParameter("SourcePath", Param_3);
			transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");					
			transformer.transform(new StreamSource(new File(sourcePath)), new StreamResult(new File(resultDir)));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	
	private void TransformNonParam(String sourcePath, String xsltPath, String resultDir) {
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(new File(xsltPath)));								
			transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");					
			transformer.transform(new StreamSource(new File(sourcePath)), new StreamResult(new File(resultDir)));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	private boolean showFileList(String path) throws Exception{
		File dir = new File(path);
		File[] files = dir.listFiles();
		
		for(int i = 0; i < files.length; i++) {
			File file = files[i];
			if(file.isFile()) {				
				System.out.println("[File]" + file.getCanonicalPath().toString());	
				if(file.getCanonicalPath().toString().contains("finalize.html")) {
					
					return true;
					//System.out.println("포스코 힐스테이트@");
				}
			}else if(file.isDirectory()) {
				System.out.println("[Directory]" + file.getCanonicalPath().toString());
				try {
					showFileList(file.getCanonicalPath().toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return false;	

	}
	
	private File getResourceAsFile(String resourcePath) {
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
	
	public void fileCopy(String sourcePath, String targetPath) {		
        Path source = Paths.get(sourcePath);
        Path target = Paths.get(targetPath);

        try {
            // 복사할 대상 폴더가 있는지 체크해서 없으면 생성
            if (!Files.exists(target.getParent())) {
                Files.createDirectories(target.getParent());
            }

            // StandardCopyOption.REPLACE_EXISTING : 파일이 이미 존재할 경우 덮어쓰기
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void deleteAllFiles(String path) {
		File file = new File(path); // 폴더내 파일을 배열로 가져온다.
		File[] tempFile = file.listFiles();

		if (tempFile.length > 0) {
			for (int i = 0; i < tempFile.length; i++) {
				if (tempFile[i].isFile()) {
					tempFile[i].delete();
				} else {
					// 재귀함수
					deleteAllFiles(tempFile[i].getPath());
				}
				tempFile[i].delete();

			}
			file.delete();

		}
	}

	private void folderCopy(File dirFolderPath, File desFolderPath) {	
		File[] ff = dirFolderPath.listFiles();
		for (File file : ff) {
			File temp = new File(desFolderPath.getAbsolutePath() + File.separator + file.getName());
			if(file.isDirectory()){
				temp.mkdir();
				folderCopy(file, temp);
			} else {
				FileInputStream fis = null;
				FileOutputStream fos = null;
				try {
					fis = new FileInputStream(file);
					fos = new FileOutputStream(temp) ;
					byte[] b = new byte[4096];
					int cnt = 0;
					while((cnt=fis.read(b)) != -1){
						fos.write(b, 0, cnt);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
					try {
						fis.close();
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}
	}


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					VSEAConverter vc = new VSEAConverter();
					vc.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
