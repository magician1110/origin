package com.kh.hmm.newTech.model.vo;

import java.sql.Date;

public class Weeksubject
{
	private int wscode;
	private String title;
	private String agree;
	private String disagree;
	private Date startdate;
	
	public Weeksubject() {}

	public Weeksubject(int wscode, String title, String agree, String disagree, Date startdate)
	{
		super();
		this.wscode = wscode;
		this.title = title;
		this.agree = agree;
		this.disagree = disagree;
		this.startdate = startdate;
	}

	public int getWscode()
	{
		return wscode;
	}

	public void setWscode(int wscode)
	{
		this.wscode = wscode;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getAgree()
	{
		return agree;
	}

	public void setAgree(String agree)
	{
		this.agree = agree;
	}

	public String getDisagree()
	{
		return disagree;
	}

	public void setDisagree(String disagree)
	{
		this.disagree = disagree;
	}

	public Date getStartdate()
	{
		return startdate;
	}

	public void setStartdate(Date startdate)
	{
		this.startdate = startdate;
	}

	@Override
	public String toString()
	{
		return "Weeksubject [wscode=" + wscode + ", title=" + title + ", agree=" + agree + ", disagree=" + disagree
				+ ", startdate=" + startdate + "]";
	}
	
	
	
	
	
}
