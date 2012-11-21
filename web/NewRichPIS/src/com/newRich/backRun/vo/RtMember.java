package com.newRich.backRun.vo;

public class RtMember implements java.io.Serializable {

	private static final long serialVersionUID = -5883063843233494872L;

	private String memberId;
	private String nickname;
	private String realname;
	private String email;
	private String scale;
	private String updateDate;

	public RtMember() {
	}

	public RtMember(String memberId) {
		this.memberId = memberId;
	}

	public RtMember(String memberId, String nickname, String realname, String email, String scale, String updateDate) {
		this.memberId = memberId;
		this.nickname = nickname;
		this.realname = realname;
		this.email = email;
		this.scale = scale;
		this.updateDate = updateDate;
	}
	
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

}
