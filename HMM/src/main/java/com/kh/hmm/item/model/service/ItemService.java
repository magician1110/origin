package com.kh.hmm.item.model.service;

import java.util.ArrayList;

import com.kh.hmm.item.model.vo.Item;
import com.kh.hmm.item.model.vo.Purchaseditem;

public interface ItemService
{
	Item selectItemOne(int itemcode);
	
	ArrayList<Item> selectItemList();
	
	ArrayList<Item> selectPurchasedItemList(int membercode);
	
	int selectIsPurchaseItem(Purchaseditem pitem);

	int insertOne(Purchaseditem pitem);

	int deleteOne(Purchaseditem pitem);	
}
