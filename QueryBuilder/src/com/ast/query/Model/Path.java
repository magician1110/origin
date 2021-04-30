package com.ast.query.Model;

public class Path {
	private String pdfSelectPath;
	private String txtPath;
	
	public Path(String pdfSelectPath, String txtPath) {
		super();
		this.pdfSelectPath = pdfSelectPath;
		this.txtPath = txtPath;
	}

	public String getPdfSelectPath() {
		return pdfSelectPath;
	}

	public void setPdfSelectPath(String pdfSelectPath) {
		this.pdfSelectPath = pdfSelectPath;
	}

	public String getTxtPath() {
		return txtPath;
	}

	public void setTxtPath(String txtPath) {
		this.txtPath = txtPath;
	}

	@Override
	public String toString() {
		return "Path [pdfSelectPath=" + pdfSelectPath + ", txtPath=" + txtPath + "]";
	}
	

	
}
