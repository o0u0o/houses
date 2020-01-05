package com.aiuiot.house.common.model;

import lombok.Data;

/**
 * 
 * @Title: City.java
 * @Package com.aiuiot.house.common.model
 * @Description: 城市实体
 * @author: ZerOneth
 * @date: 2019年6月22日 下午4:02:10
 * @version V1.0
 */
@Data
public class City {

	/**
	 * 主键ID
	 */
	private Integer id;

	/**
	 * 城市名
	 */
	private String cityName;

	/**
	 * 生成代码
	 */
	private String cityCode;

}
