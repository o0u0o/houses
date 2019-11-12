package com.aiuiot.house.common.page;

import java.util.List;

import com.google.common.collect.Lists;

//import org.assertj.core.util.Lists;
/**
 * 
 * @Title: Pagination.java
 * @Package com.aiuiot.house.common.page
 * @Description: TODO(用一句话描述该文件做什么)
 * @author: ZerOneth
 * @date: 2019年6月22日 上午11:24:55
 * @version V1.0
 */
public class Pagination {
	private int pageNum; // 第几页
	private int pageSize; // 每页大小
	private long totalCount;	//总数量
	private List<Integer> pages = Lists.newArrayList();
	
	public Pagination(Integer pageSize, Integer pageNum, Long totalCount) {
		this.pageSize = pageSize;
		this.pageNum = pageNum;
		this.totalCount = totalCount;
		for(int i = 1; i <= pageNum; i++) {
			pages.add(i);
		}
		
		Long pageCount = totalCount/pageSize + ((totalCount % pageSize == 0)?0 : 1);
		if(pageCount > pageNum) {
			for (int i = pageNum + 1; i <  pageCount; i++) {
				pages.add(i);
			}
		}
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public List<Integer> getPages() {
		return pages;
	}

	public void setPages(List<Integer> pages) {
		this.pages = pages;
	}

}
