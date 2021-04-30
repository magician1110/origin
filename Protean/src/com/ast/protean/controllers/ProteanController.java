package com.ast.protean.controllers;

import java.awt.Cursor;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Level;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ast.protean.views.resources.Dita_ot_View;
import com.ast.protean.views.resources.LanguageList;
import com.ast.protean.views.resources.CEFrameView;

public class ProteanController {
	String ipAddr = null;
	InetAddress local;	
	boolean IpCheck;		
    public static List<String> LV_name = new ArrayList<String>();	
    public static List<String> lang_code = new ArrayList<String>();
    public static List<String> pluginLangList = new ArrayList<String>();
    public static String fileMapBeforePath = null;
    public static String fileMapBeforeBeforePath = null;   
   
    
	@SuppressWarnings("static-access")
	public boolean IpValidation(){
	  try {   
		  local = InetAddress.getLocalHost(); 
		  String ipAddr = local.getHostAddress(); 
		  String validIp = "(10\\.){3}\\d{2,3}";		 
		  if (!Pattern.matches(validIp, ipAddr)) {
			  System.out.println("IP Address가 올바르지 않습니다. ====" + ipAddr.matches(validIp));			  
		  }
		  IpCheck = ipAddr.matches(validIp);
		  //CheckIp(ipAddr);
	  } 
	  catch (UnknownHostException e1) {
		  CEFrameView._logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, e1);
	  	  e1.printStackTrace(); 
	  }
	return IpCheck;	 
   }
	
	@SuppressWarnings("static-access")
	public List<String> xmlPaser() {
		try {
			File myFile = new File("_reference\\language_codes\\lang_code_tables.xml");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = docBuilder.parse(myFile);
			
			NodeList fileList = doc.getElementsByTagName("language");
			
			for(int i = 0; i < fileList.getLength(); i++){
				Node fileNode = fileList.item(i);
				if(fileNode.getNodeType() == Node.ELEMENT_NODE){
					Element fileEle = (Element)fileNode;					
					LV_name.add(fileEle.getAttribute("LV_name"));
				}											
			}
						
			
		} catch (Exception e) {
			CEFrameView._logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, e);
			e.printStackTrace();
		}
		return LV_name;		
		
	}
	
	@SuppressWarnings("static-access")
	public static void transform(String sourcePath, String xsltPath, String resultDir, String lv_name) {
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(new File(xsltPath)));
			transformer.setOutputProperty(OutputKeys.ENCODING,"utf-8");						
			transformer.setParameter("LV_name", lv_name);											
			transformer.setParameter("status", CEFrameView.selectedPlugin);						
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");				
			transformer.transform(new StreamSource(new File(sourcePath)), new StreamResult(new File(resultDir)));
		} catch (TransformerException e) {
			CEFrameView._logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, e);			
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("static-access")
	public static void transform_29_debug(String sourcePath, String xsltPath, String resultDir) {
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(new File(xsltPath)));
			transformer.setOutputProperty(OutputKeys.ENCODING,"utf-8");	
			transformer.setParameter("outdir", resultDir);			
			// transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			// transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");		
			transformer.transform(new StreamSource(new File(sourcePath)), new StreamResult(new File(resultDir)));
		} catch (TransformerException e) {
			CEFrameView._logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, e);
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("static-access")
	public int getExecuteCommand(String Command) {
		  Process p = null;
		  try {
		       p = Runtime.getRuntime().exec(Command);
		       p.waitFor ();
		       if (p.exitValue () != 0) {
		           BufferedReader err = new BufferedReader (new InputStreamReader (p.getErrorStream ()));
		           while (err.ready())
		               System.out.println ("ERR"+err.readLine ());
		           err.close ();
		       } else {
		           BufferedReader out = new BufferedReader (new InputStreamReader (p.getInputStream()));
		           while (out.ready())
		               System.out.println ("O K"+ out.readLine ());
		           out.close ();
		       }
		       p.destroy ();
		   } catch (IOException e) {
			   CEFrameView._logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, e);
		       e.printStackTrace();
		  } catch (InterruptedException e) {
			   CEFrameView._logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, e);
		       e.printStackTrace();
		   }

		  return 0;
		}
	
	@SuppressWarnings("static-access")
	public String execCommand(String cmd){
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			String line = null;
			StringBuffer readBuffer = new StringBuffer();
			
			while ((line = br.readLine()) != null) {
				readBuffer.append(line);
				readBuffer.append("\n");
				
			}
			
			return readBuffer.toString();
			
		} catch (Exception e) {
			CEFrameView._logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, e);
			System.exit(1);
		}
		return null;
	}
	
    @SuppressWarnings("static-access")
	public static void copy(File sourceF, File targetF){
    	
	File[] target_file = sourceF.listFiles();
	for (File file : target_file) {
		File temp = new File(targetF.getAbsolutePath() + File.separator + file.getName());
		if(file.isDirectory()){
			temp.mkdir();
			copy(file, temp);
		} else {
		        FileInputStream fis = null;
			FileOutputStream fos = null;
			try {
				fis = new FileInputStream(file);
				fos = new FileOutputStream(temp) ;
				byte[] b = new byte[4096];
				int cnt = 0;
				while((cnt=fis.read(b)) != -1){
					fos.write(b, 0, cnt);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				try {
					fis.close();
					fos.close();
				} catch (IOException e) {
					CEFrameView._logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, e);
					e.printStackTrace();
				}
					
			}
		}
	   }
    }
	
    @SuppressWarnings("static-access")
	public static void delete(String path) {
		
	File folder = new File(path);
	try {
		if(folder.exists()){
		    File[] folder_list = folder.listFiles();
				
		    for (int i = 0; i < folder_list.length; i++) {
			if(folder_list[i].isFile()) {
				folder_list[i].delete();
			}else {
				delete(folder_list[i].getPath());
			}
			folder_list[i].delete();
		    }
		}
	} catch (Exception e) {
		CEFrameView._logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, e);
	}
    }
    
	public void batchEx(String command) throws Exception{ 
		
		int result;
        InputStreamReader isr = null;
        BufferedReader br = null;
        //1번째 방법 외부 호출
		//String[] command = {"cmd.exe", "/C", "Start", "C:\\MMI_AutoMatching\\functions\\exportApppath.bat"};        
        //Process p =  Runtime.getRuntime().exec(command); 
        //2번째 방법 내부 호출
        //String command = "exportApppath.bat"; //해당 배치에서 applist.txt를 생성함
        Process p =  Runtime.getRuntime().exec(command);
        
        isr = new InputStreamReader(p.getInputStream());
        br = new BufferedReader(isr);
        String line = null;     
        while((line = br.readLine()) !=null){
        	//System.out.println(line);
        }
        int waitFor = p.waitFor();
        result = p.exitValue();
        //System.out.println("waitFor ==  " + waitFor + "result ====" + result );
    }
	
	@SuppressWarnings("static-access")
	public static void pluginXmlPaser(String filePath) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = docBuilder.parse(filePath);
			new StringBuilder();

			NodeList tagRegionList = doc.getElementsByTagName("region");			

			for (int i = 0; i < tagRegionList.getLength(); i++) {
				Node nNodeRegion = tagRegionList.item(i);
				tagRegionList.item(i);

				if (nNodeRegion.hasChildNodes()) {
					new ArrayList<>();
					NodeList codes = nNodeRegion.getChildNodes();
					for (int j = 0; j < codes.getLength(); j++) {
						Node code = codes.item(j);

						if (code.getTextContent().trim().length() > 0) {
							pluginLangList.add(code.getTextContent());
						}
					}

				}
			}

		} catch (Exception e) {
			CEFrameView._logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, e);			
		}
	}
	
	@SuppressWarnings("static-access")
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
			CEFrameView._logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, e);
			return null;
		}
	}
	
	@SuppressWarnings("static-access")
	public String moveFile(String beforeFilePath, String afterFilePath) {
		try{
			File file =new File(beforeFilePath); 
			// 옮기 고자 하는 경로로 파일 이동 			
			file.renameTo(new File(afterFilePath)); 			
		}catch(Exception e){ 
			CEFrameView._logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, e);
			e.printStackTrace(); 
		}
		return afterFilePath;
		 		
	}

	@SuppressWarnings("static-access")
	public void processEx() {
		 
	    try {	    	
	    	String debugs_abstract_result;
	        String outputLineFromCommand;
	        
	        execCommand("net use z: \\\\10.10.10.222\\AHFormatterV63 /PERSISTENT:YES");
	        fileMapBeforePath = CEFrameView.mapPathField.getText().substring(0, CEFrameView.mapPathField.getText().lastIndexOf("\\"));
	        System.out.println("fileMapBeforePath ========================" + fileMapBeforePath);
	        fileMapBeforeBeforePath = fileMapBeforePath.substring(0, fileMapBeforePath.lastIndexOf("\\"));
	        System.out.println("fileMapBeforeBeforePath ==========================" + fileMapBeforeBeforePath);
	        Dita_ot_View dd = new Dita_ot_View();	        
	        dd.dispose();	       
	        for(int i = 0; i < LanguageList.model_2.size(); i++){//dita_ot_Field.getText()	        	
	        	transform(CEFrameView.mapPathField.getText(), "_reference\\xsls\\00-identity.xsl", fileMapBeforeBeforePath+"\\dummy.xml", LanguageList.model_2.get(i));
	        	//Process process = Runtime.getRuntime().exec("dita.bat" + " --input=" + MAFrameView.mapPathField.getText().replaceAll("000_", "") + " --format=" + MAFrameView.selectedPlugin + " -DLV_name=" + LanguageList.model_2.get(i) + " --args.filter=" + MAFrameView.filterPathField.getText() + " --output.dir=" + fileMapBeforeBeforePath + "\\out");
	        	if(dd.dita_Text == null || dd.dita_Text == ""){
	        		Process process = Runtime.getRuntime().exec("dita.bat" + " --input=" + CEFrameView.mapPathField.getText().replaceAll("000_", "") + " --format=" + CEFrameView.selectedPlugin + " -DLV_name=" + LanguageList.model_2.get(i) + " --args.filter=" + CEFrameView.filterPathField.getText() + " --output.dir=" + fileMapBeforeBeforePath + "\\out");
			        // Capture the output
			        BufferedReader inputStream = new BufferedReader(new InputStreamReader(process.getInputStream()));	        
			        BufferedReader errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));	        
			        // Get the output of the process
			        while ((outputLineFromCommand = inputStream.readLine()) != null) {
			          // This will be displayed in the console...
			          System.out.println(outputLineFromCommand);
			        }
			        inputStream.close();
			        // Same for errors, if any
			        while ((outputLineFromCommand = errorStream.readLine()) != null) {
			          System.out.println(outputLineFromCommand);
			        }
			        errorStream.close();
			        process.waitFor();
			        
			        System.out.println("Process finished"); 
			        
	        	}else{
	        		Process process = Runtime.getRuntime().exec(dd.dita_Text + " --input=" + CEFrameView.mapPathField.getText().replaceAll("000_", "") + " --format=" + CEFrameView.selectedPlugin + " -DLV_name=" + LanguageList.model_2.get(i) + " --args.filter=" + CEFrameView.filterPathField.getText() + " --output.dir=" + fileMapBeforeBeforePath + "\\out");
			        // Capture the output
			        BufferedReader inputStream = new BufferedReader(new InputStreamReader(process.getInputStream()));	        
			        BufferedReader errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));	        
			        // Get the output of the process
			        while ((outputLineFromCommand = inputStream.readLine()) != null) {
			          // This will be displayed in the console...
			          System.out.println(outputLineFromCommand);
			        }
			        inputStream.close();
			        // Same for errors, if any
			        while ((outputLineFromCommand = errorStream.readLine()) != null) {
			          System.out.println(outputLineFromCommand);
			        }
			        errorStream.close();
			        process.waitFor();
			        
			        System.out.println("Process finished"); 
	        	}

	        }
	        //애초에 debug-abstract.html을 특정한 경로로 아웃풋 하려 했으나, xlst의 로직에서 동시에 물고 있어서 문제가 생겨 move로 진행함.
	        transform_29_debug(fileMapBeforeBeforePath+"\\dummy.xml", "_reference\\xsls\\29-debug-abstract.xsl",fileMapBeforeBeforePath+"\\dummy2.xml");	        											
	        moveFile(fileMapBeforeBeforePath + "\\debugs-abstract.html", fileMapBeforeBeforePath + "\\out\\2_HTML\\debugs\\debugs-abstract.html");	        

	      } catch (Exception err) {
	    	  CEFrameView._logger.getLogger(CEFrameView.class.getName()).log(Level.INFO, null, err);
	    	  err.printStackTrace();
	      } finally {
	    	  try {
				batchEx("_reference\\AHF_ConnectDelete.bat");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	public void openFolder(File dirToOpen){
	    Desktop desktop = Desktop.getDesktop();	    
	    try {	        
	        try {
				desktop.open(dirToOpen);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    } catch (IllegalArgumentException iae) {
	    	System.out.println(iae.getMessage());
	        System.out.println("File Not Found");
	    }	
	}
	

/*	public void getLastModifiedFile(File directory) {
		//XLSM 32bit JVM 64bit 이므로 실행 X 
	   File[] files = directory.listFiles();
	   if (files.length == 0) //return null;
	    Arrays.sort(files, new Comparator<File>() {
	        public int compare(File o1, File o2) {
	            return new Long(o2.lastModified()).compareTo(o1.lastModified()); 	            
	        }});	   

	   String command = files[0].getAbsolutePath();
	   
	   try {
	       Process process = Runtime.getRuntime().exec(command);
	    
	       BufferedReader reader = new BufferedReader(
	               new InputStreamReader(process.getInputStream()));
	       String line;
	       while ((line = reader.readLine()) != null) {
	           System.out.println(line);
	       }
	    
	       reader.close();
	    
	   } catch (IOException e) {
	       e.printStackTrace();
	   }
	    //return files[0];
	}*/

}
