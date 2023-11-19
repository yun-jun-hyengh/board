package com.example.board.domain.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.board.domain.vo.FileDTO;
import com.example.board.mapper.FileMapper;

@Repository
public class FileDAO {
	@Autowired
	public FileMapper fileMapper;
	
	public void register(FileDTO file) {
		fileMapper.insert(file);
	}
	
}
