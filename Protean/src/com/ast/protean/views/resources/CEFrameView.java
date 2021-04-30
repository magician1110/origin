package com.ast.protean.views.resources;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import com.ast.protean.controllers.ProteanController;
import com.ibm.icu.text.SimpleDateFormat;


public class CEFrameView extends JFrame implements WindowListener, Runnable{	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame fv = new JFrame();
    private JPanel panel = new JPanel();
    private JTextPane logTextArea = new JTextPane();   
    private Thread stdOutReader;
    private Thread stdErrReader;
    private boolean stopThreads;
    private final PipedInputStream stdOutPin = new PipedInputStream();
    private final PipedInputStream stdErrPin = new PipedInputStream();
    //Used to print error messages in red
    private StyledDocument doc;
    private Style style;
    public static final Logger _logger = Logger.getLogger(CEFrameView.class.getName());    
    public static final int FRAME_W = 500;
    public static final int FRAME_H = 600;
    public static final int LABEL_W = 100;
    public static final int LABEL_H = 45;
    public static final int LABEL_RL = 10;
    public static final int LABEL_UD = 20;
    public static final int FILEPATH_F_W = 430;
    public static final int FILEPATH_F_H = 25;
    public static final int FILEPATH_F_RL = 10;
    public static final int FILEPATH_F_UD = 55;
    public static final int BUTTON_F_PATH_W = 25;
    public static final int BUTTON_F_PATH_H = 24;
    public static final int BUTTON_F_PATH_RL = 440;
    public static final int BUTTON_F_PATH_UD = 55;
    public static final int PANEL_W = 10;
    //private static final int PANEL_H = 60;
    private static final int PANEL_RL = 470;
    //private static final int PANEL_UD = 280;
    public static String pluginPath = "";
    //public static String ditaValPath = "";    
    public static String selectedPlugin = "";
    public static JTextField mapPathField;
    public static JTextField filterPathField;
    public static String lv_name = "";
    public ProteanController pc = new ProteanController();
    
	@SuppressWarnings("static-access")
	public CEFrameView(String selectButton) throws IOException{		
		//DOMConfigurator.configure(MAFrameView.class.getResource("_reference\\log4j.xml"));
		DOMConfigurator.configure("_reference\\log4j.xml");				
		_logger.info("Processing starts.");
		
		JMenuBar menuBar = new JMenuBar();			
		JMenu fileMenu = new JMenu("File(F)");
		fileMenu.setToolTipText("File Menu.");
		fileMenu.setMnemonic('F');		
		
		JMenuItem Dita_ot_Menu = new JMenuItem("Dita-ot(D)");
		Dita_ot_Menu.setToolTipText("Dita-ot.");
		Dita_ot_Menu.setMnemonic('D');
		Dita_ot_Menu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				JMenuItem btn = (JMenuItem)e.getSource();
				if(btn.getText().equals("Dita-ot(D)")){					
					Dita_ot_View dov = new Dita_ot_View();					
					
				}	
				
			}
		});
		
		JMenuItem validatorMenu = new JMenuItem("Validator Execution(V)");
		validatorMenu.setToolTipText("Validator Execution");
		validatorMenu.setMnemonic('V');
		validatorMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				JMenuItem btn = (JMenuItem)e.getSource();				
				if(btn.getText().equals("Validator Execution(V)")){
					try {						
						pc.openFolder(new File("_reference\\Validator"));						
						//pc.getLastModifiedFile(new File("C:\\Protean\\_reference\\Validator"));	
					} catch (Exception e2) {
						// TODO: handle exception
					}
						
					
				}	
				
			}
		});
		
		//pc.getLastModifiedFile(new File("C:\\Protean\\_reference\\Validator"));
		
		JMenuItem exitItem = new JMenuItem("Exit                                Art+X");
		exitItem.setMnemonic('X');
		exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				JMenuItem btn = (JMenuItem)e.getSource();
				if(btn.getText().equals("Exit                                Art+X")){					
					try {
						pc.batchEx("_reference\\AHF_ConnectDelete.bat");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.exit(0);
				}
				
			}
		});
		fileMenu.add(Dita_ot_Menu);
		fileMenu.add(validatorMenu);
		fileMenu.add(exitItem);
		
		JMenu helpMenu = new JMenu("Help(H)");
		helpMenu.setToolTipText("Help Menu.");
		helpMenu.setMnemonic('H');
		JMenuItem userGuideItem = new JMenuItem("Protean User Guide              Art+P");
		userGuideItem.setMnemonic('P');
		userGuideItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JMenuItem btn = (JMenuItem)e.getSource();
				if(btn.getText().equals("Protean User Guide              Art+P")){
					try {
						pc.openFolder(new File("_reference\\language_codes\\file.pdf"));
					} catch (Exception e2) {
						_logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, e2);						
					}
				}
				
			}
		});
		
		helpMenu.add(userGuideItem);
		JMenuItem proteanReleaseHistory = new JMenuItem("Protean Release History     Art+H");
		proteanReleaseHistory.setMnemonic('H');
		proteanReleaseHistory.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JMenuItem btn = (JMenuItem)e.getSource();
				if(btn.getText().equals("Protean Release History     Art+H")){
					try {
						if(selectButton == "MA"){
							Desktop.getDesktop().browse(new URI("http://wp2.astkorea.net/task/doc/#view/20200311060020011ab"));							
						}else if(selectButton == "CE"){
							Desktop.getDesktop().browse(new URI("http://wp2.astkorea.net/task/doc/#view/20200311060220011d6"));
						}
					} catch (Exception e2) {
						_logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, e2);
						e2.printStackTrace();
					}					
				}
			}
		});
		
		helpMenu.add(proteanReleaseHistory);
		JMenuItem aboutProtean = new JMenuItem("About Protean                        Art+A");
		aboutProtean.setMnemonic('A');
		aboutProtean.addActionListener(new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				JMenuItem btn = (JMenuItem)e.getSource();				
				if(btn.getText().equals("About Protean                        Art+A")){									
					AboutProteanBarView abv = new AboutProteanBarView();
				}				
			}
		});		
			
		helpMenu.add(aboutProtean);
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);		
		fv.setJMenuBar(menuBar);	
		//-----------------------------------MenuBar End------------------------------------------------
		ImageIcon headerImgIcon = new ImageIcon(MainView.getResourceAsFile("Ast.jpg").getPath());		
		JLabel headerLabelIcon = new JLabel(headerImgIcon);
		headerLabelIcon.setSize(430, 50);
		headerLabelIcon.setLocation(23, 10);
		fv.add(headerLabelIcon);
		
		TitledBorder tittb = new TitledBorder(new LineBorder(Color.lightGray),"CE Select List");
		tittb.setTitleColor(Color.black);
		tittb.setTitleFont(new Font("맑은 고딕",  Font.PLAIN, 20));
		
		panel.setLayout(null);
		panel.setBorder(tittb);
		panel.setBounds(10,60,470,280); //좌우, 위아래, 넓이, 높이
		panel.setBackground(Color.white);
		fv.add(panel);
		
		JLabel mapPathLabel = new JLabel("File Map");		
		mapPathLabel.setBounds(LABEL_RL,LABEL_UD,LABEL_W,LABEL_H);
		mapPathLabel.setFont(new Font("맑은 고딕",  Font.PLAIN, 12)); 
		panel.add(mapPathLabel);
		
		JLabel pluginPathLabel = new JLabel("Plug-in");		
		pluginPathLabel.setBounds(LABEL_RL,LABEL_UD+55,LABEL_W,LABEL_H);
		pluginPathLabel.setFont(new Font("맑은 고딕",  Font.PLAIN, 12));
		panel.add(pluginPathLabel);
		
		JLabel filterPathLabel = new JLabel("Filter");		
		filterPathLabel.setBounds(LABEL_RL,LABEL_UD+110,LABEL_W,LABEL_H);
		filterPathLabel.setFont(new Font("맑은 고딕",  Font.PLAIN, 12));
		panel.add(filterPathLabel);
		
		JLabel languagePathLabel = new JLabel("language");		
		languagePathLabel.setBounds(LABEL_RL,LABEL_UD+165,LABEL_W,LABEL_H);
		languagePathLabel.setFont(new Font("맑은 고딕",  Font.PLAIN, 12));		
		panel.add(languagePathLabel);
		
		mapPathField = new JTextField();
		mapPathField.setBounds(FILEPATH_F_RL,FILEPATH_F_UD,FILEPATH_F_W,FILEPATH_F_H);
		mapPathField.setDragEnabled(true);
		mapPathField.setDropTarget(new DropTarget(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 8142412591601801852L;
			
			@SuppressWarnings("static-access")
			@Override
            public synchronized void dragEnter(DropTargetDragEvent evt) {                					
				try {					
					List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
					int s = droppedFiles.size();                    
                    if(s != 1){
                        evt.rejectDrag();
                    }else if(!droppedFiles.get(0).getName().endsWith(".ditamap")){
                        evt.rejectDrag();
                	}
				} catch (UnsupportedFlavorException | IOException ex) {
					_logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, ex);                    				
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}			
            };
            @SuppressWarnings("static-access")
			@Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    @SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {                    	
                    	mapPathField.setText(file.getAbsolutePath());                       
                    }
                }catch (UnsupportedFlavorException | IOException ex) {
                	_logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, ex);
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
				
	});
		panel.add(mapPathField);		
		
		JComboBox<String> pluginComboBox = new JComboBox<String>();
		ProteanController.pluginXmlPaser("_reference\\language_codes\\pluginList.xml");
		for(String str : ProteanController.pluginLangList){			
			pluginComboBox.addItem(str);
		}

		selectedPlugin = pluginComboBox.getItemAt(0);	
		pluginComboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {		        
				selectedPlugin = (String) pluginComboBox.getSelectedItem();							
			}
		});
				
		pluginComboBox.setBackground(Color.white);
		pluginComboBox.setBounds(FILEPATH_F_RL,FILEPATH_F_UD+55,FILEPATH_F_W+25,FILEPATH_F_H);
		panel.add(pluginComboBox);
		
		filterPathField = new JTextField();
		filterPathField.setBounds(FILEPATH_F_RL,FILEPATH_F_UD+110,FILEPATH_F_W,FILEPATH_F_H);
		filterPathField.setDragEnabled(true);
		filterPathField.setDropTarget(new DropTarget(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 8142412591601801852L;
			
			@SuppressWarnings("static-access")
			@Override
            public synchronized void dragEnter(DropTargetDragEvent evt) {                					
				try {				
					List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);	
					int s = droppedFiles.size();                    
                    if(s != 1){
                        evt.rejectDrag();
                    }else if(!droppedFiles.get(0).getName().endsWith(".ditaval")){
                        evt.rejectDrag();
                	}
				} catch (UnsupportedFlavorException | IOException ex) {
					_logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, ex);
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}			
            };
            @SuppressWarnings("static-access")
			@Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    @SuppressWarnings("unchecked")
					List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                    	filterPathField.setText(file.getAbsolutePath());                      
                    }
                }catch (UnsupportedFlavorException | IOException ex) {
                	_logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, ex);
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
				
	});
		panel.add(filterPathField);
		
		JTextField languagePathField = new JTextField(5);
		languagePathField.setBounds(FILEPATH_F_RL,FILEPATH_F_UD+165,FILEPATH_F_W,FILEPATH_F_H);		
		panel.add(languagePathField); 
		//PathButton@@
		JButton mapPathButton = new JButton("...");
		mapPathButton.setBounds(BUTTON_F_PATH_RL, BUTTON_F_PATH_UD, BUTTON_F_PATH_W, BUTTON_F_PATH_H);
		mapPathButton.setBackground(Color.WHITE);
		mapPathButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jc = new JFileChooser(FileSystemView.getFileSystemView());
				//jc.setCurrentDirectory("");
				jc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("DITA Map", "ditamap");
				jc.addChoosableFileFilter(filter);
				jc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jc.setCurrentDirectory(new File("."));
				
				int returnVal = jc.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION){									
					mapPathField.setText(jc.getSelectedFile().getAbsolutePath());					
				}else if(returnVal == JFileChooser.CANCEL_OPTION){
					mapPathField.setText("");
		        }
			}
		});
		panel.add(mapPathButton);

		JButton filterPathButton = new JButton("...");
		filterPathButton.setBounds(BUTTON_F_PATH_RL, BUTTON_F_PATH_UD+110, BUTTON_F_PATH_W, BUTTON_F_PATH_H);
		filterPathButton.setBackground(Color.WHITE);
		filterPathButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jc = new JFileChooser(FileSystemView.getFileSystemView());
				//jc.setCurrentDirectory("");
				jc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("DITA VAL", "ditaval");
				jc.addChoosableFileFilter(filter);
				jc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jc.setCurrentDirectory(new File("."));
				
				int returnVal = jc.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION){															
					filterPathField.setText(jc.getSelectedFile().getAbsolutePath());
					//ditaValPath = filterPathField.getText();
				}else if(returnVal == JFileChooser.CANCEL_OPTION){
					filterPathField.setText("");
		        }				
			}
		});
		panel.add(filterPathButton);
		
		JButton languagePathButton = new JButton("...");
		languagePathButton.setBounds(BUTTON_F_PATH_RL, BUTTON_F_PATH_UD+165, BUTTON_F_PATH_W, BUTTON_F_PATH_H);
		languagePathButton.setBackground(Color.WHITE);	
		languagePathButton.addActionListener(new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent e) {				
				LanguageList languageList = new LanguageList();
				languageList.setBounds(1100, 460, 420, 200);
			    languageList.setAlwaysOnTop(true);
				languageList.setVisible(true); //버튼을 눌렀을때 생성 되는데 버튼을 닫앗을때 생성되도록				
				languageList.addWindowListener(new WindowAdapter() {
			        public void windowClosing(WindowEvent e) {
			            if(LanguageList.model_2.size() <= 0){
			            	languagePathField.setText("");
			            }else{			            	
			            	lv_name = LanguageList.model_2.toString();			            				            				            
			            	languagePathField.setText(lv_name);	
			            }
			            		            
			        }
				});							
			}
			
		});
		panel.add(languagePathButton);
		
		JPanel logPanel = new JPanel();
		logPanel.setBounds(PANEL_W, 345, PANEL_RL, 140);
		logPanel.setBackground(Color.WHITE);		

		logTextArea.setEditable( false );
		logTextArea.setBackground(Color.WHITE);
		
	    doc = (StyledDocument) logTextArea.getDocument();
	    style = doc.addStyle("ConsoleStyle", null);
	    StyleConstants.setFontFamily(style, "MonoSpaced");
	    StyleConstants.setFontSize(style, 12);
	    
	    getContentPane().add(new JScrollPane(logTextArea), BorderLayout.CENTER);
	    //setVisible(true);
	    addWindowListener(this);
	    try {
	        PipedOutputStream stdOutPos = new PipedOutputStream(this.stdOutPin);
	        System.setOut(new PrintStream(stdOutPos, true));
	      } catch (java.io.IOException io) {
	    	  _logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, io);
	    	  System.out.println("Couldn't redirect STDOUT to this console\n" + io.getMessage());	    	  
	      } catch (SecurityException se) {
	    	  _logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, se);
	    	  System.out.println("Couldn't redirect STDOUT to this console\n" + se.getMessage());	    	  
	      }

	      try {
	        PipedOutputStream stdErrPos = new PipedOutputStream(this.stdErrPin);
	        System.setErr(new PrintStream(stdErrPos, true));
	      } catch (java.io.IOException io) {
	    	  _logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, io);
	    	  System.out.println("Couldn't redirect STDERR to this console\n" + io.getMessage());	    	  
	      } catch (SecurityException se) {
	    	  _logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, se);
	    	  System.out.println("Couldn't redirect STDERR to this console\n" + se.getMessage());	    	  
	      }

	      stopThreads = false; // Will be set to true at closing time. This will stop the threa.windowOpened(WindowEvent)

	      // Starting two threads to read the PipedInputStreams
	      stdOutReader = new Thread(this);
	      stdOutReader.setDaemon(true);
	      stdOutReader.start();

	      stdErrReader = new Thread(this);
	      stdErrReader.setDaemon(true);
	      stdErrReader.start();
	    
		JScrollPane scrollPane = new JScrollPane(logTextArea);		
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setPreferredSize(new Dimension(470, 135));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		logPanel.add(scrollPane);
		fv.add(logPanel);
		
		JButton startButton = new JButton("Publish");
		
		startButton.setBounds(140, 500, 100, 35);  
		startButton.setBackground(Color.WHITE);
		startButton.setFont(new Font("맑은 고딕",  Font.PLAIN, 12));
		
		startButton.addActionListener(new ActionListener() {
						
			@Override
			public void actionPerformed(ActionEvent e) {
				long start = System.currentTimeMillis();//시작 시점 계산
								
				if(mapPathField.getText().equals("") || mapPathField.getText() == null){
					JOptionPane.showMessageDialog(null, "File Map is not selected.", "Error", JOptionPane.INFORMATION_MESSAGE);	
					return;
				}else if(filterPathField.getText().equals("") || filterPathField.getText() == null){
					JOptionPane.showMessageDialog(null, "Filter is not selected.", "Error", JOptionPane.INFORMATION_MESSAGE);	
					return;
				}else if(languagePathField.getText().equals("") || languagePathField.getText() == null){					
					JOptionPane.showMessageDialog(null, "Language is not selected.", "Error", JOptionPane.INFORMATION_MESSAGE);
					return;
				}else{
					try {
						fv.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						pc.processEx();	
						fv.setCursor(Cursor.getDefaultCursor());
						//pc.getExecuteCommand("net use z: /delete /y >NUL");
						JOptionPane.showMessageDialog(null, "Complate.", "INFORMATION_MESSAGE", JOptionPane.INFORMATION_MESSAGE);
						long end = System.currentTimeMillis(); //프로그램이 끝나는 시점 계산 
						_logger.info("Processing ends");
						System.out.println( "실행 시간 : " + ( end - start )/1000.0 + "초"); //실행 시간 계산 및 출력	
					} finally {
						//pc.getExecuteCommand("net use z: /delete /y >NUL");
						try {
							pc.batchEx("_reference\\AHF_ConnectDelete.bat");
						} catch (Exception e1) {							
							e1.printStackTrace();
						}
					}
															
				}
								
			}			
			
		});
		
		fv.add(startButton);
		
		JButton exitButton = new JButton("Cancel");
		exitButton.setBounds(260, 500, 100, 35);
		exitButton.setBackground(Color.WHITE);
		exitButton.setFont(new Font("맑은 고딕",  Font.PLAIN, 12));
		exitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("탓다");
				try {
					pc.batchEx("_reference\\AHF_ConnectDelete.bat");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
				System.exit(0); //후에 전 단계로 바꿀것 
				
			}
		});
		fv.add(exitButton);
		
		fv.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					pc.batchEx("_reference\\AHF_ConnectDelete.bat");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(0); 
			}

		});
		
		fv.setTitle("PROTEAN CE Team Display");				
		fv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		ImageIcon img = new ImageIcon(MainView.getResourceAsFile("docuB.gif").getPath());
		
		fv.getContentPane().setBackground(Color.white);		
		fv.setIconImage(img.getImage());		
		fv.setBounds(600, 200, FRAME_W, FRAME_H);	//좌우, 위아래, 넓이, 높이
		fv.setLayout(null);
		fv.setVisible(true);
		fv.setResizable(false);
	}

	 /**
	   * Closes the window and stops the "stdOutReader" threads
	   *
	   * @param evt WindowEvent
	   */
	  @SuppressWarnings("static-access")
	public synchronized void windowClosed(WindowEvent evt) {

	    // Notify the threads that they must stop
	    stopThreads = true;
	    this.notifyAll();

	    try {
	      stdOutReader.join(1000);
	      stdOutPin.close();
	    } catch (Exception e) {
	    	_logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, e);	    	
	    	e.printStackTrace();
	    }							//.windowOpened(WindowEvent)
	    try {
	      stdErrReader.join(1000);
	      stdErrPin.close();
	    } catch (Exception e) {
	    	_logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, e);	    	
	    }
	  }

	  /** Close the window */
	  public synchronized void windowClosing(WindowEvent evt) {		  
	    setVisible(false);
	    dispose();
	  }

	  /** The real work... */
	  @SuppressWarnings("static-access")
	public synchronized void run() {
	    try {
	      while (Thread.currentThread() == stdOutReader) {
	        try {
	          this.wait(100);
	        } catch (InterruptedException ie) {
	        	_logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, ie);
	        }
	        if (stdOutPin.available() != 0) {
	          String input = this.readLine(stdOutPin);          
	          StyleConstants.setForeground(style, Color.black);
	          doc.insertString(doc.getLength(), input, style);          
	          // Make sure the last line is always visible
	          logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
	        }
	        if (stopThreads) {
	          return;
	        }
	      }

	      while (Thread.currentThread() == stdErrReader) {
	        try {
	          this.wait(100);
	        } catch (InterruptedException ie) {
	        }
	        if (stdErrPin.available() != 0) {
	          String input = this.readLine(stdErrPin);
	          StyleConstants.setForeground(style, Color.red);
	          doc.insertString(doc.getLength(), input, style);
	          // Make sure the last line is always visible
	          logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
	        }
	        if (stopThreads) {
	          return;
	        }
	      }
	    } catch (Exception e) {
	    	_logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, e);
	    	System.out.println(e.getMessage());
	    	logTextArea.setText("\nConsole reports an Internal error.");
	    	logTextArea.setText("The error is: " + e);
	    }
	  }

	  private synchronized String readLine(PipedInputStream in) throws IOException {
	    String input = "";
	    do {
	      int available = in.available();
	      if (available == 0) {
	        break;
	      }
	      byte b[] = new byte[available];
	      in.read(b);
	      input += new String(b, 0, b.length);
	    } while (!input.endsWith("\n") && !input.endsWith("\r\n") && !stopThreads);
	    return input;
	  }

	  //These methods must implement these inherited abstract methods from WindowListener
	  @Override
	  public void windowOpened(WindowEvent e) {
	  }

	  @Override
	  public void windowIconified(WindowEvent e) {}

	  @Override
	  public void windowDeiconified(WindowEvent e) {}

	  @Override
	  public void windowActivated(WindowEvent e) {}

	  @Override
	  public void windowDeactivated(WindowEvent e) {}

}
