package com.ast.protean.views.resources;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.ast.protean.controllers.ProteanController;

public class MainView{
	private JFrame mv = new JFrame();
	String selectButton = "";
	
	public MainView() throws IOException{
		ProteanController PC = new ProteanController();		
		PC.IpValidation();
		if(PC.IpValidation() == true){
			String Ip = InetAddress.getLocalHost().toString();
			
			JButton button1 = new JButton("CE");			
			button1.setPreferredSize(new Dimension(270, 40));
			button1.setBackground(Color.WHITE);
			button1.setFont(new Font("HY엽서L", Font.PLAIN, 15));
			button1.addActionListener(new ActionListener() {
				
				@SuppressWarnings("unused")
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton btn1 = (JButton)e.getSource();
					if(btn1.getText().equals("CE")){
						selectButton = btn1.getText();
						try {
							CEFrameView mfv = new CEFrameView(selectButton);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}						
						mv.dispose();
					}else{
						JOptionPane.showMessageDialog(null, "Please contact the administrator.", "Error", JOptionPane.ERROR_MESSAGE);
					}					
				}
			});
			JButton button2 = new JButton("MA");			
			button2.setPreferredSize(new Dimension(270, 40));
			button2.setBackground(Color.WHITE);
			button2.setFont(new Font("HY엽서L", Font.PLAIN, 15));
			button2.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton btn1 = (JButton)e.getSource();
					if(btn1.getText().equals("MA")){
						selectButton = btn1.getText();						
					}else{
						JOptionPane.showMessageDialog(null, "Please contact the administrator.", "Error", JOptionPane.ERROR_MESSAGE);
					}  
				}
			});
			JTextField ipText = new JTextField();
			ipText.setText("IP Address : " + Ip);
			ipText.setEditable(false);			
					
			ImageIcon img = new ImageIcon(getResourceAsFile("docuB.gif").getPath());
			ProteanController pc = new ProteanController();
			
			mv.add(button1);	
			mv.add(button2);
			mv.add(ipText);
			mv.getContentPane().setBackground(Color.white);
			mv.setIconImage(img.getImage());			
			mv.setTitle("PROTEAN Version_1.0");
			mv.setBounds(800, 200, 300, 300);			
			mv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			
			mv.setLayout(new FlowLayout());
			mv.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {					
					System.exit(0); 
				}			
			});
			mv.setVisible(true);
			mv.setResizable(false);												
		}else{			
			JOptionPane.showMessageDialog(null, "Please contact the administrator.", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
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
