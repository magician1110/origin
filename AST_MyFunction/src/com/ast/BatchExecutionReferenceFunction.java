package com.ast;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class BatchExecutionReferenceFunction {

	public static void batchEx(File batchFilePath) throws Exception{ 

		int result;
        InputStreamReader isr = null;
        BufferedReader br = null;
        //1번째 방법 외부 호출
		String[] command = {"cmd.exe", "/C", "Start", batchFilePath.getAbsolutePath()};        
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
    }
}
