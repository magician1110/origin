package com.tcs.protean.member.model.dao;

import javax.servlet.http.HttpSession;

import com.tcs.protean.member.model.vo.Member;

public interface MemberDAO {
	public Member loginMember(Member m);
}
