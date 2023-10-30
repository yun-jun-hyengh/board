package com.example.board.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageDTO {
	// 현재 페이지를 기준으로 시작 페이지
	private int startPage;
	// 헌제 페이지를 기준으로 마지막 페이지 
	private int endPage;
	// 실제 가장 마지막 페이지를 표시 
	private int realEnd;
	// 이전버튼과 다음버튼의 유무 검사 
	private boolean prev, next;
	// 전체 게시글 수 
	private int total; 
	private Criteria criteria;
	
	public PageDTO(int total, Criteria criteria) {
		this.total = total;
		this.criteria = criteria;
		
		// ceil(실수 값) : 올림 처리, 페이지에 게시글이 한 개라도 있다면 
		// 올림을 하여 해당 페이지를 표시하기 위함
		this.endPage = (int)(Math.ceil(criteria.getPageNum() / (double)criteria.getAmount())) * criteria.getAmount();
		// endPage에서 - 9를 하면 startPage를 구할 수 있다
		this.startPage = endPage - (criteria.getAmount() - 1);
		// 실제 마지막 페이지는 전체 게시글 개수에서 amount를 나눠주게 된다 
		// 하지만 1개의 게시글이 있더라도 페이지를 표현해야 하기 때문에
		// 실수로 나누어 준 다음 소수점을 올림하여 페이지를 + 1 해준다 
		this.realEnd = (int)Math.ceil(total * 1.0 / criteria.getAmount());
		
		// endPage는 10의 배수이기 때문에 realEnd보다 더 커지면 realEnd를 넣어준다 
		if(this.realEnd < this.endPage) {
			this.endPage = this.realEnd;
		}
		// 이전 단위가 있는 경우 
		this.prev = this.startPage > 1;
		// 다음 단위가 있는 경우 
		this.next = this.endPage < this.realEnd;
	}
	
	
}
