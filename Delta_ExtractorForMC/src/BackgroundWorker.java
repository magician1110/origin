import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;

public class BackgroundWorker extends SwingWorker<Void, Void>{
	private JProgressBar pb;
	private JDialog dialog;
	
	public BackgroundWorker(){
		addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				System.out.println("evt ===" + evt.getPropertyName());
				if("progress".equalsIgnoreCase(evt.getPropertyName())){
					if(dialog == null){
						dialog = new JDialog();
						dialog.setTitle("Processing");
						dialog.setLayout(new GridLayout());
						dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
						GridBagConstraints gbc = new GridBagConstraints();
						gbc.insets = new Insets(2, 2, 2, 2);
						gbc.weightx = 1;
						gbc.gridy = 0;
						dialog.add(new JLabel("Processing..."), gbc);
						pb = new JProgressBar();
						pb.setStringPainted(true);
						gbc.gridy = 1;
						dialog.add(pb, gbc);
						dialog.pack();
						dialog.setLocationRelativeTo(null);
						dialog.setModal(true);
						JDialog.setDefaultLookAndFeelDecorated(true);
						dialog.setVisible(true);						
					}
					pb.setValue(getProgress());
				}
				
			}
		});
	}
	
	@Override
	protected void done(){
		if(dialog != null){
			System.out.println("dialog === " + dialog.getName());
			dialog.dispose();
		}
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		for(int index = 0; index < 100; index++){
			setProgress(index);
			Thread.sleep(100);
			/**
			 * Do work
			 * Do work
			 * Do work
			 */
			//DeltaView.ad();
		}
		return null;
	}
	
}
