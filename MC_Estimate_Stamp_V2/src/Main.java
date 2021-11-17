import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDPageLabels;

public class Main {
	
	@SuppressWarnings("unused")
	public static void main(String[] args){							
		Stamp_GUI stamp_gui = new Stamp_GUI();
//		PDDocument document;
//		try {
//			document = PDDocument.load(new File("F:\\Project\\MC\\MC_Extractor_Image\\K_NAKL008_NQ5a 22_G5WIDE[USA_EU]AVNT_QRG_DWMS7CB000_211029 1105_최종검토.pdf"));
//			PDPageLabels pageLabels = document.getDocumentCatalog().getPageLabels();
//			String[] labels = pageLabels.getLabelsByPageIndices();
//			for (int i = 0; i < labels.length; ++i)
//			{
//				System.out.println(" labels.length == : " + labels.length);
//			    if ("1".equals(labels[i]))
//			    {
//			        System.out.println("found: " + i);
//			        break;
//			    }
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//		try {
//			PDDocument document = PDDocument.load(new File("F:\\Project\\MC\\MC_Extractor_Image\\K_NAKL008_NQ5a 22_G5WIDE[USA_EU]AVNT_QRG_DWMS7CB000_211029 1105_최종검토.pdf"));
//			PDPageLabels lable = document.getDocumentCatalog().getPageLabels();
//			String[] range = lable.getLabelsByPageIndices(); //all page label
//			int pageNumber = 1; //page number for find page
//			int index = Arrays.binarySearch(range, pageNumber);  //get page which have given page number
//			PDPage page = document.getPage(index);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
	}
		
}

