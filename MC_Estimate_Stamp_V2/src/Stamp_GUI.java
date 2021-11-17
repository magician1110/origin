import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class Stamp_GUI {
	private CommonFunc commFunc;
	
	private JFileChooser chooser;
	
	public Stamp_GUI(){
		initComps();
		addComps();		
	}
	
	public void initComps() {
		chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		commFunc = new CommonFunc();
	}
	
	public void addComps() {

		File[] folderPaths = null;
		File[] files = null;
        chooser.setCurrentDirectory(new File("/")); // 현재 사용 디렉토리를 지정
        chooser.setAcceptAllFileFilterUsed(true);   // Fileter 모든 파일 적용 
        chooser.setDialogTitle("MC_Estimate_Stamp_V2"); // 창의 제목
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // 파일 선택 모드
        chooser.setMultiSelectionEnabled(true);
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Adobe Acrobat PDF", "pdf"); // filter 확장자 추가
        chooser.setFileFilter(filter); // 파일 필터를 추가
        
        int returnVal = chooser.showOpenDialog(null); // 열기용 창 오픈
        
        if(returnVal == JFileChooser.APPROVE_OPTION) { // 열기를 클릭 
        	folderPaths = chooser.getSelectedFiles();
        	System.out.println("Stamp_GUI folderPaths === : " + folderPaths);
    		//PDF출력물에 대한 체크여부
        	//commFunc.checkFile(new File("PDF출력물"));
        	commFunc.checkException(folderPaths);
                  	
        }else if(returnVal == JFileChooser.CANCEL_OPTION){ // 취소를 클릭
            System.out.println("cancel"); 
            folderPaths = null;                  
        }
		
	}
	
}
