package com.tcs.protean.member.service;

import javax.servlet.http.HttpSession;

import com.tcs.protean.member.model.vo.Member;

public interface MemberService {

	Member loginMember(Member m);
	
}
