import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ProgessBar {

 // create a frame 
    static JFrame f; 
    static JProgressBar b; 
   
	public static void main(String[] args) {
		progessBarFrame();
	}
	
	public static void progessBarFrame(){
		 // create a frame 
        f = new JFrame("ProgressBar demo"); 

        // create a panel 
        JPanel p = new JPanel(); 
        // create a progressbar 
        b = new JProgressBar(); 
        // set initial value 
        b.setValue(0); 
        b.setStringPainted(true);  
        // add progressbar 
        p.add(b);  
        // add panel 
        f.add(p); 
        // set the size of the frame 
        f.setSize(500, 500); 
        f.setVisible(true); 
        
  	  int i = 0; 
      try { 
          while (i <= 100) { 
              // set text accoring to the level to which the bar is filled 
              if (i > 30 && i < 70) 
                  b.setString("wait for sometime"); 
              else if (i > 70) 
                  b.setString("almost finished loading"); 
              else
                  b.setString("loading started"); 

              // fill the menu bar 
              b.setValue(i + 10); 

              // delay the thread 
              Thread.sleep(3000); 
              i += 20; 
          } 
      } 
      catch (Exception e) { 
    	  e.printStackTrace();
      } 
	}
    
}
