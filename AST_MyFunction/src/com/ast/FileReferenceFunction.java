package com.ast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class FileReferenceFunction {
	
	//해당 클래스 파일이 위치한 곳을 불러옴.
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
	
	//해당 폴더 기준으로 모든 파일 삭제
	public static boolean deleteDirectory(File path){ 
		if(!path.exists()){			
			return false; 
			} 
		File[] files = path.listFiles(); 
		for (File file : files){
			if (file.isDirectory()){
				deleteDirectory(file); 
				}else{ 
					file.delete(); 
				} 
			} return path.delete(); 
	}
	
	public static void deleteAllFiles(String path) {
		File file = new File(path); // 폴더내 파일을 배열로 가져온다.
		File[] tempFile = file.listFiles();

		if (tempFile.length > 0) {
			for (int i = 0; i < tempFile.length; i++) {
				if (tempFile[i].isFile()) {
					tempFile[i].delete();
				} else {
					// 재귀함수
					deleteAllFiles(tempFile[i].getPath());
				}
				tempFile[i].delete();

			}
			file.delete();

		}
	}
	
	//파일 생성
	public static void createDir(String filePath) throws IOException{
		File file = new File(filePath);
		file.mkdir();
	}
	
	//파일 존재 여부
	public static boolean checkFile(String filePath){
		File file = new File(filePath);
		return file.exists();
	}
	
	public static String betWeen(String source, String firstTarget, String secondTarget){
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
	
	//해당 디렉토리 위치의 마지막 수정 파일 
	public File getLastModifiedFile(File directory) {
	    File[] files = directory.listFiles();
	   if (files.length == 0) return null;
	    Arrays.sort(files, new Comparator<File>() {
	        public int compare(File o1, File o2) {
	            return new Long(o2.lastModified()).compareTo(o1.lastModified()); 	            
	        }});	    
	    return files[0];
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
	
	/**
	* 파일 복사 메소드
	* @param folder_Be_Copied 복사 될 폴더 경로
	* @param folder_Copied 복사 되어질 폴더 경로
	*/
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						
				}
			}
		}		    
	}

	/**
	* 파일 복사 메소드
	* @param path 카피 대상 폴더 경로
	*/
	public static int fileCount(String path){
		File file = new File(path);
		File[] files = file.listFiles();
		
		//files
		int count = 0;
		for(int i = 0; i < files.length; i++){
			if(files[i].isFile()){
				count++;
				System.out.println("파일 갯수 : " + files[i].getName());
			} 
		}
		System.out.println("count 파일 갯수 ===" + count);
		return count;			
	}
}
