import java.io.File;


import javax.swing.JOptionPane;

public class CommonFunc {
	PDFunc pdfunc;	
	
	public CommonFunc() {			
		pdfunc = new PDFunc();		
	}
	
	
	public void checkException(File[] filePath) {
		boolean value = pdfunc.pdfParsing(filePath);
		
		if(value == false) {
			JOptionPane.showMessageDialog(null, "관리자에게 문의 하십시오.", "Error", JOptionPane.ERROR_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(null, "Succes", "처리가 완료 되었습니다.", JOptionPane.INFORMATION_MESSAGE);
		}
			
	}
	
	public void checkFile(File outPath) {		
					
		if(!outPath.exists()) {			
			outPath.mkdir();
		}
	}
	

		
}
