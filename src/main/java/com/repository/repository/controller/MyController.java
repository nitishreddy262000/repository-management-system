package com.repository.repository.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.repository.repository.dao.StudentInfoRepository;
import com.repository.repository.dao.credentialsRepository;
import com.repository.repository.dao.credentialsentity;
import com.repository.repository.dao.studentinfo;
import com.repository.repository.entities.MyCredentials;

import antlr.StringUtils;

@CrossOrigin
@RestController
public class MyController {

	@Autowired
	private StudentInfoRepository studentInfoRepository;
	@Autowired
	private credentialsRepository credentialsRepository;
	
	@PostMapping("/api/v1/login")
	public String login(@RequestBody MyCredentials loginCredentials) {
		
	List<credentialsentity> dbcredentials= credentialsRepository.findByUsername(loginCredentials.getUsername());
	if (dbcredentials != null && !dbcredentials.isEmpty()) {
		credentialsentity dbCred = dbcredentials.get(0);
		String dbPassword = dbCred.getpassword();
		String loginpassword = loginCredentials.getPassword();
		System.out.println("dbPassword=" + dbPassword + " loginpassword=" + loginpassword);
		if(loginpassword.equals(dbPassword)) {
			return "Valid";
		}
	}
	return "Invalid";
	
	}
	
	@GetMapping("/api/v1/studentinfo/usn/{usn}")
	public studentinfo usn(@PathVariable String usn) {
		System.out.println(studentInfoRepository.findByUsn(usn).get(0).getname()); 
		return studentInfoRepository.findByUsn(usn).get(0);
	}
	
	@GetMapping("/api/v1/studentinfo/name/{name}")
	public studentinfo name(@PathVariable String name) {
		System.out.println(studentInfoRepository.findByName(name)); 
		return studentInfoRepository.findByName(name).get(0);
	}
	
}
