package com.repository.repository.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface credentialsRepository extends JpaRepository<credentialsentity, String> {

	List<credentialsentity> findByUsername(String username);
	List<credentialsentity> findByPassword(String password);
	
	
}
