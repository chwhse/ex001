package com.dgit.domain;

public class Criteria {
	private int page = 1;
	private int perPageNum = 10;
	
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		if(page < 0 ){
			this.page = 1;
			return;
		}
		this.page = page;
	}
	
	public int getPerPageNum() {
		return perPageNum;
	}
	
	public void setPerPageNum(int perPageNum) {
		if(perPageNum <= 0 || perPageNum > 100){
			this.perPageNum = 10;
			return;
		}
		this.perPageNum = perPageNum;
	}
	
	public int getPageStart(){	// 해당 페이지 번호의 첫번째 게시물 인덱스 번호!
		return (this.page-1) * this.perPageNum; 
	}

	@Override
	public String toString() {
		return "Criteria page=" + page + ", perPageNum=" + perPageNum ;
	}
	
	
}
