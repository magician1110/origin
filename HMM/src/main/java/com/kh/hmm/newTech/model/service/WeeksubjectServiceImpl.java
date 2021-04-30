package com.kh.hmm.newTech.model.service;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.hmm.newTech.model.dao.WeeksubjectDao;
import com.kh.hmm.newTech.model.vo.Weeksubject;

@Service("weeksubjectService")
public class WeeksubjectServiceImpl implements WeeksubjectService
{
	@Autowired
	private WeeksubjectDao wDao;


	@Override
	public Weeksubject selectWeek()
	{
		return wDao.selectWeek();
	}

	@Override
	public int proCount()
	{
		return wDao.proCount();
	}

	@Override
	public int conCount()
	{
		return wDao.conCount();
	}

	@Override
	public int pcSearch(String id)
	{
		return wDao.pcSearch(id);
	}

	@Override
	public int proInsert(String id, int wscode)
	{
		return wDao.proInsert(id,wscode);
	}

	@Override
	public int conInsert(String id, int wscode)
	{
		return wDao.conInsert(id,wscode);
	}

	@Override
	public ArrayList<Date> selectDate()
	{
		return wDao.selectDate();
	}

	@Override
	public ArrayList<Weeksubject> getDWeekService(int year)
	{
		return wDao.getDWeekService(year);
	}

	@Override
	public int hproCount(int wscode)
	{
		return wDao.hproCount(wscode);
	}

	@Override
	public int hconCount(int wscode)
	{
		return wDao.hconCount(wscode);
	}

	@Override
	public ArrayList<Weeksubject> selectWeekList()
	{
		return wDao.selectWeekList();
	}

	@Override
	public void updateWeekSubject(int wscode, String title, String agree, String disagree)
	{
		wDao.updateWeekSubject(wscode,title,agree,disagree);
	}

	@Override
	public ArrayList<String> selectSubject()
	{
		return wDao.selectSubject();
	}
}
