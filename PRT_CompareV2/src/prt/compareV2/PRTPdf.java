package prt.compareV2;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class PRTPdf {		

	public PRTPdf(){
		
	}
	
	public void pdfToList(String pdfExePath, String pdfPath) throws IOException{
		ProcessBuilder pb = new ProcessBuilder(pdfExePath, pdfPath);
		pb.directory(new File("\\"));
		Process p = pb.start();

		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));

		String result = "";
		String addResult = "";
		
		while((result = br.readLine()) != null){
		    addResult = addResult + "\n";
		}
		System.out.println(addResult);
		br.close();
		
	}
	
}
