package com.tcs.protean.member.model.vo;

import java.sql.Date;

public class Member {	
	
	private int membercode;
	private String id;
	private String password;	
	private String email;
	private String department;
	private String username;
	private Date enrolldate;
	private Date quitdate;	
	
	public Member() {
		
	}	
	
	public Member(int membercode, String id, String password, String email, String department,
			String username, Date enrolldate, Date quitdate) {
		super();		
		this.membercode = membercode;
		this.id = id;
		this.password = password;		
		this.email = email;
		this.department = department;
		this.username = username;
		this.enrolldate = enrolldate;
		this.quitdate = quitdate;		
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
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getEnrolldate() {
		return enrolldate;
	}
	public void setEnrolldate(Date enrolldate) {
		this.enrolldate = enrolldate;
	}
	public Date getQuitdate() {
		return quitdate;
	}
	public void setQuitdate(Date quitdate) {
		this.quitdate = quitdate;
	}
	

	@Override
	public String toString() {
		return "Member [membercode=" + membercode + ", id=" + id + ", password=" + password 
				+ ", email=" + email + ", department=" + department + ", username=" + username + ", enrolldate="
				+ enrolldate + ", quitdate=" + quitdate + "]";
//		return "Member [id=" + id + ", password=" + password 
//				+ ", email=" + email + ", department=" + department + ", username=" + username + ", enrolldate="
//				+ enrolldate + ", quitdate=" + quitdate + ", membercode=" + membercode + "]";
	}


}
