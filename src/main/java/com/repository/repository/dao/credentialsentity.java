package com.repository.repository.dao;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="credentials")
public class credentialsentity {

	@Id
	String username;
	String password;
	String userType;
	
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
