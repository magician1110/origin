package com.kh.hmm.member.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.hmm.member.model.vo.Member;

@Repository("memberDao")
public class MemberDao {
	@Autowired
	private SqlSessionTemplate sqlSession;

	public Member selectMember(String writerid) {
		return (Member) sqlSession.selectOne("selectMember", writerid);
	}

	public Member loginMember(Member m) {
		return sqlSession.selectOne("login", m);
	}

	public Member enrollMember(Member m) {	
		int result;
		Member member = null;
		result = sqlSession.insert("enroll", m);

		if (result >= 1)
			member = sqlSession.selectOne("login", m);
		return member;
	}

	public Member updateMember(Member m) {
		int result;
		Member member = null;
		result = sqlSession.update("update", m);

		if (result >= 1)
			member = sqlSession.selectOne("login", m);
		return member;
	}

	public Member dupMember(Member m) {
		return sqlSession.selectOne("chkDup", m);
	}

	public Member emailCheck(Member m) {
		return (Member) sqlSession.selectOne("emailChk", m);
	}

	public Member updatePhoto(Member m) {
		int result;
		Member member = null;
		result = sqlSession.update("updatePhoto", m);

		if (result >= 1)
			member = sqlSession.selectOne("login", m);
		return member;
	}

	public Member googleMember(Member m) {
		int result = 0;
		Member member = null;
		member = sqlSession.selectOne("emailChk", m);

		if (member != null) {
			return member;
		} else {
			result = sqlSession.insert("google", m);
			if (result >= 1)
				member = sqlSession.selectOne("login", m);
			return member;
		}
	}

	public Member CheckEmailId(Member m) {
		Member member = (Member) sqlSession.selectOne("emailChk", m);
		System.out.println("이메일 체크 : "+member);
		member.setPassword(m.getPassword());
		int result = sqlSession.update("update", member);
		if (result >= 1) {
			member = (Member) sqlSession.selectOne("emailChk", member);
			return member;
		} else {
			return member;
		}
	}

	public int leveling(long exp) {

		int level=1;
		if(sqlSession.selectOne("leveling", exp)!=null)level=sqlSession.selectOne("leveling", exp); 

		return  level;
	}

	public Integer recompoint(String id) {
		return sqlSession.selectOne("recompoint", id);
	}

	public void recomcount5(int membercode) {
		sqlSession.update("recomcount5", membercode);
	}

	public void recomcount3(int membercode) {
		sqlSession.update("recomcount3", membercode);
	}

	public Integer havmedal(int membercode) {
		return sqlSession.selectOne("havmedal", membercode);
	}

	public void givemedal(int membercode) {
		sqlSession.update("givemedal", membercode);
	}

	public void updateDDARU(Member member) {
		sqlSession.update("updateDDARU", member);
	}
	
	public Member buyDDARU(Member member) {
		sqlSession.update("updateDDARU", member);
		return	sqlSession.selectOne("DDaruInfo",member);
	}

	public int buyMedal(int membercode, int medal) {
		HashMap map = new HashMap();
		map.put("membercode", membercode);
		map.put("medal", medal);
		System.out.println("충전할 메달 갯수 : "+medal);
		return sqlSession.update("buyMedal", map);
	}

	public Member deleteMember(String memberId) {
		Member member = null;
		String id = memberId;
		int result = sqlSession.update("DeleteMember", id);

		if (result >= 1) {
			member = sqlSession.selectOne("memberInfo", id);
		}

		System.out.println("Delete DAO Member : " + member);

		return member;
	}

	public Member profileInfo(String profileId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id1", profileId);
		map.put("id2", profileId);
		map.put("id3", profileId);

		Member member = sqlSession.selectOne("profileInfo", map);
		if (member != null) {
			return member;
		}
		return member;
	}

	public long selectExp(String writerid) {
		return sqlSession.selectOne("selectExp", writerid);
	}

	public int buyLevelItem(int membercode) {
		return sqlSession.update("buyLevelItem",membercode);
	}

	public Member profileImgDel(String membercode) {
		int result = sqlSession.update("profileImgDel",membercode);
		Member member = null;
		if(result >= 1)
			member = sqlSession.selectOne("DDaruInfo",membercode);
		return member;
	}
}