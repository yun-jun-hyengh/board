package com.example.board.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.board.domain.vo.FileDTO;
import com.example.board.domain.vo.FileVO;

@Mapper
public interface FileMapper {
	// 첨부파일 추가 
	public void insert(FileDTO file);
}
