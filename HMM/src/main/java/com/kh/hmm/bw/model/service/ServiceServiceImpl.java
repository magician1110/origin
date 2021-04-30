package com.kh.hmm.bw.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.hmm.bw.model.dao.ServiceDao;

@Service("serviceService")
public class ServiceServiceImpl implements ServiceService{
	
	@Autowired
	private ServiceDao sDao;

	@Override
	public void insertService(com.kh.hmm.bw.model.vo.Service s) {
		sDao.insertService(s);
		
	}

	@Override
	public ArrayList<com.kh.hmm.bw.model.vo.Service> selectAll() {
		
		return sDao.selectAll();
	}

	@Override
	public void checkBox(String[] valueArrTest) {
		sDao.deleteCheckbox(valueArrTest);
		
		
	}

	@Override
	public int serviceCheck(String valueArrTest) {
		return sDao.serviceCheck(valueArrTest);
	}

	@Override
	public int totalRow() {
		return sDao.totalRow();
	}

	@Override
	public ArrayList<com.kh.hmm.bw.model.vo.Service> sSelectAll(HashMap<String, Integer> map) {
		
		return sDao.sSelectAll(map);
	}

	@Override
	public int serviceCount() {
		return sDao.serviceCount();
	}

	@Override
	public ArrayList<com.kh.hmm.bw.model.vo.Service> superSelectAll() {
		
		return sDao.superSelectAll();
	}

	
	
}
