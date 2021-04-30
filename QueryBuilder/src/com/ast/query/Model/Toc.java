package com.ast.query.Model;

import java.util.List;


public class Toc {

	private List<Item> item;

	public Toc() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Toc(List<Item> item) {
		super();
		this.item = item;
	}

	@Override
	public String toString() {
		return "Toc [item=" + item + "]";
	}

	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}
}