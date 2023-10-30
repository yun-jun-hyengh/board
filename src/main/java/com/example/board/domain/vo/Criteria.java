package com.example.board.domain.vo;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;

@Data
public class Criteria {
	private int pageNum;
	private int amount;
	private String type;
	private String keyword;
	
	public Criteria() {
		this(1, 10);
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public Criteria(int pageNum, int amount, String type, String keyword) {
		this.pageNum = pageNum;
		this.amount = amount;
		this.type = type;
		this.keyword = keyword;
	}
	
	public String getParams() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
				.queryParam("pageNum", this.pageNum)
				.queryParam("type", this.type)
				.queryParam("keyword", this.keyword);
		return builder.toUriString();		
	}
	
	public String[] getTypes() {
		// "ABC".splie("") -> 3칸 배열([A][B][C])
		return type == null ? new String[] {} : type.split("");
	}
}
