import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Extraction {
	ArrayList<String> snl = new ArrayList<String>();	
	ArrayList<String> removeDuplSnl = new ArrayList<String>();		
	ArrayList<String> subStrFileTextArr = new ArrayList<String>();	
/*	ArrayList<String> dirCommandArr = new ArrayList<String>();
	ArrayList<String> dirOnlyPath = new ArrayList<String>();*/
	public void getStringNameList() throws Exception{		
		File xmlFile = new File("C:\\MMI_AutoMatching\\stringname-list.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();		
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();		
		Document doc = dBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize(); //문서 구조 안정화.
										
		NodeList nodeList = doc.getElementsByTagName("*");		
		
		for(int temp = 0; temp < nodeList.getLength(); temp++){
			Node node = nodeList.item(temp);
			
			if(node.getNodeType() == Node.ELEMENT_NODE){
				Element ele2 = (Element) node;				
				snl.add(ele2.getAttribute("package"));								
			}
		}
		for(int j = 0; j < snl.size(); j++){
			if(!removeDuplSnl.contains(snl.get(j))){
				removeDuplSnl.add(snl.get(j));
			}				
		}
		
	}
	
	public void fileWrite() throws IOException{
		File fileWiter = new File("C:\\MMI_AutoMatching\\packagelist.txt");
		BufferedWriter bw = null;		
		try {
			bw = new BufferedWriter(new FileWriter(fileWiter));			
			if(null != bw){				
				for(int j = 0; j < removeDuplSnl.size(); j++){
					bw.write(removeDuplSnl.get(j));
					bw.write("\n");
				}
				bw.flush();
				bw.close();
			}
		

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void batchEx() throws Exception{ 
		
		if(checkFile("C:\\MMI_AutoMatching\\applist.txt") == true){
			deleteFile("C:\\MMI_AutoMatching\\applist.txt");
		}
		int result;
        InputStreamReader isr = null;
        BufferedReader br = null;
        //1번째 방법 외부 호출
		String[] command = {"cmd.exe", "/C", "Start", "C:\\MMI_AutoMatching\\functions\\exportApppath.bat"};        
        Process p =  Runtime.getRuntime().exec(command); 
        //2번째 방법 내부 호출
/*        String command = "exportApppath.bat"; //해당 배치에서 applist.txt를 생성함
        Process p =  Runtime.getRuntime().exec(command);*/
        
        isr = new InputStreamReader(p.getInputStream());
        br = new BufferedReader(isr);
        String line = null;     
        while((line = br.readLine()) !=null){
        	//System.out.println("라인 ====" + line);
        }
        //split 짜르는 문제로 인해
        removeLine("split");
        
        int waitFor = p.waitFor();
        result = p.exitValue();
        System.out.println("waitFor ==  " + waitFor + "result ====" + result );
    }
	
	public void pullFileList() throws Exception{
		if(checkFile("C:\\MMI_AutoMatching\\apk-source") == false){ //존재 하지 않음
			createDir("C:\\MMI_AutoMatching\\apk-source"); //dir생성
		}/*else{ //존재함 
			deleteDir(new File("apk-source"));
			createDir("apk-source"); //dir생성
		}*/		 //우선 주석 처리 : 그냥 덮어 씌어지는걸로 처리하기로 함.
		
		String fileText = null;
		String subStrFileText = null;
		String fileName = null;
		try {  //newApplist
			FileReader filePull = new FileReader("C:\\MMI_AutoMatching\\newApplist.txt");
			BufferedReader br = new BufferedReader(filePull);
			LineNumberReader lr = new LineNumberReader(br);
				
			int i = 0;
			while((fileText = lr.readLine()) != null){				
				subStrFileText = fileText.substring(8);	
				fileName = subStrFileText.substring(subStrFileText.lastIndexOf('/')+1,subStrFileText.length());
				
				if(subStrFileText.contains("base.apk")){//base.apk를 replace를 하면 날라가니까base.apk를 넣어줌.   adb pull /data/app/com.samsung.android.themestore-K3GWwtUzxeE9KS7N2cFtpw==/base.apk  themestore-base.apk logcat *:s >> log.txt
					subStrFileText = subStrFileText.replaceAll("base.apk", "base.apk C:/MMI_AutoMatching/apk-source/" + betWeen(subStrFileText, "-", "==")+".base.apk");					
				}else if(subStrFileText.contains("apk")){//.apk를 replace를 하면 날라가니까.apk를 넣어줌.
					subStrFileText = subStrFileText.replaceAll(".apk", ".apk C:/MMI_AutoMatching/apk-source/" + fileName); 								
				}															
				subStrFileTextArr.add(subStrFileText);							
			}			
			filePull.close();
			br.close();
			lr.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		}

    }
	
	public void pullFileWriter(){	
		File pullFileWriter = new File("C:\\MMI_AutoMatching\\pullFileList.txt");
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(pullFileWriter));
			if(null != bw){				
				for(int i = 0; i < subStrFileTextArr.size(); i++){					
					bw.write("adb pull " + subStrFileTextArr.get(i));
					bw.write("\n");
				}
				bw.flush();
				bw.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String betWeen(String source, String firstTarget, String secondTarget){
		int iFirstTarget, iSecondTarget;
		String hap = "";
		iFirstTarget = source.indexOf(firstTarget);		
		if (iFirstTarget >= 0) {
			iSecondTarget= source.indexOf(secondTarget, iFirstTarget + firstTarget.length());			
			if (iSecondTarget > iFirstTarget)
			{
				hap = source.substring(iFirstTarget + firstTarget.length(), iSecondTarget);
				
			}			
		}
		
		return hap;
	}
	
	private static boolean checkFile(String filePath){
		File file = new File(filePath);
		return file.exists();
	}
	
	private void deleteFile(String filePath){
		File file = new File(filePath);
		file.delete();
	}
	
	private void createDir(String filePath) throws IOException{
		File file = new File(filePath);
		file.mkdir();
	}
	
	public static boolean deleteDir(File filePath){
		if(!filePath.exists()){
			return false; 
			} 
		File[] files = filePath.listFiles(); 
		for (File file : files){
			if (file.isDirectory()){
				deleteDir(file); 
			}else{
				file.delete(); 
				} 
			} return filePath.delete(); 
	}

	public void batchEx2() throws Exception{ 		
		int result;
        InputStreamReader isr = null;
        BufferedReader br = null;
        MainDisplay md = new MainDisplay();
        String command = "C:\\MMI_AutoMatching\\functions\\exportApp.bat";
        Process p =  Runtime.getRuntime().exec(command);
        
        isr = new InputStreamReader(p.getInputStream());
        br = new BufferedReader(isr);
        String line = null;     
        while((line = br.readLine()) !=null){
        	md.logTextArea.setText("line");        	
        }
//exportApp apk를 apk-source로 빼는게 문제
        int waitFor = p.waitFor();
        result = p.exitValue();       
    }
	
	public void deCompile2() throws Exception{
		int result;
        InputStreamReader isr = null;
        BufferedReader br = null;
        MainDisplay md = new MainDisplay();
        //fileDir(new File("C:/MMI_AutoMatching/apk-source/"));
        //for(int i = 0; i < dirCommandArr.size(); i++){
        	String[] command = {"cmd.exe", "/C", "Start", "C:/MMI_AutoMatching/functions/decompile99.bat"};        
            Process p =  Runtime.getRuntime().exec(command);
        	/*Runtime rt = Runtime.getRuntime(); 
    		Process p = rt.exec("java -jar C:/MMI_AutoMatching/functions/apk-tools/apktool.jar d -s -f" + " " + dirCommandArr.get(i)+ " " + "-o" + " " + dirOnlyPath.get(i));*/	
            /*String command = "java -jar C:/MMI_AutoMatching/functions/apk-tools/apktool.jar d -s" + " " + dirCommandArr.get(i)+ " " + "-o" + " " + dirOnlyPath.get(i);
            Process p =  Runtime.getRuntime().exec(command);*/
            isr = new InputStreamReader(p.getInputStream());
            br = new BufferedReader(isr);
            String line;
            try {
            	if((line = br.readLine()) != null){
            		while((line = br.readLine()) !=null){    
                		System.out.println("line ===" + line);
                    	md.logTextArea.setText("디컴파일중..." + line);
                    }	
            	}
            		
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
            //DECOMPILE전에 무언가 돌고있는거를 확인불가~~~~~~~~~~~~~~~~~~~~~~~

            int waitFor = p.waitFor();
            result = p.exitValue();
        //}

    }
	//robocopy "apk-source" "DC_Res" /S *strings.xml AndroidManifest.xml /XD dirs "original" "assets" "raw"
	public void recopy() throws IOException{	
		if(checkFile("C:\\MMI_AutoMatching\\DC_Res") == false){ //존재 하지 않음
			createDir("C:\\MMI_AutoMatching\\DC_Res"); //dir생성
		}/*else{ //존재함 
			deleteDir(new File("DC_Res"));
		}*/
		
		MainDisplay md = new MainDisplay();
        InputStreamReader isr = null;
        BufferedReader br = null;
		String[] command = {"cmd.exe", "/c", "Start", "C:\\MMI_AutoMatching\\functions\\rbcopy.bat"};        
        Process p =  Runtime.getRuntime().exec(command);
        isr = new InputStreamReader(p.getInputStream());
        br = new BufferedReader(isr);
        String line;
        try {
        	while((line = br.readLine()) !=null){ 
        		md.logTextArea.setText("Copy..." + line);
            	//System.out.println("Copy..." + line);
            }	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public static void transform(String sourcePath, String xsltPath, String resultDir){
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(new File(xsltPath)));
			transformer.setOutputProperty(OutputKeys.ENCODING,"utf-8");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");				
			transformer.transform(new StreamSource(new File(sourcePath)), new StreamResult(new File(resultDir)));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	public void removeLine(String lineContent) throws IOException{
	    File file = new File("C:\\MMI_AutoMatching\\applist.txt");
	    File temp = new File("C:\\MMI_AutoMatching\\newApplist.txt");
	    PrintWriter out = new PrintWriter(new FileWriter(temp));
	    Files.lines(file.toPath())
	        .filter(line -> !line.contains(lineContent))
	        .forEach(out::println);
	    out.flush();
	    out.close();
	    temp.renameTo(file);
	}
	
	public static void runVBS() throws Exception{
		if(checkFile("C:\\MMI_AutoMatching\\Exactract-mmi-db.xlsm") == false){
			JOptionPane.showMessageDialog(null, "Error.", "Exactract-mmi-db.xlsm가 존재 하지 않습니다.", JOptionPane.ERROR_MESSAGE);
		}					  
		   try {
/*			  String[] parms = {"cscript", "run_excel.vbs"};
			  Process p = Runtime.getRuntime().exec(parms);		*/
			   Runtime.getRuntime().exec(new String[] {"wscript.exe","C:\\MMI_AutoMatching\\functions\\run_excel.vbs"});
		   }
		   catch( IOException e ) {
		      e.printStackTrace();		      
		   }		   
	}
	
/*	public void fileDir(File fireD){
		File fileDir = fireD;
		String fileCommand = null;
		String onlyPath = null;
		int pos;
        // 1. check if the file exists or not
        boolean isExists = fileDir.exists();         
        if(!isExists) {
            System.out.println("There is nothing.");
        }         
       
		// 2. check if the object is directory or not.
        if(fileDir.isDirectory()) {
            File[] fileList = fileDir.listFiles();
            for(File tFile : fileList) {
                //System.out.print(tFile.getName());             
                if(tFile.isDirectory()) {                    
                    System.out.println("a directory.");
                } else {
                	fileCommand = tFile.getPath();
                    pos = fileCommand.lastIndexOf(".");
                    onlyPath = fileCommand.substring(0,pos);
                	
                	System.out.println("onlyPath ====" + onlyPath);
                	System.out.println("fileCommand ====" + fileCommand);
                	dirCommandArr.add(fileCommand);
                	dirOnlyPath.add(onlyPath);
                	
                }
            }          
        } else {
            System.out.println("It is not a directory.");
        }
	
		
	}*/


}
