import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SearchTextGUI extends JFrame{
	//프레임 관련 
	private final int WDSIZE_W = 800;
	private final int WDSIZE_H = 600;
	private final String TITLE = "Search Text";		
	//컴포넌트 관련
//	private final int JTFSIZE_W = 100;
//	private final int JTFSIZE_H = 100;
		
	private JPanel jPanel;
	private JTextField jTextField;
	private JButton jButton;	
	private JTextArea jTextArea;
	private JScrollPane jScrollPane;	
	private String SearchValue;
	private CommonFunc func;
	
	public SearchTextGUI() {
		initComps();
		addComps();
		
		//Wnd는 마지막
		initWnd();
		
	}
	
	private void initComps() {	
		jPanel = new JPanel();
		jTextField = new JTextField();
		jButton = new JButton();
		jTextArea = new JTextArea();
		jScrollPane = new JScrollPane();  
		func = new CommonFunc();
		
	}
	
	private void addComps() {
		jPanel.setLayout(null);
		jTextField.setBounds(50, 80, 600, 30);
		jButton.setBounds(670, 80, 70, 30);
		jButton.setText("검색");
		jTextArea.setEditable(false);
		jScrollPane.setBounds(50, 120, 600, 400);
		jTextArea.setText("경로\tChapter\tTitle\tBody\n");
		System.out.println("몇번타니 ?");
		System.out.println("https://glow153.tistory.com/5 GUI MVC 패턴 참고하는 주소");
		System.out.println("https://sourcestudy.tistory.com/128 TextArea에 값 뿌리는거 참고하는 주소");
		jButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ResultSet commonSval = func.getJtextFieldSearchValue(jTextField.getText());
				try {
					while(commonSval.next()) {
						System.out.println("******** == : " + commonSval.getString(jTextField.getText()));
					}
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}

			}
		});
		
		jScrollPane.add(jTextArea);		
		jPanel.add(jTextField);		
		jPanel.add(jButton);
		jPanel.add(jScrollPane);
		add(jPanel);		
	}
	
	
	private void initWnd() {
		setSize(WDSIZE_W, WDSIZE_H);
		setTitle(TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);		
		//setComps();
	}

	
}
