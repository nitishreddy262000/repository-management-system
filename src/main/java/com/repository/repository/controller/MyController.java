package com.repository.repository.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.services.drive.model.File;
import com.repository.repository.dao.CredentialsDao;
import com.repository.repository.dao.FacultyEntity;
import com.repository.repository.dao.FacultyRepositoryDao;
import com.repository.repository.dao.StudentInfoDao;
import com.repository.repository.dao.StudentInfoRepository;
import com.repository.repository.dao.credentialsRepository;
import com.repository.repository.dao.credentialsentity;
import com.repository.repository.dao.studentinfo;
import com.repository.repository.entities.CredentialsInput;
import com.repository.repository.entities.CredentialsOutput;
import com.repository.repository.entities.SignupInput;
import com.spring.service.GoogleDriveService;
import com.spring.service.imp.GoogleDriveServiceImp;

import antlr.StringUtils;

@CrossOrigin
@RestController
public class MyController {

	@Autowired
	private GoogleDriveServiceImp driveService;
	@Autowired
	private StudentInfoRepository studentInfoRepository;
	@Autowired
	private credentialsRepository credentialsRepository;
	@Autowired
	private FacultyRepositoryDao facultyRepositoryDao;
	@Autowired
	private CredentialsDao credentialsDao;
	@Autowired
	private StudentInfoDao studentInfoDao;
	
	@PostMapping("/api/v1/login")
	public CredentialsOutput login(@RequestBody CredentialsInput loginCredentials) {
		
		List<credentialsentity> dbcredentials= credentialsRepository.findByUsername(loginCredentials.getUsername());
		CredentialsOutput credentialsOutput = new CredentialsOutput();
		credentialsOutput.setValid(false);
		
		if (dbcredentials != null && !dbcredentials.isEmpty()) {
			credentialsentity dbCred = dbcredentials.get(0);
			String dbPassword = dbCred.getPassword();
			String loginpassword = loginCredentials.getPassword();
			credentialsOutput.setUserType(dbCred.getUserType());
			System.out.println("dbPassword=" + dbPassword + " loginpassword=" + loginpassword);
			if(loginpassword.equals(dbPassword)) {
				credentialsOutput.setValid(true);
			} 
		}
		return credentialsOutput;
	
	}
	
	@PostMapping("/api/v1/signup")
	public ResponseEntity signup(@RequestBody SignupInput signupInput) {
		
		if("FACULTY".equals(signupInput.getUserType())) {
			FacultyEntity facultyEntity = new FacultyEntity();
			facultyEntity.setId(signupInput.getUsn());
			facultyEntity.setName(signupInput.getName());
			facultyRepositoryDao.insert(facultyEntity);

			credentialsentity credentialsentity = new credentialsentity();
			credentialsentity.setUserType("FACULTY");
			credentialsentity.setUsername(signupInput.getEmail());
			credentialsentity.setPassword(signupInput.getPassword());
			credentialsDao.insert(credentialsentity);
			
		} else if ("STUDENT".equals(signupInput.getUserType())) {
			studentinfo studentinfo = new studentinfo();
			studentinfo.setName(signupInput.getName());
			studentinfo.setUsn(signupInput.getUsn());
			studentInfoDao.insert(studentinfo);
			
			credentialsentity credentialsentity = new credentialsentity();
			credentialsentity.setUserType("STUDENT");
			credentialsentity.setUsername(signupInput.getEmail());
			credentialsentity.setPassword(signupInput.getPassword());
			credentialsDao.insert(credentialsentity);
		}
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/api/v1/studentinfo/usn/{usn}")
	public studentinfo usn(@PathVariable String usn) {
		System.out.println(studentInfoRepository.findByUsn(usn).get(0).getName()); 
		return studentInfoRepository.findByUsn(usn).get(0);
	}
	
	@GetMapping("/api/v1/studentinfo/name/{name}")
	public studentinfo name(@PathVariable String name) {
		System.out.println(studentInfoRepository.findByName(name)); 
		return studentInfoRepository.findByName(name).get(0);
	}
	
	
//    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity uploadFile(@RequestParam MultipartFile file) {
//        return ResponseEntity.ok().build();
//    }
//    
//    @GetMapping("/test")
//	public void testImageUpload() {
//		java.io.File file = new java.io.File("C:\\Users\\mandl\\OneDrive\\Pictures\\Screenshots\\image.png");
//		com.google.api.services.drive.model.File file2  = driveService.upLoadFile(file.getName(), file.getAbsolutePath(),"image/png");
//		try {
//			System.err.println(file2.toPrettyString());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
    
    @PostMapping(value = "/upload/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity testFolder(@RequestParam MultipartFile file) throws IOException {

    	
		com.google.api.services.drive.model.File file2  = driveService.upLoadFileNew(convertMultiPartToFile(file),null);
		try {
			System.err.println(file2.toPrettyString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().build();
	}
    
    private java.io.File convertMultiPartToFile(MultipartFile file ) throws IOException {
        java.io.File convFile = new java.io.File( file.getOriginalFilename() );
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( file.getBytes() );
        fos.close();
        return convFile;
    }
}
