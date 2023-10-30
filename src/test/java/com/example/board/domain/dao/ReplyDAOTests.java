package com.example.board.domain.dao;

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
public class ReplyDAOTests {
	
	private Long[] arBno = {13362L,13361L,13342L,13341L,13326L};
	
	@Autowired
	private ReplyDAO replyDAO;
	
	@Test
	public void mapperTest() {
		log.info(replyDAO);
	}
	
	@Test
	public void insertTest() {
		// 5개의 게시글에 2개의 게시글 달기 
		IntStream.rangeClosed(11, 20).forEach(i->{
			ReplyVO replyVO = new ReplyVO();
			replyVO.setBno(arBno[i % 5]);
			replyVO.setReply("댓글 테스트 " + i);
			replyVO.setReplier("작성자 " + i);
			
			replyDAO.register(replyVO);
		});
	}
	
	@Test
	public void testSelect() {
		log.info(replyDAO.findByRNO(11L));
	}
	
	@Test
	public void deleteTest() {
		log.info(replyDAO.remove(11L));
	}
	
	@Test
	public void removeAllByBNO_test() {
		log.info(replyDAO.removeAllByBNO(13361L));
	}
	
	@Test
	public void modifyTest() {
		ReplyVO replyVO = replyDAO.findByRNO(23L);
		replyVO.setReply("수정된 댓글 테스트2");
		log.info(replyDAO.modify(replyVO) + "건 수정");
	}
	
	
	@Test
	public void findByAllBNO_test() {
		replyDAO.findAllByBNO(new Criteria(),13362L).forEach(log::info);
	}
	
	
}
