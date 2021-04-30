package com.ast.query.Controller;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import com.ast.query.Model.Item;
import com.ast.query.Model.Root;
import com.ast.query.Model.Span;

public class Xml {
    
	public Root xmlPaser() {
		Root tocTest = null;
		try {
			File filepathRe1 = new File("C:\\QueryBuilderTemp\\query3XML.xml"); //XML파일 추출
			JAXBContext jaxbContext = JAXBContext.newInstance(Root.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		    tocTest = (Root) jaxbUnmarshaller.unmarshal(filepathRe1);
		    //System.out.println(tocTest);
		   
		    for(Item t : tocTest.getToc().getItem()){		    	
		    	//System.out.println("Item Title : " + t.getTitle());
		    	//System.out.println("Item Level : " + t.getLevel());
		    	//System.out.println("Item P : " + t.getP());
		    	if(t.getP() != null){
		    		//System.out.println("   P 태그 내용 : " + t.getP().getContent().get(0));
		    		for(Span s : t.getP().getSpan()){
		    			/*System.out.println("-----------------------------------------");
		    			System.out.println("       Span dir : " + s.getDir());
		    			System.out.println("       Span style : " + s.getStyle());
		    			System.out.println("       Span content : " + s.getContent().get(0));*/
		    		}

	    			//System.out.println("-----------------------------------------");
		    	}

    			//System.out.println("-----------------------------------------");
		    }

			 }catch (Exception e) {
				e.printStackTrace();
			}
		

	    return tocTest;
	}
}
