package com.repository.repository.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class StudentInfoDao {
	@PersistenceContext
	protected EntityManager eManager;
	
	public void insert(studentinfo studentinfo) {
		eManager.persist(studentinfo);
	} 
}
