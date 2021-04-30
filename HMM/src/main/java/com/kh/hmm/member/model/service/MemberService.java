package com.kh.hmm.member.model.service;

import java.util.ArrayList;

import com.kh.hmm.member.model.vo.Member;

public interface MemberService {
	// Member selectMember();
	Member dupMember(Member m);

	Member loginMember(Member m);

	Member enrollMember(Member m);

	Member updateMember(Member m);

	Member updatePhoto(Member m);

	Member emailCheck(Member m);

	Member checkEmailId(Member m);

	Member googleMember(Member m);

	/**
	 * 메일 전송
	 * 
	 * @param subject
	 *            제목
	 * @param text
	 *            내용
	 * @param from
	 *            보내는 메일 주소
	 * @param to
	 *            받는 메일 주소
	 * @param filePath
	 *            첨부 파일 경로: 첨부파일 없을시 null
	 **/
	public boolean send(String subject, String text, String from, String to, String filePath);

	Member selectMember(String writerid);

	int leveling(long exp);

	Integer recompoint(String id);

	void recomcount5(int membercode);

	void recomcount3(int membercode);

	Integer havmedal(int membercode);

	void givemedal(int membercode);

	void updateDDARU(Member member);

	int buyMedal(int membercode, int medal);

	Member deleteMember(String memberId);

	Member profileInfo(String profileId);

	Member buyDDARU(Member member);

	long selectExp(String writerid);

	int buyLevelItem(int membercode);

	Member profileImgDel(String membercode);
}
