package com.kh.hmm.item.model.vo;

import java.sql.Date;

public class Purchaseditem
{
	private int membercode;
	private int itemcode;
	private Date purchasedate;
	
	public Purchaseditem() {}

	public Purchaseditem(int membercode, int itemcode, Date purchasedate)
	{
		super();
		this.membercode = membercode;
		this.itemcode = itemcode;
		this.purchasedate = purchasedate;
	}

	public int getMembercode()
	{
		return membercode;
	}

	public void setMembercode(int membercode)
	{
		this.membercode = membercode;
	}

	public int getItemcode()
	{
		return itemcode;
	}

	public void setItemcode(int itemcode)
	{
		this.itemcode = itemcode;
	}

	public Date getPurchasedate()
	{
		return purchasedate;
	}

	public void setPurchasedate(Date purchasedate)
	{
		this.purchasedate = purchasedate;
	}

	
	
	
}
