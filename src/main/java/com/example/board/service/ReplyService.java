package com.example.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.board.domain.vo.Criteria;
import com.example.board.domain.vo.ReplyVO;

@Service
public interface ReplyService {
	public boolean register(ReplyVO vo);
	public ReplyVO findByRNO(Long rno);
	public boolean remove(Long rno);
	public boolean removeAllByBNO(Long bno);
	public boolean modify(ReplyVO replyVO);
	public List<ReplyVO> findAllByBNO(Criteria criteria, Long bno);
}
