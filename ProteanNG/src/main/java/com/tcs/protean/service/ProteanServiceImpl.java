package com.tcs.protean.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.tcs.protean.service.ProteanService;
import com.tcs.protean.DAO.ProteanDAO;
import com.tcs.protean.bean.TestBean;

@Service
public class ProteanServiceImpl implements ProteanService {
	@Inject
	private ProteanDAO dao;
	
	@Override
	public List<TestBean> protean() throws Exception{		
		return dao.protean();
		
	}
}
