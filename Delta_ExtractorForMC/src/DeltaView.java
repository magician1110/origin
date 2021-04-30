import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;


public class DeltaView extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6214500446685406399L;
	public static final Logger _logger = Logger.getRootLogger();   
	
	JPanel jp = new JPanel();
	public static JTextField jfBeforeXML = new JTextField();
	public static JLabel jlBeforeXML = new JLabel("Before XML");
	public static JTextField jfAfterXML = new JTextField();
	public static JLabel jlAfterXML = new JLabel("After XML");
	public static JTextField jfAfterImg = new JTextField();
	public static JLabel jlAfterImg = new JLabel("After Image");
	JButton jbBeforeXML = new JButton("폴더 선택");
	JButton jbAfterXML = new JButton("폴더 선택");
	JButton jbAfterImg = new JButton("폴더 선택");
	JButton jStartXML = new JButton("Run");
	static String comboList[] = {"현대자동차", "모비스"};
	static JComboBox<String> combo = new JComboBox<>(comboList);	
	
	
	public DeltaView() {		
		DOMConfigurator.configure("temp\\log4j.xml");				
		_logger.debug("Processing starts.");
		setTitle("Delta Extractor for MC: 차분 번역 추출기");
		setSize(540, 290);
		setLocation(700, 200);
		setVisible(true);
		setLayout(null);
		setResizable(true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		
		jfBeforeXML.setSize(320, 22);
		jfBeforeXML.setLocation(90, 45);
		jfBeforeXML.setDropTarget(new DropTarget(){
			/**
			 * 
			 */
			
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("static-access")
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
                    }/*else if(!droppedFiles.get(0).getName().endsWith(".pdf")){
                        evt.rejectDrag();
                	}*/
				} catch (UnsupportedFlavorException | IOException ex) {
					Logger.getLogger(DeltaView.class.getName()).log(Level.DEBUG, null, ex);
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}			
            };
            @SuppressWarnings("static-access")
			@Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    @SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>) evt
                            .getTransferable().getTransferData(
                                    DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                    	jfBeforeXML.setText(file.getAbsolutePath());                    
                        //pdfOpenFile.setSelectedFile(file);
                    }
                }catch (UnsupportedFlavorException | IOException ex) {
                	Logger.getLogger(DeltaView.class.getName()).log(Level.DEBUG, null, ex);
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
		});
		
		jlBeforeXML.setLayout(null);
		jlBeforeXML.setLocation(15,5);
		jlBeforeXML.setSize(100,100);
		
		jfAfterXML.setSize(320,22);
		jfAfterXML.setLocation(90, 90);
		jfAfterXML.setDropTarget(new DropTarget(){
			/**
			 * 
			 */
			
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("static-access")
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
                    }/*else if(!droppedFiles.get(0).getName().endsWith(".pdf")){
                        evt.rejectDrag();
                	}*/
				} catch (UnsupportedFlavorException | IOException ex) {
					Logger.getLogger(DeltaView.class.getName()).log(Level.DEBUG, null, ex);
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}			
            };
            @SuppressWarnings("static-access")
			@Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    @SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>) evt
                            .getTransferable().getTransferData(
                                    DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                    	jfAfterXML.setText(file.getAbsolutePath());
                        //pdfOpenFile.setSelectedFile(file);
                    }
                }catch (UnsupportedFlavorException | IOException ex) {
                	Logger.getLogger(DeltaView.class.getName()).log(Level.DEBUG, null, ex);
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
		});
		
		jlAfterXML.setLayout(null);
		jlAfterXML.setSize(320,22);
		jlAfterXML.setLocation(15,90);
		
		jbBeforeXML.setSize(90,25);
		jbBeforeXML.setLocation(415,43);
		jbBeforeXML.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if (obj == jbBeforeXML) {
					JFileChooser jc = new JFileChooser(FileSystemView.getFileSystemView());
					jc.setAcceptAllFileFilterUsed(false);
					jc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					jc.setCurrentDirectory(new File("/"));
					
					int returnVal = jc.showOpenDialog(null);
					if(returnVal == JFileChooser.APPROVE_OPTION){									
						jfBeforeXML.setText(jc.getSelectedFile().getAbsolutePath());						 						
					}else if(returnVal == JFileChooser.CANCEL_OPTION){
						jfBeforeXML.setText("");
			        }
				}
				
			}
		});
		
		jbAfterXML.setSize(90,25);
		jbAfterXML.setLocation(415, 88);
		jbAfterXML.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if (obj == jbAfterXML) {
					JFileChooser jc = new JFileChooser(FileSystemView.getFileSystemView());
					jc.setAcceptAllFileFilterUsed(false);
					jc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					jc.setCurrentDirectory(new File("/"));
					
					int returnVal = jc.showOpenDialog(null);
					if(returnVal == JFileChooser.APPROVE_OPTION){									
						jfAfterXML.setText(jc.getSelectedFile().getAbsolutePath());						 						
					}else if(returnVal == JFileChooser.CANCEL_OPTION){
						jfAfterXML.setText("");
			        }
				}
				
			}
		});
		
		jfAfterImg.setSize(320,22);
		jfAfterImg.setLocation(90,135);
		jfAfterImg.setDropTarget(new DropTarget(){
			/**
			 * 
			 */
			
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("static-access")
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
                    }/*else if(!droppedFiles.get(0).getName().endsWith(".pdf")){
                        evt.rejectDrag();
                	}*/
				} catch (UnsupportedFlavorException | IOException ex) {
					Logger.getLogger(DeltaView.class.getName()).log(Level.DEBUG, null, ex);
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}			
            };
            @SuppressWarnings("static-access")
			@Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    @SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>) evt
                            .getTransferable().getTransferData(
                                    DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                    	jfAfterImg.setText(file.getAbsolutePath());
                        //pdfOpenFile.setSelectedFile(file);
                    }
                }catch (UnsupportedFlavorException | IOException ex) {
                	Logger.getLogger(DeltaView.class.getName()).log(Level.DEBUG, null, ex);
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
		});
		
		jlAfterImg.setLayout(null);
		jlAfterImg.setSize(320,22);
		jlAfterImg.setLocation(15,133);
		
		jbAfterImg.setSize(90,25);
		jbAfterImg.setLocation(415,133);
		jbAfterImg.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if (obj == jbAfterImg) {
					JFileChooser jc = new JFileChooser(FileSystemView.getFileSystemView());
					jc.setAcceptAllFileFilterUsed(false);
					jc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					jc.setCurrentDirectory(new File("/"));
					
					int returnVal = jc.showOpenDialog(null);
					if(returnVal == JFileChooser.APPROVE_OPTION){									
						jfAfterImg.setText(jc.getSelectedFile().getAbsolutePath());						 						
					}else if(returnVal == JFileChooser.CANCEL_OPTION){
						jfAfterImg.setText("");
			        }
				}
				
			}
		});
		
		combo.setSize(100,25);
		combo.setLocation(90, 180);
		
		//Delta xls param
		//tempPath = jfBeforeXML.getText();
		//tempPath = "C:\\Project\\MC_Delta_Extractor\\_Reference\\temp";
		
		jStartXML.setSize(70,25);
		jStartXML.setLocation(415,190);		
		jStartXML.addActionListener(new ActionListener() {
			
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e) { //out을 xsl에 만들어서그러내
				/*if(jfBeforeXML.getText().isEmpty() || jfAfterXML.getText().isEmpty() || jfAfterImg.getText().isEmpty()){					
					JOptionPane.showMessageDialog(null, "Please Select About Before XML, After XML, After Images", "Information", JOptionPane.INFORMATION_MESSAGE);
					return;
				}*///else{
					ad();
					new BackgroundWorker().execute();
				//}
				
			}
			
		});

		jp.setSize(540, 290);
		//jp.setBackground(Color.black);
		jp.add(jlBeforeXML);
		jp.add(jfBeforeXML);
		jp.add(jfAfterXML);
		jp.add(jlAfterXML);
		jp.add(jbBeforeXML);
		jp.add(jbAfterXML);
		jp.add(jfAfterImg);
		jp.add(jlAfterImg);
		jp.add(jbAfterImg);
		jp.add(jStartXML);
		jp.add(combo);
		jp.setLayout(null);		

		add(jp);
	}
	
	public static void ad(){

		
		File xsltDummyFile = new File("xslt\\dummy.xml");
		File carDummyFile = new File("car\\dummy.xml");
		File mobisDummyFile = new File("mobis\\dummy.xml");
		System.out.println("Complate @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		try {											
			if(combo.getSelectedItem().equals("현대자동차")){
				long start = System.currentTimeMillis();//시작 시점 계산
				//setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				if(xsltDummyFile.exists()){
					Common.deleteFile("xslt\\dummy.xml");
					System.out.println("해당 경로 : xslt\\dummy.xml 파일이 존재하여 디렉토리 안에 파일을 삭제 합니다.");
					Common.WriteXmlFile("xslt\\dummy.xml");
					System.out.println("해당 경로 : xslt\\dummy.xml 파일을 생성 합니다.");
				}else if(!xsltDummyFile.exists()){
					Common.WriteXmlFile("xslt\\dummy.xml");
					System.out.println("해당 경로 : xslt\\dummy.xml 파일을 생성 합니다.");
				}
				Common.transform("xslt\\dummy.xml", "xslt\\mergeA.xsl", "temp\\mergedA.xml");
				Common.transform("temp\\mergedA.xml", "xslt\\flattenA.xsl", "temp\\flattenA.xml");
				Common.transform("temp\\flattenA.xml", "xslt\\uniqueA.xsl", "temp\\uniqueA.xml");
				
				Common.transform("xslt\\dummy.xml", "xslt\\mergeB.xsl", "temp\\mergedB.xml");
				Common.transform("temp\\mergedB.xml", "xslt\\flattenB.xsl", "temp\\flattenB.xml");
				Common.transform("temp\\flattenB.xml", "xslt\\delta.xsl", "car\\delta.xml");
				//Common.batchEx(new File("get_size.exe"), "source\\car\\images","car\\image_size.xml");
				if(Common.fileCount("car\\images") > 0){
					Common.deleteFile("car\\images");
					System.out.println("해당 경로 : car\\image 파일이 존재하여 디렉토리 안에 파일을 삭제 합니다.");
				}
				Common.folderCopy(jfAfterImg.getText(), "car\\images");
				//------------------------------------------------------------------------------여기서 안들어짐
				Common.batchEx(new File("get_size.exe"), jfAfterImg.getText() ,"car\\image_size.xml");
				Common.CreateFolder("car\\template");
				Common.batchEx(new File("bandizip\\Bandizip.x64.exe"), "x", "-y","car\\template.idml", "car\\template");
				if(carDummyFile.exists()){
					Common.deleteFile("car\\dummy.xml");
					System.out.println("해당 경로 : car\\dummy.xml 파일이 존재하여 디렉토리 안에 파일을 삭제 합니다.");
					Common.WriteXmlFile("car\\dummy.xml");
					System.out.println("해당 경로 : car\\dummy.xml 파일이 존재하여 파일을 생성 합니다.");
				}else if(!carDummyFile.exists()){
					Common.WriteXmlFile("car\\dummy.xml");
					System.out.println("해당 경로 : car\\dummy.xml 파일이 존재하여 파일을 생성 합니다.");
				}
				Common.transform("car\\dummy.xml", "car\\xsl\\pagebuilder.xsl", "car\\pagebuilder.xml");
				Common.transform("car\\pagebuilder.xml", "car\\xsl\\package.xsl", "car\\dummy.xml");
				Common.batchEx(new File("bandizip\\Bandizip.x64.exe"), "c", "-y", "car\\delta.idml", "car\\out");						
				Common.deleteFile("car\\pagebuilder.xml");
				Common.deleteFile("car\\image_size.xml");
				//Common.deleteFile("car\\delta.xml"); delta.xml은 살려두기로 결정 함
				Common.deleteAllFiles(new File("car\\out"));
				Common.deleteAllFiles(new File("car\\template"));
				long end = System.currentTimeMillis(); //프로그램이 끝나는 시점 계산 
				double resultSE = (end - start)/1000.0;
				System.out.println( "실행 시간 : " + ( end - start )/1000.0 + "초"); //실행 시간 계산 및 출력	
				Common.openFolder(new File("car"));
				JOptionPane.showMessageDialog(null, "Hyundai Sucess \n Running time(Second) :" + resultSE, "INFORMATION_MESSAGE", JOptionPane.INFORMATION_MESSAGE);							
				}else{	
					long start = System.currentTimeMillis();//시작 시점 계산
					//setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));		
					if(xsltDummyFile.exists()){
						Common.deleteFile("xslt\\dummy.xml");
						System.out.println("해당 경로 : xslt\\dummy.xml 파일이 존재하여 디렉토리 안에 파일을 삭제 합니다.");
						Common.WriteXmlFile("xslt\\dummy.xml");
						System.out.println("해당 경로 : xslt\\dummy.xml 파일을 생성 합니다.");
					}else if(!xsltDummyFile.exists()){
						Common.WriteXmlFile("xslt\\dummy.xml");
						System.out.println("해당 경로 : xslt\\dummy.xml 파일을 생성 합니다.");
					}
					Common.transform("xslt\\dummy.xml", "xslt\\mergeA.xsl", "temp\\mergedA.xml");
					Common.transform("temp\\mergedA.xml", "xslt\\flattenA.xsl", "temp\\flattenA.xml");
					Common.transform("temp\\flattenA.xml", "xslt\\uniqueA.xsl", "temp\\uniqueA.xml");
					
					Common.transform("xslt\\dummy.xml", "xslt\\mergeB.xsl", "temp\\mergedB.xml");
					Common.transform("temp\\mergedB.xml", "xslt\\flattenB.xsl", "temp\\flattenB.xml");								
					Common.transform("temp\\flattenB.xml", "xslt\\delta.xsl", "mobis\\delta.xml");
					//Common.batchEx(new File("get_size.exe"), "source\\mobis\\images","mobis\\image_size.xml");								
					if(Common.fileCount("mobis\\images") > 0){
						Common.deleteFile("mobis\\images");		
						System.out.println("해당 경로 : mobis\\image 파일이 존재 하여 디렉토리 안에 파일을 삭제 합니다.");
					}	
					Common.folderCopy(jfAfterImg.getText(), "mobis\\images");								
					Common.batchEx(new File("get_size.exe"), jfAfterImg.getText(),"mobis\\image_size.xml");								
					//Common.batchEx("car\\compress.bat");
					Common.CreateFolder("mobis\\template");
					Common.batchEx(new File("bandizip\\Bandizip.x64.exe"), "x", "-y","mobis\\template.idml", "mobis\\template");
					if(mobisDummyFile.exists()){
						Common.deleteFile("mobis\\dummy.xml");
						System.out.println("해당 경로 : mobis\\dummy.xml 파일이 존재하여 디렉토리 안에 파일을 삭제 합니다.");
						Common.WriteXmlFile("mobis\\dummy.xml");
						System.out.println("해당 경로 : mobis\\dummy.xml 파일이 존재하여 파일을 생성 합니다.");
					}else if(!mobisDummyFile.exists()){
						Common.WriteXmlFile("mobis\\dummy.xml");
						System.out.println("해당 경로 : mobis\\dummy.xml 파일이 존재하여 파일을 생성 합니다.");
					}
					Common.transform("mobis\\dummy.xml", "mobis\\xsl\\pagebuilder.xsl", "mobis\\pagebuilder.xml");
					Common.transform("mobis\\pagebuilder.xml", "mobis\\xsl\\package.xsl", "mobis\\dummy.xml");
					Common.batchEx(new File("bandizip\\Bandizip.x64.exe"), "c", "-y", "mobis\\delta.idml", "mobis\\out");
					Common.deleteFile("mobis\\pagebuilder.xml");
					Common.deleteFile("mobis\\image_size.xml");
					//Common.deleteFile("mobis\\delta.xml");
					Common.deleteAllFiles(new File("mobis\\out"));
					Common.deleteAllFiles(new File("mobis\\template"));
					long end = System.currentTimeMillis(); //프로그램이 끝나는 시점 계산 
					double resultSE = (end - start)/1000.0;
					System.out.println( "실행 시간 : " + ( end - start )/1000.0 + "초"); //실행 시간 계산 및 출력
					Common.openFolder(new File("mobis"));
					JOptionPane.showMessageDialog(null, "Mobis Sucess \n Running time(Second) :" + resultSE, "INFORMATION_MESSAGE", JOptionPane.INFORMATION_MESSAGE);
				}/*else{
					JOptionPane.showMessageDialog(null, "Before XML, After XML의  경로가 서로 다릅니다.", "Error", JOptionPane.ERROR_MESSAGE);
				}*/
		} catch (Exception e2) {
			_logger.getLogger(DeltaView.class.getName()).log(Level.DEBUG, null, e2);	
			JOptionPane.showMessageDialog(null, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);						
		} finally {
			//setCursor(Cursor.getDefaultCursor());
			_logger.info("Processing ends");
		}
		
	
	}
	
}
