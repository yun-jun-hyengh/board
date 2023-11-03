package com.example.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.board.domain.dao.ReplyDAO;
import com.example.board.domain.vo.Criteria;
import com.example.board.domain.vo.ReplyVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
	
	private final ReplyDAO replyDAO;

	@Override
	public boolean register(ReplyVO vo) {
		// TODO Auto-generated method stub
		return replyDAO.register(vo);
	}

	@Override
	public ReplyVO findByRNO(Long rno) {
		// TODO Auto-generated method stub
		return replyDAO.findByRNO(rno);
	}

	@Override
	public boolean remove(Long rno) {
		// TODO Auto-generated method stub
		return replyDAO.remove(rno);
	}

	@Override
	public boolean removeAllByBNO(Long bno) {
		// TODO Auto-generated method stub
		return replyDAO.removeAllByBNO(bno);
	}

	@Override
	public boolean modify(ReplyVO replyVO) {
		// TODO Auto-generated method stub
		return replyDAO.modify(replyVO);
	}

	@Override
	public List<ReplyVO> findAllByBNO(Criteria criteria,Long bno) {
		// TODO Auto-generated method stub
		return replyDAO.findAllByBNO(criteria, bno);
	}

	@Override
	public int getTotal(Long bno) {
		return replyDAO.getTotal(bno);
	}
	
}
