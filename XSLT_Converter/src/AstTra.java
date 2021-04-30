import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.XMLEvent;
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

public class AstTra extends JFrame {
	// 현재 상황 BAT-three -> temp -> merged, dummy 파일이 있어야 됨
	// resource 안에 merged 될 파일 두개가 있어야 됨
	/**
	 * 
	 */
	private static final long serialVersionUID = 923253704727849460L;

	private static String sourcePath = null;
	private static ArrayList<String> language = null;
	private static ArrayList<String> language2 = null;
	private static ArrayList<String> language3 = null;
	private static ArrayList<String> languageF = null;
	private static HashMap<String, ArrayList<String>> languageCodes = new HashMap<>();
	static String selectItem1 = null;
	static String selectItem2 = null;
	static String selectItem3 = null;
	static File[] files;

	public static void main(String args[]) {
		xmlPaser();
		xmlPaser2();
		xmlPaser3();
		AstTraMe();

	}

	public static void AstTraMe() {
		Frame f = new Frame("AST Converter");
		f.setSize(500, 440); // 500, 440
		f.setLayout(null);
		f.setResizable(true);
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		// ---------------------------------------------------------------------------------------------------------------

		// ---------------------------------------------------------------------------------------------------------------
		ImageIcon img = new ImageIcon(AstTra.getResourceAsFile("Ast.jpg").getPath());
		JLabel lbImage1 = new JLabel(img);
		lbImage1.setSize(430, 50);
		lbImage1.setLocation(40, 40);
		// ---------------------------------------------------------------------------------------------------------------
/*		ImageIcon img2 = new ImageIcon(AstTra.getResourceAsFile("Facc.jpg").getPath());
		JLabel lbImage2 = new JLabel(img2);
		lbImage2.setSize(150, 50);
		lbImage2.setLocation(400, 300);*/
		// ----------------------------------------------------------------------------------------------------------------
		JLabel headerLabel = new JLabel();
		headerLabel.setFont(new Font("Serif", Font.BOLD, 20));
		headerLabel.setForeground(Color.DARK_GRAY);
		headerLabel.setSize(200, 90);
		headerLabel.setLocation(190, 5);
		// -----------------------------------------------------------------------------------------------------------------
		Font statusFont = new Font("Sans-Serif", Font.BOLD, 14);
		Label statusLabel = new Label();
		statusLabel.setAlignment(Label.CENTER);
		statusLabel.setSize(100, 100);
		statusLabel.setLocation(120, 330);
		statusLabel.setFont(statusFont);
		// -----------------------------------------------------------------------------------------------------------------
		JLabel middleLabel = new JLabel();
		middleLabel.setText("What is the language?");
		middleLabel.setFont(new Font("Serif", Font.BOLD, 15));
		middleLabel.setForeground(Color.DARK_GRAY);
		middleLabel.setSize(150, 20);
		middleLabel.setLocation(110, 105);
		// -----------------------------------------------------------------------------------------------------------------
		JLabel firstLabel = new JLabel();
		firstLabel.setText("What is the model name?");
		firstLabel.setFont(new Font("Serif", Font.BOLD, 15));
		firstLabel.setForeground(Color.DARK_GRAY);
		firstLabel.setSize(170, 20);
		firstLabel.setLocation(110, 155);
		// -----------------------------------------------------------------------------------------------------------------
		JLabel twoLabel = new JLabel();
		twoLabel.setText("What is the cartype?");
		twoLabel.setFont(new Font("Serif", Font.BOLD, 15));
		twoLabel.setForeground(Color.DARK_GRAY);
		twoLabel.setSize(170, 20);
		twoLabel.setLocation(110, 205);
		// -----------------------------------------------------------------------------------------------------------------
		JLabel threeLabel = new JLabel();
		threeLabel.setText("Resource file path");
		threeLabel.setFont(new Font("Serif", Font.BOLD, 15));
		threeLabel.setForeground(Color.DARK_GRAY);
		threeLabel.setSize(170, 50);
		threeLabel.setLocation(110, 235);
		// -----------------------------------------------------------------------------------------------------------------
		JLabel forLabel = new JLabel();
		forLabel.setText("File Exporter");
		forLabel.setFont(new Font("Serif", Font.BOLD, 15));
		forLabel.setForeground(Color.DARK_GRAY);
		forLabel.setSize(170, 50);
		forLabel.setLocation(110, 285);
		// -----------------------------------------------------------------------------------------------------------------
		// Choice객체를 생성한 다음
		Choice cho = new Choice();
		Choice cho2 = new Choice();
		Choice cho3 = new Choice();
		Choice cho4 = new Choice();

		for (String str : language2) {
			cho.add(str);
		}

		for (String str : language3) {
			cho2.add(str);
		}

		for (String str : language) {
			cho3.add(str);
		}

		cho.setSize(300, 200);
		cho.setLocation(110, 180);
		selectItem1 = cho.getItem(0); // ok

		cho.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				selectItem1 = cho.getSelectedItem();

			}
		});

		cho2.setSize(300, 200);
		cho2.setLocation(110, 225);
		selectItem2 = cho2.getItem(0);
		cho2.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				selectItem2 = cho2.getSelectedItem();

			}
		});

		cho3.setSize(100, 50);
		cho3.setLocation(110, 130);
		//selectItem3 = cho4.getItem(0);		
		cho3.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				cho4.removeAll();

				for (String str : languageCodes.get(e.getItem())) {
					cho4.add(str);
				}
				selectItem3 = cho4.getSelectedItem();
			}
		});
		cho4.setSize(100, 50);
		cho4.setLocation(220, 130);
		
		for (String str : languageF) {
			cho4.add(str);
		}		
		selectItem3 = cho4.getItem(0);
		
		cho4.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				selectItem3 = cho4.getSelectedItem();

			}
		});
		// ----------------------------------------------------------------------------------------------------------------
		TextField txtF = new TextField();
		txtF.setSize(270, 25);
		txtF.setLocation(115, 275);
		// ----------------------------------------------------------------------------------------------------------------
		TextField txtF2 = new TextField();
		txtF2.setSize(270, 25);
		txtF2.setLocation(115, 325);
		txtF2.setEnabled(false);
		// ----------------------------------------------------------------------------------------------------------------
		Button pathBtn = new Button("...");
		pathBtn.setSize(25, 25);
		pathBtn.setLocation(385, 275);

		pathBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();

				if ((Button) obj == pathBtn) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setCurrentDirectory(new File("C:\\"));
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fileChooser.setMultiSelectionEnabled(true);
					int returnVal = fileChooser.showOpenDialog(null);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						files = fileChooser.getSelectedFiles();
						String filesPath = "";
						for (File file : files) {
							filesPath += file.getAbsolutePath() /* + ";" */;
						}
						sourcePath = filesPath;
						txtF.setText(sourcePath);
						txtF2.setText(sourcePath + "\\output");

						for (String filePath : sourcePath.split(";"))
							System.out.println("file : " + filePath);
					}

				}

			}
		});

		Button pathBtn2 = new Button("...");
		pathBtn2.setSize(25, 25);
		pathBtn2.setLocation(385, 325);
		pathBtn2.setEnabled(false);
		pathBtn2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();

				if ((Button) obj == pathBtn2) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setCurrentDirectory(new File("C:\\"));
					fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fileChooser.setMultiSelectionEnabled(true);
					int returnVal = fileChooser.showOpenDialog(null);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						files = fileChooser.getSelectedFiles();
						String filesPath = "";
						for (File file : files) {
							filesPath += file.getAbsolutePath() + ";";
						}
						sourcePath = filesPath;
						txtF2.setText(sourcePath + "\\output");

					}

				}
			}
		});

		// -----------------------------------------------------------------------------------------------------

		Button conversionBtn = new Button();
		conversionBtn = new Button("Conversion");
		conversionBtn.setSize(70, 30);
		conversionBtn.setLocation(265, 365);
		conversionBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (new File(sourcePath + "\\merged.xml").exists()) {
						new File(sourcePath + "\\merged.xml").delete();
					} else if (new File(sourcePath + "\\output").exists()) {
						deleteAllFiles(sourcePath + "\\output");
					}
					mergeT();
					putBodySetTxt(selectItem1, selectItem2, selectItem3);
					statusLabel.setText("Converting...");
					Desktop deskTop = Desktop.getDesktop();
					if (!sourcePath.isEmpty()) {
						 transform(sourcePath + "\\merged.xml","C:\\HTML_converter\\xsls\\call-merged.xsl",sourcePath + "\\xml" +"\\merged.xml");
						 transform(sourcePath + "\\xml" +"\\01-merged.xml","C:\\HTML_converter\\xsls\\00-external_inline-strc.xsl",sourcePath + "\\xml" +"\\00-external_inline-strc.xml");
						 transform(sourcePath + "\\xml" +"\\00-external_inline-strc.xml","C:\\HTML_converter\\xsls\\02-trash_clean.xsl",sourcePath + "\\xml" + "\\02-trash_clean.xml");
						 transform(sourcePath + "\\xml" +"\\02-trash_clean.xml","C:\\HTML_converter\\xsls\\03-list_indent_define.xsl",sourcePath + "\\xml" + "\\03-list_indent_define.xml"); 
						 transform(sourcePath + "\\xml" + "\\03-list_indent_define.xml","C:\\HTML_converter\\xsls\\04-ol-list-style-grouping.xsl",sourcePath + "\\xml" +"\\04-ol-list-style-grouping.xml");
						 transform(sourcePath + "\\xml" +"\\04-ol-list-style-grouping.xml","C:\\HTML_converter\\xsls\\05-ul-list-style-grouping.xsl",sourcePath + "\\xml" +"\\05-ul-list-style-grouping.xml");
						 transform(sourcePath + "\\xml" +"\\05-ul-list-style-grouping.xml","C:\\HTML_converter\\xsls\\06-ol-numbering.xsl",sourcePath + "\\xml" + "\\06-ol-numbering.xml");
						 transform(sourcePath + "\\xml" +"\\06-ol-numbering.xml","C:\\HTML_converter\\xsls\\07-ol-nested.xsl",sourcePath + "\\xml" + "\\07-ol-nested.xml");
						 transform(sourcePath + "\\xml" +"\\07-ol-nested.xml","C:\\HTML_converter\\xsls\\08-ul-nested.xsl",sourcePath + "\\xml" + "\\08-ul-nested.xml");
						 transform(sourcePath + "\\xml" +"\\08-ul-nested.xml","C:\\HTML_converter\\xsls\\09-style-list-adjacent.xsl",sourcePath + "\\xml" +"\\09-style-list-adjacent.xml"); 
						 transform(sourcePath + "\\xml" + "\\09-style-list-adjacent.xml","C:\\HTML_converter\\xsls\\10-style-list-structure.xsl",sourcePath + "\\xml" +"\\10-style-list-structure.xml");
						 transform(sourcePath + "\\xml" +"\\10-style-list-structure.xml","C:\\HTML_converter\\xsls\\11-list-child.xsl",sourcePath + "\\xml" + "\\11-list-child.xml");
						 transform(sourcePath + "\\xml" +"\\11-list-child.xml","C:\\HTML_converter\\xsls\\12-waring-group.xsl",sourcePath + "\\xml" + "\\12-waring-group.xml");
						 transform(sourcePath + "\\xml" +"\\12-waring-group.xml","C:\\HTML_converter\\xsls\\13-Table-split.xsl",sourcePath + "\\xml" + "\\13-Table-split.xml");
						 transform(sourcePath + "\\xml" +"\\13-Table-split.xml","C:\\HTML_converter\\xsls\\14-Table-converting.xsl",sourcePath + "\\xml" + "\\14-Table-converting.xml");
						 transform(sourcePath + "\\xml" +"\\14-Table-converting.xml","C:\\HTML_converter\\xsls\\15-Table-structure.xsl",sourcePath + "\\xml" + "\\15-Table-structure.xml");
						 transform(sourcePath + "\\xml" +"\\15-Table-structure.xml","C:\\HTML_converter\\xsls\\16-nest-grouping.xsl",sourcePath + "\\xml" + "\\16-nest-grouping.xml");
						 transform(sourcePath + "\\xml" +"\\16-nest-grouping.xml","C:\\HTML_converter\\xsls\\17-div-split.xsl",sourcePath + "\\xml" + "\\17-div-split.xml");
						 transform(sourcePath + "\\xml" +"\\17-div-split.xml","C:\\HTML_converter\\xsls\\18-heading-define.xsl",sourcePath + "\\xml" + "\\18-heading-define.xml");
						 transform(sourcePath + "\\xml" +"\\18-heading-define.xml","C:\\HTML_converter\\xsls\\19-h2-grouping.xsl",sourcePath + "\\xml" + "\\19-h2-grouping.xml");
						 transform(sourcePath + "\\xml" +"\\19-h2-grouping.xml","C:\\HTML_converter\\xsls\\20-href-a.xsl",sourcePath+ "\\xml" + "\\20-href-a.xml"); 
						 transform(sourcePath + "\\xml" +"\\20-href-a.xml","C:\\HTML_converter\\xsls\\21-img-conlr.xsl",sourcePath + "\\xml" + "\\21-img-conlr.xml");
						 transform(sourcePath + "\\xml" +"\\21-img-conlr.xml","C:\\HTML_converter\\xsls\\22-img-class-define.xsl",sourcePath + "\\xml" + "\\22-img-class-define.xml");
						 transform(sourcePath + "\\xml" +"\\22-img-class-define.xml","C:\\HTML_converter\\xsls\\23-element-define.xsl",sourcePath + "\\xml" + "\\23-element-define.xml");
						 transform(sourcePath + "\\xml" +"\\23-element-define.xml","C:\\HTML_converter\\xsls\\24-img_box_out.xsl",sourcePath + "\\xml" + "\\24-img_box_out.xml");
						 transform(sourcePath + "\\xml" +"\\24-img_box_out.xml","C:\\HTML_converter\\xsls\\25-olul-class-define.xsl",sourcePath + "\\xml" + "\\25-olul-class-define.xml");
						 transform(sourcePath + "\\xml" +"\\25-olul-class-define.xml","C:\\HTML_converter\\xsls\\26-mini-toc.xsl",sourcePath + "\\xml" + "\\26-mini-toc.xml");
						 transform(sourcePath + "\\xml" +"\\25-olul-class-define.xml","C:\\HTML_converter\\xsls\\27-index.xsl",sourcePath +"\\xml" + "\\27-index.xml"); 
						 transform(sourcePath +"\\xml" + "\\25-olul-class-define.xml","C:\\HTML_converter\\xsls\\28-split_html.xsl",sourcePath + "\\xml" + "\\28-split_html.xml");						
						JOptionPane.showMessageDialog(f, "The conversion is complete.");
						statusLabel.setText("");
					} else {
						JOptionPane.showMessageDialog(f, "error. Contact your administrator");
						statusLabel.setText("");
					}
					 new File(sourcePath + "\\merged.xml").delete();
					 deleteAllFiles(sourcePath + "\\xml");
					 deskTop.open(new File(sourcePath + "\\output"));
					 //Arrays.stream(new File(sourcePath + "\\xml").listFiles()).forEach(File::delete);

				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		});

		Button exitBtn = new Button();
		exitBtn = new Button("Exit");
		exitBtn.setSize(70, 30);
		exitBtn.setLocation(340, 365);
		exitBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		f.add(headerLabel);
		f.add(middleLabel);
		f.add(firstLabel);
		f.add(twoLabel);
		f.add(cho);
		f.add(cho2);
		f.add(cho3);
		f.add(cho4);
		f.add(conversionBtn);
		f.add(exitBtn);
		f.add(pathBtn);
		f.add(pathBtn2);
		f.add(txtF);
		f.add(txtF2);
		f.add(threeLabel);
		f.add(forLabel);
		f.add(statusLabel);
		f.add(lbImage1);
		//f.add(lbImage2);
		f.setBackground(Color.white);
		f.setVisible(true);

	}

	public static void transform(String sourcePath, String xsltPath, String resultDir) {
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(new File(xsltPath)));
			// transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			// transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(new StreamSource(new File(sourcePath)), new StreamResult(new File(resultDir)));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	public static void xmlPaser() {
		try {
			String filepathRe1 = "region-code1.xml";
			filepathRe1.getClass().getResource(filepathRe1);

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = docBuilder.parse(AstTra.getResourceAsFile(filepathRe1));

			new StringBuilder();

			NodeList tagRegionList = doc.getElementsByTagName("region");
			language = new ArrayList<>();
			languageF = new ArrayList<>();
			
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
							if(el.getAttribute("type").equals("ASIA")){
								languageF.add(code.getTextContent());								
							}
						}							

					}
					languageCodes.put(el.getAttribute("type"), codeList);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ----------------------------------------------------------------------------------------------------------------
	public static void xmlPaser2() {
		try {
			String filepathRe2 = "region-code2.xml";

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = docBuilder.parse(AstTra.getResourceAsFile(filepathRe2));
			new StringBuilder();

			NodeList tagRegionList = doc.getElementsByTagName("region");
			language2 = new ArrayList<>();

			for (int i = 0; i < tagRegionList.getLength(); i++) {
				Node nNodeRegion = tagRegionList.item(i);
				tagRegionList.item(i);

				if (nNodeRegion.hasChildNodes()) {
					new ArrayList<>();
					NodeList codes = nNodeRegion.getChildNodes();
					for (int j = 0; j < codes.getLength(); j++) {
						Node code = codes.item(j);

						if (code.getTextContent().trim().length() > 0) {
							language2.add(code.getTextContent());
						}
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void xmlPaser3() {
		try {
			String filepathRe3 = "region-code3.xml";

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = docBuilder.parse(AstTra.getResourceAsFile(filepathRe3));
			new StringBuilder();

			NodeList tagRegionList = doc.getElementsByTagName("region");
			language3 = new ArrayList<>();

			for (int i = 0; i < tagRegionList.getLength(); i++) {
				Node nNodeRegion = tagRegionList.item(i);
				tagRegionList.item(i);

				if (nNodeRegion.hasChildNodes()) {
					new ArrayList<>();
					NodeList codes = nNodeRegion.getChildNodes();
					for (int j = 0; j < codes.getLength(); j++) {
						Node code = codes.item(j);

						if (code.getTextContent().trim().length() > 0) {
							language3.add(code.getTextContent());
						}
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void putBodySetTxt(String selectItem1, String selectItem2, String selectItem3) {
		try {

			String filepath = sourcePath + "\\merged.xml";
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File(filepath));

			NodeList bodySetT = doc.getElementsByTagName("body");

			for (int i = 0; i < bodySetT.getLength(); i++) {
				Node nodeBodySet = bodySetT.item(i);
				if (nodeBodySet.getNodeType() == Node.ELEMENT_NODE) {
					Element elementBody = (Element) nodeBodySet;

					elementBody.setAttribute("language", selectItem3);
					elementBody.setAttribute("modelname", selectItem1);
					elementBody.setAttribute("cartype", selectItem2);
				}
			} // merged가 되기전에 select가 들어가야됨

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void deleteAllFiles(String path) {
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

	public static void mergeT() throws IOException {
		File dir = new File(sourcePath);
		File[] rootFiles = dir.listFiles();
		File fileMerged = new File(sourcePath + "\\merged.xml");
		
		try {
			BufferedWriter BufferedWriterreader = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileMerged), "utf-8"));
			//Writer outputWriter = new FileWriter(sourcePath + "\\merged.xml");
			XMLOutputFactory xmlOutFactory = XMLOutputFactory.newFactory();
			XMLEventWriter xmlEventWriter = xmlOutFactory.createXMLEventWriter(BufferedWriterreader);
			XMLEventFactory xmlEventFactory = XMLEventFactory.newFactory();

			xmlEventWriter.add(xmlEventFactory.createStartDocument());
			xmlEventWriter.add(xmlEventFactory.createStartElement("", null, "body"));

			XMLInputFactory xmlInFactory = XMLInputFactory.newFactory();
			for (File rootFile : rootFiles) {
				XMLEventReader xmlEventReader = xmlInFactory.createXMLEventReader(new StreamSource(rootFile));
				XMLEvent event = xmlEventReader.nextEvent();
				// Skip ahead in the input to the opening document element
				while (event.getEventType() != XMLStreamConstants.START_ELEMENT) {
					event = xmlEventReader.nextEvent();
				}
				do {
					xmlEventWriter.add(event);
					event = xmlEventReader.nextEvent();
				} while (event.getEventType() != XMLStreamConstants.END_DOCUMENT);
				xmlEventReader.close();
			}

			xmlEventWriter.add(xmlEventFactory.createEndElement("", null, "body"));
			xmlEventWriter.add(xmlEventFactory.createEndDocument());
			xmlEventWriter.close();
			//outputWriter.close();
			BufferedWriterreader.close();

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

}
