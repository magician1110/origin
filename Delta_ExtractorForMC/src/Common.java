import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Common {	
	
	@SuppressWarnings("static-access")
	public static void transform(String sourcePath, String xsltPath, String resultDir) {
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(new File(xsltPath)));
			//transformer.setParameter("beforePath", "C:\\workspace\\Delta_ExtractorForMC\\source\\car\\beforeXML");
			transformer.setParameter("beforePath", DeltaView.jfBeforeXML.getText());
			//transformer.setParameter("afterPath", "C:\\workspace\\Delta_ExtractorForMC\\source\\car\\afterXML");
			transformer.setParameter("afterPath", DeltaView.jfAfterXML.getText());
			//transformer.setParameter("tempPath", "C:\\workspace\\Delta_ExtractorForMC\\temp");
			//transformer.setParameter("tempPath", "temp");
			transformer.setOutputProperty(OutputKeys.ENCODING,"utf-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");					
			transformer.transform(new StreamSource(new File(sourcePath)), new StreamResult(new File(resultDir)));
		} catch (TransformerException e) {
			Logger.getLogger(DeltaView.class.getName()).log(Level.DEBUG, null, e);
			e.printStackTrace();
		}
	}

	
	@SuppressWarnings("static-access")
	public static void batchEx(File batchFilePath, String inPath,String outPath) throws Exception{ 

		int result;
        InputStreamReader isr = null;
        BufferedReader br = null;
        //1번째 방법 외부 호출
		String[] command = {"cmd.exe", "/C", "Start", batchFilePath.getAbsolutePath(), inPath, outPath};   
		
		try {
	        Process p =  Runtime.getRuntime().exec(command); 
	        //2번째 방법 내부 호출
	/*        String command = "exportApppath.bat"; //해당 배치에서 applist.txt를 생성함
	        Process p =  Runtime.getRuntime().exec(command);*/        
	        isr = new InputStreamReader(p.getInputStream());
	        br = new BufferedReader(isr);
	        String line = null;     
	        while((line = br.readLine()) !=null){
	        	System.out.println("Line ====" + line);
	        } 	        
	        int waitFor = p.waitFor();
	        result = p.exitValue();
	        System.out.println("waitFor ==  " + waitFor + "result ====" + result );
		} catch (Exception e) {
			Logger.getLogger(DeltaView.class.getName()).log(Level.DEBUG, null, e);
		}

    }
	
	public static void batchEx(File batchFilePath, String optionOne, String optionTwo, String outPath, String out) throws Exception{ 

		int result;
        InputStreamReader isr = null;
        BufferedReader br = null;
        //1번째 방법 외부 호출
		String[] command = {"cmd.exe", "/C", "Start", batchFilePath.getAbsolutePath(), optionOne, optionTwo, outPath, out};
		try {
	        Process p =  Runtime.getRuntime().exec(command); 
	        //2번째 방법 내부 호출
	/*        String command = "exportApppath.bat"; //해당 배치에서 applist.txt를 생성함
	        Process p =  Runtime.getRuntime().exec(command);*/        
	        isr = new InputStreamReader(p.getInputStream());
	        br = new BufferedReader(isr);
	        String line = null;     
	        while((line = br.readLine()) !=null){
	        	System.out.println("Line ====" + line);
	        } 	        
	        int waitFor = p.waitFor();
	        result = p.exitValue();
	        System.out.println("waitFor ==  " + waitFor + "result ====" + result );
		} catch (Exception e) {
			Logger.getLogger(DeltaView.class.getName()).log(Level.DEBUG, null, e);
		}

    }
	
/*	public static void batchEx(File batchFilePath, String optionOne, String optionTwo, String outPath, String out) throws Exception{ 

		int result;
        InputStreamReader isr = null;
        BufferedReader br = null;
        //1번째 방법 외부 호출
		String[] command = {"cmd.exe", "/C", "Start", batchFilePath.getAbsolutePath(), optionOne, optionTwo, outPath, out};        
        Process p =  Runtime.getRuntime().exec(command); 
        //2번째 방법 내부 호출
        String command = "exportApppath.bat"; //해당 배치에서 applist.txt를 생성함
        Process p =  Runtime.getRuntime().exec(command);        
        isr = new InputStreamReader(p.getInputStream());
        br = new BufferedReader(isr);
        String line = null;     
        while((line = br.readLine()) !=null){
        	System.out.println("Line ====" + line);
        } 
        
        int waitFor = p.waitFor();
        result = p.exitValue();
        System.out.println("waitFor ==  " + waitFor + "result ====" + result );
    }*/
	
	public static void batchEx(String string) throws IOException, InterruptedException {
		int result;
        InputStreamReader isr = null;
        BufferedReader br = null;
        //1번째 방법 외부 호출
		String[] command = {"cmd.exe", "/C", "Start", string};   
		
		try {
	        Process p =  Runtime.getRuntime().exec(command); 
	        //2번째 방법 내부 호출
	/*        String command = "exportApppath.bat"; //해당 배치에서 applist.txt를 생성함
	        Process p =  Runtime.getRuntime().exec(command);*/        
	        isr = new InputStreamReader(p.getInputStream());
	        br = new BufferedReader(isr);
	        String line = null;     
	        while((line = br.readLine()) !=null){
	        	System.out.println("Line ====" + line);
	        } 	        
	        int waitFor = p.waitFor();
	        result = p.exitValue();
	        System.out.println("waitFor ==  " + waitFor + "result ====" + result );
		} catch (Exception e) {
			Logger.getLogger(DeltaView.class.getName()).log(Level.DEBUG, null, e);
		}
	}
	
	/**
	* 
	* 압축풀기 메소드
	* @param zipFileName 압축파일
	* @param directory 압축 풀 폴더
	*/
	public static void decompress(String zipFileName, String directory) throws Throwable {
		File zipFile = new File(zipFileName);
		FileInputStream fis = null;
		ZipInputStream zis = null;
		ZipEntry zipentry = null;
		try {
			//파일 스트림
			fis = new FileInputStream(zipFile);
			//Zip 파일 스트림
			zis = new ZipInputStream(fis);
			//entry가 없을때까지 뽑기
			while ((zipentry = zis.getNextEntry()) != null) {
				String filename = zipentry.getName();
				File file = new File(directory, filename);
				//entiry가 폴더면 폴더 생성
				if (zipentry.isDirectory()) {
					file.mkdirs();
				} else {
					//파일이면 파일 만들기
					createFile(file, zis);
				}
			}
		}catch(Throwable e) {
		throw e;
		} finally {
			if (zis != null)
				zis.close();
			if (fis != null)
				fis.close();
		}
	}
	
	/**
	* 파일 만들기 메소드
	* @param file 파일
	* @param zis Zip스트림
	*/
	private static void createFile(File file, ZipInputStream zis) throws Throwable {
		//디렉토리 확인
		File parentDir = new File(file.getParent());
		//디렉토리가 없으면 생성하자
		if (!parentDir.exists()) {
		parentDir.mkdirs();
		}
		//파일 스트림 선언
		try (FileOutputStream fos = new FileOutputStream(file)) {
			byte[] buffer = new byte[256];
			int size = 0;
			//Zip스트림으로부터 byte뽑아내기
			while ((size = zis.read(buffer)) > 0) {
			//byte로 파일 만들기
			fos.write(buffer, 0, size);
		  }
		} catch (Throwable e) {
		throw e;
		}
	}

	/*
	 * 파일 ZIP압축
	 * */
	public static void compress(String path,String outputFileName) throws Throwable{
		File file = new File(path);
		int pos = outputFileName.lastIndexOf(".");
		if(!outputFileName.substring(pos).equalsIgnoreCase(".idml")){
		outputFileName += ".idml";
		}
		// 압축 경로 체크
		if(!file.exists()){
			throw new Exception("Not File!");
		}
		// 출력 스트림
		FileOutputStream fos = null;
		// 압축 스트림
		ZipOutputStream zos = null;
		try{
			fos = new FileOutputStream(new File(outputFileName));
			zos = new ZipOutputStream(fos);
			// 디렉토리 검색
			searchDirectory(file,zos);
		}catch(Throwable e){
			throw e;
		}finally{
			if(zos != null) zos.close();
			if(fos != null) fos.close();
		  }
		}
		/**
		* 다형성
		*/
		private static void searchDirectory(File file, ZipOutputStream zos) throws Throwable{
			searchDirectory(file,file.getPath(),zos);
		}
		/**
		* 디렉토리 탐색
		* @param file 현재 파일
		* @param root 루트 경로
		* @param zos 압축 스트림
		*/
		private static void searchDirectory(File file,String root,ZipOutputStream zos) throws Exception{		
			//지정된 파일이 디렉토리인지 파일인지 검색
			if(file.isDirectory()){
			//디렉토리일 경우 재탐색(재귀)
				File[] files = file.listFiles();
				for(File f : files){
					searchDirectory(f,root,zos);
				}
				}else{
				//파일일 경우 압축을 한다.
				compressZip(file,root,zos);
			}
		}
		/**
		* 압축 메소드
		* @param file
		* @param root
		* @param zos
		* @throws Exception
		*/
		private static void compressZip(File file, String root, ZipOutputStream zos) throws Exception{
			FileInputStream fis = null;
			try{
				String zipName = file.getPath().replace(root+"\\", "");
				// 파일을 읽어드림
				fis = new FileInputStream(file);
				// Zip엔트리 생성(한글 깨짐 버그)
				ZipEntry zipentry = new ZipEntry(zipName);
				// 스트림에 밀어넣기(자동 오픈)
				zos.putNextEntry(zipentry);
				int length = (int)file.length();
				byte[] buffer = new byte[length];
				//스트림 읽어드리기
				fis.read(buffer, 0, length);
				//스트림 작성
				zos.write(buffer, 0, length);
				//스트림 닫기
				zos.closeEntry();
				
			}catch(Throwable e){
				throw e;
			}finally{
				if(fis != null) fis.close();
			}
		}
		
		public static boolean deleteAllFiles(File folderPath) {
			
			if(!folderPath.exists()) {
				return false; 
				} 
			File[] files = folderPath.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					deleteAllFiles(file); 
					}else{
						file.delete(); 
						} 
				} return folderPath.delete();

		}
		
		public static void deleteFile(String folderPath) {
			File file = new File(folderPath);
			if( file.exists() ){
				if(file.delete()){
					System.out.println("파일삭제 성공");
					}else{
						System.out.println("파일삭제 실패"); 
						} 
				}else{
					System.out.println("파일이 존재하지 않습니다."); 
				} 
			
		}
		
		public static void CreateFolder(String folderPath){
		       //생성할 파일경로 지정
	        String path = folderPath;
	        //파일 객체 생성
	        File file = new File(path);
	        //!표를 붙여주어 파일이 존재하지 않는 경우의 조건을 걸어줌
	        if(!file.exists()){
	            //디렉토리 생성 메서드
	            file.mkdirs();
	            System.out.println("created directory successfully!");
	        }
	        
		}
		
		@SuppressWarnings("static-access")
		public static void openFolder(File dirToOpen){
		    Desktop desktop = Desktop.getDesktop();	    
		    try {	        
		        try {
					desktop.open(dirToOpen);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "The file does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
		    } catch (IllegalArgumentException iae) {
		    	Logger.getLogger(DeltaView.class.getName()).log(Level.DEBUG, null, iae);
		    	System.out.println(iae.getMessage());
		        System.out.println("File Not Found");
		    }	
		}
		
		@SuppressWarnings("static-access")
		public static void folderCopy(String folder_Be_Copied, String folder_Copied){
			//복사 될 폴더 경로
			File folder1 = new File(folder_Be_Copied);
			//복사 되어질 폴더 경로
			File folder2 = new File(folder_Copied);
				
			File[] target_file = folder1.listFiles();
			for (File file : target_file) {
				File temp = new File(folder2.getAbsolutePath() + File.separator + file.getName());
				if(file.isDirectory()){
					temp.mkdir();
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
							Logger.getLogger(DeltaView.class.getName()).log(Level.DEBUG, null, e);
							e.printStackTrace();
						}
							
					}
				}
			}		    
		}
		
		public static int fileCount(String path){
			File file = new File(path);
			File[] files = file.listFiles();
			
			//files
			int count = 0;
			for(int i = 0; i < files.length; i++){
				if(files[i].isFile()){
					count++;					
				} 
			}
			System.out.println("count 파일 갯수 ===" + count);
			return count;			
		}
		
		@SuppressWarnings("static-access")
		public static void WriteXmlFile(String outPath){
			
			try {
				DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
				Document document = documentBuilder.newDocument();
				
				Element root = document.createElement("dummy");
				document.appendChild(root);
				
				TransformerFactory transformetFactory = TransformerFactory.newInstance();
				Transformer transformer = transformetFactory.newTransformer();
				DOMSource domSource = new DOMSource(document);
				StreamResult streamResult = new StreamResult(new File(outPath));
				
				transformer.transform(domSource, streamResult);
				
				System.out.println("Done creating XML File");
				
			} catch (ParserConfigurationException pce) {
				Logger.getLogger(DeltaView.class.getName()).log(Level.DEBUG, null, pce);
				pce.printStackTrace();
			} catch (TransformerException tfe) {
				Logger.getLogger(DeltaView.class.getName()).log(Level.DEBUG, null, tfe);
				tfe.printStackTrace();
			} catch (Exception e){
				Logger.getLogger(DeltaView.class.getName()).log(Level.DEBUG, null, e);
				e.printStackTrace();
			}
			
		}
		
		public static boolean FileExistence(String outPath){
			File file = new File(outPath);
			
			return file.exists();
		}
		
}
