package com.repository.repository.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity(name="faculty")
@Table(name="faculty")
public class FacultyEntity {
	
	
	@Id
	String facultyid;
	String name;
	
	public String getId() {
		return facultyid;
	}
	public void setId(String facultyid) {
		this.facultyid = facultyid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
