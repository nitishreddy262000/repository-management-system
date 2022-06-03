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
import com.repository.repository.dao.StudentInfoRepository;
import com.repository.repository.dao.credentialsRepository;
import com.repository.repository.dao.credentialsentity;
import com.repository.repository.dao.studentinfo;
import com.repository.repository.entities.MyCredentials;
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
