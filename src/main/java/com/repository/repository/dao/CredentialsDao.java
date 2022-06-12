package com.repository.repository.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class CredentialsDao {

	@PersistenceContext
	protected EntityManager eManager;
	
	public void insert(credentialsentity credentialsentity) {
		eManager.persist(credentialsentity);
	} 
}
