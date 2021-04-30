package com.kh.hmm.newTech.model.vo;

public class Conlist
{
	private int wscode;
	private String id;
	
	public Conlist() {}
	
	public Conlist(int wscode, String id)
	{
		super();
		this.wscode = wscode;
		this.id = id;
	}
	public int getWscode()
	{
		return wscode;
	}
	public void setWscode(int wscode)
	{
		this.wscode = wscode;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	@Override
	public String toString()
	{
		return "Conlist [wscode=" + wscode + ", id=" + id + "]";
	}
}
