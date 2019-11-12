package com.aiuiot.house.common.model;

/**
 * 
 * @Title: Community.java
 * @Package com.aiuiot.house.common.model
 * @Description: 小区
 * @author: ZerOneth
 * @date: 2019年6月22日 上午11:17:42
 * @version V1.0
 */
public class Community {

	private Integer id;
	private String cityCode;
	private String cityName;
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Community [id=" + id + ", cityCode=" + cityCode + ", cityName=" + cityName + ", name=" + name + "]";
	}

	
	
}
