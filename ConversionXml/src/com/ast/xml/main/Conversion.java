package com.ast.xml.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.LLOAD;

public class Conversion {
	private static String WebManualfileTemp = new String("C:\\Users\\LBW\\Desktop\\200402Test\\WebManualfileTemp");
	private static String WebManualfile = new String("C:\\Users\\LBW\\Desktop\\apktest\\WebManual.apk");		
	private static String themestore_baseTemp = new String("C:\\Users\\LBW\\Desktop\\200402Test\\themestore_baseTemp");
	private static String themestore_base = new String("C:\\Users\\LBW\\Desktop\\apktest\\themestore-base.apk");
	private static String SecSettingsTemp = new String("C:\\Users\\LBW\\Desktop\\200402Test\\SecSettingsTemp");
	private static String SecSettings = new String("C:\\Users\\LBW\\Desktop\\apktest\\SecSettings.apk");
	private static String sbrowser_baseTemp = new String("C:\\Users\\LBW\\Desktop\\200402Test\\sbrowser_baseTemp");
	private static String sbrowser_base = new String("C:\\Users\\LBW\\Desktop\\apktest\\sbrowser_base.apk");
	private static String play_storeTemp = new String("C:\\Users\\LBW\\Desktop\\200402Test\\play_storeTemp");
	private static String play_store = new String("C:\\Users\\LBW\\Desktop\\apktest\\play_store.apk");
	private static String AREmojiTemp = new String("C:\\Users\\LBW\\Desktop\\200402Test\\AREmojiTemp");
	private static String AREmoji = new String("C:\\Users\\LBW\\Desktop\\apktest\\AREmoji.apk");
	
	public static void main(String[] args) {
		try {
			ListFile("C:\\Users\\LBW\\Desktop\\200402Test\\WebManualfileTemp\\resources");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void ListFile( String strDirPath ) { 
		File path = new File( strDirPath ); 
		File[] fList = path.listFiles();
		List<File> ll = null;
		for( int i = 0; i < fList.length; i++ ) {			
			if( fList[i].isFile()){				
				if(fList[i].getName().equals("strings.xml")){									
					//System.out.println("파일 이름 : " + fList[i].getName());		
					System.out.println("파일 경로 : " + fList[i].getPath());
					ll.add(i, fList[i].getCanonicalFile());
					switch (fList[i].getPath()) {

					case :
						System.out.println("zzzz");
						break;

					default: //모두 해당 안되면
						break;
					}
				}
			}else if( fList[i].isDirectory()) {
				//System.out.println( "[폴더] " + fList[i].getPath() ); // 재귀함수 호출 }
				ListFile(fList[i].getPath());
			}


		}
	}

	
	public static void runTime(){
		try {
			String[] cmd = {"C:\\And\\jadx-1.1.0\\bin\\jadx.bat", "-d",WebManualfileTemp, WebManualfile};
			Runtime rt = Runtime.getRuntime();
			rt.exec(cmd);
			
			String[] cmd2 = {"C:\\And\\jadx-1.1.0\\bin\\jadx.bat", "-d",themestore_baseTemp, themestore_base};
			Runtime rt2 = Runtime.getRuntime();
			rt2.exec(cmd2);
			
			String[] cmd3 = {"C:\\And\\jadx-1.1.0\\bin\\jadx.bat", "-d",SecSettingsTemp, SecSettings};
			Runtime rt3 = Runtime.getRuntime();
			rt3.exec(cmd3);
			
			String[] cmd4 = {"C:\\And\\jadx-1.1.0\\bin\\jadx.bat", "-d",sbrowser_baseTemp, sbrowser_base};
			Runtime rt4 = Runtime.getRuntime();
			rt4.exec(cmd4);
			
			String[] cmd5 = {"C:\\And\\jadx-1.1.0\\bin\\jadx.bat", "-d",play_storeTemp, play_store};
			Runtime rt5 = Runtime.getRuntime();
			rt5.exec(cmd5);
			
			String[] cmd6 = {"C:\\And\\jadx-1.1.0\\bin\\jadx.bat", "-d",AREmojiTemp, AREmoji};
			Runtime rt6 = Runtime.getRuntime();
			rt6.exec(cmd6);
			
System.out.println("End =======");
/*			
			int t8;
			Runtime rt8 = Runtime.getRuntime();
			Process pr8 = rt8.exec(new String[]{"cmd.exe", "/c", "start C:\\And\\jadx-1.1.0\\bin\\jadx.bat", "-d",AREmojiTemp, AREmoji});
			t8 = pr8.waitFor();
			t8 =pr8.exitValue();*/
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
