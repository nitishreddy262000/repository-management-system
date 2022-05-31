package com.repository.repository.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class studentinfo {
	
	@Id
	String id;
	String usn;
	String name;
	
	public String getUsn() {
		return usn;
	} 
	public String getname() {
		return name;
	} 
}
