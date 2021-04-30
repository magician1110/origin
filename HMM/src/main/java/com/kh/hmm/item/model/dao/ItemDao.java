package com.kh.hmm.item.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.hmm.item.model.vo.Item;
import com.kh.hmm.item.model.vo.Purchaseditem;

@Repository("itemDao")
public class ItemDao
{
	@Autowired
	private SqlSessionTemplate sqlSession;

	public Item selectItemOne(int itemcode)
	{
		return (Item)sqlSession.selectOne("selectItemOne",itemcode);
	}

	public ArrayList<Item> selectItemList()
	{
		List<Item> list=sqlSession.selectList("selectItemList");
		return (ArrayList<Item>)list;
	}

	public ArrayList<Item> selectPurchasedItemList(int membercode)
	{
		List<Item> list=sqlSession.selectList("selectPurchasedItem",membercode);
		return (ArrayList<Item>)list;
	}

	public int insertOne(Purchaseditem pitem)
	{
		return sqlSession.insert("insertOne",pitem);
	}

	public int deleteOne(Purchaseditem pitem)
	{
		return sqlSession.delete("deleteOne",pitem);
	}

	public int selectIsPurchaseItem(Purchaseditem pitem) {
		return sqlSession.selectOne("selectIsPurchaseItem",pitem);
	}

}
