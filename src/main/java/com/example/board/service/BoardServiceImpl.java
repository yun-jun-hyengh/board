package com.example.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.board.domain.dao.BoardDAO;
import com.example.board.domain.dao.FileDAO;
import com.example.board.domain.vo.BoardDTO;
import com.example.board.domain.vo.BoardVO;
import com.example.board.domain.vo.Criteria;
import com.example.board.domain.vo.FileDTO;
import com.example.board.domain.vo.FileVO;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO boardDAO;
	
	@Autowired
	private FileDAO fileDAO;
	
	@Override
	public void register(BoardDTO boardDTO) {
		boardDAO.register(boardDTO);
		for(FileVO file : boardDTO.getFiles()) {
			FileDTO fileDTO = new FileDTO();
			fileDTO.setUuid(file.getUuid());
			fileDTO.setUploadPath(file.getUploadPath());
			fileDTO.setFileName(file.getFileName());
			fileDTO.setFileType(file.isFileType());
			fileDTO.setBno(boardDTO.getBno());
			
			fileDAO.register(fileDTO);
		}
	}

	@Override
	public BoardVO get(Long bno) {
		return boardDAO.get(bno);
	}

	@Override
	public boolean modify(BoardVO board) {
		return boardDAO.modify(board);
	}

	@Override
	public boolean remove(Long bno) {		
		return boardDAO.remove(bno);
	}

	@Override
	public List<BoardVO> getList(Criteria criteria) {		
		return boardDAO.getList(criteria);
	}

	@Override
	public int getTotal(Criteria criteria) {
		return boardDAO.getTotal(criteria);
	}

	@Override
	public List<FileVO> getFiles(Long bno) {
		return boardDAO.getFiles(bno);
	}
	

}
