package com.ast.query.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import com.ast.query.Model.Toc;
import com.ast.query.View.GuiManager;

public class Main {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws IOException, XMLStreamException {
		
		List<Toc> list = new ArrayList<Toc>();
			
		GuiManager gui  = new GuiManager();
		GuiManager.GuiManager();

	}
}
