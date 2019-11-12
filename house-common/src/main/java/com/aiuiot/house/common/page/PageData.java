package com.aiuiot.house.common.page;
/**
 * 
 * @Title:  PageData.java   
 * @Package com.aiuiot.house.common.page   
 * @Description:    分页组件
 * @author: ZerOneth 
 * @date:   2019年6月22日 上午11:21:13   
 * @version V1.0
 */

import java.util.List;

public class PageData<T> {
	private List<T> list;//结果列表
	private Pagination pagination;	//分页类型
	
	public PageData(List<T> list, Pagination pagination){
		this.pagination = pagination;
		this.list = list;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	
	public static <T> PageData<T> buildPage(List<T> list, long count, Integer pageSize, Integer pageNum){
		Pagination pagination = new Pagination(pageSize, pageNum, count);
		return new PageData<T>(list, pagination);
	}
	
}
