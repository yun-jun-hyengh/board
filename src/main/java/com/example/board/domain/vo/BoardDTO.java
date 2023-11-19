package com.example.board.domain.vo;

import java.util.List;

import lombok.Data;

@Data
public class BoardDTO {
	private Long bno;
	private String title;
	private String content;
	private String writer;
	private String regDate;
	private String updateDate;
	private List<FileVO> files;
}
