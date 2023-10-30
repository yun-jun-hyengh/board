package com.example.board.service;

import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.board.domain.vo.Criteria;
import com.example.board.domain.vo.ReplyVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyServiceTests {
	
private Long[] arBno = {13362L,13361L,13342L,13341L,13326L};
	
	@Autowired
	private ReplyService replyService;
	
	@Test
	public void serviceTest() {
		log.info(replyService);
	}
	
	@Test
	public void insertTest() {
		// 5개의 게시글에 2개의 게시글 달기 
		IntStream.rangeClosed(21, 30).forEach(i->{
			ReplyVO replyVO = new ReplyVO();
			replyVO.setBno(arBno[i % 5]);
			replyVO.setReply("댓글 테스트 " + i);
			replyVO.setReplier("작성자 " + i);
			
			replyService.register(replyVO);
		});
	}
	
	@Test
	public void testSelect() {
		log.info(replyService.findByRNO(40L));
	}
	
	@Test
	public void deleteTest() {
		log.info(replyService.remove(40L));
	}
	
	@Test
	public void removeAllByBNO_test() {
		log.info(replyService.removeAllByBNO(13361L));
	}
	
	@Test
	public void modifyTest() {
		ReplyVO replyVO = replyService.findByRNO(39L);
		replyVO.setReply("수정된 댓글 테스트2");
		log.info(replyService.modify(replyVO) + "건 수정");
	}
	
	
	@Test
	public void findByAllBNO_test() {
		replyService.findAllByBNO(new Criteria(),13341L).forEach(log::info);
	}
}
