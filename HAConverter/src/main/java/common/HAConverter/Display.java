package common.HAConverter;

import java.awt.Container;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class Display extends JFrame{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Logger _logger = Logger.getRootLogger();  
	public static JTextField jf = new JTextField();
	public static JButton pathButton = new JButton("...");	
	
	public void view() {
		
		DOMConfigurator.configure("xsls\\log4j.xml");				
		_logger.debug("Processing starts.");
		setTitle("HAConverter");
		setBounds(700, 300, 400, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setLayout(null);
		setVisible(true);		
		setResizable(false);
		
		Container contentPane = this.getContentPane();
		JPanel pane = new JPanel();
		pane.setBounds(0, 0, 390, 200);
		//pane.setBackground(Color.BLUE);
		pane.setLayout(null);
		
		JLabel idmljl = new JLabel();
		idmljl.setText("IDML File");
		idmljl.setBounds(30, 0, 100, 40);
		

		/*buttonStart.setSize(50,50);
		buttonStart.setLocation(200, 200);*/
		pathButton.setBounds(330, 35, 25, 25);		
		pathButton.setMnemonic('S');
		pathButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if (obj == pathButton) {
					JFileChooser jc = new JFileChooser(FileSystemView.getFileSystemView());
					jc.setAcceptAllFileFilterUsed(false);
					jc.setFileSelectionMode(JFileChooser.FILES_ONLY);
					jc.setFileFilter(new FileNameExtensionFilter("InDesign Markup Document(.idml)", "idml"));
					jc.setCurrentDirectory(new File("/"));
					
					int returnVal = jc.showOpenDialog(null);
					if(returnVal == JFileChooser.APPROVE_OPTION){									
						jf.setText(jc.getSelectedFile().getAbsolutePath());
					}else if(returnVal == JFileChooser.CANCEL_OPTION){
						jf.setText("");
			        }
				}
				
			}
		});
		
		//final JTextField jf = new JTextField();
		jf.setBounds(30, 35, 290, 25);
		jf.setDropTarget(new DropTarget(){
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
                    }else if(!droppedFiles.get(0).getName().endsWith(".idml")){
                        evt.rejectDrag();
                	}
				} catch (UnsupportedFlavorException | IOException ex) {
					Logger.getLogger(Display.class.getName()).log(Level.DEBUG, null, ex);
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
                    	jf.setText(file.getAbsolutePath());                    
                        //pdfOpenFile.setSelectedFile(file);
                    }
                }catch (UnsupportedFlavorException | IOException ex) {
                	Logger.getLogger(Display.class.getName()).log(Level.DEBUG, null, ex);
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } 
            }
		});
		
		JButton startButton = new JButton("Run");
		startButton.setBounds(280,70,60,30);
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jf.getText().equals("") || jf.getText() == null){
					JOptionPane.showMessageDialog(null, "IDML File 선택되지 않았습니다.", "Alert", JOptionPane.INFORMATION_MESSAGE);
					return;
				}else{					
					runStart();	
				}								
				
			}
		});
		
		pane.add(startButton);
		pane.add(pathButton);
		pane.add(idmljl);
		pane.add(jf);
		
		contentPane.add(pane);
		contentPane.revalidate();
		contentPane.repaint(); //창을 키웠다가 줄여야 보이는 증상을 해결해줌
		
	}
	
	public String exFilePath(){
		File file = new File(jf.getText());
		String fileName = file.getName();
		int pos = fileName.lastIndexOf(".");
		String fileName2 = fileName.substring(0, pos);
		
		return fileName2;
	}
	
	public void runStart(){
		  try {			 			  
			  String onlyFilePath = jf.getText();			  
			  int lastIdx = onlyFilePath.lastIndexOf("\\");			  
			  String susbFilePath = onlyFilePath.substring(0, lastIdx+1);
			  //String top_level_Path = onlyFilePath.substring(0, 2);			  
			  File idmlFile = new File(onlyFilePath); 			  
			  idmlFile.renameTo(new File(susbFilePath + exFilePath() + ".zip"));	
			  //** * * * @param zipFileName 압축파일 * @param directory 압축 풀 폴더 *//* 
			  unZip(susbFilePath, exFilePath()+".zip", susbFilePath);
			  transform("xsls\\dummy.xml", "xsls\\001.xsl", "dummy.xml", jf.getText());	
		      //System.out.println("susbFilePath + exFilePath() 플러스 .zip 경로 ===" + susbFilePath + exFilePath() + ".zip");
		      deleteAllFile(susbFilePath + exFilePath() + ".zip");		      
			  batchEx(new File("bandizip\\Bandizip.x64.exe"), "c", "-y",  susbFilePath + exFilePath() + ".idml", susbFilePath + exFilePath());			  
			  deleteDirectory(new File(susbFilePath + exFilePath()));			  
			  JOptionPane.showMessageDialog(null, "Completed.", "Sucess", JOptionPane.INFORMATION_MESSAGE);
		} catch (Throwable e) {
			Logger.getLogger(Display.class.getName()).log(Level.DEBUG, null, e);
			JOptionPane.showMessageDialog(null, "Start Error.", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	public static void batchEx(File batchFilePath, String optionOne, String optionTwo, String outPath, String out) throws Exception{ 

		int result;
        InputStreamReader isr = null;
        BufferedReader br = null;
        //1번째 방법 외부 호출
		String[] command = {"cmd.exe", "/C", "Start", batchFilePath.getAbsolutePath(), optionOne, optionTwo, outPath, out};
		try {
	        Process p =  Runtime.getRuntime().exec(command); 
	        //2번째 방법 내부 호출
	/*        String command = "exportApppath.bat"; //해당 배치에서 applist.txt를 생성함
	        Process p =  Runtime.getRuntime().exec(command);*/        
	        isr = new InputStreamReader(p.getInputStream());
	        br = new BufferedReader(isr);
	        String line = null;     
	        while((line = br.readLine()) !=null){
	        	System.out.println("Line ====" + line);
	        } 	        
	        int waitFor = p.waitFor();
	        result = p.exitValue();
	        System.out.println("waitFor ==  " + waitFor + "result ====" + result );
		} catch (Exception e) {
			Logger.getLogger(Display.class.getName()).log(Level.DEBUG, null, e);
			JOptionPane.showMessageDialog(null, "Batch Error.", "Error", JOptionPane.ERROR_MESSAGE);
		}

    }
	
	/** * 압축풀기 메소드 * * @param zipFileName 압축파일 * @param directory 압축 풀 폴더 */ 
	public boolean unZip(String zipPath, String zipFileName, String zipUnzipPath) {		
		// 파일 정상적으로 압축이 해제가 되어는가. 
		boolean isChk = false; 
		// 해제할 홀더 위치를 재조정 
		zipUnzipPath = zipUnzipPath + zipFileName.replace(".zip", ""); // zip 파일 
		File zipFile = new File(zipPath + zipFileName); 
		FileInputStream fis = null; 
		ZipInputStream zis = null; 
		ZipEntry zipentry = null; 
		try { // zipFileName을 통해서 폴더 만들기 
			if (makeFolder(zipUnzipPath)) {
				System.out.println("폴더를 생성했습니다"); 
				} // 파일 스트림 
			try {
				fis = new FileInputStream(zipFile);
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(null, "unZip Error.", "Error", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			} 
			// Zip 파일 스트림 
			zis = new ZipInputStream(fis, Charset.forName("UTF-8")); 
			// 압축되어 있는 ZIP 파일의 목록 조회
			try {
				while ((zipentry = zis.getNextEntry()) != null) {
					String filename = zipentry.getName(); 
					System.out.println("filename(zipentry.getName()) => " + filename); 
					File file = new File(zipUnzipPath, filename); 
					// entiry가 폴더면 폴더 생성 
					if (zipentry.isDirectory()) {
						System.out.println("zipentry가 디렉토리입니다."); 
						file.mkdirs(); 
						} else {
							// 파일이면 파일 만들기
							System.out.println("zipentry가 파일입니다.");
							try {
								createFile(file, zis); 
								} catch (Throwable e) {
									e.printStackTrace(); 
									JOptionPane.showMessageDialog(null, "unZip Error 2step.", "Error", JOptionPane.ERROR_MESSAGE);
									} 
							} 
					}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} isChk = true; 
		} finally {
			if (zis != null) {
				try {
					zis.close(); 
					} catch (IOException e) {
						
					} 
				}
			if (fis != null) {
				try { 
					fis.close(); 
					} catch (IOException e) {
						
					} 
				} 
			} return isChk;
		
	}
	
	/** * @param folder - 생성할 폴더 경로와 이름 */ 
	private boolean makeFolder(String folder) {
		if (folder.length() < 0) {
			return false; 
			} String path = folder; 
			// 폴더 경로 
			File Folder = new File(path); 
			// 해당 디렉토리가 없을경우 디렉토리를 생성합니다. 
			if (!Folder.exists()) {
				try {
					Folder.mkdir(); 
					// 폴더 생성합니다. 
					System.out.println("폴더가 생성되었습니다."); 
					} catch (Exception e) {
						e.getStackTrace(); 
					} 
			} else { 
				System.out.println("이미 폴더가 생성되어 있습니다."); 
			} 
			return true;
	}

	/**
	* 파일 만들기 메소드
	* @param file 파일
	* @param zis Zip스트림
	*/
	private static void createFile(File file, ZipInputStream zis) throws Throwable {
		//디렉토리 확인
		File parentDir = new File(file.getParent());
		//디렉토리가 없으면 생성하자
		if (!parentDir.exists()) {
		parentDir.mkdirs();
		}
		//파일 스트림 선언
		try (FileOutputStream fos = new FileOutputStream(file)) {
			byte[] buffer = new byte[256];
			int size = 0;
			//Zip스트림으로부터 byte뽑아내기
			while ((size = zis.read(buffer)) > 0) {
			//byte로 파일 만들기
			fos.write(buffer, 0, size);
		  }
		} catch (Throwable e) {
		throw e;
		}
	}
		
	public static void transform(String sourcePath, String xsltPath, String resultDir, String jfText) {
		try {			
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(new File(xsltPath)));
			transformer.setParameter("filename", jfText);
			transformer.setOutputProperty(OutputKeys.ENCODING,"utf-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			//transformer.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, CDATA_XML_ELEMENTS);
			transformer.transform(new StreamSource(new File(sourcePath)), new StreamResult(new File(resultDir)));
		} catch (TransformerException e) {
			Logger.getLogger(Display.class.getName()).log(Level.DEBUG, null, e);
			JOptionPane.showMessageDialog(null, "Transform Error.", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	public static void deleteAllFile(String path) {
		
		File file = new File(path); // 폴더내 파일을 배열로 가져온다.
		System.out.println("filePath ===" + file.getPath());
		if( file.exists() ){
			if(file.delete()){
				System.out.println("파일삭제 성공"); 
				}else{
					System.out.println("파일삭제 실패"); 
					JOptionPane.showMessageDialog(null, "File deletion failed.", "Error", JOptionPane.ERROR_MESSAGE);
					} 
			}else{
				System.out.println("파일이 존재하지 않습니다.");
				JOptionPane.showMessageDialog(null, "The file does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
				}

	}
	
	//해당 폴더 기준으로 모든 파일 삭제
	public static boolean deleteDirectory(File path){ 
		if(!path.exists()){
			System.out.println("경로를 입력하지 않았습니다.");
			JOptionPane.showMessageDialog(null, "경로를 입력하지 않았습니다.", "Error", JOptionPane.ERROR_MESSAGE);
			return false; 
			} 
		File[] files = path.listFiles(); 
		for (File file : files){
			if (file.isDirectory()){
				deleteDirectory(file);
				System.out.println("Has been deleted.");
				}else{ 
					file.delete(); 
				} 
			} return path.delete(); 
	}

}
