package com.kh.hmm.bw.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.hmm.board.model.vo.Board;
import com.kh.hmm.bw.model.vo.Service;

@Repository("serviceDao")
public class ServiceDao 
{
	@Autowired
	private SqlSessionTemplate sqlSession;

	public void insertService(com.kh.hmm.bw.model.vo.Service s) {
		sqlSession.insert("serviceinsert", s);
		
	}
	
	
	public ArrayList<com.kh.hmm.bw.model.vo.Service> selectAll() {
		
	
		
		return new ArrayList<Service>(sqlSession.selectList("serviceselectAll"));
		
		
	}


	public void deleteCheckbox(String[] valueArrTest) {
		for(String s : valueArrTest){
		sqlSession.delete("servicedelete", s);
		
		}
	}


	public int serviceCheck(String valueArrTest) {
		
			
			return sqlSession.update("servicecheck", valueArrTest);
	}

	public int totalRow() {
		
		return sqlSession.selectOne("totalRow") ;
	}

	public ArrayList<com.kh.hmm.bw.model.vo.Service> sSelectAll(HashMap<String, Integer> map) {
		
		return new ArrayList<Service>(sqlSession.selectList("serviceselectAll",map));
	}


	public int serviceCount() {
		return sqlSession.selectOne("serviceMapper.serviceCount");
	}


	public ArrayList<com.kh.hmm.bw.model.vo.Service> superSelectAll() {
		return new ArrayList<Service>(sqlSession.selectList("superSelectAll"));
	}


	



	
}
