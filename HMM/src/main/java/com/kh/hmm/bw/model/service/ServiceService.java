package com.kh.hmm.bw.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.kh.hmm.bw.model.vo.Service;

public interface ServiceService {

	void insertService(Service s);

	ArrayList<Service> selectAll();

	void checkBox(String[] valueArrTest);

	int serviceCheck(String valueArrTest);

	int totalRow();

	ArrayList<Service> sSelectAll(HashMap<String, Integer> map);

	ArrayList<Service> superSelectAll();
	
	int serviceCount();

}
