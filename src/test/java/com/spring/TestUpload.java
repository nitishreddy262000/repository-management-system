//package com.spring;
//
//import java.io.File;
//import java.io.IOException;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.spring.service.GoogleDriveService;
//
//@RunWith(SpringRunner.class)
//@SpringBootApplication
//public class TestUpload {	
//	@Autowired
//	GoogleDriveService driveService;
//	
//	@Test
//	public void testUpload() {
//		//File file = new File("C:\\Users\\ttlang\\Downloads\\pic\\hinh.png");
//		File file = new File("C:\\Users\\mandl\\OneDrive\\Pictures\\Screenshots");
//		com.google.api.services.drive.model.File file2  = driveService.upLoadFile(file.getName(), file.getAbsolutePath(),"image.png");
//		try {
//			System.err.println(file2.toPrettyString());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}
//	
//	
//
//}
