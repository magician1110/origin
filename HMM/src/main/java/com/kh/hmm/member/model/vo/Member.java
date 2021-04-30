package com.kh.hmm.member.model.vo;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

public class Member {
	private int membercode;
	private String id;
	private String password;
	private String email;
	private String photo;
	private int medal;
	private int havmedal;
	private long exp;
	private int chash;
	private int ddaru;
	private int report;
	private int recompoint;
	private int boardCount;
	private String job;
	private Date enrolldate;
	private Date quitdate;
	private MultipartFile uploadFile;
	private String levelitem;

	public String getLevelitem() {
		return levelitem;
	}

	public void setLevelitem(String levelitem) {
		this.levelitem = levelitem;
	}

	public int getBoardCount() {
		return boardCount;
	}

	public void setBoardCount(int boardCount) {
		this.boardCount = boardCount;
	}

	public int getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}

	private int commentsCount;

	public Date getQuitdate() {
		return quitdate;
	}

	public void setQuitdate(Date quitdate) {
		this.quitdate = quitdate;
	}

	public Member() {
	}

	public Member(int membercode, String id, String password, String email, String photo, int medal, int havmedal,
			long exp, int chash, int ddaru, int report, int recompoint, int boardCount, int commentsCount, String job,
			Date enrolldate, Date quitdate, MultipartFile uploadFile) {
		super();
		this.membercode = membercode;
		this.id = id;
		this.password = password;
		this.email = email;
		this.photo = photo;
		this.medal = medal;
		this.havmedal = havmedal;
		this.exp = exp;
		this.chash = chash;
		this.ddaru = ddaru;
		this.report = report;
		this.recompoint = recompoint;
		this.boardCount = boardCount;
		this.commentsCount = commentsCount;
		this.job = job;
		this.enrolldate = enrolldate;
		this.quitdate = quitdate;
		this.uploadFile = uploadFile;
	}

	public int getMembercode() {
		return membercode;
	}

	public void setMembercode(int membercode) {
		this.membercode = membercode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getMedal() {
		return medal;
	}

	public void setMedal(int medal) {
		this.medal = medal;
	}

	public int getHavmedal() {
		return havmedal;
	}

	public void setHavmedal(int havmedal) {
		this.havmedal = havmedal;
	}

	public long getExp() {
		return exp;
	}

	public void setExp(long exp) {
		this.exp = exp;
	}

	public int getChash() {
		return chash;
	}

	public void setChash(int chash) {
		this.chash = chash;
	}

	public int getDdaru() {
		return ddaru;
	}

	public void setDdaru(int ddaru) {
		this.ddaru = ddaru;
	}

	public int getReport() {
		return report;
	}

	public void setReport(int report) {
		this.report = report;
	}

	public int getRecompoint() {
		return recompoint;
	}

	public void setRecompoint(int recompoint) {
		this.recompoint = recompoint;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Date getEnrolldate() {
		return enrolldate;
	}

	public void setEnrolldate(Date enrolldate) {
		this.enrolldate = enrolldate;
	}

	public MultipartFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}

	@Override
	public String toString() {
		return "Member [membercode=" + membercode + ", id=" + id + ", password=" + password + ", email=" + email
				+ ", photo=" + photo + ", medal=" + medal + ", havmedal=" + havmedal + ", exp=" + exp + ", chash="
				+ chash + ", ddaru=" + ddaru + ", report=" + report + ", recompoint=" + recompoint + ", boardCount="
				+ boardCount + ", commentsCount=" + commentsCount + ", job=" + job + ", enrolldate=" + enrolldate
				+ ", quitdate=" + quitdate + ", uploadFile=" + uploadFile + "]";
	}

}
