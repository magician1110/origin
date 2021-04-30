package com.ast.query.Model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Item {

	@XmlElement
	private P p;

	@XmlAttribute
    private int level;

    private String title;

	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Item(P p, int level, String title) {
		super();
		this.p = p;
		this.level = level;
		this.title = title;
	}

	@Override
	public String toString() {
		return "Item [p=" + p + ", level=" + level + ", title=" + title + "]";
	}

	public P getP() {
		return p;
	}

	public void setP(P p) {
		this.p = p;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

    
    
    
}
