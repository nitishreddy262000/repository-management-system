package com.repository.repository.entities;

public class SignupInput {

	String name;
	String usn;
	String email;
	String password;
	String userType;
	int academicyear;
	int batchnumber;
	String guidename;
	
	
	public int getAcademicyear() {
		return academicyear;
	}
	public void setAcademicyear(int academicyear) {
		this.academicyear = academicyear;
	}
	public int getBatchnumber() {
		return batchnumber;
	}
	public void setBatchnumber(int batchnumber) {
		this.batchnumber = batchnumber;
	}
	public String getGuidename() {
		return guidename;
	}
	public void setGuidename(String guidename) {
		this.guidename = guidename;
	}
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsn() {
		return usn;
	}
	public void setUsn(String usn) {
		this.usn = usn;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
		
}	
	
