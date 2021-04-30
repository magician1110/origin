package com.kh.hmm.item.model.service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.hmm.item.model.dao.ItemDao;
import com.kh.hmm.item.model.vo.Item;
import com.kh.hmm.item.model.vo.Purchaseditem;

@Service("itemService")
public class ItemServiceImpl implements ItemService
{

	@Autowired
	private ItemDao iDao;
	
	@Override
	public Item selectItemOne(int itemcode)
	{
		return iDao.selectItemOne(itemcode);
	}

	@Override
	public ArrayList<Item> selectItemList()
	{
		return iDao.selectItemList();
	}

	@Override
	public ArrayList<Item> selectPurchasedItemList(int membercode)
	{
		return iDao.selectPurchasedItemList(membercode);
	}

	@Override
	public int insertOne(Purchaseditem pitem)
	{
		return iDao.insertOne(pitem);
		
	}

	@Override
	public int deleteOne(Purchaseditem pitem)
	{
		return iDao.deleteOne(pitem);
	}

	@Override
	public int selectIsPurchaseItem(Purchaseditem pitem) {
		return iDao.selectIsPurchaseItem(pitem);
	}

}
