package com.example.board.domain.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.board.domain.vo.BoardDTO;
import com.example.board.domain.vo.BoardVO;
import com.example.board.domain.vo.Criteria;
import com.example.board.mapper.BoardMapper;

@Repository
public class BoardDAOImpl implements BoardDAO {
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Override
	public void register(BoardDTO board) {
		// TODO Auto-generated method stub
		boardMapper.insert(board);
	}

	@Override
	public BoardVO get(Long bno) {
		// TODO Auto-generated method stub
		return boardMapper.read(bno);
	}

	@Override
	public boolean modify(BoardVO board) {
		// TODO Auto-generated method stub
		return boardMapper.update(board) != 0;
	}

	@Override
	public boolean remove(Long bno) {
		// TODO Auto-generated method stub
		return boardMapper.delete(bno) != 0;
	}

	@Override
	public List<BoardVO> getList(Criteria criteria) {
		// TODO Auto-generated method stub
		return boardMapper.getList(criteria);
	}

	@Override
	public int getTotal(Criteria criteria) {
		return boardMapper.getTotal(criteria);
	}
	

}
