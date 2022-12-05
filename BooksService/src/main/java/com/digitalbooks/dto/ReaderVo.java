package com.digitalbooks.dto;

public class ReaderVo {

	private String readerId;
	private String readerEmail;

	public String getReaderId() {
		return readerId;
	}

	public void setReaderId(String readerId) {
		this.readerId = readerId;
	}

	public String getReaderEmail() {
		return readerEmail;
	}

	public void setReaderEmail(String readerEmail) {
		this.readerEmail = readerEmail;
	}

	public ReaderVo() {
		super();
	}

	public ReaderVo(String readerId, String readerEmail) {
		super();
		this.readerId = readerId;
		this.readerEmail = readerEmail;
	}

	@Override
	public String toString() {
		return "ReaderVo [readerId=" + readerId + ", readerEmail=" + readerEmail + "]";
	}
	
	

}
