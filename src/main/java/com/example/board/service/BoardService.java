package com.example.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.board.domain.vo.BoardVO;
import com.example.board.domain.vo.Criteria;

@Service
public interface BoardService {
	// 게시글 등록
	public void register(BoardVO board);
	// 특정 게시글 가져오기
	public BoardVO get(Long bno);
	// 게시글 수정
	public boolean modify(BoardVO board);
	// 게시글 삭제
	public boolean remove(Long bno);
	// 전체 게시글 가져오기 
	public List<BoardVO> getList(Criteria criteria);
	// 전체 게시글 개수 
	public int getTotal(Criteria criteria);
}
