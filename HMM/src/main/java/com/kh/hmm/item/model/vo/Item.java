package com.kh.hmm.item.model.vo;

public class Item
{
	private int itemcode;
	private String name;
	private int usagedate;
	private String mood;
	private String filelink;
	private int price;
		
	public Item() {}

	public Item(int itemcode, String name, int usagedate, String mood, String filelink, int price)
	{
		super();
		this.itemcode = itemcode;
		this.name = name;
		this.usagedate = usagedate;
		this.mood = mood;
		this.filelink = filelink;
		this.price = price;
	}

	public int getItemcode()
	{
		return itemcode;
	}

	public void setItemcode(int itemcode)
	{
		this.itemcode = itemcode;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getUsagedate()
	{
		return usagedate;
	}

	public void setUsagedate(int usagedate)
	{
		this.usagedate = usagedate;
	}

	public String getMood()
	{
		return mood;
	}

	public void setMood(String mood)
	{
		this.mood = mood;
	}

	public String getFilelink()
	{
		return filelink;
	}

	public void setFilelink(String filelink)
	{
		this.filelink = filelink;
	}

	public int getPrice()
	{
		return price;
	}

	public void setPrice(int price)
	{
		this.price = price;
	}

	@Override
	public String toString()
	{
		return "Item [itemcode=" + itemcode + ", name=" + name + ", usagedate=" + usagedate + ", mood=" + mood
				+ ", filelink=" + filelink + ", price=" + price + "]";
	}

	
	

	
	
}
