package com.newRich.backRun.vo;

public class StockToolForm implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3585371262763451092L;
	private String id;
	private String userid;
	private String name;
	private String url;
	private String remark;
	public String updateDate;

	public StockToolForm() {
	}

	public StockToolForm(String id) {
		this.id = id;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
}
