package com.repository.repository.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ReviewId implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "studentusn")
	String usn;
	
	@Column(name = "batch_no")
	int batch;
	
	public ReviewId() {}
	
	public ReviewId(String usn, int batch) {
		this.batch = batch;
		this.usn = usn;
	}
	
}
