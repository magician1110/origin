package com.mobis.remark.main;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileSystemView;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;

public class Remark extends JFrame {
	private static JFrame  frame = new JFrame ();		
	private static File[] selectPath = {};
	private static String[] stringPath = {};
	private static JTextPane pathText = new JTextPane();
	private static JButton startBtn = new JButton("Count");
	private static JButton pathBtn = new JButton("...");			
	private static Properties prop = new Properties();
	private static Logger _logger = Logger.getLogger(Remark.class.getName());
	
	public static void main(String[] args) throws IOException {	
		AnnGUI();
		//Annotator();
	}
	
	public static void AnnGUI() {			
		//DOMConfigurator.configure(Remark.getResourceAsFile("log4j.xml").getPath());	
		DOMConfigurator.configure("log\\log4j.xml");		
		try {
			prop.load(new FileInputStream("annotationINI.ini"));						
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if(pathText.getText() != null || !pathText.getText().isEmpty()) {
			pathText.setText(prop.getProperty("sourceFilePath"));	
		}else {
			pathText.setText("");	
		}
		
		startBtn.setBounds(140, 30, 70, 25);
		pathBtn.setBounds(308, 5, 30, 20);
		pathText.setBounds(10,5,297,20);
		//btn1.setPreferredSize(new Dimension(45, 28));
		startBtn.addActionListener(new ActionListener() {
			@Override			
			public void actionPerformed(ActionEvent e) {
				if(pathText.getText().isEmpty() || pathText.getText().equals("") || pathText.getText() == null) {					
					JOptionPane.showMessageDialog(null, "Please select a .pdf File.", "Execution Fail", JOptionPane.ERROR_MESSAGE);
					return;				
				}else {
					try {	
						_logger.info("Processing Starts.");					
						Annotator();
						_logger.info("Processing End.");						
						JOptionPane.showMessageDialog(null, "succeeded in counting.");	//화면 가운데에 메시지출력
					} catch (Exception e2) {
						e2.printStackTrace();
						JOptionPane.showMessageDialog(null, "Please contact the administrator.", "Execution Fail", JOptionPane.ERROR_MESSAGE);
					}	
				}

			}
		});
		
		pathBtn.addActionListener(new ActionListener() {	//btn1을 눌렀을 때 효과추가
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				JFileChooser jc = new JFileChooser(FileSystemView.getFileSystemView());
				jc.setMultiSelectionEnabled(true);
				jc.setAcceptAllFileFilterUsed(false);
				jc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jc.setCurrentDirectory(new File(pathText.getText()));
				
				int returnVal = jc.showOpenDialog(frame);
				
				if(returnVal == JFileChooser.APPROVE_OPTION){
				
					selectPath = jc.getSelectedFiles();						
					
					String fileNames = "";
		               for(File file: selectPath){
		                   fileNames += file.getName() + " ";
		                   //System.out.println("fileNames == : " + fileNames);
		                }		               					
														            
					pathText.setText(fileNames);
					
					if(obj == selectPath) {
						if(pathText.getText().isEmpty() || pathText.getText().equals("") 
								|| pathText.getText() == null) {					
							JOptionPane.showMessageDialog(null, "Please select a .pdf File.", "Execution Fail", JOptionPane.ERROR_MESSAGE);
							return;				
						}												
					}
					try {
						prop.setProperty("sourceFilePath", pathText.getText());
						prop.store(new FileOutputStream("annotationINI.ini"), null);
					
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}else if(returnVal == JFileChooser.CANCEL_OPTION){
					pathText.setText("");
				}

			}
		});

		pathText.setDropTarget(new DropTarget(){
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
					if(!droppedFiles.get(0).getName().endsWith(".pdf")){
                        evt.rejectDrag();
                	}
				} catch (UnsupportedFlavorException | IOException ex) {					
                    JOptionPane.showOptionDialog(null, "Error", "Error", JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, new Object[] { "   OK   "}, JOptionPane.ERROR_MESSAGE);
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
                    if(!droppedFiles.get(0).getName().endsWith(".pdf")) {
                    	JOptionPane.showOptionDialog(null, "It is not a .pdf", "It is not a .pdf.", JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, new Object[] { "   OK   "}, JOptionPane.ERROR_MESSAGE);
                    	return;
                    }else {                       	
                    	for (File file : droppedFiles) { 
                    		if(file.isFile()) {
                    			selectPath = file.listFiles();
                    			JOptionPane.showOptionDialog(null, "It will be updated in the future.", "It will be updated in the future.", JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, new Object[] { "   OK   "}, JOptionPane.ERROR_MESSAGE);
                    			return;
                    			//System.out.println("zzz" + selectPath);
                    		} 		                
                    		pathText.setText(file.getAbsolutePath());                        	                    
                        }
//                    	for(int i = 0; i < droppedFiles.size(); i++) {          
//                    		if(droppedFiles.get(i).getAbsoluteFile().listFiles() == null) {
//                    			System.out.println();
//                    		}
//                    		selectPath = droppedFiles.listIterator().next().listFiles();
//                			System.out.println("selectPath == : " + selectPath);                		
//                    		
//                    		pathText.setText(droppedFiles.get(i).getAbsolutePath());
//                    	}
                    }
                    
                }catch (UnsupportedFlavorException | IOException ex) {                	
                	JOptionPane.showOptionDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE, null, new Object[] { "   OK   "}, JOptionPane.ERROR_MESSAGE);
                }
            }
		});

		frame.add(pathText);
		frame.add(startBtn);
		frame.add(pathBtn);
		
		frame.getContentPane().setLayout(null);
		frame.setTitle("Estimate automation");
		frame.setVisible(true);
		frame.setSize(360, 100);	
		//frame.setResizable(false);
		frame.setLocationRelativeTo(null);	
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);	
		
	}
	
	public static void Annotator() throws IOException {
		PDDocument document;			
		ArrayList<ArrayList> FilePageList = new ArrayList<ArrayList>();			
		ArrayList<String> fileSet = new ArrayList<>();		
		ArrayList<String> OnlyName = new ArrayList<>();
		HashSet<String> fileSplitNameArr = new HashSet<>();	
		HashMap<String,String> useMap = new HashMap<String,String>();
		ArrayList<String> ResultIndex = new ArrayList<String>();
		PDPage page;
		int numPages = 0;
		
		//String[] files = {"F:\\Project\\mobis\\TEST3.pdf", "F:\\Project\\mobis\\TEST2.pdf"};
		//String path = "F:\\Project\\mobis\\JK1_ENG\\";
		//		String[] files = {path + "001_JK1_ENG_210202_Check.pdf", path + "001_JK1_ENG_210223_Check.pdf", path + "002_JK1_ENG_210202_Check.pdf",
		//				path + "002_JK1_ENG_210223_Check.pdf", path + "003_JK1_ENG_210202_Check.pdf", path + "003_JK1_ENG_210223_Check.pdf", path + "004_JK1_ENG_210223_Check.pdf"};				
		String fileName = "";		
		String fileSplitName = "";	
		Path p; 		
		for(int idx = 0; idx < selectPath.length; idx++) {			
			p = Paths.get(selectPath[idx].getAbsolutePath());
			OnlyName.add(p.getFileName().toString());			
			fileSplitName = p.getFileName().toString().substring(0,3);	
			fileSplitNameArr.add(fileSplitName);					
			fileName = selectPath[idx].getAbsolutePath();
			fileSet.add(fileName);

			document = PDDocument.load(new File(fileName));
			numPages = document.getNumberOfPages();

			ArrayList<Integer> arrAnnotationPage = new ArrayList<>();
			for(int n = 0; n < numPages; n++) {								
				page = document.getPage(n);  				
				List<PDAnnotation> annotations = page.getAnnotations();
				
				int annotNum = 0;

				for (int x = 0; x < annotations.size(); x++) {					
					if (annotations.get(x).getSubtype().equals("Link") || annotations.get(x).getSubtype().equals("Popup")) {
						
					} else {						
						annotNum ++;
					}
				}
				if (annotNum > 0) {	
					//arrAnnotationPage.add(Integer.toString(n+1));					
					arrAnnotationPage.add(n+1);
				}
			}
//			//중복제거 하는 구문
//			for(String item : arrAnnotationPage) {
//				System.out.println("arrAnnotationPage === : " + arrAnnotationPage);
//				if(!FilePageListT.contains(item)) {
//					FilePageListT.add(item);
//					System.out.println("FilePageListT==== : " + FilePageListT);
//				}
//			}
			FilePageList.add(arrAnnotationPage);
			//FilePageList.add(arrAnnotationPage);
			//FilePageListH.add(arrAnnotationPage);
		}

		File file = new File("F:\\Project\\mobis\\JK1_ENG\\Report!!" + ".txt");		
		FileWriter writer = new FileWriter(file,false);								

		for(int i =0; i < OnlyName.size(); i++) {
			if(OnlyName.size() > 0) {
				String str1 = OnlyName.get(i).substring(0,3);

				for(int j = 0; j < OnlyName.size(); j++) {					
					
					String str2 = OnlyName.get(j).substring(0,3);
					if(str1.equals(str2)) {						
						String szfronttmp = FilePageList.get(i).toString();						
						String szbacktmp = FilePageList.get(j).toString();

						if(!szfronttmp.equals(szbacktmp))
						{	
							ArrayList<Integer> tmpNumFrontList = new ArrayList<>(FilePageList.get(i));
							ArrayList<Integer> tmpNumBackList = new ArrayList<>(FilePageList.get(j));						
							Set<Integer> set = new LinkedHashSet<>();							
							//set.addAll(FilePageList.get(i));
							//System.out.println("FilePageList.get(i) === : " + FilePageList.get(i));
						    //set.addAll(FilePageList.get(j));
						    //System.out.println("FilePageList.get(j) === : " + FilePageList.get(j));
							set.addAll(tmpNumFrontList);
							set.addAll(tmpNumBackList);							
							List<Integer> mergeList = new ArrayList<>(set);	
							//useMap.put(OnlyName.get(i), mergeList.toString());
							System.out.println("mergeList === : " + mergeList);
							if(!ResultIndex.contains(OnlyName.get(i).substring(0,3)))
							{ //여기서 빠지내 ~~~~~~~~~~~~~~~~~~								
								ResultIndex.add(OnlyName.get(i).substring(0,3));
								System.out.println("ResultIndex ===" + ResultIndex);
								//useMap.put(OnlyName.get(i), mergestr);
								useMap.put(OnlyName.get(i), mergeList.toString());
								System.out.println("userMap 111111========== : " + useMap);
								
							}							
						}												

					} 

				}  
								
				if(!ResultIndex.contains(OnlyName.get(i).substring(0,3)))
				{
					ResultIndex.add(OnlyName.get(i).substring(0,3));					
					useMap.put(OnlyName.get(i), FilePageList.get(i).toString());
					System.out.println("userMap 2222222========== : " + useMap);
				}
							
			}

		}
		//System.out.println("userMap ========== : " + useMap);
		//System.out.println("useMap.keySet() === : " + useMap.keySet()); 
		try {
			for(String key : useMap.keySet())
			{
				String szvalue = useMap.get(key);
				//System.out.println("@@@@@@@@@@@@@ == : " + key +  " : " + szvalue);
				writer.write(key +  " : " + szvalue);						
				writer.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(writer != null) writer.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
//		for(String key : useMap.keySet())
//		{
//			String szvalue = useMap.get(key);
//		}

	}

}

