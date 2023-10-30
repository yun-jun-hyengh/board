package com.example.board.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.board.domain.vo.BoardVO;
import com.example.board.domain.vo.Criteria;
import com.example.board.domain.vo.PageDTO;
import com.example.board.service.BoardService;

import lombok.extern.log4j.Log4j;

/*
 * Task			URL			      Method			Parameter		Form 		URL이동
 * 전체목록	  /board/list		    GET									
 * 등록처리     /board/register       POST				모든항목			입력화면필요	이동
 * 조회       /board/read            GET				BNO							
 * 삭제처리    /board/remove          GET				BNO				입력화면		이동
 * 수정처리    /board/modify          POST				모든항목			입력화면		이동
 */

@Controller
@Log4j
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/list")
	public void list(Model model, Criteria criteria) {
		model.addAttribute("boardList", boardService.getList(criteria));
		model.addAttribute("pageDTO", new PageDTO(boardService.getTotal(criteria), criteria));
	}
	
	@GetMapping("/register")
	public void register() { // 글쓰기 페이지로 이동 
		
	}
	
	@PostMapping("/register")
	public String register(BoardVO boardVO, RedirectAttributes rttr) {
		log.info("/register : " + boardVO);
		boardService.register(boardVO);
		
		// Flash라는 영역은 Session에 생기고, redirect로 전송할 때 request영역이 초기화 된다
		// 초기화 되기 전에 Flash영역에 데이터를 저장해놓고, 초기화된 후 Flash 영역에서 
		// 데이터를 가지고 온다 
		// 데이터를 가져왔다면, Flash 영역에 있던 데이터는 없어진다 
		rttr.addFlashAttribute("bno", boardVO.getBno());
		
		// redirect로 전송할 때에는 경로 앞에 redirect:를 붙여준다 
		return "redirect:/board/list";
	}
	
	@GetMapping({"/read","/modify"})
	public void read(Criteria criteria, Long bno, HttpServletRequest request, Model model) {
		String url = request.getRequestURI();
		log.info(url.substring(url.lastIndexOf("/")) + " : " + bno);
		model.addAttribute("board", boardService.get(bno));
	}
	
	@GetMapping("/remove")
	public String remove(Long bno, RedirectAttributes rttr) {
		log.info("/remove : " + bno);
		if(boardService.remove(bno)) {
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list";
	}
	
	@PostMapping("/modify")
	public String modify(Criteria criteria, BoardVO boardVO, RedirectAttributes rttr) {
		log.info("/modify : " + boardVO);
		if(boardService.modify(boardVO)) {
			rttr.addFlashAttribute("result","success");
		}
		return "redirect:/board/list" + criteria.getParams();
	}
	
	
	
	
	
}
