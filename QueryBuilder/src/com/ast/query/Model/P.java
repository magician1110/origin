package com.ast.query.Model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;

@XmlAccessorType(XmlAccessType.FIELD)
public class P {
	
	@XmlAttribute
	private String dir;
	
	@XmlAttribute
	private String style;
	
	@XmlElement
	private List<Span> span;

	@XmlMixed
	@XmlElementRefs({
			// @XmlElementRef(name="span", type=Span.class)
	})
	List<?> content;

	public P() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public List<Span> getSpan() {
		return span;
	}

	public void setSpan(List<Span> span) {
		this.span = span;
	}

	public List<?> getContent() {
		return content;
	}

	public void setContent(List<?> content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "P [dir=" + dir + ", style=" + style + ", span=" + span + ", content=" + content + "]";
	}
	
}
