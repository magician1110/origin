package com.ast.protean.views.resources;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class AboutProteanBarView {
	JFrame ap = new JFrame();
    JPanel panel1= new JPanel(); 	
    ImageIcon icon = new ImageIcon(MainView.getResourceAsFile("aboutProtean.png").getPath());
    
	public AboutProteanBarView(){		
	   JLabel label1= new JLabel(icon, SwingConstants.CENTER);		
	   JLabel label2= new JLabel("Copyright ㈜ 2019-2020 TCS",SwingConstants.CENTER);
	   JTextArea textField1Area = new JTextArea("System Information: Windows7, Windows10");
	   JTextArea textField2Area = new JTextArea("Ask TCS if you have any problems");			
	   panel1.setBackground(Color.white);
       panel1.add(label1);
	   panel1.add(label2);
	   panel1.add(textField1Area);
	   panel1.add(textField2Area);
	   ap.add(panel1);
	   ap.setTitle("About Protean View");
	   ap.setBounds(600, 400, 300, 250);						
	   ap.setVisible(true);
	   ap.setResizable(false);
	}

	
}
