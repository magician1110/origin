package com.kh.hmm.board.model.vo;

public class Attachfile
{
	private int atcode;
	private String originname;
	private String changedname;
	private int bcode;
	private String filelink;
	
	public Attachfile() {}

	public Attachfile(int atcode, String originname, String changedname, int bcode, String filelink)
	{
		super();
		this.atcode = atcode;
		this.originname = originname;
		this.changedname = changedname;
		this.bcode = bcode;
		this.filelink = filelink;
	}

	public int getAtcode()
	{
		return atcode;
	}

	public void setAtcode(int atcode)
	{
		this.atcode = atcode;
	}

	public String getOriginname()
	{
		return originname;
	}

	public void setOriginname(String originname)
	{
		this.originname = originname;
	}

	public String getChangedname()
	{
		return changedname;
	}

	public void setChangedname(String changedname)
	{
		this.changedname = changedname;
	}

	public int getBcode()
	{
		return bcode;
	}

	public void setBcode(int bcode)
	{
		this.bcode = bcode;
	}

	public String getFilelink()
	{
		return filelink;
	}

	public void setFilelink(String filelink)
	{
		this.filelink = filelink;
	}

	@Override
	public String toString()
	{
		return "Attachfile [atcode=" + atcode + ", originname=" + originname + ", changedname=" + changedname
				+ ", bcode=" + bcode + ", filelink=" + filelink + "]";
	}
	
	
}
