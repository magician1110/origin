package com.tcs.protean.member.model.dao;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tcs.protean.member.model.vo.Member;
import com.tcs.protean.member.service.MemberService;

@Repository("memberDAO")
public class MemberDAOImpl implements MemberDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public Member loginMember(Member m) {
		return sqlSession.selectOne("login", m);
	}
	
}
