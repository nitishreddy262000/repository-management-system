package com.repository.repository.dao;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="credentials")
public class credentialsentity {

	@Id
	String username;
	String password;
	
	public String getusername() {
		return username;
	} 
	public String getpassword() {
		return password;
	} 
}
