package com.kh.hmm.newTech.model.service;

import java.sql.Date;
import java.util.ArrayList;

import com.kh.hmm.newTech.model.vo.Weeksubject;

public interface WeeksubjectService
{
	int proInsert(String id,int wscode);

	int conInsert(String id,int wscode);
	
	int proCount();

	int conCount();
	
	int pcSearch(String id);
	
	Weeksubject selectWeek();

	ArrayList<Date> selectDate();

	ArrayList<Weeksubject> getDWeekService(int year);

	int hproCount(int wscode);
	
	int hconCount(int wscode);

	ArrayList<Weeksubject> selectWeekList();

	void updateWeekSubject(int wscode, String title, String agree, String disagree);
	
	ArrayList<String> selectSubject();
}
