package com.ast.protean.views.resources;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
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
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.ast.protean.controllers.ProteanController;


public class Dita_ot_View extends JFrame {
	
	JPanel panel;
	JLabel dita_ot_Label;
	JButton dita_ot_Button;
	public static JTextField dita_ot_Field;
	JLabel reference_Label;
	public static JTextField reference_Field;
	public static String dita_Text = null;
	JButton reference_Button;
	public static final Logger _logger = Logger.getLogger(Dita_ot_View.class.getName());  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Dita_ot_View(){		
	    panel = new JPanel();	    
		
	    dita_ot_Label = new JLabel("Dita_ot");
		dita_ot_Label.setBounds(15,5,50,50);		
		dita_ot_Label.setFont(new Font("맑은 고딕",  Font.PLAIN, 14));
		panel.add(dita_ot_Label);
	    
		dita_ot_Field = new JTextField();
		dita_ot_Field.setBounds(15, 45, 345, 25);
		dita_ot_Field.setFont(new Font("맑은 고딕",  Font.PLAIN, 12));
		dita_ot_Field.setDragEnabled(true);
		dita_ot_Field.setDropTarget(new DropTarget(){
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
                    }/*else if(!droppedFiles.get(0).getName().endsWith(".ditamap")){
                        evt.rejectDrag();
                	}*/
				} catch (UnsupportedFlavorException | IOException ex) {
					_logger.getLogger(Dita_ot_View.class.getName()).log(Level.INFO, null, ex);                    				
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
                    	dita_ot_Field.setText(file.getAbsolutePath());                       
                    }
                }catch (UnsupportedFlavorException | IOException ex) {
                	_logger.getLogger(Dita_ot_View.class.getName()).log(Level.INFO, null, ex);
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
				
	});
		
		panel.add(dita_ot_Field);
		
		dita_ot_Button = new JButton("...");
		dita_ot_Button.setBounds(360,45,25,24);
		dita_ot_Button.setBackground(Color.white);
		dita_ot_Button.setFont(new Font("맑은 고딕",  Font.PLAIN, 12));
		dita_ot_Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jc = new JFileChooser(FileSystemView.getFileSystemView());
				//jc.setCurrentDirectory("");
				jc.setAcceptAllFileFilterUsed(false);
				//FileNameExtensionFilter filter = new FileNameExtensionFilter("DITA Map", "ditamap");
				//jc.addChoosableFileFilter(filter);
				jc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				
				int returnVal = jc.showOpenDialog(null);				
				
				if(returnVal == JFileChooser.APPROVE_OPTION){					
					dita_ot_Field.setText(jc.getSelectedFile().getAbsolutePath());
					dita_Text = dita_ot_Field.getText();					
				}/*else if(returnVal == JFileChooser.CANCEL_OPTION){
					dita_ot_Field.setText("");
		        }*/
				
			}
		});
		panel.add(dita_ot_Button);
/*	    
	    reference_Label  = new JLabel("Reference");
	    reference_Label.setBounds(15,60,80,50);		
	    reference_Label.setFont(new Font("맑은 고딕",  Font.PLAIN, 14));
	    panel.add(reference_Label);
	    
		reference_Field = new JTextField();
		reference_Field.setBounds(15, 100, 345, 25);
		reference_Field.setFont(new Font("맑은 고딕",  Font.PLAIN, 12));
		reference_Field.setDragEnabled(true);
		reference_Field.setDropTarget(new DropTarget(){
			
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
				_logger.getLogger(Dita_ot_View.class.getName()).log(Level.INFO, null, ex);                    				
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
                	reference_Field.setText(file.getAbsolutePath());                       
                }
            }catch (UnsupportedFlavorException | IOException ex) {
            	_logger.getLogger(Dita_ot_View.class.getName()).log(Level.INFO, null, ex);
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
			
});
		panel.add(reference_Field);
		
		reference_Button = new JButton("...");
		reference_Button.setBounds(360,100,25,24);
		reference_Button.setBackground(Color.white);
		reference_Button.setFont(new Font("맑은 고딕",  Font.PLAIN, 12));
		reference_Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jc = new JFileChooser(FileSystemView.getFileSystemView());
				//jc.setCurrentDirectory("");
				jc.setAcceptAllFileFilterUsed(false);
				//FileNameExtensionFilter filter = new FileNameExtensionFilter("DITA Map", "ditamap");
				//jc.addChoosableFileFilter(filter);
				jc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				int returnVal = jc.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION){									
					reference_Field.setText(jc.getSelectedFile().getAbsolutePath());					
				}else if(returnVal == JFileChooser.CANCEL_OPTION){
					reference_Field.setText("");
		        }
				
			}
		});
		panel.add(reference_Button);*/
		
		TitledBorder tittb = new TitledBorder(new LineBorder(Color.lightGray), "Settings");
		tittb.setTitleColor(Color.black);			
		tittb.setTitleFont(new Font("맑은 고딕",  Font.PLAIN, 18));
		
		panel.setLayout(null);		
		panel.setBounds(0,0,395,100); //좌우, 위아래, 넓이, 높이
		panel.setBackground(Color.white);
		panel.setBorder(tittb);
		
		this.setTitle("Settings");
	    this.setBounds(600, 400, 400, 150);//좌우, 위아래, 넓이, 높이
	    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	    
	    this.getContentPane().setBackground(Color.WHITE);
		ImageIcon img = new ImageIcon(MainView.getResourceAsFile("docuB.gif").getPath());					
		this.setIconImage(img.getImage());
	    this.setLayout(null);	    
		this.setResizable(false);			
		this.setVisible(true);		
		this.getContentPane().add(panel);

		//this.add(panel);

		
	}
	
}
