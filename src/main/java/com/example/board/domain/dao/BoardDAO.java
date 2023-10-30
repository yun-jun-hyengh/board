package com.example.board.domain.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.board.domain.vo.BoardVO;
import com.example.board.domain.vo.Criteria;

@Repository
public interface BoardDAO {
	
	public void register(BoardVO board);
	public BoardVO get(Long bno);
	public boolean modify(BoardVO board);
	public boolean remove(Long bno);
	public List<BoardVO> getList(Criteria criteria);
	public int getTotal(Criteria criteria);
}
