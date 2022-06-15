package com.repository.repository.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.core.io.InputStreamResource;
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
import com.repository.repository.dao.ReviewDao;
import com.repository.repository.dao.ReviewEntity;
import com.repository.repository.dao.ReviewId;
import com.repository.repository.dao.StudentInfoDao;
import com.repository.repository.dao.StudentInfoRepository;
import com.repository.repository.dao.credentialsRepository;
import com.repository.repository.dao.credentialsentity;
import com.repository.repository.dao.studentinfo;
import com.repository.repository.entities.AllStudentsInfo;
import com.repository.repository.entities.CredentialsInput;
import com.repository.repository.entities.CredentialsOutput;
import com.repository.repository.entities.ReviewInput;
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
	@Autowired
	private ReviewDao reviewDao;
	
	@PostMapping("/api/v1/login")
	public ResponseEntity<CredentialsOutput> login(@RequestBody CredentialsInput loginCredentials) {
		
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
		return ResponseEntity.ok().body(credentialsOutput);

	
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
		System.out.println("Signup successful");
		return ResponseEntity.ok().build();
		//.header("Access-Control-Allow-Origin", "*")
	}
	
	@GetMapping("/api/v1/studentinfo/all")
	public AllStudentsInfo getAllStudents() {
		List<studentinfo> suList =  studentInfoRepository.findAll();
		AllStudentsInfo allStudentsInfo = new AllStudentsInfo();
		allStudentsInfo.setStudentsList(suList);
		
		return allStudentsInfo;
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
	
	
	@PostMapping("/api/v1/review")
	public ResponseEntity<?> giveReview(@RequestBody ReviewInput reviewInput) {
		System.out.println("HIII");
		ReviewEntity reviewEntity = new ReviewEntity();
		reviewEntity.setReviewId(new ReviewId(reviewInput.getUsn(), reviewInput.getBatch()));
		reviewEntity.setMarks(reviewInput.getMarks());
		reviewEntity.setReview(reviewInput.getReview());
		reviewDao.insert(reviewEntity);
		return ResponseEntity.ok().build();
		
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
    	System.out.println("upload file");
    	
		com.google.api.services.drive.model.File file2  = driveService.upLoadFileNew(convertMultiPartToFile(file),null);
		try {
			System.err.println(file2.toPrettyString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().build();
	}
    
    
    @GetMapping("/view/files")
	public List<String> view() throws IOException {
		return driveService.viewFiles();
	}
    
    @GetMapping("/view/file/{filename}")
//    @Produces(MediaType.APPLICATION_OCTET_STREAM)
	public OutputStream download(@RequestParam String fileName) throws IOException {
    	int x = 1;
//		OutputStream outputStream = driveService.getFile(fileName);
//		InputStreamResource inputStreamResource = new InputStreamResource(outputStream);
//		return new ResponseEntity(inputStreamResource, headers, HttpStatus.OK);
//		return outputStream;
    	return null;
	}
    
//    @GetMapping("/download")
//    public void downlString() throws IOException {
//    	final String path = "some_150_mb_file";
//    	
//    	int x = 1;
//    	byte[] outputStream = driveService.getFile("1QI73kpEb9wECp5gKlKh_6n9x-GQ7QFqw");
//    	byte[] buffer = new byte[8192];
//    	int bytesRead = -1;
//    	
//    	outputStream.close();
//    	
//    	 InputStream is = new FileInputStream(path);
//         ByteArrayOutputStream baos = new ByteArrayOutputStream();
//         int len;
//         byte[] buffer = new byte[4096];
//         while ((len = is.read(buffer, 0, buffer.length)) != -1) {
//             baos.write(buffer, 0, len);
//         }
//         System.out.println("Server size: " + baos.size());
//         return Response.ok(baos).build();
//    	
//    }
    
    private java.io.File convertMultiPartToFile(MultipartFile file ) throws IOException {
        java.io.File convFile = new java.io.File( file.getOriginalFilename() );
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( file.getBytes() );
        fos.close();
        return convFile;
    }
}
