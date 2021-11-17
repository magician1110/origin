import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationText;
import org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi.ecCVCDSA;

public class Main {

	public static void main(String[] args) throws Exception {	
		//testPDFBoxExtractImages(new File("F:\\Project\\MC\\MC_Extractor_Image\\K_NAKL008_NQ5a 22_G5WIDE[USA_EU]AVNT_QRG_DWMS7CB000_211029 1105_최종검토.pdf"));
		//pdfParsing(new File("F:\\Project\\MC\\MC_Extractor_Image\\VER2\\K_NAKL007_NQ5kH 22_G5WIDE[KOR_KO]AVNT_CHMS1CB000_210625 1955.pdf"));
		Stamp_GUI stamp_gui = new Stamp_GUI();
	       //Initialize array   
//        int [] arr = new int [] {1, 2, 3, 4, 2, 7, 8, 8, 3};   
//          
//        System.out.println("Duplicate elements in given array: ");  
//        //Searches for duplicate element  
//        for(int i = 0; i < arr.length; i++) {
//        	//System.out.println("첫번째 == : " + arr[i]);
//            for(int j = i + 1; j < arr.length; j++) {
//            	//System.out.println("두번째 == : " + arr[j]);
//                if(arr[i] == arr[j])
//                	System.out.println("1번 = : " + arr[i]);
//                    System.out.println("2번 = : " + arr[j]);  
//            }  
//        }  
	}
	


}
