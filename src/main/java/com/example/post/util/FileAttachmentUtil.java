package com.example.post.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class FileAttachmentUtil {
	    // 파일 업로드
	    public static String uploadFile(MultipartFile file, String uploadPath) {
	        try {
	            if (!new File(uploadPath).exists()) {
	                new File(uploadPath).mkdirs(); //mkdirs: 경로
	            }
	            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
	            				// UUID : 중복 될 수 없는 난수를 랜덤 생성
	            File savedFile = new File(uploadPath, fileName);
	            file.transferTo(savedFile);

	            return fileName;
	        } catch (IOException e) {
	            throw new RuntimeException("파일 업로드 중 오류가 발생했습니다: " + e.getMessage());
	        }
	    }

	    // 파일 삭제
	    public static void deleteFile(String filePath) {
	        File file = new File(filePath);
	        if (file.exists()) {
	            file.delete();
	        }
	    }
	}

