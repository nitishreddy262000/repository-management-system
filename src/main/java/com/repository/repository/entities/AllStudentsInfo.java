package com.repository.repository.entities;

import java.util.List;

import com.repository.repository.dao.studentinfo;

public class AllStudentsInfo {
	
	List<studentinfo> studentsList;

	public List<studentinfo> getStudentsList() {
		return studentsList;
	}

	public void setStudentsList(List<studentinfo> studentsList) {
		this.studentsList = studentsList;
	}

}
