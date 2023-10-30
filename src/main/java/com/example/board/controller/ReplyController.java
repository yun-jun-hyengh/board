package com.example.board.controller;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.domain.vo.Criteria;
import com.example.board.domain.vo.ReplyVO;
import com.example.board.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/replies/*")
@Log4j
public class ReplyController {
	
	private final ReplyService replyService;
	
	// 댓글등록
	@PostMapping(value="/new", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> create(@RequestBody ReplyVO replyVO) {
		log.info("create... : " + replyVO);
		return replyService.register(replyVO) ? new ResponseEntity<>("success", HttpStatus.OK) :
			new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// 게시글 댓글 전체 조회 
	// XML로도 보낼수 있고 JSON으로도 보낼 수 있다 !! 
	@GetMapping(value="/{bno}/{page}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<List<ReplyVO>> getList(@PathVariable("bno") Long bno,
			@PathVariable int page) {
		log.info("getList... : " + bno);
		return new ResponseEntity<>(replyService.findAllByBNO(new Criteria(page, 10), bno), HttpStatus.OK);
	}
	
	// 댓글 1개 조회 
	@GetMapping(value="/{rno}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<ReplyVO> reply(@PathVariable("rno") Long rno){
		return new ResponseEntity<>(replyService.findByRNO(rno), HttpStatus.OK);
	}
	
	// 댓글 삭제 
	@DeleteMapping(value="/{rno}", produces = MediaType.TEXT_PLAIN_VALUE)
	public String delete(@PathVariable Long rno) {
		return replyService.remove(rno) ? "success" : "fail";
	}
	
	// 댓글 수정 
	// PUT : 자원의 전체 수정, 자원 내 모든 필드를 전달해야 함
	// PATCH : 자원의 일부 수정, 수정할 필드만 전송 
	@PutMapping(value = "/{rno}", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public String modify(@PathVariable Long rno, @RequestBody ReplyVO replyVO) {
		replyVO.setRno(rno);
		return replyService.modify(replyVO) ? "success" : "fail";
	}
	
	
	
	
}
