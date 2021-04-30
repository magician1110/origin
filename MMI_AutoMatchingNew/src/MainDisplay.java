import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import com.saxonica.ee.schema.SymbolSpace;
import com.sun.xml.internal.ws.api.server.Container;

public class MainDisplay extends JFrame{	
	
	/**
	 * 
	 */	
	private static final long serialVersionUID = 1L;
	Extraction et = new Extraction();	
	JPanel panel = new JPanel();
	JTextArea logTextArea = new JTextArea();
	JPanel logPanel = new JPanel();	
	ImageIcon headerImgIcon = new ImageIcon(MainDisplay.getResourceAsFile("Ast.jpg").getPath());	
	JLabel headerLabelIcon = new JLabel(headerImgIcon);
	JButton connectJbtn = new JButton("연결 확인(ADB Device)");
	JScrollPane scrollPane = new JScrollPane(logTextArea);	
	JButton appAndDpAndXmlMoveJbtn = new JButton("APP 추출 & Decompile & XML 파일 이동");
	JButton indisignXmlCompareJbtn = new JButton("인디자인과 XML 비교하여 추출");
	JButton stringNameLanguageJbtn = new JButton("String Name.xml & LanguageStr 추출");
	JButton applistEt = new JButton("APP List.txt로 추출");
	JButton mmiResFolderMove = new JButton("...");
	
	static JFrame f; 
    static JProgressBar b; 
	String connectMessage = "";
	//String connectMessage2 = "";
    static String folderPath = "";
    
    public void Display(){     	
    	this.setTitle("Main display");
    	this.setSize(390,630);
    	this.setLocation(700, 80);
    	this.setResizable(false);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setLayout(null);    
    	 String path= System.getProperty("user.dir");    	 
    	//Panel      	
		panel.setLayout(null);
		//panel.setBorder(tittb);
		panel.setBounds(0,0,390,600); //좌우, 위아래, 넓이, 높이
		panel.setBackground(Color.white);
    	this.add(panel);
		
    	//Main ImageIcon  headerLabelIcon       	    	
		headerLabelIcon.setBounds(20, 30, 350, 50);		
		panel.add(headerLabelIcon);			
		
    	
    	//log		
		logPanel.setBounds(20,370,350,210); //좌우, 위아래, 넓이, 높이
		logPanel.setBackground(Color.WHITE);
		
		//logTextArea		
		logTextArea.setLineWrap(true);
		logTextArea.setWrapStyleWord(true);
		logTextArea.setBackground(Color.WHITE);		
			
		scrollPane.setBackground(Color.WHITE);		
		scrollPane.setPreferredSize(new Dimension(350, 205));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		logPanel.add(scrollPane);
		panel.add(logPanel);
		
		//ConnectJbtn Button 		
    	connectJbtn.setBounds(20, 120, 350, 35);
    	connectJbtn.setBackground(Color.WHITE);
    	
    	connectMessage = execCommand("adb devices"); //만약 환경 변수를 설정 안해주게 되면 배치파일로 해당 경로로 이동 후 adb devices를 치게 하면됨
    	
    	connectJbtn.addActionListener(new ActionListener() {
    			
			@Override
			public void actionPerformed(ActionEvent e) {				
				if(connectMessage.contains("	device")){										
					logTextArea.setText(connectMessage + "Connected");
					connectJbtn.setBackground(Color.green);					
				}else{					
					logTextArea.setText(connectMessage + "Not Connected");
					connectJbtn.setBackground(Color.RED);	
				}
				
			}			
		});
    	panel.add(connectJbtn);
    	
    	//BTN appAndDpAndXmlMoveJbtn    	
    	appAndDpAndXmlMoveJbtn.setBounds(20, 170, 350, 35);
    	appAndDpAndXmlMoveJbtn.setBackground(Color.WHITE);
    	appAndDpAndXmlMoveJbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					long start = System.currentTimeMillis(); //시작하는 시점 계산
					et.getStringNameList();    //ok
					et.fileWrite();    //ok 패키지리스트를 만들어냄 ==> 패키징시 => 아무도모르는temp
					et.batchEx();  //ok 앱리스트를 만들어냄	split를 통해 NEW앱리스트도 만들어냄  패키징시 => 아무도모르는temp
					et.pullFileList(); //newApplist에서 데이터를 읽어냄  
					et.pullFileWriter(); //pullFileList만들어냄		 패키징시 => 아무도모르는temp
					et.batchEx2(); //pullFileList를 읽어냄 
					et.deCompile2(); //apk source를 읽어냄										
					et.recopy();
					
					long end = System.currentTimeMillis(); //프로그램이 끝나는 시점 계산
					double rt = (end - start)/1000.0;
					logTextArea.setText("진핼률 : " + rt);
					logTextArea.setText("App 추출 & Decompile & XML 파일이동 \n 실행시간 : " + rt);					
				} catch (Exception e2) {
					e2.printStackTrace();
				}				
				
			}
		});    	
    	panel.add(appAndDpAndXmlMoveJbtn);
    	
    	//indisignXmlCompareJbtn    	
    	indisignXmlCompareJbtn.setBounds(20, 220, 320, 35);//좌우, 위아래, 넓이, 높이 (340, 220, 30, 35
    	indisignXmlCompareJbtn.setBackground(Color.WHITE);
    	indisignXmlCompareJbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
	            try {
					long start = System.currentTimeMillis(); //시작하는 시점 계산
					Extraction.transform("C:\\MMI_AutoMatching\\xsls\\dummy.xml", "C:\\MMI_AutoMatching\\xsls\\01-MMI-merged.xsl", "C:\\MMI_AutoMatching\\temp\\dummy.xml");					
					Extraction.transform("C:\\MMI_AutoMatching\\temp\\01-MMI-merged.xml", "C:\\MMI_AutoMatching\\xsls\\02-MMI-clean_tag.xsl", "C:\\MMI_AutoMatching\\temp\\02-MMI-clean_tag.xml");					
					Extraction.transform("C:\\MMI_AutoMatching\\temp\\02-MMI-clean_tag.xml", "C:\\MMI_AutoMatching\\xsls\\03-MMI-MMI_extract.xsl", "C:\\MMI_AutoMatching\\temp\\03-MMI-MMI_extract.xml");					
					Extraction.transform("C:\\MMI_AutoMatching\\temp\\03-MMI-MMI_extract.xml", "C:\\MMI_AutoMatching\\xsls\\04-MMI-deep-equal.xsl", "C:\\MMI_AutoMatching\\temp\\04-MMI-deep-equal.xml");					
					Extraction.transform("C:\\MMI_AutoMatching\\temp\\04-MMI-deep-equal.xml", "C:\\MMI_AutoMatching\\xsls\\05-MMI-mmi_distinct.xsl", "C:\\MMI_AutoMatching\\temp\\05-MMI-mmi_distinct.xml");					
					//DC_Export		
					Extraction.transform("C:\\MMI_AutoMatching\\xsls\\dummy.xml", "C:\\MMI_AutoMatching\\xsls\\00-DC-filename_export.xsl", "C:\\MMI_AutoMatching\\temp\\00-DC-filename_export.xml"); //메모리8G					
					Extraction.transform("C:\\MMI_AutoMatching\\temp\\00-DC-filename_export.xml", "C:\\MMI_AutoMatching\\xsls\\01-DC-default_compare.xsl", "C:\\MMI_AutoMatching\\temp\\01-DC-default_compare.xml"); //메모리4G					
					Extraction.transform("C:\\MMI_AutoMatching\\temp\\01-DC-default_compare.xml", "C:\\MMI_AutoMatching\\xsls\\02-DC-target_compare.xsl", "C:\\MMI_AutoMatching\\temp\\02-DC-target_compare.xml");					
					Extraction.transform("C:\\MMI_AutoMatching\\temp\\02-DC-target_compare.xml", "C:\\MMI_AutoMatching\\xsls\\03-DC-grouping.xsl", "C:\\MMI_AutoMatching\\temp\\03-DC-grouping.xml");					
					Extraction.transform("C:\\MMI_AutoMatching\\temp\\03-DC-grouping.xml", "C:\\MMI_AutoMatchingNew\\xsls\\04-DC-MMI-list-compare.xsl",  "C:\\MMI_AutoMatching\\temp\\MMI-list-compare.xml");
					
					long end = System.currentTimeMillis(); //프로그램이 끝나는 시점 계산
					double rt = (end - start)/1000.0;
					logTextArea.setText("인디자인 xml과 비교 \n 실행시간 : " + rt);
					JOptionPane.showMessageDialog(null, "Complate.", "Good", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error.", "Error", JOptionPane.ERROR_MESSAGE);
				}			        		     
			}
		});
    	panel.add(indisignXmlCompareJbtn);
    	
    	//stringNameLanguageJbtn        	
    	stringNameLanguageJbtn.setBounds(20, 270, 350, 35);
    	stringNameLanguageJbtn.setBackground(Color.WHITE);
    	stringNameLanguageJbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					long start = System.currentTimeMillis(); //시작하는 시점 계산
					Extraction.transform("C:\\MMI_AutoMatching\\xsls\\dummy.xml", "C:\\MMI_AutoMatching\\xsls\\00-DC-filename_export.xsl", "C:\\MMI_AutoMatching\\temp\\00-DC-filename_export.xml"); //메모리 8G
					Extraction.transform("C:\\MMI_AutoMatching\\temp\\00-DC-filename_export.xml", "C:\\MMI_AutoMatching\\xsls\\01-SN-str_name_list.xsl", "C:\\MMI_AutoMatching\\temp\\01-SN-str_name_list.xml"); //메모리 8G					
					Extraction.transform("C:\\MMI_AutoMatching\\temp\\01-SN-str_name_list.xml", "C:\\MMI_AutoMatching\\xsls\\02-SN-str_name_compare.xsl", "C:\\MMI_AutoMatching\\temp\\02-SN-str_name_compare.xml"); //메모리 512M에서 2048m					
					Extraction.transform("C:\\MMI_AutoMatching\\temp\\02-SN-str_name_compare.xml", "C:\\MMI_AutoMatching\\xsls\\03-SN-MMI_list_compare.xsl", "C:\\MMI_AutoMatching\\temp\\MMI-list-compare.xml");					
					Extraction.runVBS();
					long end = System.currentTimeMillis(); //프로그램이 끝나는 시점 계산
					double rt = (end - start)/1000.0;
					logTextArea.setText("stringName.xml / LanguageStr 추출 \n 실행시간 : " + rt);
					JOptionPane.showMessageDialog(null, "Complate.", "Good", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			
			}
		});
    	panel.add(stringNameLanguageJbtn);    	
    	
    	//applistEtJBtn        	
    	applistEt.setBounds(20, 320, 350, 35); //좌우, 위아래, 넓이, 높이
    	applistEt.setBackground(Color.WHITE);
    	applistEt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {//adb shell pm list packages > applist.txt
				try {
					execCommand("C:\\MMI_AutoMatchingNew\\functions\\appListEt.bat");
					JOptionPane.showMessageDialog(null, "Complate.", "Good", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e2) {
					e2.printStackTrace();	
					JOptionPane.showMessageDialog(null, "Fail.", "Error", JOptionPane.INFORMATION_MESSAGE);
				}

				
			}
		});
    	
    	panel.add(applistEt);  
    	
    	//applistEtJBtn
    	
    	mmiResFolderMove.setBounds(340, 220, 30, 35); //좌우, 위아래, 넓이, 높이 20, 220, 300, 35)
    	mmiResFolderMove.setBackground(Color.WHITE);
    	mmiResFolderMove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {	
				try {
					openFolder("C:\\MMI_AutoMatchingNew");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();					
				}
			}
		});
    	
    	panel.add(mmiResFolderMove);   
    	
    	this.setVisible(true); //프레임 
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
    
	public String execCommand(String cmd){
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			String line = null;
			StringBuffer readBuffer = new StringBuffer();
			
			while ((line = br.readLine()) != null) {
				readBuffer.append(line);
				readBuffer.append("\n");
			}
			
			return readBuffer.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
 
	
    public static String jFileChooserUtil(){
        
        //String folderPath = "";
        
        JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); // 디렉토리 설정
        chooser.setCurrentDirectory(new File("/")); // 현재 사용 디렉토리를 지정
        chooser.setAcceptAllFileFilterUsed(true);   // Fileter 모든 파일 적용 
        chooser.setDialogTitle("타이틀"); // 창의 제목
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); // 파일 선택 모드
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Binary File", "cd11"); // filter 확장자 추가
        chooser.setFileFilter(filter); // 파일 필터를 추가
        
        int returnVal = chooser.showOpenDialog(null); // 열기용 창 오픈
        
        if(returnVal == JFileChooser.APPROVE_OPTION) { // 열기를 클릭 
            folderPath = chooser.getSelectedFile().toString();
        }else if(returnVal == JFileChooser.CANCEL_OPTION){ // 취소를 클릭
            System.out.println("cancel"); 
            folderPath = "";
            
        }
        
        return folderPath;
        
    }
    
    public void openFolder(String folderPath) throws IOException{
        Desktop desktop = Desktop.getDesktop();
        File dirToOpen = null;
        try {
            dirToOpen = new File(folderPath);
            desktop.open(dirToOpen);
        } catch (IllegalArgumentException iae) {
            System.out.println("File Not Found");
        }
    }
    
}	
