package com.repository.repository.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class studentinfo {
	
	@Id
	String usn;
	String name;
	@Column(name="academic_year")
	int academicyear;
	@Column(name="batch_number")
	int batchnumber;
	@Column(name="guide_name")
	String guidename;
	
	public String getUsn() {
		return usn;
	}
	public void setUsn(String usn) {
		this.usn = usn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
	
	
	
}
