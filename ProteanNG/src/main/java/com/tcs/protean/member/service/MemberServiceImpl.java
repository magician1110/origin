package com.tcs.protean.member.service;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.protean.member.model.dao.MemberDAO;
import com.tcs.protean.member.model.vo.Member;

@Service("memberService")
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberDAO mDAO;
	
	@Override
	public Member loginMember(Member m) {
		return mDAO.loginMember(m);
	}

}
