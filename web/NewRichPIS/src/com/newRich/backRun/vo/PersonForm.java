package com.newRich.backRun.vo;

public class PersonForm implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6423581700704323835L;
	private String id;
	private String name;
	private String password;

	public PersonForm() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
