import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import jdk.internal.org.xml.sax.SAXParseException;
import net.sf.saxon.functions.Count;
import net.sf.saxon.functions.Replace;
import net.sf.saxon.trans.Timer;

public class MMI_3_WayChecker {
	
	public static HashMap<String, ArrayList<String>> languageCodes = new HashMap<>();
	public static ArrayList<String> language = null;
	public static ArrayList<String> language2 = null;
	public static String selectItem = null;
	public static String selectItem2 = null;
	private static String ExcelPath = null;
	//public static int checkYes = 2;
	//public static int checkNo = 0;	 
	public static File saveFile;
	public static String savePathName;	
	public static String filePathS = "";	
	static File getFile;
	
	public static void Gui(){
		//public static JButton jbt_save = new JButton("Save");		
		Frame f = new Frame("MMI 3-Way Checker");
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
		
		
		Font statusFont = new Font("TimesRoman", Font.BOLD, 12);
		Label statusLabel = new Label();
		statusLabel.setAlignment(Label.CENTER);
		statusLabel.setSize(100, 150);
		statusLabel.setLocation(30, 130);				
		statusLabel.setFont(statusFont);
		//statusLabel.setBackground(Color.black);
				
		ImageIcon img = new ImageIcon(MMI_3_WayChecker.getResourceAsFile("Ast.jpg").getPath());		
		JLabel lbImage1 = new JLabel(img);
		lbImage1.setSize(430, 50);
		lbImage1.setLocation(15, 40);
//-------------------------------------------------------------------------------------------------		
		JLabel firstQ = new JLabel();
		firstQ.setText("Google ID Excel");
		firstQ.setFont(new Font("Serif", Font.BOLD, 16));
		firstQ.setForeground(Color.DARK_GRAY);
		firstQ.setSize(120, 40);
		firstQ.setLocation(25, 105);
		
		TextField txtF = new TextField();
		txtF.setSize(260, 22);
		txtF.setLocation(145, 115);
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
					
					if(txtF.getText().isEmpty()){
						return;
					}else{
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setAcceptAllFileFilterUsed(false);
						FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel File", "xls");
						fileChooser.addChoosableFileFilter(filter);
						fileChooser.setCurrentDirectory(new File(txtF.getText()));
						fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
						//fileChooser.setMultiSelectionEnabled(true);
						int returnVal = fileChooser.showOpenDialog(null);					

						if (returnVal == JFileChooser.APPROVE_OPTION) {
							getFile = fileChooser.getSelectedFile();
							String filesPath = getFile.getAbsolutePath();							
			
							ExcelPath = filesPath;
							
							fileChooser.setCurrentDirectory(new File(txtF.getText()));
							txtF.setText(ExcelPath);
						}
					}
				}
				
			}
		});
		//txtF.setEnabled(false);
		
		Button pathBtn = new Button("...");
		pathBtn.setSize(22, 22);
		pathBtn.setLocation(405, 115);

		pathBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
								
				if ((Button) obj == pathBtn) {					
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setAcceptAllFileFilterUsed(false);
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel File", "xls");
					fileChooser.addChoosableFileFilter(filter);
					fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
					fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					//fileChooser.setMultiSelectionEnabled(true);
					int returnVal = fileChooser.showOpenDialog(null);					

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						getFile = fileChooser.getSelectedFile();
						String filesPath = getFile.getAbsolutePath();							
		
						ExcelPath = filesPath;						
						txtF.setText(ExcelPath);
					}
				}
			}
		});
		
/*		JCheckBox yesJck = new JCheckBox();
		yesJck.setSize(20,20);
		yesJck.setLocation(220, 117);			
		yesJck.addItemListener(new ItemListener() {		
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					checkYes = ItemEvent.SELECTED;									
				}else if(e.getStateChange() == ItemEvent.DESELECTED){
					checkYes = ItemEvent.DESELECTED;					
				}
			}
		});		*/
		
		JLabel secondQ = new JLabel();
		secondQ.setText("Select your language code");
		secondQ.setFont(new Font("Serif", Font.BOLD, 16));
		secondQ.setForeground(Color.DARK_GRAY);
		secondQ.setSize(300, 50);
		secondQ.setLocation(25, 130);
/*		secondQ.setBackground(Color.red);
		secondQ.setOpaque(true);*/
		Choice cho = new Choice();		
		cho.setSize(80, 80);
		cho.setLocation(205, 147);	
		
		Choice cho2 = new Choice();
		//cho2.setSize(80, 80);
		cho2.setLocation(290, 147);
		cho2.setSize(140, 30);
		cho2.add(languageCodes.get(language.get(0)).get(0));		
		selectItem2 = cho2.getItem(0);
		
		for (String str : language) {
			cho.add(str);
		}
		
		cho.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				cho2.removeAll();

				for (String str : languageCodes.get(e.getItem())) {
					cho2.add(str);
				}
				selectItem = cho.getSelectedItem();			
				selectItem2 = cho2.getSelectedItem();
			
			}
		});

		cho2.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				selectItem2 = cho2.getSelectedItem();
				
			}
		});
		
		Button clickBtn = new Button();
		
		clickBtn = new Button("Click");
		clickBtn.setSize(70, 30);
		clickBtn.setLocation(150, 180);
		clickBtn.addActionListener(new ActionListener() {										
			
			@Override
			public void actionPerformed(ActionEvent e) {

				 if(txtF.getText().isEmpty()){
					 JOptionPane.showMessageDialog(null, " Please \n Select Google ID Excel File.");
					 return;
				 }
				 
				File savefile;	 
				statusLabel.setText("Converting...");
				Desktop deskTop = Desktop.getDesktop();	 
				String line;								
				String script = "cscript";				
				String filePathS2 = ExcelPath;
				int idx = filePathS2.indexOf(0);
				int idx2 = filePathS2.lastIndexOf("\\");				
				String filePathS3 = filePathS2.substring(0, idx2);				
				filePathS = filePathS3;
				File dirPath = new File(filePathS + "\\zzz");
				String myvbs = filePathS + "\\xsls\\saveAsXML.vbs";
				String cmdArr[] = {script, myvbs, filePathS, ExcelPath};			
				
				if(dirPath.exists()){
					dirPath.delete();
				}else if(!dirPath.exists()){
				dirPath.mkdir();
				}
				try {
					Process p = Runtime.getRuntime().exec(cmdArr);							
					BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
					while ((line = input.readLine()) != null) {}					
					input.close();
					
					transform(filePathS + "\\zzz\\source.xml", filePathS + "\\xsls\\01-simplify.xsl", filePathS + "\\zzz\\simplified.xml"); 						
					transform(filePathS + "\\zzz\\simplified.xml", filePathS + "\\xsls\\02-make-device-mmi.xsl", filePathS + "\\zzz\\device-mmi.xml");						
					dummyCustomer(); 
					transform(filePathS + "\\xsls\\dummy.xml", filePathS + "\\xsls\\03-merge-apps.xsl", filePathS +"\\zzz\\merged-apps.xml");					
					transform(filePathS + "\\xsls\\dummy.xml", filePathS + "\\xsls\\04-merge-tm.xsl", filePathS + "\\zzz\\merged-tm.xml");						
					transform(filePathS + "\\zzz\\merged-apps.xml", filePathS + "\\xsls\\05-add-flags.xsl", filePathS + "\\zzz\\added-flags.xml");
 					transform(filePathS + "\\zzz\\added-flags.xml", filePathS + "\\xsls\\06-revert-importable.xsl", filePathS + "\\xsls\\dummy.xml");	
					transform(filePathS + "\\zzz\\merged-apps.xml", filePathS + "\\xsls\\07-make-lookup-table.xsl", filePathS + "\\final\\lookup.html");
 					statusLabel.setText("");
 					JOptionPane.showMessageDialog(null, "Successfully completed.");												
					deleteDirectory(new File(filePathS + "\\zzz"));
					deskTop.open(new File(filePathS + "\\final"));

					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				return;
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
		f.add(cho);
		f.add(cho2);
		f.add(txtF);
		f.add(pathBtn);
		//f.add(yesJck);
		f.add(secondQ);
		f.add(clickBtn);
		f.add(exitBtn);
		f.add(statusLabel);		
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

	public static void xmlPaser() {
		try {
			String filepathRe1 = "MA_langcode_200312.xml";
			//String filepathRe1 = MMI_3_WayChecker.class.getResource("").getPath();
			filepathRe1.getClass().getResource(filepathRe1);

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = docBuilder.parse(MMI_3_WayChecker.getResourceAsFile(filepathRe1));
			//org.w3c.dom.Document doc = docBuilder.parse(new File(filepathRe1 + "MA_langcode_200312.xml"));
			
			new StringBuilder();

			NodeList tagRegionList = doc.getElementsByTagName("region");

			language = new ArrayList<>();

			for (int i = 0; i < tagRegionList.getLength(); i++) {
				Node nNodeRegion = tagRegionList.item(i);
				Element el = (Element) tagRegionList.item(i);
				language.add(el.getAttribute("type"));

				if (nNodeRegion.hasChildNodes()) {
					// 자식 code가 있다면
					ArrayList<String> codeList = new ArrayList<>();
					NodeList codes = nNodeRegion.getChildNodes();
					for (int j = 0; j < codes.getLength(); j++) {
						Node code = codes.item(j);	
																	
						if (code.getTextContent().trim().length() > 0) {
							codeList.add(code.getTextContent());
						}
						
					}
					languageCodes.put(el.getAttribute("type"), codeList);
					
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void dummyCustomer() throws ParserConfigurationException {
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			
			Element root = document.createElement("dummy");
			document.appendChild(root);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(filePathS + "\\xsls\\dummy.xml"));

			transformer.transform(domSource, streamResult);
			
		} catch (Exception e) {
			// TODO: handle exception
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
			transformer.setOutputProperty(OutputKeys.ENCODING,"utf-8");
			// transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			// transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.setParameter("xlsPath", ExcelPath);			
			transformer.setParameter("region", selectItem);			
			transformer.setParameter("lang", selectItem2);			
			transformer.transform(new StreamSource(new File(sourcePath)), new StreamResult(new File(resultDir)));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		xmlPaser();
		Gui();		
	}
}
