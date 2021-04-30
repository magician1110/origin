package com.ast.protean.views.resources;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.ast.protean.controllers.ProteanController;

public class LanguageList extends JFrame{
	  /**
	 * 
	 */
      private static final long serialVersionUID = 1L;
      
      public static DefaultListModel<String> model_1 = new DefaultListModel<String>();
      public static DefaultListModel<String> model_2 = new DefaultListModel<String>();
	  public static String LV_name = null;
	  public static String lang_code = null;//replace_LV_name(LV_name); //LV_Name을 lang_code로 바꿈;		  
      public JButton btnMove = new JButton();
      public JButton bthRemove = new JButton();
      public JButton btnFinalizechoices = new JButton();
	  public JList<String> jList1 = new JList<String>(model_2);
	  public JList<String> jList2 = new JList<String>(model_1);	   
	  public JScrollPane jScrollPane1 = new JScrollPane();
	  public JScrollPane jScrollPane2 = new JScrollPane();
	  
	  public LanguageList() {     // our constructor
	    super("Language List"); // call to super constructor
	    try {
	      jbInit();
	      
	    }
	    catch(Exception e) {
	      e.printStackTrace();
	    }
	 }
	  
	  private void jbInit() throws Exception {	  
	  // Note we are adding and removing JList items from the MODEL
	  // NOT directly from the JList.  Because JList is associated with the
	  // model, the JList contents are updated automatically
		ProteanController pc = new ProteanController();				
		Iterator<String> it = pc.xmlPaser().iterator();
		while (it.hasNext()) {
			String str = it.next();	
			model_1.addElement(str);
		}		
 
	    this.getContentPane().setLayout(null);
	  
	    btnMove.setText("Move >>");
	    btnMove.setBackground(Color.WHITE);
	    btnMove.setBounds(new Rectangle(152, 15, 98, 36));
	    btnMove.addActionListener(new java.awt.event.ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        btnMove_actionPerformed(e);
	      }
	    });
	  
	    bthRemove.setText("<< Remove");
	    bthRemove.setBackground(Color.white);
	    bthRemove.setBounds(new Rectangle(152, 60, 97, 36));
	    bthRemove.addActionListener(new java.awt.event.ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        bthRemove_actionPerformed(e);
	      }
	    });
	    
	    jScrollPane1.setBounds(new Rectangle(286, 15, 109, 133));	    
	    jScrollPane2.setBounds(new Rectangle(14, 15, 106, 136));	    
	    // Allow user to select a single item from JList	    
	    	   
	    //JLIST2  왼쪽 판넬 	
	    jScrollPane2.getViewport().add(jList2, null); // Must add JList
	    jList2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	    jList2.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent evt){	    		
	    		if(evt.getClickCount() == 2){
	    			model_2.addElement(jList2.getSelectedValue());
	    			model_1.removeElement(jList2.getSelectedValue());
	    		}
	    	}
	    });	    
	    
	    //JLIST1 오른쪽 판넬
	    jScrollPane1.getViewport().add(jList1, null); // JList to JScrollPane
	    jList1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	    jList1.addMouseListener(new MouseAdapter() {
	    	public void mouseClicked(MouseEvent evt){	    		
	    		if(evt.getClickCount() == 2){			
	    			model_1.addElement(jList2.getSelectedValue());
	    			model_2.removeElement(jList1.getSelectedValue());
	    		}
	    	}
	    });
	    
	    this.getContentPane().add(jScrollPane2, null);	    	  
	    this.getContentPane().add(jScrollPane1, null); // to a JScrollPane	    
	    this.getContentPane().add(btnMove, null);
	    this.getContentPane().add(bthRemove, null);
	    
	  }	  
	  
	  void btnMove_actionPerformed(ActionEvent e) {
		    Object[] from = jList2.getSelectedValues();
		    for(Object s1 : from){
		    	if(s1.toString() != "" || s1.toString() != null){
			    	model_2.addElement((String)s1);  // change the MODEL
				    model_1.removeElement((String)s1);  // change the MODEL				    				   				    
		    	}else{
		    		JOptionPane.showMessageDialog(null, "Please one More than Language select.", "Error", JOptionPane.ERROR_MESSAGE);
		    	}
		    }	    	        
	  }
	  
	  void bthRemove_actionPerformed(ActionEvent e) {		   
		    Object[] to = jList1.getSelectedValues();
		    for(Object s2 : to){
		    	if(s2.toString() != "" || s2.toString() != null){
			    	model_1.addElement((String)s2);  // change the MODEL
			    	model_2.removeElement((String)s2);  // change the MODEL			    	
			    	//jList2.setSelectedIndex(0);       // Highlight first item in JList
			    		    
		    	}else{
		    		JOptionPane.showMessageDialog(null, "Please one More than Language select.", "Error", JOptionPane.ERROR_MESSAGE);
		    	}
		    }		    

	  }	  
	  
}

