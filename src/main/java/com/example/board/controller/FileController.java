package com.example.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.board.domain.vo.FileVO;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class FileController {
	
	@PostMapping(value="/upload", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody // 일반 컨트롤러에서 REST로 사용해야 할 때 작성 
	public ResponseEntity<List<FileVO>> upload(MultipartFile[] multipartFiles){
		log.info("upload.........");
		
		List<FileVO> files = new ArrayList<FileVO>();
		String uploadDirectory = "C:\\upload";
		String uploadDatePath = getDirectoryForm();
		
		File uploadPath = new File(uploadDirectory, uploadDatePath);
		log.info("upload path : " + uploadPath);
		
		if(uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		
		for(MultipartFile multipartFile : multipartFiles) {
			log.info("-----------------------");
			log.info("Upload File Name : " + multipartFile.getOriginalFilename());
			log.info("Upload File Size : " + multipartFile.getSize());
			
			FileVO fileVO = new FileVO();
			String originalFileName = multipartFile.getOriginalFilename();
			String fileName = null;
			fileVO.setFileName(originalFileName);
			
			UUID uuid = UUID.randomUUID();
			fileName = uuid.toString() + "_" + originalFileName;
			
			fileVO.setUuid(uuid.toString());
			fileVO.setFileName(originalFileName);
			fileVO.setUploadPath(uploadDatePath);
			
			try {
				File file = new File(uploadPath, fileName);
				// 해당 경로에 파일 업로드 
				multipartFile.transferTo(file);
				InputStream in = new FileInputStream(file);
				
				if(checkImageType(file)) {
					// 썸네일 작업 !! 
				}
			} catch(IllegalStateException e) {
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private String getDirectoryForm() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy\\MM\\dd");
		Date today = new Date();
		return simpleDateFormat.format(today);
	}
	
	private boolean checkImageType(File file) throws IOException {
		String contentType = Files.probeContentType(file.toPath());
		return contentType.startsWith("image");
	}
}
