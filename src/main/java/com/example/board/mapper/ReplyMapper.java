package com.example.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.board.domain.vo.Criteria;
import com.example.board.domain.vo.ReplyVO;

@Mapper
public interface ReplyMapper {
	public int insert(ReplyVO vo);
	public ReplyVO select(Long rno);
	public int delete(Long rno);
	public int deleteAll(Long bno);
	public int update(ReplyVO replyVO);
	public List<ReplyVO> selectAll(@Param("cri") Criteria criteria, @Param("bno") Long bno);
}
