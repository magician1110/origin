import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class FileFind {
	private static ArrayList<String> pathList = new ArrayList<String>();
	private static ArrayList<String> toc_id_arr;
	private static ArrayList<String> body_arr;
	public static void main(String[] args) throws IOException, ParseException {
		//showFilesInDIr("F:\\Project\\Kohyoung\\searchText\\");
		//System.out.println("zz ? ^^ == : " + showFilesInDIr("F:\\Project\\Kohyoung\\searchText\\"));
		readJsonFile();
		//몌모리상에 데이터를 넣고 뺴고 처리 해볼까 해서 만들었던 fileSearch();
		//fileSearch(); 
		//SearchTextGUI searchTextGUI = new SearchTextGUI();
//        SQLiteManager manager = new SQLiteManager();       
//        manager.createConnection();     // 연결
//        manager.closeConnection();      // 연결 해제
        //manager.ensureConnection();     // 재연결
       
	}
	
	public static ArrayList<String> showFilesInDIr(String dirPath) {	
		
	    File dir = new File(dirPath);
//		FilenameFilter filter = new FilenameFilter() {
//		    public boolean accept(File f, String name) {
//		        return f.getName().endsWith("js");
//		    }
//		};
	    File files[] = dir.listFiles();	   
	    
	    try {
		    for(File tempFile : files) {
		    	if(tempFile.isFile()) {
		    		if(tempFile.getName().equals("test.json")) {
		    			//System.out.println("\t 파일 = " + dirPath + "/" + tempFile.getName());	
		    			//System.out.println("? = : " + tempFile.getAbsolutePath());
		    			pathList.add(tempFile.getAbsolutePath());
		    		}
		    		
		    	}else if(tempFile.isDirectory()) {
		    		//System.out.println("디렉토리 = " + dirPath + "/" + tempFile.getName());
		    		showFilesInDIr(tempFile.getCanonicalPath().toString());
		    	}
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pathList;

	}
	
	public static void readJsonFile() throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		FileReader fileReader = new FileReader(showFilesInDIr("F:\\Project\\Kohyoung\\searchText\\").get(0));
		Object obj = parser.parse(fileReader);				
		JSONArray toc_id_List = (JSONArray) obj;
		toc_id_List.forEach(std -> parseJsonObj((JSONObject)std));
	}
	
	private static void parseJsonObj(JSONObject jsonObj) {	
		//System.out.println("jsonObj == : " + jsonObj.get("toc_id"));	
		toc_id_arr = new ArrayList<String>();
		//body_arr = new ArrayList<String>();
		int i = 0;
		System.out.println("zzzz = : " + jsonObj.toJSONString());
		toc_id_arr.add((String) jsonObj.get("toc_id"));		
		toc_id_arr.add((String) jsonObj.get("body"));
		//System.out.println("인덱스 0 ==== : " + toc_id_arr.get(0));
		//System.out.println("인덱스 1 ==== : " + toc_id_arr.get(1));
		//body_arr.add((String) jsonObj.get("body"));
			
		//System.out.println("toc_id_arr ==" + toc_id_arr);
		//System.out.println("body_arr ==" + body_arr);
	
//		if(toc_id_arr.contains("chapter02")) {		
//			System.out.println("**");
//			System.out.println("^^position = : " + i);
//			i++;
//		}
		 //JSONObject top_id_Object = (JSONObject)jsonObj.get(0);		    
		//JSONObject top_id_Object = new JSONObject();
		 //System.out.println("top_id_Object === " + top_id_Object);
//		if(top_id_Object != null) {
//			String top_id = (String) top_id_Object.get("toc_id");
//		    System.out.println(top_id);	
//		}
	    
	}
//	public static void fileSearch() {
//        // Testing only
//		ArrayList<String> path = new ArrayList<String>();
//		path.add("F:\\Project\\Kohyoung\\searchText\\AOI_Additional_Features\\KOR\\js\\test.json");
//		path.add("AOI 시스템을 사용하여 보드 검사를 진행하려면 JOB 프로그래밍 과정을 수행해야 합니다");
//        File f = new File(path.get(0));
//        String search = path.get(1);
//        System.out.printf("Result of searching for %s in %s was %b\n", search, f.getName(), find(f, search));
//    }
//
//    public static boolean find(File f, String searchString) {
//        boolean result = false;
//        Scanner in = null;
//        try {
//            in = new Scanner(new FileReader(f));
//            while(in.hasNextLine() && !result) {
//                result = in.nextLine().indexOf(searchString) >= 0;
//            }
//        }
//        catch(IOException e) {
//            e.printStackTrace();      
//        }
//        finally {
//            try { in.close() ; } catch(Exception e) { /* ignore */ }  
//        }
//        return result;
//	}
	
}
