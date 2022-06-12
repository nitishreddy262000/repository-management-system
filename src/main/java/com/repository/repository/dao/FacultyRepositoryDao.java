package com.repository.repository.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class FacultyRepositoryDao {
	
	@PersistenceContext
	protected EntityManager eManager;
	
	public void insert(FacultyEntity facultyEntity) {
		eManager.persist(facultyEntity);
	} 
}
