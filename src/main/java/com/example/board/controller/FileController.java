package com.example.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.board.domain.vo.FileVO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class FileController {
	
	@PostMapping(value="/upload", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody // 일반 컨트롤러에서 REST로 사용해야할 때 작성
	public ResponseEntity<List<FileVO>> upload(MultipartFile[] multipartFiles){
		log.info("upload.............");
		
		List<FileVO> files = new ArrayList<FileVO>();
		String uploadDirectory = "C:/upload";
		String uploadDatePath = getDirectoryForm();
		
		File uploadPath = new File(uploadDirectory, uploadDatePath);
		log.info("upload path : " + uploadPath);
		
		if(!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		
		for (MultipartFile multipartFile : multipartFiles) {
			log.info("--------------------");
			log.info("Upload File Name : " + multipartFile.getOriginalFilename());
			log.info("Upload File Size : " + multipartFile.getSize());
			
			FileVO fileVO = new FileVO();
			String originalFileName = multipartFile.getOriginalFilename();
			String fileName = null;
			
			// random한 UUID를 생성하여 원본 이름과 같은 파일들이 다른 이름으로 생성되도록 한다
			UUID uuid = UUID.randomUUID();
			fileName = uuid.toString() + "_" + originalFileName;
			
			fileVO.setUuid(uuid.toString());
			fileVO.setFileName(originalFileName);
			fileVO.setUploadPath(uploadDatePath);
			
			try {
				File file = new File(uploadPath, fileName);
				//해당 경로에 파일 업로드
				multipartFile.transferTo(file);
				
				InputStream in = new FileInputStream(file);
				
				if(checkImageType(file)) { //업로드된 파일이 이미지라면(썸네일 처리) 
					fileVO.setFileType(true);
					FileOutputStream out = new FileOutputStream(new File(uploadPath, "t_" + fileName));
					Thumbnailator.createThumbnail(in, out, 100, 100);
					in.close();
					out.close();
				}
				
				files.add(fileVO);
				
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<List<FileVO>>(files, HttpStatus.OK);
	}
	
	private String getDirectoryForm() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date today = new Date();
		return simpleDateFormat.format(today);
	}
	
	private boolean checkImageType(File file) throws IOException{
		String contentType = Files.probeContentType(file.toPath());
		return contentType.startsWith("image");
	}
	
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> display(String fileName){
		log.info("file name : " + fileName);
		
		File file = new File("C:/upload/" + fileName); 
		log.info("file : " + file);
		
		ResponseEntity<byte[]> result = null;
		HttpHeaders header = new HttpHeaders();
		try {
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@GetMapping(value = "/download", produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> download(String fileName) {
		Resource resource = new FileSystemResource("C:/upload/" + fileName);
		
		if(!resource.exists()) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		HttpHeaders header = new HttpHeaders();
		
		String resourceName = resource.getFilename();
		resourceName = resourceName.substring(resourceName.indexOf("_") + 1);
		
		try {
			header.add("Content-Disposition", "attachment; filename=" + new String(resourceName.getBytes("UTF-8"), "ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}
	
	@PostMapping("/deleteFile")
	@ResponseBody
	public void delete(@RequestParam("fileName") String fileName, @RequestParam("fileType") boolean fileType) {
		File file = null;
		try {
			file = new File("C:/upload/" + URLDecoder.decode(fileName, "UTF-8"));
			
			// Garbage Collection 수행을 명령하는 메소드 
			// GC가 발생하면 소멸의 대상이 되는 인스턴스는 결정되지만 이것이 실제 소멸로 
			// 바로 이어지지는 않는다. 왜나면 인스턴스의 실제 소멸로 이어지지 않은 상태에서 
			// 프로그램이 종료될 수 도 있다. 
			// 종료가 되면 어차피 인스턴스는 소멸 되기 때문
			System.gc();
			
			// GC에 의해서 소멸이 결정된 인스턴스를 즉시 소멸시키는 메소드
			System.runFinalization();
			
			file.delete();
			if(fileType) {
				file = new File(file.getPath().replace("t_", ""));
				file.delete();
			}
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
}

