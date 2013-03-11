package com.newRich.backRun.vo;

public class CountLogin implements java.io.Serializable {

	private static final long serialVersionUID = -5784330010683389679L;
	
	private String memberId;
	private String realName;
	private String updateDate;
	
	private String dateStart;// for query
	private String dateEnd;// for query

	public CountLogin() {
	}

	public CountLogin(String memberId) {
		this.memberId = memberId;
	}

	public CountLogin(String memberId, String realName, String updateDate) {
		this.memberId = memberId;
		this.realName = realName;
		this.updateDate = updateDate;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	
}