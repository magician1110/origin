import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Font;
import java.awt.Frame;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.plaf.OptionPaneUI;

public class AutoHi {

	static PointerInfo pi;
	static Robot rb;
	static Frame f;	
	static TextField tf;
	static Button bt; 

	static long val;
	static int val2;
	
	public AutoHi() throws AWTException{
		pi = MouseInfo.getPointerInfo();		
		rb = new Robot();
	}
	
	public static void pointLocation(){
		pi = MouseInfo.getPointerInfo();
		System.out.println("x ==== " + pi.getLocation().x);
		System.out.println("y ====" + pi.getLocation().y);
		
	}
	
	public static void mouseMove() throws AWTException{
		rb = new Robot();

		try {			
			//메인화면의 사용자 버튼 (3234, 124)
			rb.mouseMove(3234, 124); 
			rb.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			rb.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			// 근태관리 버튼 위치(3204,177)
			rb.mouseMove(3204,177); 
			rb.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			rb.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			Thread.sleep(2000);
			//근태등록 버튼 위치(3209,175)
			rb.mouseMove(3209,175); 
			rb.mousePress(InputEvent.BUTTON1_DOWN_MASK);			
			rb.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			//퇴근 라디오 박스 위치(2425, 250)
			Thread.sleep(2000);
			rb.mouseMove(2425,250);
			rb.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			rb.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			//저장하기 버튼 위치(2759,437)
			Thread.sleep(2000);
			rb.mouseMove(2759,437);
			rb.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			rb.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("실패");
		}
		
	}
	
	public static void awtFrame(){
		f = new Frame();
		tf = new TextField("500000");
		bt = new Button("Execution"); 
		
		f.setTitle("퇴근 시간 지정 창");
		f.setSize(200,100);
		f.setLocation(800, 400);
		
		f.setVisible(true);		
		f.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);	
			}
		});

		bt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {				
				try {
					jButton_startActionPerformed(evt);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}								
			}
		});
		
		f.add(bt, BorderLayout.SOUTH);
		f.add(tf, BorderLayout.CENTER);
		
	}
	
	private static void jButton_startActionPerformed(java.awt.event.ActionEvent evt) throws ParseException {

		if(tf.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "종료 예약 시간을 입력 하시오.", "INFORMATION_MESSAGE", JOptionPane.INFORMATION_MESSAGE);
		}else if(isNumeric(tf.getText()) == false){
			JOptionPane.showMessageDialog(null, "밀리세컨드 시간만을 입력 하시오.", "INFORMATION_MESSAGE", JOptionPane.INFORMATION_MESSAGE);
		}else{			
			//val = tf.getText();				
			exec(tf.getText());
		}

		
	}
	
	public static void exec(String setOutTime) throws ParseException{
		Timer m_timer = new Timer();
		TimerTask m_task = new TimerTask() {
			
			@Override
			public void run() {
				
				pointLocation();
				try {
					mouseMove();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		};				
		//5000은 5초
		//date		
		val = Long.parseLong(setOutTime);
		System.out.println("val =====" + val);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(val);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(val);
		System.out.println("seconds Time ==" + seconds);
		System.out.println("minutes Time ==" + minutes);
		m_timer.schedule(m_task, val);
		//shutdown(seconds+20);
		JOptionPane.showMessageDialog(null, seconds+ "초 뒤에 동작 합니다.", "INFORMATION_MESSAGE", JOptionPane.INFORMATION_MESSAGE);
		//JOptionPane.showMessageDialog(null, seconds + "초 뒤에 컴퓨터가 종료 됩니다.", "INFORMATION_MESSAGE", JOptionPane.INFORMATION_MESSAGE);
	}
	public static String currentTime(int setOutTime){ //hhmm
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, setOutTime);		
		
		SimpleDateFormat format = new SimpleDateFormat("mm");	
		String strDate = format.format(cal.getTime());
		System.out.println("strDate ===" + strDate);
		return strDate;
	}
	
	public static void shutdown(long setOutTime){
		Runtime r = Runtime.getRuntime();
		try {
			//Shutdown after specific time
			r.exec("shutdown -s -f -t" + " " + setOutTime);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static boolean isNumeric(String s) {
		  try {
		      Double.parseDouble(s);

		      return true;
		  } catch(NumberFormatException e) {

		      return false;
		  }
		}
		
	public static void main(String[] args) {
		awtFrame();			
	}

}
