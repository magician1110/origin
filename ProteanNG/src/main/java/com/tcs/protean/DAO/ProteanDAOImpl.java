package com.tcs.protean.DAO;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.tcs.protean.bean.TestBean;



@Repository
public class ProteanDAOImpl implements ProteanDAO {
	private static final String namespace = "com.tcs.protean.mappers.testMapper";

	@Inject
	private SqlSession sqlSession;
	
	@Override
	public List<TestBean> protean() throws Exception {
		System.out.println("######## ==" + sqlSession.selectList(namespace+".protean"));
		return sqlSession.selectList(namespace+".protean");
	}

}
