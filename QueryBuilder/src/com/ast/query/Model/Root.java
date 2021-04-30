package com.ast.query.Model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="root")
public class Root {

	private Toc toc;

	public Root() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Root(Toc toc) {
		super();
		this.toc = toc;
	}

	@Override
	public String toString() {
		return "Root [toc=" + toc + "]";
	}

	public Toc getToc() {
		return toc;
	}

	public void setToc(Toc toc) {
		this.toc = toc;
	}
}
