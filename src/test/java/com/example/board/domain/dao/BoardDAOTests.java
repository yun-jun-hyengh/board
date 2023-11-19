package com.example.board.domain.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.board.domain.vo.BoardVO;
import com.example.board.domain.vo.Criteria;

import lombok.extern.log4j.Log4j;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardDAOTests {
	
	@Autowired
	private BoardDAO boardDAO;
	
	@Test
	public void daoTest() {
		log.info(boardDAO);
	}
	
	@Test
	public void registerTest() {
		BoardVO boardVO = new BoardVO();
		boardVO.setTitle("새로운 게시글 제목");
		boardVO.setContent("새로운 게시글 내용");
		boardVO.setWriter("hds1234");
		
		//boardDAO.register(boardVO);
		log.info("생성된 게시글 번호 : " + boardVO.getBno());
	}
	
	@Test
	public void getTest() {
		BoardVO boardVO = boardDAO.get(8L);
		if(boardVO != null) {
			log.info(boardVO);
			return;
		}
		log.info("No Board");
	}
	
	@Test
	public void modifyTest() {
		BoardVO boardVO = boardDAO.get(7L);
		if(boardVO == null) {
			log.info("No board");
		}
		boardVO.setTitle("수정된 게시글 제목");
		boardVO.setContent("수정된 게시글 내용");
		
		if(boardDAO.modify(boardVO)) {
			log.info("update success");
			return;
		}
		log.info("update fail");
	}
	
	@Test
	public void removeTest() {
		BoardVO boardVO = boardDAO.get(2L);
		if(boardVO == null) {
			log.info("no board");
			return;
		}
		
		if(boardDAO.remove(boardVO.getBno())) {
			log.info("remove success");
			return;
		}
		log.info("remove fail");
	}
	
	
	@Test
	public void getListTest() {
		boardDAO.getList(new Criteria()).forEach(log::info);
	}
	
}
