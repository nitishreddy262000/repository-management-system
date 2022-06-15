package com.repository.repository.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class ReviewDao {

	@PersistenceContext
	protected EntityManager eManager;
	
	public void insert(ReviewEntity reviewEntity) {
		eManager.persist(reviewEntity);
	} 
	
}
