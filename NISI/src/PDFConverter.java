import java.io.BufferedWriter;
import java.io.File; 
import java.io.FileOutputStream;
import java.io.IOException; 
import java.io.OutputStreamWriter;
import java.util.Objects;
import java.util.Optional;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;



public class PDFConverter {
    public static void main(String[] args) {

    	//extractingText("F:\\Project\\국과수\\데이터 샘플\\4. 논 문\\사례 분석을 통한 김치냉장고의 화재 위험성에 관한 연구.pdf");
    	if(args.length > 0) {
    		for(String arg : args) {
    			extractingText(arg);
    		}
 
    	}
    	
    }
    
	 public static boolean extractingText(String targetFile) {
        boolean result = true;
        
        File file = null;
        
    	String sourceFilePath = "";
    	String outFilePath = "";    
    	String fileName = "";    	
    	int idx = 0;
    	
        if(targetFile.contains(";")) {
        	sourceFilePath = targetFile.substring(0, targetFile.lastIndexOf(";"));
        	file = new File(sourceFilePath);
        	fileName = file.getName();
        	idx = fileName.lastIndexOf(".");
        	fileName = fileName.substring(0,idx);
        	outFilePath = targetFile.substring(targetFile.indexOf(";")+1, targetFile.length());        	       
        }
        String extractTxtFile = outFilePath + "\\" + fileName +".txt";
        PDDocument pdDocument = null;
        
        try {        	
            pdDocument = PDDocument.load(new File(sourceFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }   
        FileOutputStream fileOutputStream = openOutputStream(new File(extractTxtFile));
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        BufferedWriter bufferedWriter = null;
        bufferedWriter = new BufferedWriter(outputStreamWriter);
        PDFTextStripper stripper = null;
        
        try {
            stripper = new PDFTextStripper();
        } catch (IOException e) {
            System.out.println("TextExtraction-extractingText ERROR: PDFTextStripper 객체생성 실패");
        }
        	stripper.setStartPage(1);
        //stripper.setEndPage(11);
        try {
        	stripper.writeText(pdDocument, bufferedWriter);
        } catch (IOException e) {
            System.out.println("TextExtraction-extractingText ERROR: text 추출 실패");
        }

        try {
        	pdDocument.close();
        } catch (IOException e) {
            System.out.println("TextExtraction-extractingText ERROR: PDDocument close 실패");
        }
        IOUtils.closeQuietly(bufferedWriter);
        return result;
	
	 }



	 public static FileOutputStream openOutputStream(File file) {
	        FileOutputStream fileOutputStream = null;
	        
	        try {
	            fileOutputStream = new FileOutputStream(file);
	        } catch (Exception e) {
	            System.out.println("TextExtraction-openOutputStream ERROR: " + e.getMessage());
	        }
	
	        return fileOutputStream;
	   }
 
	 public static Optional<Integer> getNumberOfPages(String fileName) {
		 if (Objects.isNull(fileName)) {
		  throw new NullPointerException("fileName shouldn't be null");
		 }
		 try (
			final PDDocument pdDoc = PDDocument.load(new File(fileName))) {			 
			 
		   return Optional.of(pdDoc.getNumberOfPages());
		 } catch (IOException e) {
			 System.out.println(e.getMessage());
			 
		   return Optional.empty();
		 }
		 
	}
	

}
