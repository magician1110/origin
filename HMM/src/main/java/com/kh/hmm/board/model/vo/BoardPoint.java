package com.kh.hmm.board.model.vo;

public class BoardPoint
{
	private int bcode;
	private int viewnum;
	private int best;
	private int good;
	private int bad;
	private int worst;
	private int cal;
	private int medal;
	private int report;
	
	public BoardPoint() {}

	public BoardPoint(int bcode, int viewnum, int best, int good, int bad, int worst, int cal, int medal, int report)
	{
		super();
		this.bcode = bcode;
		this.viewnum = viewnum;
		this.best = best;
		this.good = good;
		this.bad = bad;
		this.worst = worst;
		this.cal = cal;
		this.medal = medal;
		this.report = report;
	}

	public int getBcode()
	{
		return bcode;
	}

	public void setBcode(int bcode)
	{
		this.bcode = bcode;
	}

	public int getViewnum()
	{
		return viewnum;
	}

	public void setViewnum(int viewnum)
	{
		this.viewnum = viewnum;
	}

	public int getBest()
	{
		return best;
	}

	public void setBest(int best)
	{
		this.best = best;
	}

	public int getGood()
	{
		return good;
	}

	public void setGood(int good)
	{
		this.good = good;
	}

	public int getBad()
	{
		return bad;
	}

	public void setBad(int bad)
	{
		this.bad = bad;
	}

	public int getWorst()
	{
		return worst;
	}

	public void setWorst(int worst)
	{
		this.worst = worst;
	}

	public int getCal()
	{
		return cal;
	}

	public void setCal(int cal)
	{
		this.cal = cal;
	}

	public int getMedal()
	{
		return medal;
	}

	public void setMedal(int medal)
	{
		this.medal = medal;
	}

	public int getReport()
	{
		return report;
	}

	public void setReport(int report)
	{
		this.report = report;
	}

	@Override
	public String toString()
	{
		return "BoardPoint [bcode=" + bcode + ", viewnum=" + viewnum + ", best=" + best + ", good=" + good + ", bad="
				+ bad + ", worst=" + worst + ", cal=" + cal + ", medal=" + medal + ", report=" + report + "]";
	}

	
	
	
}

