package com.kh.hmm.board.model.vo;

public class BoardCode
{
	private int discode;
	private String name;
	
	public BoardCode() {}

	public BoardCode(int discode, String name)
	{
		super();
		this.discode = discode;
		this.name = name;
	}

	public int getDiscode()
	{
		return discode;
	}

	public void setDiscode(int discode)
	{
		this.discode = discode;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return "BoardCode [discode=" + discode + ", name=" + name + "]";
	}
	
	
}
