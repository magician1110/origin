import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;import com.ibm.icu.impl.number.Rounder.IBasicRoundingProperties;

public class MainFrame extends JFrame {
	
	
	public static void MainFrame() {
		Frame f = new Frame("AST Main");
		f.setSize(700, 540); // 500, 440
		f.setLayout(null);
		f.setResizable(true);		
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		
		ImageIcon img = new ImageIcon(MainFrame.getResourceAsFile("Ast.jpg").getPath());
		JLabel lbImage1 = new JLabel(img);
		lbImage1.setSize(430, 50);
		lbImage1.setLocation(40, 40);	
		
		ImageIcon xmlImg = new ImageIcon(MainFrame.getResourceAsFile("XMLImage2.jpg").getPath());
		JLabel xmlImage = new JLabel(xmlImg);
		xmlImage.setSize(200, 200);
		xmlImage.setLocation(35, 45);
		
		ImageIcon docuBImg = new ImageIcon(MainFrame.getResourceAsFile("001-add-file.png").getPath());
		JLabel docuBImage = new JLabel(docuBImg);
		docuBImage.setSize(300, 200);
		docuBImage.setLocation(65, 85);
		/*
		ImageIcon docu2Img = new ImageIcon(MainFrame.getResourceAsFile("docu2.jpg").getPath());
		JLabel docu2Image = new JLabel(docu2Img);
		docu2Image.setSize(200, 200);
		docu2Image.setLocation(35, 45);*/
		
		f.add(lbImage1);
		f.add(xmlImage);
		f.add(docuBImage);
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
	
	public static void main(String args[]) {
		MainFrame();
	}
	
	
}
