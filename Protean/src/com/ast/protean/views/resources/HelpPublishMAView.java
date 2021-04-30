package com.ast.protean.views.resources;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class HelpPublishMAView {
	JFrame HPMV = new JFrame();
    JPanel panel1= new JPanel(); 	 
    
	public HelpPublishMAView(){					   
	   HPMV.add(panel1);
	   HPMV.setTitle("MA Publish Display");
	   HPMV.setBounds(880, 400, 450, 450);//좌우, 위아래, 넓이, 높이					
	   HPMV.setVisible(true);
	   HPMV.setResizable(false);
	}
}
