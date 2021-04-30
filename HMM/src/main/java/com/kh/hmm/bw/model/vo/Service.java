package com.kh.hmm.bw.model.vo;

import java.sql.Date;

public class Service {
	
	private String sername;
	private String seradd;
	private String sertitle;
	private String sercontent;
	private Date serdate;
	private char yncheck;
	
	
	
	public Service(){}



	public Service(String sername, String seradd, String sertitle, String sercontent, Date serdate, char yncheck) {
		super();
		this.sername = sername;
		this.seradd = seradd;
		this.sertitle = sertitle;
		this.sercontent = sercontent;
		this.serdate = serdate;
		this.yncheck = yncheck;
	}



	public String getSername() {
		return sername;
	}



	public void setSername(String sername) {
		this.sername = sername;
	}



	public String getSeradd() {
		return seradd;
	}



	public void setSeradd(String seradd) {
		this.seradd = seradd;
	}



	public String getSertitle() {
		return sertitle;
	}



	public void setSertitle(String sertitle) {
		this.sertitle = sertitle;
	}



	public String getSercontent() {
		return sercontent;
	}



	public void setSercontent(String sercontent) {
		this.sercontent = sercontent;
	}



	public Date getSerdate() {
		return serdate;
	}



	public void setSerdate(Date serdate) {
		this.serdate = serdate;
	}



	public char getYncheck() {
		return yncheck;
	}



	public void setYncheck(char yncheck) {
		this.yncheck = yncheck;
	}



	@Override
	public String toString() {
		return "Service [sername=" + sername + ", seradd=" + seradd + ", sertitle=" + sertitle + ", sercontent="
				+ sercontent + ", serdate=" + serdate + ", yncheck=" + yncheck + "]";
	}




	
}

