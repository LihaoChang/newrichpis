package com.newRich.model;

public class Person implements java.io.Serializable {
	private static final long serialVersionUID = 1983691355339139003L;
	private String id;
	private String name;
	private String password;
	private String role;

	public Person() {
	}

	public Person(String id) {
		this.id = id;
	}

	public Person(String id, String name, String password, String role) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.role = role;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
