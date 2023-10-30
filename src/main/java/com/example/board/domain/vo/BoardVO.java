package com.example.board.domain.vo;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class BoardVO {
	private Long bno;
	private String title;
	private String content;
	private String writer;
	private String regDate;
	private String updateDate;
}
