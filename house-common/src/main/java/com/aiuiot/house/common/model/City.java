package com.aiuiot.house.common.model;

/**
 * 
 * @Title: City.java
 * @Package com.aiuiot.house.common.model
 * @Description: 城市
 * @author: ZerOneth
 * @date: 2019年6月22日 下午4:02:10
 * @version V1.0
 */
public class City {
	private Integer id;
	private String cityName;
	private String cityCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

}
