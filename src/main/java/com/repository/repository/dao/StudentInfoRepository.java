package com.repository.repository.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentInfoRepository extends JpaRepository<studentinfo, String> {

	List<studentinfo> findByUsn(String usn);
	List<studentinfo> findByName(String name);
	List<studentinfo> findAll();
	
}
