package com.kh.hmm.board.model.vo;

import java.sql.Date;

public class Comments
{
	private int ccode;
	private String content;
	private String writerid;
	private String postdate;
	private int lev;
	private int upper;
	private int bcode;
	private String isdelete;
	
	private CommentsPoint point;
	
	public Comments() {}

	public Comments(int ccode, String content, String writerid, String postdate, int lev, int upper, int bcode,
			String isdelete, CommentsPoint point)
	{
		super();
		this.ccode = ccode;
		this.content = content;
		this.writerid = writerid;
		this.postdate = postdate;
		this.lev = lev;
		this.upper = upper;
		this.bcode = bcode;
		this.isdelete = isdelete;
		this.point = point;
	}

	public int getCcode()
	{
		return ccode;
	}

	public void setCcode(int ccode)
	{
		this.ccode = ccode;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getWriterid()
	{
		return writerid;
	}

	public void setWriterid(String writerid)
	{
		this.writerid = writerid;
	}

	public String getPostdate()
	{
		return postdate;
	}

	public void setPostdate(String postdate)
	{
		this.postdate = postdate;
	}

	public int getLev()
	{
		return lev;
	}

	public void setLev(int lev)
	{
		this.lev = lev;
	}

	public int getUpper()
	{
		return upper;
	}

	public void setUpper(int upper)
	{
		this.upper = upper;
	}

	public int getBcode()
	{
		return bcode;
	}

	public void setBcode(int bcode)
	{
		this.bcode = bcode;
	}

	public String getIsdelete()
	{
		return isdelete;
	}

	public void setIsdelete(String isdelete)
	{
		this.isdelete = isdelete;
	}

	public CommentsPoint getPoint()
	{
		return point;
	}

	public void setPoint(CommentsPoint point)
	{
		this.point = point;
	}

	@Override
	public String toString()
	{
		return "Comments [ccode=" + ccode + ", content=" + content + ", writerid=" + writerid + ", postdate=" + postdate
				+ ", lev=" + lev + ", upper=" + upper + ", bcode=" + bcode + ", isdelete=" + isdelete + ", point="
				+ point + "]";
	}

	
	
	
}
