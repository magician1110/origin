package com.kh.hmm.newTech.model.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.hmm.newTech.model.vo.Weeksubject;

@Repository("weeksubjectDao")
public class WeeksubjectDao
{
	@Autowired
	private SqlSessionTemplate sqlSession;

	public int proInsert(String id,int wscode)
	{
		HashMap map=new HashMap();
		map.put("id", id);
		map.put("wscode", wscode);
		
		return sqlSession.insert("proInsert",map);
	}

	public int conInsert(String id,int wscode)
	{
		HashMap map=new HashMap();
		map.put("id", id);
		map.put("wscode", wscode);
		
		return sqlSession.insert("conInsert",map);
	}	

	public Weeksubject selectWeek()
	{
		return sqlSession.selectOne("selectWeek");
	}

	public int proCount()
	{
		return sqlSession.selectOne("proCount");
	}

	public int conCount()
	{
		return sqlSession.selectOne("conCount");
	}

	public int pcSearch(String id)
	{
		return sqlSession.selectOne("pcSearch",id);
	}

	public ArrayList<Date> selectDate()
	{
		List<Date> hlist=sqlSession.selectList("selectDate");
		return (ArrayList<Date>)hlist;
	}

	public ArrayList<Weeksubject> getDWeekService(int year)
	{
		HashMap map=new HashMap();
		map.put("first", year);
		map.put("second", year);
		
		List<Weeksubject> wlist=sqlSession.selectList("selectYWsubject",map);
		return (ArrayList<Weeksubject>)wlist;
	}

	public int hproCount(int wscode)
	{
		return sqlSession.selectOne("hproCount",wscode);
	}

	public int hconCount(int wscode)
	{
		return sqlSession.selectOne("hconCount",wscode);
	}

	public ArrayList<Weeksubject> selectWeekList()
	{
		List<Weeksubject> wlist=sqlSession.selectList("selectWeekList");
		return (ArrayList<Weeksubject>)wlist;
	}

	public void updateWeekSubject(int wscode, String title, String agree, String disagree)
	{
		HashMap map=new HashMap();
		map.put("wscode", wscode);
		map.put("title", title);
		map.put("agree", agree);
		map.put("disagree", disagree);
		
		sqlSession.update("updateWeekSubject", map);
	}

	public ArrayList<String> selectSubject()
	{
		List<String> slist=sqlSession.selectList("selectSubject");
		return (ArrayList<String>)slist;
	}
}
