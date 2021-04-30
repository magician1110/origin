package com.ast.query.Model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;

@XmlAccessorType(XmlAccessType.FIELD)
public class Span {
	@XmlAttribute
	private String dir;

	@XmlAttribute
	private String style;

	@XmlMixed
	@XmlElementRefs({
			// @XmlElementRef(name="span", type=Span.class)
	})
	List<?> content;

	public Span() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Span(String dir, List<?> content, String style) {
		super();
		this.dir = dir;
		this.content = content;
		this.style = style;
	}

	@Override
	public String toString() {
		return "Span [dir=" + dir + ", content=" + content + ", style=" + style + "]";
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public List<?> getContent() {
		return content;
	}

	public void setContent(List<?> content) {
		this.content = content;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}


}
